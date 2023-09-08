/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.kafka.server.starter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.top_logic.basic.AliasedProperties;
import com.top_logic.basic.FileManager;
import com.top_logic.basic.config.ConfigurationItem;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.annotation.defaults.StringDefault;

import kafka.Kafka;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;

/**
 * Service class to Start {@link Kafka}
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class KafkaStarter implements Starter {

	/**
	 * Configuration of the {@link KafkaStarter}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	public interface Config extends ConfigurationItem {

		/**
		 * {@link Kafka} configuration file.
		 */
		@StringDefault("/WEB-INF/conf/kafka/server.properties")
		String getConfigFile();

	}

	private KafkaServerStartable _kafkaStarter;

	/**
	 * Creates a new {@link KafkaStarter}.
	 */
	public KafkaStarter(InstantiationContext context, Config config) {
		Properties properties = new AliasedProperties();
		try {
			try (InputStream configStream = FileManager.getInstance().getStream(config.getConfigFile())) {
				properties.load(configStream);
			}
		} catch (IOException ex) {
			properties = new Properties();
			properties.setProperty("zookeeper.connect", "localhost:%ZOO_KEEPER_PORT%");
			context.error("Unable to load configuration '" + config.getConfigFile() + "'.", ex);
		}
		_kafkaStarter = KafkaServerStartable.fromProps(properties);
	}

	/**
	 * The configuration used to create {@link Kafka} server.
	 */
	public KafkaConfig getKafkaConfig() {
		return _kafkaStarter.staticServerConfig();
	}

	@Override
	public void startup() {
		_kafkaStarter.startup();
	}

	@Override
	public void shutdown() {
		_kafkaStarter.shutdown();
	}

}

