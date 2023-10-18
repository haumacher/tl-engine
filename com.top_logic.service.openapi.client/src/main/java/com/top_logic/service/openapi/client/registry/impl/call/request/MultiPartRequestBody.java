/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.service.openapi.client.registry.impl.call.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;

import com.top_logic.basic.config.AbstractConfiguredInstance;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.NamedConfigMandatory;
import com.top_logic.basic.config.annotation.Key;
import com.top_logic.basic.config.annotation.Mandatory;
import com.top_logic.basic.config.annotation.TagName;
import com.top_logic.basic.io.BinaryContent;
import com.top_logic.basic.io.binary.BinaryData;
import com.top_logic.basic.io.binary.BinaryDataSource;
import com.top_logic.basic.json.JSON;
import com.top_logic.layout.form.values.edit.annotation.PropertyEditor;
import com.top_logic.layout.form.values.edit.editor.PlainEditor;
import com.top_logic.layout.provider.MetaLabelProvider;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.query.QueryExecutor;
import com.top_logic.service.openapi.client.registry.impl.call.Call;
import com.top_logic.service.openapi.client.registry.impl.call.CallBuilder;
import com.top_logic.service.openapi.client.registry.impl.call.CallBuilderFactory;
import com.top_logic.service.openapi.client.registry.impl.call.MethodSpec;
import com.top_logic.util.error.TopLogicException;

/**
 * Produces a {@link CallBuilder} constructing a multi-part request body.
 *
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class MultiPartRequestBody extends AbstractConfiguredInstance<MultiPartRequestBody.Config>
		implements CallBuilderFactory {

	/**
	 * Typed configuration interface definition for {@link MultiPartRequestBody}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	@TagName("multi-part-body")
	public interface Config extends CallBuilderFactory.Config<MultiPartRequestBody> {

		/**
		 * The parts of the request body.
		 */
		@Key(MultiPartContent.NAME_ATTRIBUTE)
		@Mandatory
		List<MultiPartContent> getParts();
	}

	/**
	 * Part of a multi part request.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	public interface MultiPartContent extends NamedConfigMandatory {

		/**
		 * The name of the part.
		 */
		@Override
		String getName();


		/**
		 * Content of the part.
		 *
		 * <p>
		 * The expression has access to all parameter values that are implicitly defined as
		 * TL-Script variables.
		 * </p>
		 */
		@PropertyEditor(PlainEditor.class)
		@Mandatory
		Expr getContent();

	}

	/**
	 * Create a {@link MultiPartRequestBody}.
	 * 
	 * @param context
	 *        the {@link InstantiationContext} to create the new object in
	 * @param config
	 *        the configuration object to be used for instantiation
	 */
	public MultiPartRequestBody(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	public CallBuilder createRequestModifier(MethodSpec method) {
		List<MultiPartContent> parts = getConfig().getParts();
		QueryExecutor[] executors = new QueryExecutor[parts.size()];
		for (int i = 0; i < executors.length; i++) {
			MultiPartContent part = parts.get(i);
			Expr expr = part.getContent();
			expr = JSONRequestBody.addMethodParameters(expr, method);
			executors[i] = QueryExecutor.compile(expr);
		}
		return new CallBuilder() {
			
			@Override
			public void buildRequest(ClassicHttpRequest request, Call call) {
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				
				Object[] args = JSONRequestBody.createCallArguments(call, method);
				for (int i = 0 ; i< executors.length; i++) {
					addBodyPart(builder, parts.get(i), executors[i].execute(args));
					
				}
				request.setEntity(builder.build());
			}
			
			private void addBodyPart(MultipartEntityBuilder builder, MultiPartContent part, Object value) {
				String name = part.getName();
				if (value == null) {
					builder.addTextBody(name, "");
				} else if (value instanceof BinaryDataSource) {
					BinaryData content = ((BinaryDataSource) value).toData();
					InputStream stream;
					try {
						stream = content.getStream();
					} catch (IOException ex) {
						throw new UncheckedIOException(ex);
					}
					builder.addBinaryBody(
						name,
						stream,
						createContentType(name, content.getContentType()),
						content.getName());

				} else if (value instanceof BinaryContent) {
					BinaryContent content = (BinaryContent) value;
					InputStream stream;
					try {
						stream = content.getStream();
					} catch (IOException ex) {
						throw new UncheckedIOException(ex);
					}
					builder.addBinaryBody(
						name,
						stream,
						ContentType.APPLICATION_OCTET_STREAM,
						content.getName());
				} else if (value instanceof Map) {
					builder.addTextBody(name, JSON.toString(value), ContentType.APPLICATION_JSON);
				} else {
					builder.addTextBody(name, MetaLabelProvider.INSTANCE.getLabel(value));
				}
			}

			
		};
	}

	static ContentType createContentType(String partName, String contentType) {
		try {
			return ContentType.parse(contentType);
		} catch (RuntimeException ex) {
			throw new TopLogicException(I18NConstants.ILLEGAL_CONTENT_TYPE__TYPE_NAME.fill(contentType, partName));
		}
	}
}
