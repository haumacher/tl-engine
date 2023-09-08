/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.kafka.server.module;

import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.utils.Time;

import com.top_logic.basic.ArrayUtil;
import com.top_logic.basic.col.ListBuilder;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.module.ManagedClass;
import com.top_logic.basic.module.ServiceDependencies;
import com.top_logic.basic.module.TypedRuntimeModule;
import com.top_logic.kafka.server.starter.KafkaStarter;

import kafka.Kafka;
import kafka.admin.TopicCommand;
import kafka.server.KafkaConfig;
import kafka.zk.KafkaZkClient;

/**
 * Module starting {@link Kafka}.
 * 
 * <p>
 * Actually a wrapper for {@link KafkaStarter}.
 * </p>
 * 
 * @see KafkaStarter
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
@ServiceDependencies(ZooKeeperModule.Module.class)
public class KafkaModule extends ManagedClass {

	/**
	 * Configuration of the {@link KafkaModule}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	public interface Config extends ManagedClass.ServiceConfiguration<KafkaModule>, KafkaStarter.Config {

		// sum interface
	}

	private KafkaStarter _kafkaStarter;

	/**
	 * Creates a new {@link KafkaModule} from the given configuration.
	 * 
	 * @param context
	 *        {@link InstantiationContext} to instantiate sub configurations.
	 * @param config
	 *        Configuration for this {@link KafkaModule}.
	 */
	public KafkaModule(InstantiationContext context, Config config) {
		_kafkaStarter = new KafkaStarter(context, config);
	}

	@Override
	protected void startUp() {
		super.startUp();
		_kafkaStarter.startup();
	}

	@Override
	protected void shutDown() {
		_kafkaStarter.shutdown();
		super.shutDown();
	}

	/**
	 * Returns the configuration of the started {@link Kafka}.
	 */
	public KafkaConfig getKafkaConfig() {
		return _kafkaStarter.getKafkaConfig();
	}

	/**
	 * Creates the topic (if not already exists) with the given name, one partition, replication
	 * factor one.
	 */
	public void createTopic(String topic) {
		createTopic(topic, 1, 1);
	}

	/**
	 * Creates the topic (if not already exists) with the given name
	 * 
	 * @param topic
	 *        Name of the topic.
	 * @param partitions
	 *        The number of partitions for the topic being created.
	 * @param replicationFactor
	 *        The replication factor for each partition in the topic being created.
	 */
	public void createTopic(String topic, int partitions, int replicationFactor) {
		String zookeeperAddress = _kafkaStarter.getKafkaConfig().zkConnect();
		ListBuilder<String> commandOptions = new ListBuilder<String>()
			.add("--if-not-exists")
			.add("--zookeeper").add(zookeeperAddress)
			.add("--partitions").add(Integer.toString(partitions))
			.add("--replication-factor").add(Integer.toString(replicationFactor))
			.add("--topic").add(topic);
		String[] args = commandOptions.toList().toArray(ArrayUtil.EMPTY_STRING_ARRAY);
		TopicCommand.TopicCommandOptions topicCommandOptions = new TopicCommand.TopicCommandOptions(args);
		String zkUrl = topicCommandOptions.options().valueOf(topicCommandOptions.zkConnectOpt());
		
		KafkaZkClient client = KafkaZkClient.apply(zkUrl, JaasUtils.isZkSecurityEnabled(), 30000, 30000,
			Integer.MAX_VALUE, Time.SYSTEM, "kafka.server", "SessionExpireListener");
		 
		TopicCommand.createTopic(client, topicCommandOptions);
	}

	/**
	 * Module for the {@link KafkaModule}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	public static final class Module extends TypedRuntimeModule<KafkaModule> {

		/** Sole instance of {@link Module}. */
		public static final Module INSTANCE = new Module();

		@Override
		public Class<KafkaModule> getImplementation() {
			return KafkaModule.class;
		}

	}

}

