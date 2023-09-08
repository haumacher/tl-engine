/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.component.edit;

import com.top_logic.base.locking.LockService;
import com.top_logic.base.locking.handler.ConfiguredLockHandler;
import com.top_logic.base.locking.handler.DefaultLockHandler;
import com.top_logic.base.locking.handler.LockHandler;
import com.top_logic.base.locking.handler.NoTokenHandling;
import com.top_logic.base.locking.token.Token;
import com.top_logic.basic.config.ConfigurationItem;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.PolymorphicConfiguration;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.basic.config.annotation.Nullable;
import com.top_logic.basic.config.annotation.defaults.ImplementationClassDefault;
import com.top_logic.basic.config.annotation.defaults.StringDefault;
import com.top_logic.layout.component.IComponent;
import com.top_logic.mig.html.layout.LayoutComponent;

/**
 * {@link LayoutComponent} plug-in interface for adding a {@link LockHandler}.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface CanLock extends IComponent {

	/**
	 * Configuration options for {@link CanLock}.
	 */
	public interface Config extends ConfigurationItem {

		/**
		 * Algorithm for edit-mode token handling.
		 * 
		 * <p>
		 * For just specifying an alternative operation, use {@link #getLockOperation()}.
		 * </p>
		 */
		@Name("lockHandler")
		@ImplementationClassDefault(ConfiguredLockHandler.class)
		PolymorphicConfiguration<LockHandler> getLockHandler();

		/**
		 * Abstract operation performed by this component with regard to locking.
		 * 
		 * <p>
		 * This property is a short-cut for {@link #getLockHandler()} using
		 * {@link DefaultLockHandler} with the given operation. You can disable lock handling by
		 * setting this property to empty.
		 * </p>
		 * 
		 * @see LockService#createLock(String, Object...)
		 */
		@Name("lockOperation")
		@Nullable
		@StringDefault(Token.DEFAULT_OPERATION)
		String getLockOperation();

	}

	/**
	 * Acquires the lock for {@link #getTokenContextBase()}.
	 */
	default void acquireTokenContext() {
		getLockHandler().acquireLock(getTokenContextBase());
	}

	/**
	 * This component's {@link LockHandler}, see {@link Config#getLockHandler()}.
	 */
	public LockHandler getLockHandler();

	/**
	 * Get the base object to lock.
	 * 
	 * @return The object to lock in this component.
	 */
	default Object getTokenContextBase() {
		return getModel();
	}

	/**
	 * Initializes the configured {@link LockHandler}.
	 */
	static LockHandler createLockHandler(InstantiationContext context, CanLock.Config config) {
		PolymorphicConfiguration<LockHandler> handlerConfig = config.getLockHandler();
		if (handlerConfig != null) {
			return context.getInstance(handlerConfig);
		}
	
		String operation = config.getLockOperation();
		if (operation == null) {
			return NoTokenHandling.INSTANCE;
		} else {
			return DefaultLockHandler.newInstance(operation);
		}
	}
}
