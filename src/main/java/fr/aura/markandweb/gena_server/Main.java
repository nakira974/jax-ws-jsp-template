package fr.aura.markandweb.gena_server;

import fr.aura.markandweb.gena_server.servlets.TomcatServer;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.apache.catalina.LifecycleException;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the main entry point of the GENA app backend in an embedded context like Docker or Kubernetes.
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
public class Main {

    /**
     * The entry point of the application. Initializes the CDI container and starts the Tomcat server.
     *
     * @param args The command line arguments.
     */
    public static void main(@NotNull String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // Get the Tomcat server instance from the container
            TomcatServer server = container.select(TomcatServer.class).get();
            // Start the server on a specified port
            server.start(8083);

            // Wait for the server to stop
            server.getTomcat().getServer().await();
        } catch (LifecycleException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Error starting server");
            // Attempt to stop the server gracefully
            stopServerGracefully(ex);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Attempts to stop the Tomcat server gracefully and exits the program with an error status code.
     *
     * @param ex The exception that occurred during startup.
     */
    private static void stopServerGracefully(@NotNull LifecycleException ex) {
        System.err.println("stopServerGracefully has been called, fatal error!");
        System.err.println(ex.getMessage());
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // Get the Tomcat server instance from the container and stop it
            TomcatServer server = container.select(TomcatServer.class).get();
            server.stop();
        } catch (LifecycleException ex2) {
            System.err.println("A LifecycleException has occurred!");
            System.err.println(ex2.getMessage());
            System.err.println("Error stopping server!");
        }
        System.exit(1);
    }
}