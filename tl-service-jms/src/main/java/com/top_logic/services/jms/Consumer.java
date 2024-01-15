/*
 * Copyright (c) 2023 Business Operation Systems GmbH. All Rights Reserved.
 */
package com.top_logic.services.jms;

import com.top_logic.basic.Logger;
import com.top_logic.basic.config.misc.TypedConfigUtil;
import com.top_logic.services.jms.JMSService.DestinationConfig;
import com.top_logic.services.jms.JMSService.MessageProcessor;
import com.top_logic.services.jms.JMSService.Type;

import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Topic;

/**
 * Class for a jms consumer (fetches messages from a queue).
 */
public class Consumer extends JMSClient {

	private JMSConsumer _consumer;

	private MessageProcessor _processor;

	/**
	 * @param config
	 *        The config for the connection to the queue
	 * @throws JMSException
	 *         Exception if something is not jms conform
	 */
	public Consumer(DestinationConfig config) throws JMSException {
		super(config);
		if (config.getType().equals(Type.TOPIC)) {
			_consumer = getContext().createSharedDurableConsumer((Topic) getDestination(), config.getDestName());
		} else {
			_consumer = getContext().createConsumer(getDestination());
		}
		_processor = TypedConfigUtil.createInstance(config.getProcessor());
	}

	/**
	 * Fetches a message from the given queue
	 */
	public void receive() {
		try {
			while (true) {
				Message message = _consumer.receive();
				_processor.processMessage(message);
			}
		} finally {
			Logger.info("Stopping consumer " + getDestinationName() + ".", Consumer.class);
			_consumer.close();
		}
	}
}
