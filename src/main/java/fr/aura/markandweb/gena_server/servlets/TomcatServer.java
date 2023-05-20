
package fr.aura.markandweb.gena_server.servlets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.jetbrains.annotations.NotNull;

/**
 * This class is responsible for configuring and starting the embedded Tomcat server.
 * The server is started with the provided port and base directory.
 * This class was essentially designed for the future <u><b>docker support incoming</b></u>
 * <ul>
 *     <li>
 *         The <u>WEB_APP_DIR_LOCATION</u> property specifies the location of the web application directory to be deployed.
 *     </li>
 *     The <u>CONTEXT_PATH</u> property specifies the context path that will be used for the web application.
 *     </li>
 * </ul>
 <p>
 * This class is annotated with {@literal @}ApplicationScoped to ensure that only one instance of this class is created
 * and shared across the entire application. This is important for security reasons, as it ensures that there are no
 * duplicate instances of the Tomcat server running and listening on different ports.
 </p>
 * @since 19/05/2023
 * @author maxim
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

    /**Minimal tomcat starter for embedding*/
    private Tomcat tomcat;

    /**
     * Starts the embedded Tomcat server with the given port.
     *
     * @param port The port number to use for the server.
     * @throws ServletException If an issue occurs during initialization of the servlet.
     * @throws LifecycleException If there is a life-cycle error.
     */
    public void start(@NotNull Integer port) throws ServletException, LifecycleException {
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
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
        }
    }

    /**
     * Gets the Tomcat instance.
     *
     * @return The Tomcat instance.
     */
    public @NotNull Tomcat getTomcat() {
        return tomcat;
    }
}