/*
 * SPDX-FileCopyrightText: 2006 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.base.services.simpleajax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;

import com.top_logic.base.context.TLSessionContext;
import com.top_logic.base.context.TLSubSessionContext;
import com.top_logic.base.services.simpleajax.RequestStore.Response;
import com.top_logic.basic.DebugHelper;
import com.top_logic.basic.Logger;
import com.top_logic.basic.StringServices;
import com.top_logic.basic.config.ApplicationConfig;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.io.PrefixBufferedInputStream;
import com.top_logic.basic.util.ResKey;
import com.top_logic.basic.util.Utils;
import com.top_logic.basic.xml.TagWriter;
import com.top_logic.basic.xml.sax.SAXUtil;
import com.top_logic.event.infoservice.InfoService;
import com.top_logic.event.infoservice.InfoServiceXMLStringConverter;
import com.top_logic.layout.ContentHandlersRegistry;
import com.top_logic.layout.DisplayContext;
import com.top_logic.layout.ProcessingInfo;
import com.top_logic.layout.UpdateWriter;
import com.top_logic.layout.UpdateWriter.FatalXMLError;
import com.top_logic.layout.basic.DefaultDisplayContext;
import com.top_logic.layout.basic.DummyDisplayContext;
import com.top_logic.layout.internal.SubsessionHandler;
import com.top_logic.mig.html.HTMLConstants;
import com.top_logic.mig.html.UserAgent;
import com.top_logic.mig.html.layout.CommandDispatcher;
import com.top_logic.mig.html.layout.ComponentName;
import com.top_logic.mig.html.layout.LayoutComponent;
import com.top_logic.mig.html.layout.LayoutConstants;
import com.top_logic.mig.html.layout.LayoutUtils;
import com.top_logic.mig.html.layout.MainLayout;
import com.top_logic.mig.html.layout.RevalidationVisitor;
import com.top_logic.tool.boundsec.CommandHandler;
import com.top_logic.tool.boundsec.HandlerResult;
import com.top_logic.util.Resources;
import com.top_logic.util.TLContextManager;
import com.top_logic.util.TopLogicServlet;
import com.top_logic.util.error.ErrorHandlingHelper;

/**
 * Servlet that answers requests from JavaScript on web pages and dispatches
 * them to the component that created the corresponding page.
 * 
 * <p>
 * Requests and responses are encapsulated in SOAP envelops. After
 * {@link AJAXRequestParser unmarshaling}, a requests consists of a single
 * {@link AJAXRequest} object and is answered with a XML-encoded sequence of
 * {@link ClientAction} objects.
 * </p>
 * 
 * <p>
 * This servlet finds the component that is referenced from the request and
 * looks up a corresponding {@link CommandHandler}, which is the final server-side
 * handler for the request. After the command has been
 * {@link CommandHandler#handleCommand(DisplayContext, LayoutComponent, Object, Map) executed}, the resulting sequence of
 * {@link ClientAction client actions} is marshaled back to the requester. On
 * the client side, these actions are interpreted from a JavaScript function.
 * </p>
 * 
 * <p>
 * This class is derived from {@link com.top_logic.util.TopLogicServlet} to
 * inherit the default security checks performed before serving requests.
 * </p>
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class AJAXServlet extends TopLogicServlet {
    
	/**
	 * Configuration for {@link AJAXServlet}.
	 */
	public interface Config extends TopLogicServlet.Config {
		// same configuration as parent
	}

	/**
	 * Getter for the configuration.
	 */
	@Override
	public Config getConfig() {
		return ApplicationConfig.getInstance().getConfig(Config.class);
	}

	/**
	 * Response header that marks an AJAX response.
	 * 
	 * <p>
	 * This header is used from client-side scripts to decide whether a response should be parsed as
	 * AJAX response. This can be used to detect contents that is generated by proxy servers
	 * replacing the original reply.
	 * </p>
	 */
	public static final String HEADER_X_RESPONSE_SERVER = "X-Response-Server";

	/**
	 * Value of {@link #HEADER_X_RESPONSE_SERVER} to mark an AJAX response.
	 */
	private static final String HEADER_X_RESPONSE_SERVER_TL_AJAX = "tl-ajax";

    /**
     * Constant meaning that no sequence number is associated with an
     * {@link AJAXRequest}.
     */
    private static final Integer NO_SEQUENCE = null;
    
    /** Cache the AJAXRequestParser and the SAXParser per Thread */
    private static ThreadLocal<Object[]> cachedParsers    = new ThreadLocal<>();

    /**
     * A static command that forwards client-side logging requests to the server log.
     */
    private static final CommandHandler LOG_COMMAND = AJAXLogHandler.INSTANCE;

	private static final String SOURCE_COMPONENT_NOT_VISIBLE = new String("SourceComponentNotLongerVisible");
    
    /**
     * Map of static commands that can be executed without a component context.
     * The commands are identified by their {@link AJAXCommandHandler#getID() name}.
     */
    protected HashMap<String, CommandHandler> commandsByName = new HashMap<>();

    /**
     * Creates a new {@link AJAXServlet} that has a {@link #LOG_COMMAND}
     * registered for accepting client-side log messages.
     * 
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
	public void init(ServletConfig aConfig) throws ServletException {
        super.init(aConfig);
        
        registerStaticCommand(LOG_COMMAND);
    }
    
    /**
     * Register the given {@link CommandHandler} globally with this servlet. 
     * 
     * <p>
     * A static command is processed independently of a {@link LayoutComponent}.
     * </p>
     * 
     * @param command
     *     The command to register as static command.
     */
    protected void registerStaticCommand(CommandHandler command) {
        commandsByName.put(command.getID(), command);
    }

    /**
     * Overridden method from {@link TopLogicServlet} that is called from
     * {@link TopLogicServlet#service(HttpServletRequest, HttpServletResponse)},
     * (among other reasons) if the current request is not associated to a valid
     * session or the current session is timed out.
     */
    @Override
	protected void forwardPage(String aPage, HttpServletRequest aRequest, HttpServletResponse aResponse) throws IOException, ServletException {
		// Send a reload action. This triggers the default action behavior of
		// forwarding the user to the login page, if the session has timed out.
		String redirectMessage =
			Resources.getInstance(aRequest.getLocale()).getString(I18NConstants.SESSION_TIMED_OUT_REDIRECT_LOGIN, null);

		ArrayList<ClientAction> actionsBuffer = new ArrayList<>();
		if (!StringServices.isEmpty(redirectMessage)) {
			/* Alert must be executed before reload, as otherwise the alert is not displayed. */
			actionsBuffer.add(JSSnipplet.createAlert(redirectMessage));
		} else {
			// Silent redirect.
		}
		actionsBuffer.add(MainLayout.createFullReload());
		ClientAction[] actions = actionsBuffer.toArray(new ClientAction[actionsBuffer.size()]);
		marshalResult(aRequest, aResponse, actions, NO_SEQUENCE);
    }

    /**
	 * Handle the AJAX SOAP request and marshal the result back to the caller.
	 * 
	 * <p>
	 * Handling a request consists of the following steps:
	 * </p>
	 * 
	 * <ol>
	 * <li>Parsing in {@link #parseAJAXRequest(HttpServletRequest, InputStream)}.</li>
	 * <li>Processing in {@link #processRequest(LayoutComponent, CommandRequest, UpdateWriter)}.</li>
	 * </ol>
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
    {

//      // Uncomment to simulate a slow connection
//      try {
//          Thread.sleep(1600);   
//      } catch(InterruptedException e) {}
        
        AJAXRequest ajaxRequest = null;
        Integer rxSequence = NO_SEQUENCE;

		// see ticket #741
		response.setContentType("text/xml;charset=utf-8");
		// see Ticket #4213, #6742, and soap.js
		response.addHeader(HEADER_X_RESPONSE_SERVER, HEADER_X_RESPONSE_SERVER_TL_AJAX);
		String encoding = LayoutConstants.UTF_8;
		String sessionId = TopLogicServlet.getSessionId(request);
		UserAgent userAgent = UserAgent.getUserAgent(request);

        try {
			ajaxRequest = parseAJAXRequest(request, request.getInputStream());
            
			setServiceMessage(request, ajaxRequest);
            
            // Check, whether the session exists. If not, ignore the request.
			TLSessionContext sessionContext = TLContextManager.getSession();
			if (sessionContext == null) {
				// See Ticket #244.
				return;
			}

			// Check, whether there is a layout in the current session. See
			String rootId = ajaxRequest.getWindow();
			ContentHandlersRegistry handlersRegistry = sessionContext.getHandlersRegistry();
			SubsessionHandler rootHandler = handlersRegistry.getContentHandler(rootId);
			if (rootHandler == null) {
				TagWriter out = MainLayout.getTagWriter(request, response);
				UpdateWriter writer = newUpdateWriter(rxSequence, out, encoding);
				writer.endResponse();
				out.flushBuffer();
				return;
            }
            
			DisplayContext displayContext = DefaultDisplayContext.getDisplayContext(request);
			TLSubSessionContext subSession = sessionContext.getSubSession(rootId);
			displayContext.installSubSessionContext(subSession);

			RequestLock lock = rootHandler.getLock();

			if (ajaxRequest.hasTxSequence()) {
				rxSequence = ajaxRequest.getTxSequence();
			}

			RequestStore store;
			if (rxSequence == NO_SEQUENCE) {
				store = RequestStore.NO_STORE;
			} else {
				store = subSession.get(RequestStore.SESSION_KEY);
				if (store != null) {
					// Check, whether this is an retry for an already answered request. Note: This
					// must happen before request lock acquisition to avoid reporting reordering
					// errors.
					if (store.replay(rxSequence, response)) {
						Logger.info(
							"Replaying lost response for RX sequence '" + rxSequence + "', client: "
								+ request.getRemoteAddr() + ".", AJAXServlet.class);
						return;
					}
				}
			}

			Integer key = lock(ajaxRequest, rxSequence, request, response, encoding, sessionId, userAgent, rootHandler, lock);
			if (key == null) {
				return;
			}
			try {
				MainLayout mainLayout = rootHandler.getMainLayout();
				String sourceReference = getSourceComponentReference(ajaxRequest, mainLayout);
				if (Utils.equals(sourceReference, SOURCE_COMPONENT_NOT_VISIBLE)) {
					/* This may occur if loading a component automatically triggers an AJAX-request,
					 * e.g. if an image is loaded. If the user is fast enough, he may change the tab
					 * before the request is executed. In this case the tab switch gets a lower id
					 * and is therefore executed before the other request. */
					TagWriter out = MainLayout.getTagWriter(request, response);
					UpdateWriter writer = new UpdateWriter(displayContext, out, encoding, rxSequence);
					writer.endResponse();
					out.flushBuffer();
					return;
				}

				if (store == null) {
					// Initialization of subsession's request store. Note: The initialization must
					// happen single-threaded. Therefore it is deferred until request lock has been
					// aquired.
					store = RequestStore.FACTORY.create();
					subSession.set(RequestStore.SESSION_KEY, store);
				} else {
					store.acknowledge(ajaxRequest.getAcks());
				}

				try (Response handle = store.createResponse(rxSequence)) {
				TagWriter out = handle.open(displayContext);
				UpdateWriter writer = new UpdateWriter(displayContext, out, encoding, rxSequence, sourceReference);
				boolean processExternalModelEvents = false;

				// Whether model updates are allowed (during this the whole request). The initial
				// value is never used but required to comply with Java local variable
				// initialization rules.
				for (CommandRequest command : ajaxRequest.getCommands()) {
					boolean isSystemCommand = !isExternalUpdateRequestedByCommand(command);
					processExternalModelEvents = processExternalModelEvents || !isSystemCommand;
					{
						LayoutComponent contextComponent =
							getContextComponent(command, sessionId, userAgent, mainLayout, displayContext);
						if (contextComponent == null) {
							// Terminate processing silently. A response must be
							// marshaled to prevent out-of-sequence problems in the
							// client.
							continue;
						}
						// A static command must be evaluated in each case, e.g. the
						// log command is a command which is independent of the
						// submit number and should always be executed. (see #3266)
						if (!command.isStatic()) {

							// Requests from an old view of a component (before a reload) must be
							// ignored. Such an
							// old request may actually happen, if multiple requests are issued
							// concurrently, but
							// the answer to the first request triggers a reload.
							final int expectedSubmitNumber = contextComponent.getSubmitNumber();
							final int receivedSubmitNumber = command.getSubmitNumber();
							if (expectedSubmitNumber != receivedSubmitNumber) {
								Logger.info("Old request dropped. " +
									" (command: '" + command.getCommand() +
									"', expectedSubmitNumber: '" + expectedSubmitNumber +
									"', receivedSubmitNumber: '" + receivedSubmitNumber +
									"', arguments: '" + logArguments(command) +
									"', source: '" + command.getContextComponentID() +
									"', target: '" + command.getTargetComponentID() +
									"', session: " + sessionId + ',' + userAgent + ')', AJAXServlet.class);

								continue;
							}
						}
						processRequest(contextComponent, command, writer);
					}
				}
				validate(rootHandler, mainLayout, ajaxRequest, writer, displayContext, processExternalModelEvents);

				addInfoServiceItems(displayContext, writer);
				writer.endResponse();
				out.flushBuffer();
				}
			} finally {
				unlock(lock, key, ajaxRequest, rootHandler);
			}
		} catch (FatalXMLError ex) {
			Logger.error("IOException or TagException during evaluating result actions. (session: " + sessionId
				+ ',' + userAgent + ')', ex, AJAXServlet.class);
			throw (IOException) new IOException().initCause(ex);
        } catch (Throwable ex) {
            // FactoryConfigurationError
            // ParserConfigurationException
            // SAXException
            if (ajaxRequest != null) {
                Logger.error(
                        "Error in AJAX command. (command: '" + ajaxRequest.getCommand() + "', arguments: '" + logArguments(ajaxRequest) + 
						"', sourceComponent: '" + ajaxRequest.getSource() +
                        "', targetComponent: '" + ajaxRequest.getTargetComponentID() + 
							"', session: " + sessionId + ',' + userAgent + ')', ex, AJAXServlet.class);

                ajaxRequest = null;
            }
            else {
				Logger.error("Error while parsing AJAX command. (session: " + sessionId + ',' + userAgent + ')', ex,
					AJAXServlet.class);
            }

            clearParsers(); // Throw away parsers, as their state is undefined.

			TagWriter out = MainLayout.getTagWriter(request, response);
			UpdateWriter writer = newUpdateWriter(rxSequence, out, encoding);
			notifyRequestDropped(writer, I18NConstants.ILLEGAL_REQUEST_IGNORED, true);
			out.flushBuffer();
        }
    }

	@SuppressWarnings("unchecked")
	private void addInfoServiceItems(DisplayContext displayContext, UpdateWriter writer) {
		if (displayContext.isSet(InfoService.INFO_SERVICE_ENTRIES)) {
			List<HTMLFragment> errors = displayContext.get(InfoService.INFO_SERVICE_ENTRIES);
			writer.add(new JSSnipplet(getInfoServiceJSInvocation(displayContext, errors)));
		}
	}

	private String getInfoServiceJSInvocation(DisplayContext context, List<HTMLFragment> errors) {
		return InfoServiceXMLStringConverter.getJSInvocation(context, errors);
	}

	/**
	 * Whether the given command is a regular user-triggered command that should update the UI to
	 * the current state of the shared application model.
	 */
	private boolean isExternalUpdateRequestedByCommand(CommandRequest command) {
		Map<String, Object> commandArguments = command.getArguments();
		if (commandArguments.containsKey(AJAXCommandHandler.SYSTEM_COMMAND_ID)) {
			return !(Boolean) commandArguments.get(AJAXCommandHandler.SYSTEM_COMMAND_ID);
		}
		return true;
	}

	private Integer lock(AJAXRequest ajaxRequest, Integer rxSequence, HttpServletRequest request,
			HttpServletResponse response, String encoding, String sessionId, UserAgent userAgent,
			SubsessionHandler rootHandler, RequestLock lock) throws IOException {
		try {
			return lock(lock, ajaxRequest, rootHandler);
		} catch (InterruptedException ex) {
			Logger.info("Wait for sequence number becoming valid was interrupted. (sequence: '" + rxSequence
				+ "', session: " + sessionId + ',' + userAgent + ')', AJAXServlet.class);
			TagWriter out = MainLayout.getTagWriter(request, response);
			UpdateWriter writer = newUpdateWriter(rxSequence, out, encoding);
			writer.setRequestDropped();
			writer.endResponse();
			out.flushBuffer();
			return null;
		} catch (AJAXOutOfSequenceException ex) {
			Logger.warn("Received AJAX command out of sequence. (command: '" + ajaxRequest.getCommand()
				+ "', sequence: '" + rxSequence + "', arguments: '" + logArguments(ajaxRequest)
				+ "', session: "
				+ sessionId + ',' + userAgent + ')', ex, AJAXServlet.class);
			TagWriter out = MainLayout.getTagWriter(request, response);
			UpdateWriter writer = newUpdateWriter(rxSequence, out, encoding);
			notifyRequestDropped(writer, I18NConstants.INCONSISTENT_SERVER_REPIES, true);
			out.flushBuffer();
			return null;
		} catch (RequestTimeoutException ex) {
			Logger.warn("Request canceled after timeout. (command: '" + ajaxRequest.getCommand()
				+ "', sequence: '"
				+ rxSequence + "', arguments: '" + logArguments(ajaxRequest) + "', session: " + sessionId
				+ ','
				+ userAgent + ')', ex, AJAXServlet.class);
			TagWriter out = MainLayout.getTagWriter(request, response);
			UpdateWriter writer = newUpdateWriter(rxSequence, out, encoding);
			notifyRequestDropped(writer, I18NConstants.REQUEST_TIMEOUT_RELOAD_REQUIRED, true);
			out.flushBuffer();
			return null;
		} catch (MaxRequestNumberException ex) {
			Logger.warn("Received too many AJAX requests. (command: '" + ajaxRequest.getCommand()
				+ "', sequence: '"
				+ rxSequence + "', arguments: '" + logArguments(ajaxRequest) + "', session: " + sessionId
				+ ','
				+ userAgent + ')', ex, AJAXServlet.class);
			TagWriter out = MainLayout.getTagWriter(request, response);
			UpdateWriter writer = newUpdateWriter(rxSequence, out, encoding);
			notifyRequestDropped(writer, I18NConstants.MAX_NUMBER_REQUESTS_RECEIVED, false);
			out.flushBuffer();
			return null;
		}
	}

	/**
	 * Serialize command arguments in a form that is safe to be written to the log file.
	 * 
	 * <p>
	 * No sensitive information (such as arbitrary user input) must be written to the log.
	 * </p>
	 */
	private String logArguments(CommandRequest command) {
		final Map<String, Object> rawArguments = command.getArguments();

		final StringBuilder buffer = new StringBuilder();
		buffer.append("{");

		boolean first = true;
		for (Entry<String, Object> arg : rawArguments.entrySet()) {
			final String name = arg.getKey();
			Object value = arg.getValue();
			if (name.equals("value")) {
				value = "<hidden>";
			}

			if (first) {
				first = false;
			} else {
				buffer.append(", ");
			}
			buffer.append(name);
			buffer.append("=");
			buffer.append(value);
		}
		buffer.append("}");
		return buffer.toString();
	}

	private UpdateWriter newUpdateWriter(Integer rxSequence, TagWriter out, String encoding) {
		return new UpdateWriter(DummyDisplayContext.newInstance(), out, encoding, rxSequence);
	}

	private void notifyRequestDropped(UpdateWriter writer, ResKey messageKey, boolean reload) {
		writer.setRequestDropped();
		writer.add(JSSnipplet.createAlert(Resources.getInstance().getString(messageKey)));
		if (reload) {
			writer.add(MainLayout.createFullReload());
		}
		writer.endResponse();
	}

	private void unlock(RequestLock lock, Integer key, AJAXRequest ajaxRequest, SubsessionHandler rootHandler) {
		if (mustRunSingleThreaded(ajaxRequest)) {
			rootHandler.enableUpdate(false);

			lock.exitWriter(key);
		} else {
			lock.exitReader(key, ajaxRequest.getSource());
		}
	}

	private Integer lock(RequestLock lock, AJAXRequest ajaxRequest, SubsessionHandler rootHandler)
			throws InterruptedException, AJAXOutOfSequenceException, RequestTimeoutException, MaxRequestNumberException {
		Integer key;
		if (mustRunSingleThreaded(ajaxRequest)) {
			key = ajaxRequest.getTxSequence();
			lock.enterWriter(key);

			rootHandler.enableUpdate(true);
		} else {
			// The component itself may only be looked up after the lock
			// has been acquired. Otherwise, a concurrent command may
			// concurrently modify the component tree.
			key = lock.enterReader(ajaxRequest.getSource());
		}
		return key;
	}

	/**
	 * the context component of the given {@link AJAXRequest} or null to indicate that no
	 *         component was found and the processing must be stopped.
	 */
	private LayoutComponent getContextComponent(CommandRequest ajaxRequest, String sessionId, UserAgent userAgent,
			MainLayout mainLayout, DisplayContext displayContext) {
		try {
			LayoutComponent contextComponent = getContextComponent(mainLayout, ajaxRequest);

			if (contextComponent == null) {
				// This might happen in a late display request after a tab switch.
				Logger.info("Unable to find component for request " +
					"(source: '" + ajaxRequest.getContextComponentID() +
					"', target: '" + ajaxRequest.getTargetComponentID() +
					"', session: " + sessionId + ',' + userAgent + ')', AJAXServlet.class);
			} else {
				// Associate the context component with the current request. This is
				// required at least for the creation of the DisplayContext, see below.
				LayoutUtils.setContextComponent(displayContext, contextComponent);
			}

			return contextComponent;

		} catch (NumberFormatException e) {
		    // the path to the requested component is  (most likely) not valid any more
		    // the request can be ignored. (ASK TSA,BHU)
			Logger.info(
				"Request for path could not be handled, the component is most likely no longer visible. (session: "
						+ sessionId + ',' + userAgent + ')', AJAXServlet.class);
		    assert false: "Request for invalid path, the request will be ignored.";
			return null;
		}
         
	}

	private void setServiceMessage(HttpServletRequest request, AJAXRequest ajaxRequest) {
		UserAgent userAgent = UserAgent.getUserAgent(request);
		// Compose a message for later logging in case the request took some time
		String serviceMsg;
		if (ajaxRequest.hasTxSequence()) {
		    serviceMsg = "(" + ajaxRequest.getContextComponentID() + ','
		        + ajaxRequest.getTargetComponentID() + ',' + ajaxRequest.getCommand() 
				+ ", tx:" + ajaxRequest.getTxSequence() + ',' + userAgent + ')';
		} else {
		    serviceMsg = "(" + ajaxRequest.getContextComponentID() + ','
				+ ajaxRequest.getTargetComponentID() + ',' + ajaxRequest.getCommand() + ',' + userAgent + ')';
		}
		// Session id will be logged by TopLogicServlet
		
		request.setAttribute(DebugHelper.SERVICE_MSG_ATTR, serviceMsg);
	}

	private String getSourceComponentReference(AJAXRequest ajaxRequest, MainLayout mainLayout) {
		ComponentName componentID = ajaxRequest.getSource();
		LayoutComponent requestSourceComponent = getComponent(mainLayout, componentID);
		if (requestSourceComponent == null) {
			return SOURCE_COMPONENT_NOT_VISIBLE;
		}
		String sourceReference = LayoutUtils.createActionContext(requestSourceComponent);
		return sourceReference;
	}

	/**
	 * Determines whether the request requires to run without any concurrent thread.
	 */
	private static boolean mustRunSingleThreaded(AJAXRequest ajaxRequest) {
		return ajaxRequest.hasTxSequence() && ajaxRequest.getTxSequence().intValue() >= 0;
	}
    

	protected void processRequest(LayoutComponent contextComponent, CommandRequest ajaxRequest, UpdateWriter writer) {
    	DisplayContext context = writer.getDisplayContext();
        boolean isStaticCommand = ajaxRequest.isStatic();
        
        // Look the command that handles this request.
        String commandName = ajaxRequest.getCommand();
        CommandHandler command = getCommand(contextComponent, isStaticCommand, commandName);
        if (command == null) {
            String message = isStaticCommand ? "AJAX servlet received unknown static command: "
                    + commandName
                    : "Command not registered: '" + commandName + "' @ "
                            + contextComponent;
            writer.add(createErrorResult(message, null));
			return;
        }
        
		String message = contextComponent.getName().qualifiedName() + '~' + commandName;
		context.asRequest().setAttribute(DebugHelper.SERVICE_MSG_ATTR, message);

		Map<String, Object> theArguments = ajaxRequest.getArguments();
        
        // Compute the resulting actions by invoking the command.
        HandlerResult theResult = CommandDispatcher.getInstance().dispatchCommand(command, context, contextComponent, theArguments);

		ProcessingInfo theInfo = context.getProcessingInfo();
        if (theInfo != null) {
			context.asRequest().setAttribute(DebugHelper.SERVICE_MSG_ATTR,
				message + '[' + theInfo.getCommandID() + ']');
        }

		ClientAction[] directResultActions =
			ErrorHandlingHelper.transformHandlerResult(context.getWindowScope(), theResult);

		// Error sniplets must be added before validation actions, because validation actions
		// may produce frame reloads. Such reloads may prevent the correct execution of the 
		// alert actions. See Ticket #1130.
        writer.add(directResultActions);
	}

	/**
	 * Validates, if the current request can produce updates.
	 * 
	 * @param ajaxRequest
	 *        The request that triggered the update.
	 * 
	 * @see #validate(DisplayContext, SubsessionHandler, MainLayout, UpdateWriter, boolean)
	 */
	private void validate(SubsessionHandler rootHandler, MainLayout mainLayout, AJAXRequest ajaxRequest, UpdateWriter writer,
			DisplayContext context, boolean processExternalModelEvents) {
        // Do not send revalidation messages as response to static requests.
        // This might produce unexpected behavior, if e.g. the answer to 
        // a logging request modifies the client document.
		if (mustRunSingleThreaded(ajaxRequest)) {
			validate(context, rootHandler, mainLayout, writer, processExternalModelEvents);
		} else {
			// everyone mentions it is valid
			rootHandler.enableUpdate(false);
        }
	}

	/**
	 * Validates all views and produces corresponding updates to be marshaled back to the client.
	 * 
	 * @param rootHandler
	 *        The current session.
	 * @param mainLayout
	 *        The top-level component in the session.
	 * @param writer
	 *        Where to write the updates to.
	 * @param context
	 *        The current interaction.
	 */
	public static void validate(DisplayContext context, SubsessionHandler rootHandler, MainLayout mainLayout,
			UpdateWriter writer, boolean processExternalModelEvents) {
		if (processExternalModelEvents) {
			ClientAction[] actions = mainLayout.globallyValidateModel(context);
			writer.add(actions);
		}
		
		// everyone mentions it is valid
		rootHandler.enableUpdate(false);
		
		// Compute the actions that are necessary to re-validate the
		// client-side page.
		RevalidationVisitor.runValidation(mainLayout, writer);
	}
    
    /**
     * Lookup the component in whose context the command processing occurs.
     * 
     * <p>
     * The context component is either the component to which the request was
     * sent (for a non-static command), or the component that issued the request
     * from the client-side (for a static command).
     * </p>
     */
	private LayoutComponent getContextComponent(MainLayout mainLayout, CommandRequest ajaxRequest) {
        boolean isStaticCommand = ajaxRequest.isStatic();

		ComponentName componentID = ajaxRequest.getContextComponentID();
		LayoutComponent sourceComponent = getComponent(mainLayout, componentID);

        LayoutComponent targetComponent = 
            isStaticCommand ?
                null : 
                // Look up the component that is identified as target in the request.
                getComponent(mainLayout, ajaxRequest.getTargetComponentID());
        
        LayoutComponent contextComponent = 
            isStaticCommand?
                // Execute the static command in the context of the source 
                // component (which issued the request).
                sourceComponent :
                // This non-static request must be dispatched to a component.
                targetComponent;

        return contextComponent;
    }

	private LayoutComponent getComponent(MainLayout mainLayout, ComponentName componentID) {
		LayoutComponent result = mainLayout.getComponentByName(componentID);
		if (result == null) {
			Logger.warn("Component cannot be resolved: " + componentID, AJAXServlet.class);
			return null;
		}
		if (!result.isVisible()) {
			return null;
		}
		return result;
	}

    /**
     * Lookup the {@link CommandHandler} that should be executed. 
     */
    private CommandHandler getCommand(LayoutComponent contextComponent, boolean isStaticCommand, String commandName) {
    	CommandHandler command;
        if (isStaticCommand) {
            // This is a "static" request that is not dispatched to a component.
            command = commandsByName.get(commandName);
        } else {
			command = contextComponent.getCommandById(commandName);
        }
        return command;
    }


    /**
     * When running in debug mode, send an alert to the user interface.
     * Otherwise, only log the error.
     */
    private ClientAction createErrorResult(String errorMessage, Exception ex) {
		Logger.error(errorMessage, ex, AJAXServlet.class);

        if (LayoutComponent.isDebugHeadersEnabled()) {
            String theMessage = "";

            if (ex != null) {
                String theExMessage = ex.getMessage();

                theMessage = (StringServices.isEmpty(theExMessage)) ? " (" + ex.toString() + ")" 
                                                                    : " (" + theExMessage + ")";
            }
            return JSSnipplet.createAlert(
                    "Your last request could not be processed. " + 
                    "If this problem persists, try to reload the current page." +
                    "Description: " + errorMessage + theMessage);
        } else {
            // Prevent errors popping up at the GUI in the installed version.
            return null;
        }
    }
    
    /**
     * Lazy Accessor to AJAXRequestParser, cached per Thread. 
     */
    protected Object[] getParsers() throws ParserConfigurationException, SAXException {
        Object[] parsers = cachedParsers.get();
        if (parsers == null) {
			parsers = new Object[] { new AJAXRequestParser(), SAXUtil.newSAXParserNamespaceAware() };
            cachedParsers.set(parsers);
        }
        return parsers;
    }
    
    /**
     * Reset AJAXRequestParser, cached per Thread. 
     */
    protected final void clearParsers()  {
        cachedParsers.set(null);
    }

    /**
	 * Unmarshal a {@link AJAXRequest} object from the given input stream.
	 * 
	 * @param request
	 *        The original request.
	 * 
	 * @see AJAXRequestParser for a description of the expected XML format.
	 */
	protected AJAXRequest parseAJAXRequest(HttpServletRequest request, InputStream requestInputStream)
			throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException {

        Object[] parsers = getParsers();
        AJAXRequestParser requestParser = (AJAXRequestParser) parsers[0];
        SAXParser         saxParser     = (SAXParser)         parsers[1];

        // Switch on/off for debugging illegal requests.
        boolean readBuffered = true;
        
        InputStream in;
		if (readBuffered) {
			in = new PrefixBufferedInputStream(requestInputStream);
        } else {
        	in = requestInputStream;
        }
        try {
        	saxParser.parse(in, requestParser);

			String retryHeader = request.getHeader("X-Retry");
			if (retryHeader != null) {
				Logger.info("Received request retried '" + retryHeader + "' times from client '"
					+ request.getRemoteAddr() + "': " + requestContents(readBuffered, in), AJAXServlet.class);
			}
        } catch (SAXException ex) {
			Logger.error("Illegal request: '" + requestContents(readBuffered, in) + "'.", ex, AJAXServlet.class);
        	throw ex;
        }
        
        AJAXRequest ajaxRequest = requestParser.getAJAXRequest();
        return ajaxRequest;
    }

	private String requestContents(boolean readBuffered, InputStream in) {
		String result = "<unknown>";
		if (readBuffered) {
			try {
				PrefixBufferedInputStream buffer = (PrefixBufferedInputStream) in;
				result = new String(buffer.getBuffer(), 0, buffer.getSize(), LayoutConstants.UTF_8);
			} catch (Throwable ex) {
				// Ignore.
			}
		}
		return result;
	}

    /**
	 * Marshal the given array of {@link ClientAction} objects back to the caller.
	 * 
     * @param txSequence
	 *        The sequence number of the request that this result is the answer for.
	 * @see ClientAction#writeAsXML(DisplayContext, TagWriter) for a descripton of the generated
	 *      format.
	 */
	private void marshalResult(HttpServletRequest request, HttpServletResponse response, ClientAction[] ajaxResult,
			Integer txSequence) throws IOException {
		// see ticket #741
		response.setContentType("text/xml;charset=utf-8");
		// see Ticket #4213, #6742, and soap.js
		response.addHeader(HEADER_X_RESPONSE_SERVER, HEADER_X_RESPONSE_SERVER_TL_AJAX);

		TagWriter out = MainLayout.getTagWriter(request, response);

		out.writeXMLHeader(response.getCharacterEncoding());

		out.beginBeginTag("env:Envelope");
		out.writeAttribute("xmlns", HTMLConstants.XHTML_NS);
		out.writeAttribute("xmlns:env", "http://www.w3.org/2001/12/soap-envelope");
		out.writeAttribute(AJAXConstants.AJAX_XMLNS_ATTRIBUTE, AJAXConstants.AJAX_NAMESPACE);
		out.endBeginTag();
		out.beginTag("env:Body");
		
		out.beginBeginTag(AJAXConstants.AJAX_ACTIONS_ELEMENT);
		if (txSequence != null) {
		    out.writeAttribute(AJAXConstants.RX_ATTRIBUTE, txSequence.toString());
		}
		out.endBeginTag();
		
		
		DisplayContext context = DummyDisplayContext.newInstance();
		for (int n = 0; n < ajaxResult.length; n++) {
		    ajaxResult[n].writeAsXML(context, out);
		}
		
		out.endTag(AJAXConstants.AJAX_ACTIONS_ELEMENT);
		
		out.endTag("env:Body");
		out.endTag("env:Envelope");
		
		out.flush();
    }

    /**
     * A special AJAX-command to propagate client / java-script errors to the server.
     */ 
	public static final class AJAXLogHandler extends AbstractSystemAjaxCommand {
        
		public static final CommandHandler INSTANCE = newInstance(AJAXLogHandler.class, "log");

		public AJAXLogHandler(InstantiationContext context, Config config) {
			super(context, config);
        }

		@Override
		public HandlerResult handleCommand(DisplayContext context, 
            LayoutComponent aComponent, Object model, Map<String, Object> someArguments) 
        {
            StringBuffer logMessage = new StringBuffer(64 * someArguments.size());

            logMessage.append("Client-side message: ");
            logMessage.append((String) someArguments.get("message"));
            String level = null;
            boolean first = true;

			for (Map.Entry<String, Object> entry : someArguments.entrySet()) {
				String key = entry.getKey();
                String    value = (String)    entry.getValue();
                if ("message".equals(key)) {
                    continue; // was prepended before
                }
                if ("level".equals(key)) {
                    level = value;
                    continue;
                }

                first = separator(logMessage, first);
                logMessage.append(key).append(": '").append(value).append('\'');
            }

            first = separator(logMessage, first);
            logMessage.append("layout: '");
            logMessage.append(aComponent.getLocation());
            logMessage.append('\'');
            
            first = separator(logMessage, first);
            logMessage.append("session: ");
			logMessage.append(TopLogicServlet.getSessionId(context.asRequest()));
            
			logMessage.append(", ");
			logMessage.append(context.getUserAgent());

            logMessage.append(')');
            
            String message = logMessage.toString();
            if ("ERROR".equals(level)) {
				Logger.error(message, AJAXLogHandler.class);
            }
            else if ("WARN".equals(level)) {
				Logger.warn(message, AJAXLogHandler.class);
            }
            else if ("INFO".equals(level)) {
				Logger.info(message, AJAXLogHandler.class);
            }
            else {
				Logger.error("Missing required attribute 'level': " + message, AJAXLogHandler.class);
            }
            
            return HandlerResult.DEFAULT_RESULT;
        }

		private boolean separator(StringBuffer logMessage, boolean first) {
			if (first) {
			    first = false;
			    logMessage.append(" (");
			}
			else { 
			    logMessage.append(", ");
			}
			return first;
		}

		@Override
		protected boolean mustNotRecord(DisplayContext context, LayoutComponent component,
				Map<String, Object> someArguments) {
			return true;
		}
    }

}
