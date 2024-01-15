/*
 * Copyright (c) 2023 Business Operation Systems GmbH. All Rights Reserved.
 */
package com.top_logic.services.jms;

import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import com.top_logic.basic.config.AbstractConfiguredInstance;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.io.binary.BinaryDataSource;

import jakarta.jms.BytesMessage;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.TextMessage;

/**
 * Class for a jms producer (sends messages to a queue).
 * 
 * @author <a href="mailto:sha@top-logic.com">Simon Haneke</a>
 */
public class Producer extends AbstractConfiguredInstance<Producer.Config<?>> {

	private JMSProducer _producer;

	private JMSContext _context;

	private String _charsetProperty;

	private Destination _destination;

	/**
	 * Configuration options for {@link Consumer}.
	 */
	public interface Config<I extends Producer> extends ClientConfig<I> {
		/**
		 * Currently no additional configs needed. All needed configuration options are inherited
		 * from the super interface.
		 */
	}
	
	/**
	 * Constructor for a producer that sets the config.
	 * 
	 * @param config
	 *        The config for the connection to the queue
	 */
	public Producer(InstantiationContext instContext, Config<?> config) {
		super(instContext, config);
	}

	/**
	 * Creates a {@link JMSProducer} on an existing {@link JMSContext} containing the connection to
	 * a message queue system.
	 * 
	 * @param client
	 *        The Object establishing the connection and holding the {@link JMSContext}.
	 */
	public void setup(JMSClient client) {
		_context = client.getContext();
		_producer = _context.createProducer();
		_charsetProperty = client.getCharsetProperty();

		Config.Type type = getConfig().getType();
		String destName = getConfig().getDestName();
		_destination = client.createDestination(type, destName);
	}

	/**
	 * Method to send a string (or XML) message to a queue or a topic.
	 * 
	 * @param text
	 *        The message to be sent as string
	 */
	public void send(String text) {
		TextMessage message = _context.createTextMessage(text);
		_producer.send(_destination, message);
	}

	/**
	 * Method to send a binary message to a queue or a topic.
	 * 
	 * @param data
	 *        Binary message to be sent
	 */
	public void send(BinaryDataSource data) {
		BytesMessage message = _context.createBytesMessage();
		OutputStream out = new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				try {
					message.writeByte((byte) b);
				} catch (JMSException ex) {
					throw new IOException(ex);
				}
			}

			@Override
			public void write(byte[] b) throws IOException {
				try {
					message.writeBytes(b);
				} catch (JMSException ex) {
					throw new IOException(ex);
				}
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				try {
					message.writeBytes(b, off, len);
				} catch (JMSException ex) {
					throw new IOException(ex);
				}
			}
		};
		try {
			data.deliverTo(out);
		} catch (IOException ex) {
			throw new IOError(ex);
		}
		try {
			message.setStringProperty(_charsetProperty,
				new MimeType(data.getContentType()).getParameter("charset"));
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (MimeTypeParseException ex) {
			ex.printStackTrace();
		}
		_producer.send(_destination, message);
	}
}
