package fr.aura.markandweb.gena_server;

import fr.aura.markandweb.gena_server.servlets.TomcatServer;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

/**
 * This class represents the main entry point of the GENA app backend.
 * It initializes the CDI container and starts the Tomcat server on a specified port, handling requests from the client.
 *
 * <p>Methods:</p>
 * <ul>
 *  <li>{@link #main(String[])} : The entry point of the application. Initializes the CDI container and starts the Tomcat server.</li>
 * </ul>
 *
 * @since Creation date: 17/05/2023
 * @author maxim
 */
public class Program {

    /**
     * The entry point of the application. Initializes the CDI container and starts the Tomcat server.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // Get the Tomcat server instance from the container
            TomcatServer server = container.select(TomcatServer.class).get();
            // Start the server on a specified port
            server.start(8083);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
