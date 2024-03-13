/*
 * SPDX-FileCopyrightText: 2011 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.knowledge.service.encryption.pbe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.top_logic.base.security.attributes.PersonAttributes;
import com.top_logic.base.security.device.db.DBUserRepository;
import com.top_logic.base.security.util.SignatureService;
import com.top_logic.base.security.util.SignatureService.Module;
import com.top_logic.base.user.UserInterface;
import com.top_logic.basic.CalledFromJSP;
import com.top_logic.basic.Logger;
import com.top_logic.basic.StringServices;
import com.top_logic.basic.config.ApplicationConfig;
import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.db.schema.setup.SchemaSetup;
import com.top_logic.basic.encryption.EncryptionService;
import com.top_logic.basic.module.ModuleException;
import com.top_logic.basic.module.ModuleUtil;
import com.top_logic.basic.sql.ConnectionPoolRegistry;
import com.top_logic.basic.sql.PooledConnection;
import com.top_logic.basic.util.Computation;
import com.top_logic.basic.util.DBOperation;
import com.top_logic.basic.version.Version;
import com.top_logic.dob.DataObject;
import com.top_logic.dob.DataObjectException;
import com.top_logic.dob.ex.UnknownTypeException;
import com.top_logic.dob.meta.MORepository;
import com.top_logic.knowledge.objects.meta.DefaultMOFactory;
import com.top_logic.knowledge.service.KBUtils;
import com.top_logic.knowledge.service.KnowledgeBaseConfiguration;
import com.top_logic.knowledge.service.KnowledgeBaseFactory;
import com.top_logic.knowledge.service.KnowledgeBaseFactoryConfig;
import com.top_logic.knowledge.service.KnowledgeBaseRuntimeException;
import com.top_logic.knowledge.service.PersistencyLayer;
import com.top_logic.util.DeferredBootUtil;
import com.top_logic.util.TLContext;

/**
 * JSP model class for the initial application setup.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class ApplicationSetup {

	private enum Phase {
		KEY_INPUT, PASSWORD_INPUT, USER_SETUP, FINISHED
	}

	private final ServletContext _application;

	private final HttpServletRequest _request;

	private final HttpServletResponse _response;

	private Phase _phase;

	private String _applicationKey = "";

	private String _newPassword = "";

	private String _passwordTwice = "";

	private boolean _errorInvalidKey;

	private boolean _errorPasswordMissmatch;

	private boolean _errorInvalidPassword;

	private boolean _errorInternal;

	private boolean _isSetup;

	private Map<String, Account> _accounts;

	/**
	 * Creates a {@link ApplicationSetup}.
	 */
	@CalledFromJSP
	public ApplicationSetup(ServletContext application, HttpServletRequest request, HttpServletResponse response) {
		_application = application;
		_request = request;
		_response = response;

		try {
			process();
		} catch (InvalidPasswordException ex) {
			Logger.info("Invalid application password.", ApplicationSetup.class);
			_errorInvalidPassword = true;
		} catch (SQLException ex) {
			Logger.error("Database error: " + ex.getMessage(), ApplicationSetup.class);
			_errorInternal = true;
		} catch (ModuleException ex) {
			Logger.error("Module error: " + ex.getMessage(), ApplicationSetup.class);
			_errorInternal = true;
		} catch (Exception ex) {
			Logger.error("Internal error: " + ex.getMessage(), ApplicationSetup.class);
			_errorInternal = true;
		}
	}

	private void process() throws Exception {
		if (!DeferredBootUtil.isBootPending()) {
			redirect("");
			phase(Phase.FINISHED);
			return;
		}

		_isSetup = !ApplicationPasswordUtil.hasPassword();

		phase(Phase.KEY_INPUT);
		_applicationKey = param("key");

		if (StringServices.isEmpty(_applicationKey)) {
			return;
		}
		
		if (!ApplicationPasswordUtil.checkApplicationKey(_applicationKey)) {
			_errorInvalidKey = true;
			return;
		}

		_newPassword = param("newPassword");
		_passwordTwice = param("passwordTwice");
		phase(Phase.PASSWORD_INPUT);

		if (StringServices.isEmpty(_newPassword)) {
			return;
		}
		
		if (!_newPassword.equals(_passwordTwice)) {
			_errorPasswordMissmatch = true;
			return;
		}
		
		if (!_isSetup) {
			String oldPassword = param("oldPassword");
			ApplicationPasswordUtil.changePassword(oldPassword, _newPassword);

			redirect("/applicationPassword.jsp");
			phase(Phase.FINISHED);
			return;
		}
		
		phase(Phase.USER_SETUP);
		
		final DBUserRepository userRepository = newDBUserRepository();
		final List<UserInterface> userDatas =
			TLContext.inSystemContext(this.getClass(), new Computation<List<UserInterface>>() {
			@Override
			public List<UserInterface> run() {
				try {
					return userRepository.getAllUsers(ConnectionPoolRegistry.getDefaultConnectionPool());
				} catch (SQLException ex) {
					return Collections.emptyList();
				}
			}
		});
		
		boolean missingPassword = false;
		_accounts = new LinkedHashMap<>();
		for (DataObject userData : userDatas) {
			Account user = new Account((String) userData.getAttributeValue(PersonAttributes.USER_NAME));

			user.setPassword(param("password-" + user.getId()));
			user.setPasswordTwice(param("passwordTwice-" + user.getId()));

			if (user.hasPassword()) {
				if (!user.hasMatchingPasswords()) {
					_errorPasswordMissmatch = true;
				}
			} else {
				missingPassword = true;
			}

			_accounts.put(user.getId(), user);
		}

		if (missingPassword || errorPasswordMissmatch()) {
			return;
		}

		TLContext.inSystemContext(ApplicationSetup.class, new DBOperation() {
			@Override
			protected void update(PooledConnection connection) throws ModuleException, InvalidPasswordException, SQLException {
				{
					installKey(connection);
					setupUserPasswords(connection);
				}
			}

			private void installKey(PooledConnection connection) throws ModuleException, InvalidPasswordException,
					SQLException {
				ApplicationPasswordUtil.setupPassword(connection, getNewPassword());

				// Starting the encryption service requires the encryption key to be set up.
				connection.commit();
			}

			private void setupUserPasswords(PooledConnection connection)
					throws ModuleException, InvalidPasswordException,
					DataObjectException, SQLException {

				// Temporarily start the encryption service to be able to sign passwords.
				PasswordBasedEncryptionService.startUp(getNewPassword().toCharArray());
				try {
					ModuleUtil modules = ModuleUtil.INSTANCE;
					Module signatureModule = SignatureService.Module.INSTANCE;
					modules.startUp(signatureModule);
					try {
						SignatureService signatureService = SignatureService.getInstance();
			
						for (DataObject userData : userDatas) {
							String userName = (String) userData.getAttributeValue(PersonAttributes.USER_NAME);
							Account user = getAccount(userName);
							String password = user.getPassword();
							String signedPassword = signatureService.sign(password);
							userData.setAttributeValue(PersonAttributes.PASSWORD, signedPassword);
			
							userRepository.updateUser(connection, userData);
						}
					} finally {
						modules.shutDown(signatureModule);
					}
				} finally {
					EncryptionService.stop();
				}
			}
		}).reportProblem();

		redirect("/applicationPassword.jsp");
		phase(Phase.FINISHED);
	}

	private DBUserRepository newDBUserRepository() throws UnknownTypeException, ConfigurationException {
		ApplicationConfig instance = ApplicationConfig.getInstance();
		KnowledgeBaseFactoryConfig kbfConfig =
			(KnowledgeBaseFactoryConfig) instance.getServiceConfiguration(KnowledgeBaseFactory.class);
		KnowledgeBaseConfiguration defaultKBConfiguration =
			kbfConfig.getKnowledgeBases().get(PersistencyLayer.DEFAULT_KNOWLEDGE_BASE_NAME);
		if (defaultKBConfiguration == null) {
			throw new KnowledgeBaseRuntimeException(
				"No KnowledgeBaseConfiguration with name " + PersistencyLayer.DEFAULT_KNOWLEDGE_BASE_NAME + " found.");
		}
		SchemaSetup schemaConfig = KBUtils.getSchemaConfigResolved(defaultKBConfiguration);
		MORepository repository = schemaConfig.createMORepository(DefaultMOFactory.INSTANCE);
		return new DBUserRepository(repository);
	}

	private void redirect(String location) throws IOException {
		_response.sendRedirect(_request.getContextPath() + location);
	}

	private String param(String parameter) {
		return StringServices.nonNull(_request.getParameter(parameter));
	}

	/**
	 * Information about an account during initial setup.
	 * 
	 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
	 */
	public static class Account {

		private final String _id;

		private String _password = "";

		private String _passwordTwice = "";

		/**
		 * Creates a {@link Account}.
		 * 
		 * @param id
		 *        See {@link #getId()}.
		 */
		Account(String id) {
			_id = id;
		}

		/**
		 * The login ID.
		 */
		@CalledFromJSP
		public String getId() {
			return _id;
		}

		/**
		 * Whether a {@link #getPassword()} was provided.
		 */
		boolean hasPassword() {
			return !StringServices.isEmpty(_password);
		}

		/**
		 * Whether {@link #getPassword()} and {@link #getPasswordTwice()} match.
		 */
		boolean hasMatchingPasswords() {
			return StringServices.equals(_password, _passwordTwice);
		}

		/**
		 * The entered initial password.
		 */
		String getPassword() {
			return _password;
		}

		/** @see #getPassword() */
		void setPassword(String password) {
			_password = password;
		}

		/**
		 * The retyped {@link #getPassword()}.
		 */
		String getPasswordTwice() {
			return _passwordTwice;
		}

		/** @see #getPasswordTwice() */
		void setPasswordTwice(String passwordTwice) {
			_passwordTwice = passwordTwice;
		}

	}

	/**
	 * The name of the application being set up.
	 */
	@CalledFromJSP
	public String getApplicationName() {
		return Version.getApplicationName();
	}

	/**
	 * The entered application key.
	 */
	@CalledFromJSP
	public String getApplicationKey() {
		return _applicationKey;
	}

	/**
	 * The application passphrase.
	 */
	@CalledFromJSP
	public String getNewPassword() {
		return _newPassword;
	}

	/**
	 * The retyped application passphrase.
	 */
	@CalledFromJSP
	public String getPasswordTwice() {
		return _passwordTwice;
	}

	/**
	 * The {@link Account} with the given name.
	 */
	Account getAccount(String userName) {
		return _accounts.get(userName);
	}

	/**
	 * All initial accounts.
	 */
	@CalledFromJSP
	public Collection<Account> getAccounts() {
		return _accounts.values();
	}

	/**
	 * Whether the setup has finished.
	 */
	@CalledFromJSP
	public boolean phaseFinished() {
		return phase() == Phase.FINISHED;
	}

	/**
	 * Whether the setup requests the application key.
	 */
	@CalledFromJSP
	public boolean phaseKeyInput() {
		return phase() == Phase.KEY_INPUT;
	}

	/**
	 * Whether the setup requests the application passphrase.
	 */
	@CalledFromJSP
	public boolean phasePasswordInput() {
		return phase() == Phase.PASSWORD_INPUT;
	}

	/**
	 * Whether the setup is initializing accounts.
	 */
	@CalledFromJSP
	public boolean phaseUserSetup() {
		return phase() == Phase.USER_SETUP;
	}

	/**
	 * Whether this is the initial setup. <code>false</code> for changing the application
	 * passphrase.
	 */
	@CalledFromJSP
	public boolean isSetup() {
		return _isSetup;
	}

	/**
	 * Whether an internal error has occurred.
	 */
	@CalledFromJSP
	public boolean errorInternal() {
		return _errorInternal;
	}

	/**
	 * Whether the application key is invalid.
	 */
	@CalledFromJSP
	public boolean errorInvalidKey() {
		return _errorInvalidKey;
	}

	/**
	 * Whether the application passphrase is invalid.
	 */
	@CalledFromJSP
	public boolean errorInvalidPassword() {
		return _errorInvalidPassword;
	}

	/**
	 * Whether the both application passphrases do not match.
	 */
	@CalledFromJSP
	public boolean errorPasswordMissmatch() {
		return _errorPasswordMissmatch;
	}

	private void phase(Phase phase) {
		_phase = phase;
	}

	private Phase phase() {
		return _phase;
	}

}
