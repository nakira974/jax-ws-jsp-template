
package fr.aura.markandweb.gena_server.servlets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * This class is responsible for configuring and starting the embedded Tomcat server.
 * The server is started with the provided port and base directory.
 * <ul>
 *     <li>
 *         The <u>WEB_APP_DIR_LOCATION</u> property specifies the location of the web application directory to be deployed.
 *     </li>
 *     The <li><u>CONTEXT_PATH</u> property specifies the context path that will be used for the web application.
 *     </li>
 * </ul>
 */
@ApplicationScoped
public class TomcatServer extends HttpServlet {

    /**
     * The location of the web application directory to be deployed.
     */
    private static final String WEB_APP_DIR_LOCATION = "src/main/webapp";

    /**
     * The context path that will be used for the web application.
     */
    private static final String CONTEXT_PATH = "/";

    private Tomcat tomcat;

    /**
     * Starts the embedded Tomcat server with the given port.
     *
     * @param port The port number to use for the server.
     * @throws ServletException If an issue occurs during initialization of the servlet.
     * @throws LifecycleException If there is a life-cycle error.
     */
    public void start(int port) throws ServletException, LifecycleException {
        this.tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(".");
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp(CONTEXT_PATH, WEB_APP_DIR_LOCATION);
        tomcat.start();
    }

    /**
     * Stops the embedded Tomcat server.
     *
     * @throws LifecycleException If there is a life-cycle error.
     */
    public void stop() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
        tomcat.getServer().await();
    }
}