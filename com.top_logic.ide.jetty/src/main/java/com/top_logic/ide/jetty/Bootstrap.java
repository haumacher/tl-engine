/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.ide.jetty;

import java.awt.Desktop;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.servlet.TldScanner;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.xml.sax.SAXException;

import com.top_logic.basic.core.workspace.Environment;
import com.top_logic.basic.core.workspace.PathInfo;
import com.top_logic.basic.core.workspace.Workspace;

/**
 * Starts a <i>TopLogic</i> application module in development mode.
 * 
 * @see Shutdown
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class Bootstrap {

	private static final String OK_RESULT = "OK";

	static final String HOSTNAME = "localhost";

	static final String ADMIN_WEBAPP = "/admin";

	static final String STOP_SERVLET = "/stop";

	/**
	 * Main routine.
	 * 
	 * @param args
	 *        Supported arguments are <code>-port</code> and <code>-contextPath</code>.
	 */
	public static void main(String[] args) throws Exception {
		Thread.currentThread().setName("TL-Bootstrap");
		Bootstrap main = new Bootstrap();
		main.run(args);
	}

	private int _port = 8080;

	String _contextPath = "/webapp";

	private void run(String[] args) throws Exception {
		for (int n = 0, length = args.length; n < length; n++) {
			String arg = args[n];
			if (arg.equals("-port")) {
				_port = Integer.parseInt(args[++n]);
			}
			else if (arg.equals("-contextPath")) {
				_contextPath = args[++n];
				if (!_contextPath.startsWith("/")) {
					_contextPath = "/" + _contextPath;
				}
				if (_contextPath.equals(ADMIN_WEBAPP)) {
					throw new IllegalArgumentException("Context path is reserved: " + _contextPath);
				}
			}
			else {
				throw new IllegalArgumentException("Unknown option '" + arg + "'.");
			}
		}

		start();
	}

	private void start() throws Exception {
		String stopUrl = "http://" + HOSTNAME + ":" + _port + ADMIN_WEBAPP + STOP_SERVLET;
		stopPreviousApp(stopUrl);

		System.setProperty(Environment.DEVELOPER_MODE, "true");
		PathInfo paths = Workspace.getAppPaths();

		final Server server = new Server();

		ServerConnector connector = new ServerConnector(server);
		connector.setPort(_port);
		// open connector to bind port yet.
		connector.open();
		server.addConnector(connector);

		HandlerCollection handlers = new HandlerCollection();

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(_contextPath);
		webapp.setDefaultsDescriptor("com/top_logic/ide/jetty/webdefault.xml");

		List<URL> resourcePath = paths.getResourcePath();
		List<Resource> resources = new ArrayList<>(resourcePath.size());
		for (URL resourceUrl : resourcePath) {
			if (resourceUrl.getPath().endsWith(".war")) {
				resourceUrl = new URL("jar:" + resourceUrl.toExternalForm() + "!/");
			}
			resources.add(Resource.newResource(resourceUrl));
		}
		webapp.setBaseResource(new ResourceCollection(resources.toArray(new Resource[0])));

		List<URL> classPath = paths.getClassJars();
		for (URL entry : classPath) {
			webapp.getMetaData().addWebInfJar(Resource.newResource(entry));
		}

		for (File simulatedJar : paths.getClassFolders()) {
			// Simulated startup in a deployed context, where each JAR has its own META-INF
			// folder.
			// This is required to let the development container resolve web-fragment.xml
			// resources.
			webapp.getMetaData().addWebInfJar(Resource.newResource(simulatedJar));
		}

		List<String> configurationClasses =
			new ArrayList<>(Arrays.asList(WebAppContext.getDefaultConfigurationClasses()));
		configurationClasses.remove("org.eclipse.jetty.webapp.WebInfConfiguration");
		configurationClasses.add(0, WebInfConfiguration.class.getName());
		webapp.setConfigurationClasses(configurationClasses);

		handlers.addHandler(webapp);
		
		File tmpDir = new File("tmp/scratch");
		tmpDir.mkdirs();
		webapp.setAttribute(ServletContext.TEMPDIR, tmpDir);
		webapp.addBean(new JspStarter(webapp));

		ServletContextHandler adminHandler = new ServletContextHandler(handlers, ADMIN_WEBAPP);
		handlers.addHandler(adminHandler);

		Thread shutdown = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					server.stop();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};

		adminHandler.addServlet(new ServletHolder("stop",
			new HttpServlet() {
				@Override
				protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
						IOException {
					shutdown.start();

					resp.setStatus(HttpServletResponse.SC_OK);
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("utf-8");
					resp.getWriter().println(OK_RESULT);
				}
			}),
			STOP_SERVLET);

		ServletContextHandler rootContext = new ServletContextHandler(handlers, "/");
		rootContext.addServlet(new ServletHolder("redirect", new HttpServlet() {
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				resp.sendRedirect(_contextPath);
			}
		}), "/*");

		server.setHandler(handlers);

		server.start();

		Runtime.getRuntime().addShutdownHook(shutdown);

		String appUrl = "http://" + HOSTNAME + ":" + _port + _contextPath + "/";

		System.out.println("Server started: " + appUrl);
		System.out.println("Stop server accessing: " + stopUrl);

		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI(appUrl));
			}
		} catch (RuntimeException | Error ex) {
			System.err.println("Failed to launch browser: " + ex.getMessage());
		}

		server.join();
	}

	private void stopPreviousApp(String stopUrl) throws MalformedURLException, InterruptedException {
		URL url = new URL(stopUrl);
		try {
			URLConnection connection = url.openConnection();
			try (InputStream in = connection.getInputStream()) {
				if (connection instanceof HttpURLConnection) {
					int response = ((HttpURLConnection) connection).getResponseCode();
					if (response != HttpURLConnection.HTTP_OK) {
						errorCannotStop();
						System.exit(1);
					}
				}

				CharBuffer buffer = CharBuffer.allocate(10);
				try (Reader r = new InputStreamReader(in, "utf-8")) {
					while (buffer.position() < buffer.limit()) {
						int direct = r.read(buffer);
						if (direct < 0) {
							break;
						}
					}
				}
				buffer.flip();

				if (!OK_RESULT.equals(buffer.toString().trim())) {
					errorCannotStop();
					System.exit(1);
				}
			}

			// Previously running app was stopped. Give it some time to shut down.
			System.out.println("Stopped previously running app.");
			Thread.sleep(1000);
		} catch (IOException ex) {
			// No app currently running, continue.
		}
	}

	private void errorCannotStop() {
		System.err.println("Port '" + _port + "' is occupied.");
	}

	/**
	 * Helper to initialize the JSP compiler.
	 */
	public static class JspStarter extends AbstractLifeCycle
			implements ServletContextHandler.ServletContainerInitializerCaller {
		JettyJasperInitializer sci;

		ServletContextHandler context;

		/**
		 * Creates a {@link JspStarter}.
		 */
		public JspStarter(ServletContextHandler context) {
			this.sci = new JettyJasperInitializer() {
				@Override
				public TldScanner newTldScanner(ServletContext scannerContext, boolean namespaceAware, boolean validate,
						boolean blockExternal) {
					return new TldScanner(scannerContext, namespaceAware, validate, blockExternal) {
						@Override
						protected void scanResourcePaths(String startPath) throws IOException, SAXException {
							// Ignore, all TLDs are registered in web-fragment.xml declarations.
						}
					};
				}
			};
			this.context = context;
			StandardJarScanner jarScanner = new StandardJarScanner();
			jarScanner.setScanManifest(false);
			this.context.setAttribute("org.apache.tomcat.JarScanner", jarScanner);
		}

		@Override
		protected void doStart() throws Exception {
			ClassLoader old = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(context.getClassLoader());
			try {
				sci.onStartup(null, context.getServletContext());
				super.doStart();
			} finally {
				Thread.currentThread().setContextClassLoader(old);
			}
		}
	}

	/**
	 * Optimization for lookup of JARs from WEB-INF/lib in a highly modular application with may
	 * resource paths.
	 */
	public static class WebInfConfiguration extends org.eclipse.jetty.webapp.WebInfConfiguration {
		@Override
		protected List<Resource> findWebInfLibJars(WebAppContext context) throws Exception {
			Resource webInf = context.getWebInf();
			if (webInf == null || !webInf.exists())
				return null;

			List<Resource> jarResources = new ArrayList<>();
			Resource webInfLibs = webInf.addPath("/lib");
			if (webInfLibs.exists() && webInfLibs.isDirectory()) {
				if (webInfLibs instanceof ResourceCollection) {
					Resource[] resources = ((ResourceCollection) webInfLibs).getResources();
					for (Resource webInfLib : resources) {
						scan(jarResources, webInfLib);
					}
				} else {
					scan(jarResources, webInfLibs);
				}
			}
			Collections.sort(jarResources, (Resource r1, Resource r2) -> {
				try {
					return r1.getFile().getName().compareTo(r2.getFile().getName());
				} catch (IOException ex) {
					throw new IOError(ex);
				}
			});

			return jarResources;
		}

		private void scan(List<Resource> jarResources, Resource webInfLib) throws IOException, MalformedURLException {
			String[] files = webInfLib.list();
			if (files == null) {
				return;
			}
			for (int f = 0; f < files.length; f++) {
				Resource file = webInfLib.addPath(files[f]);
				String fnlc = file.getName().toLowerCase(Locale.ENGLISH);
				int dot = fnlc.lastIndexOf('.');
				String extension = (dot < 0 ? null : fnlc.substring(dot));
				if (extension != null && (extension.equals(".jar") || extension.equals(".zip"))) {
					jarResources.add(file);
				}
			}
		}
	}
}