/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.kafka.services.common;

import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.apache.kafka.common.security.auth.Login;

import com.top_logic.basic.config.NamedPolymorphicConfiguration;
import com.top_logic.basic.config.PolymorphicConfiguration;
import com.top_logic.basic.config.annotation.Encrypted;
import com.top_logic.basic.config.annotation.Mandatory;
import com.top_logic.basic.config.annotation.MapBinding;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.basic.config.annotation.defaults.DoubleDefault;
import com.top_logic.basic.config.annotation.defaults.IntDefault;
import com.top_logic.basic.config.annotation.defaults.LongDefault;
import com.top_logic.basic.config.annotation.defaults.ShortDefault;
import com.top_logic.basic.config.annotation.defaults.StringDefault;
import com.top_logic.kafka.log.KafkaLogWriter;
import com.top_logic.kafka.services.consumer.ConsumerDispatcher;
import com.top_logic.kafka.services.consumer.KafkaClientProperty;

/**
 * Common configurations for Kafka consumer and producer.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public interface CommonClientConfig<V, T> extends NamedPolymorphicConfiguration<T> {

	/** Property name of {@link #getSecurityProviders()}. */
	String SECURITY_PROVIDERS = "security.providers";

	/** Property name of {@link #getUntypedProperties()}. */
	String UNTYPED_PROPERTIES = "untyped-properties";

	/** Property name of {@link #getLogWriter()}. */
	String LOG_WRITER = "log-writer";

	/**
	 * @see CommonClientConfigs#BOOTSTRAP_SERVERS_DOC
	 */
	@Name(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG)
	@KafkaClientProperty
	String getBootstrapServers();

	/**
	 * @see CommonClientConfigs#BOOTSTRAP_SERVERS_DOC
	 */
	@Name(CommonClientConfigs.CLIENT_DNS_LOOKUP_CONFIG)
	@StringDefault("default")
	@KafkaClientProperty
	String getClientDnsLookup();

	/**
	 * @see CommonClientConfigs#CONNECTIONS_MAX_IDLE_MS_DOC
	 */
	@Name(CommonClientConfigs.CONNECTIONS_MAX_IDLE_MS_CONFIG)
	@LongDefault(9 * 60 * 1000)
	@KafkaClientProperty
	long getConnectionsMaxIdleMS();

	/**
	 * @see CommonClientConfigs#METADATA_MAX_AGE_DOC
	 */
	@Name(CommonClientConfigs.METADATA_MAX_AGE_CONFIG)
	@LongDefault(5 * 60 * 1000)
	@KafkaClientProperty
	long getMetadataMaxAgeMS();

	/**
	 * @see CommonClientConfigs#METRIC_REPORTER_CLASSES_DOC
	 */
	@Name(CommonClientConfigs.METRIC_REPORTER_CLASSES_CONFIG)
	@KafkaClientProperty
	String getMetricReporters();

	/**
	 * @see CommonClientConfigs#METRICS_NUM_SAMPLES_DOC
	 */
	@Name(CommonClientConfigs.METRICS_NUM_SAMPLES_CONFIG)
	@IntDefault(2)
	@KafkaClientProperty
	int getMetricsNumSamples();

	/**
	 * @see CommonClientConfigs#METRICS_RECORDING_LEVEL_DOC
	 */
	@Name(CommonClientConfigs.METRICS_RECORDING_LEVEL_CONFIG)
	@StringDefault("INFO")
	@KafkaClientProperty
	String getMetricsRecordingLevel();

	/**
	 * @see CommonClientConfigs#METRICS_SAMPLE_WINDOW_MS_DOC
	 */
	@Name(CommonClientConfigs.METRICS_SAMPLE_WINDOW_MS_CONFIG)
	@LongDefault(30 * 1000)
	@KafkaClientProperty
	long getMetricsSampleWindowMS();

	/**
	 * @see CommonClientConfigs#RECEIVE_BUFFER_DOC
	 */
	@Name(CommonClientConfigs.RECEIVE_BUFFER_CONFIG)
	@KafkaClientProperty
	int getReceiveBufferBytes();

	/**
	 * @see CommonClientConfigs#RECONNECT_BACKOFF_MAX_MS_DOC
	 */
	@Name(CommonClientConfigs.RECONNECT_BACKOFF_MAX_MS_CONFIG)
	@LongDefault(1000)
	@KafkaClientProperty
	long getReconnectBackoffMaxMS();

	/**
	 * NOTE: not all characters are admissible, e.g. the character ':' must be avoided.
	 * 
	 * @see CommonClientConfigs#CLIENT_ID_DOC
	 */
	@Name(CommonClientConfigs.CLIENT_ID_CONFIG)
	@KafkaClientProperty
	String getClientId();

	/**
	 * @see CommonClientConfigs#RECONNECT_BACKOFF_MS_DOC
	 */
	@Name(CommonClientConfigs.RECONNECT_BACKOFF_MS_CONFIG)
	@LongDefault(30 * 1000)
	@KafkaClientProperty
	long getReconnectBackoffMS();

	/**
	 * @see CommonClientConfigs#REQUEST_TIMEOUT_MS_DOC
	 */
	@Name(CommonClientConfigs.REQUEST_TIMEOUT_MS_CONFIG)
	@KafkaClientProperty
	int getRequestTimeoutMS();

	/**
	 * @see CommonClientConfigs#RETRY_BACKOFF_MS_DOC
	 */
	@Name(CommonClientConfigs.RETRY_BACKOFF_MS_CONFIG)
	@LongDefault(100)
	@KafkaClientProperty
	long getRetryBackoffMS();

	/**
	 * @see SaslConfigs#SASL_CLIENT_CALLBACK_HANDLER_CLASS_DOC
	 */
	@Name(SaslConfigs.SASL_CLIENT_CALLBACK_HANDLER_CLASS)
	@KafkaClientProperty
	Class<? extends AuthenticateCallbackHandler> getSaslClientCallbackHandlerClass();

	/**
	 * @see SaslConfigs#SASL_JAAS_CONFIG_DOC
	 */
	@Name(SaslConfigs.SASL_JAAS_CONFIG)
	@KafkaClientProperty
	String getSaslJaasConfig();

	/**
	 * @see SaslConfigs#SASL_KERBEROS_KINIT_CMD_DOC
	 */
	@Name(SaslConfigs.SASL_KERBEROS_KINIT_CMD)
	@StringDefault("/usr/bin/kinit")
	@KafkaClientProperty
	String getSaslKerberosKinitCmd();

	/**
	 * @see SaslConfigs#SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN_DOC
	 */
	@Name(SaslConfigs.SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN)
	@LongDefault(60 * 1000)
	@KafkaClientProperty
	long getSaslKerberosMinTimeBeforeRelogin();

	/**
	 * @see SaslConfigs#SASL_KERBEROS_SERVICE_NAME_DOC
	 */
	@Name(SaslConfigs.SASL_KERBEROS_SERVICE_NAME)
	@KafkaClientProperty
	String getSaslKerberosServiceName();

	/**
	 * @see SaslConfigs#SASL_KERBEROS_TICKET_RENEW_JITTER_DOC
	 */
	@Name(SaslConfigs.SASL_KERBEROS_TICKET_RENEW_JITTER)
	@DoubleDefault(0.05)
	@KafkaClientProperty
	double getSaslKerberosTicketRenewJitter();

	/**
	 * @see SaslConfigs#SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR_DOC
	 */
	@Name(SaslConfigs.SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR)
	@DoubleDefault(0.8)
	@KafkaClientProperty
	double getSaslKerberosTicketRenewWindowFactor();

	/**
	 * @see SaslConfigs#SASL_LOGIN_CALLBACK_HANDLER_CLASS_DOC
	 */
	@Name(SaslConfigs.SASL_LOGIN_CALLBACK_HANDLER_CLASS)
	@KafkaClientProperty
	Class<? extends AuthenticateCallbackHandler> getSaslLoginCallbackHandlerClass();

	/**
	 * @see SaslConfigs#SASL_LOGIN_CLASS_DOC
	 */
	@Name(SaslConfigs.SASL_LOGIN_CLASS)
	@KafkaClientProperty
	Class<? extends Login> getSaslLoginClass();

	/**
	 * @see SaslConfigs#SASL_LOGIN_REFRESH_BUFFER_SECONDS_DOC
	 */
	@Name(SaslConfigs.SASL_LOGIN_REFRESH_BUFFER_SECONDS)
	@ShortDefault(5 * 60)
	@KafkaClientProperty
	short getSaslLoginRefreshBufferSeconds();

	/**
	 * @see SaslConfigs#SASL_LOGIN_REFRESH_MIN_PERIOD_SECONDS_DOC
	 */
	@Name(SaslConfigs.SASL_LOGIN_REFRESH_MIN_PERIOD_SECONDS)
	@ShortDefault(60)
	@KafkaClientProperty
	short getSaslLoginRefreshMinPeriodSeconds();

	/**
	 * @see SaslConfigs#SASL_LOGIN_REFRESH_WINDOW_FACTOR_DOC
	 */
	@Name(SaslConfigs.SASL_LOGIN_REFRESH_WINDOW_FACTOR)
	@DoubleDefault(0.8)
	@KafkaClientProperty
	double getSaslLoginRefreshWindowFactor();

	/**
	 * @see SaslConfigs#SASL_LOGIN_REFRESH_WINDOW_JITTER_DOC
	 */
	@Name(SaslConfigs.SASL_LOGIN_REFRESH_WINDOW_JITTER)
	@DoubleDefault(0.05)
	@KafkaClientProperty
	double getSaslLoginRefreshWindowJitter();

	/**
	 * @see SaslConfigs#SASL_MECHANISM_DOC
	 */
	@Name(SaslConfigs.SASL_MECHANISM)
	@StringDefault("GSSAPI")
	@KafkaClientProperty
	String getSaslMechanism();

	/**
	 * @see CommonClientConfigs#SECURITY_PROTOCOL_DOC
	 */
	@Name(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG)
	@StringDefault("PLAINTEXT")
	@KafkaClientProperty
	String getSecurityProtocol();

	/**
	 * The Kafka client property: "security.providers"
	 */
	@Name(SECURITY_PROVIDERS)
	@KafkaClientProperty
	String getSecurityProviders();

	/**
	 * @see CommonClientConfigs#SEND_BUFFER_DOC
	 */
	@Name(CommonClientConfigs.SEND_BUFFER_CONFIG)
	@IntDefault(128 * 1024)
	@KafkaClientProperty
	int getSendBufferBytes();

	/**
	 * @see SslConfigs#SSL_CIPHER_SUITES_DOC
	 */
	@Name(SslConfigs.SSL_CIPHER_SUITES_CONFIG)
	@KafkaClientProperty
	String getSslCipherSuites();

	/**
	 * @see SslConfigs#SSL_ENABLED_PROTOCOLS_DOC
	 */
	@Name(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG)
	@StringDefault("TLSv1.2,TLSv1.1,TLSv1")
	@KafkaClientProperty
	String getSslEnabledProtocols();

	/**
	 * @see SslConfigs#SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_DOC
	 */
	@Name(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG)
	@StringDefault("https")
	@KafkaClientProperty
	String getSslEndpointIdentificationAlgorithm();

	/**
	 * @see SslConfigs#SSL_KEY_PASSWORD_DOC
	 */
	@Name(SslConfigs.SSL_KEY_PASSWORD_CONFIG)
	@KafkaClientProperty
	@Encrypted
	String getSslKeyPassword();

	/**
	 * @see SslConfigs#SSL_KEYMANAGER_ALGORITHM_DOC
	 */
	@Name(SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG)
	@StringDefault("SunX509")
	@KafkaClientProperty
	String getSslKeymanagerAlgorithm();

	/**
	 * @see SslConfigs#SSL_KEYSTORE_LOCATION_DOC
	 */
	@Name(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG)
	@KafkaClientProperty
	String getSslKeystoreLocation();

	/**
	 * @see SslConfigs#SSL_KEYSTORE_PASSWORD_DOC
	 */
	@Name(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG)
	@KafkaClientProperty
	@Encrypted
	String getSslKeystorePassword();

	/**
	 * @see SslConfigs#SSL_KEYSTORE_TYPE_DOC
	 */
	@Name(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG)
	@StringDefault("JKS")
	@KafkaClientProperty
	String getSslKeystoreType();

	/**
	 * @see SslConfigs#SSL_PROTOCOL_DOC
	 */
	@Name(SslConfigs.SSL_PROTOCOL_CONFIG)
	@StringDefault("TLS")
	@KafkaClientProperty
	String getSslProtocol();

	/**
	 * @see SslConfigs#SSL_PROVIDER_DOC
	 */
	@Name(SslConfigs.SSL_PROVIDER_CONFIG)
	@KafkaClientProperty
	String getSslProvider();

	/**
	 * @see SslConfigs#SSL_SECURE_RANDOM_IMPLEMENTATION_DOC
	 */
	@Name(SslConfigs.SSL_SECURE_RANDOM_IMPLEMENTATION_CONFIG)
	@KafkaClientProperty
	String getSslSecureRandomImplementation();

	/**
	 * @see SslConfigs#SSL_TRUSTMANAGER_ALGORITHM_DOC
	 */
	@Name(SslConfigs.SSL_TRUSTMANAGER_ALGORITHM_CONFIG)
	@StringDefault("PKIX")
	@KafkaClientProperty
	String getSslTrustManagerAlgorithm();

	/**
	 * @see SslConfigs#SSL_TRUSTSTORE_LOCATION_DOC
	 */
	@Name(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG)
	@KafkaClientProperty
	String getSslTruststoreLocation();

	/**
	 * @see SslConfigs#SSL_TRUSTSTORE_PASSWORD_DOC
	 */
	@Name(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG)
	@KafkaClientProperty
	@Encrypted
	String getSslTruststorePassword();

	/**
	 * @see SslConfigs#SSL_TRUSTSTORE_TYPE_DOC
	 */
	@Name(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG)
	@KafkaClientProperty
	String getSslTruststoreType();

	/**
	 * The map of Kafka configuration properties.
	 * <p>
	 * Properties which have dedicated TypedConfiguration properties must not be used here.
	 * </p>
	 */
	@Name(UNTYPED_PROPERTIES)
	@MapBinding(tag = "property", key = "name")
	Map<String, String> getUntypedProperties();

	/**
	 * The {@link KafkaLogWriter} to use for logging the messages.
	 * 
	 * @implNote In a {@link Producer}, the "message" is {@link ProducerRecord#value()}. In a
	 *           {@link ConsumerDispatcher}, it is {@link ConsumerRecord#value()}.
	 */
	@Name(LOG_WRITER)
	@Mandatory
	PolymorphicConfiguration<KafkaLogWriter<V>> getLogWriter();

}
