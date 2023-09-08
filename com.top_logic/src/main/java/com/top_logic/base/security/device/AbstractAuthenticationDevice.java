/*
 * SPDX-FileCopyrightText: 2005 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.base.security.device;

import com.top_logic.base.security.device.interfaces.AuthenticationDevice;
import com.top_logic.basic.config.InstantiationContext;

/**
 * A SecurityDevice that is an AuthenticationDevice.
 * 
 * This is an abstract implementation for  an AuthenticationDevice
 * Eases implementation of new AuthDevices as configuration access and stuff
 * is already taken care of
 * 
 * @author    <a href="mailto:tri@top-logic.com">Thomat Trichter</a>
 */
public abstract class AbstractAuthenticationDevice extends AbstractSecurityDevice implements AuthenticationDevice {
	
	/**
	 * Configuration of an {@link AbstractAuthenticationDevice}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	public interface Config extends AbstractSecurityDevice.Config, AuthenticationDeviceConfig {

		// Sum interface
	}

	/**
	 * Creates a new {@link AbstractAuthenticationDevice} from the given configuration.
	 * 
	 * @param context
	 *        {@link InstantiationContext} to instantiate sub configurations.
	 * @param config
	 *        Configuration for this {@link AbstractAuthenticationDevice}.
	 * 
	 */
	public AbstractAuthenticationDevice(InstantiationContext context, Config config) {
		super(context, config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.top_logic.base.security.device.interfaces.PersonDataAccessDevice#allowPwdChange()
	 */
	@Override
	public boolean allowPwdChange() {
		return ((Config) getConfig()).isAllowPwdChange();
	}
}