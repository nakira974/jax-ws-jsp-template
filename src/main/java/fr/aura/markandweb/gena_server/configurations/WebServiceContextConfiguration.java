package fr.aura.markandweb.gena_server.configurations;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.jetbrains.annotations.NotNull;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * This class configures a JNDI context to provide access to the WebServiceContext
 * of a JAX-WS endpoint, using the Jakarta EE annotations {@code @PostConstruct}
 * and {@code @PreDestroy}. The configuration is based on an instance of
 * {@link WebServiceContextProducer} that is bound to a JNDI name defined by the
 * constant {@code contextName}. The producer creates instances of
 * {@link jakarta.xml.ws.WebServiceContext} from the {@link jakarta.xml.ws.handler.MessageContext}
 * of incoming SOAP messages.
 */
public class WebServiceContextConfiguration {

    /**
     * The JNDI name used to bind the {@link WebServiceContextProducer} instance
     * to the naming context. The name is "java:comp/env/jakarta.xml.ws.WebServiceContext".
     */
    private final String contextName = "java:comp/env/jakarta.xml.ws.WebServiceContext";

    /**
     * Initializes this configuration by creating an instance of {@link WebServiceContextProducer},
     * and binding it to the JNDI context using the name specified by {@code contextName}.
     * If the binding fails, this method throws a {@link RuntimeException}.
     */
    @PostConstruct
    public void init() {
        try {

            // Create an instance of WebServiceContextProducer
            WebServiceContextProducer producer = new WebServiceContextProducer() {
                @Override
                public @NotNull WebServiceContext createWebServiceContext(@NotNull MessageContext msgContext) {
                    return (WebServiceContext) msgContext.get("jakarta.xml.ws.WebServiceContext");
                }
            };

            // Bind the producer to JNDI
            new InitialContext().bind(contextName, producer);

        } catch (NamingException e) {
            throw new RuntimeException("Failed to configure WebServiceContext", e);
        }
    }

    /**
     * Cleans up this configuration by unbinding the {@link WebServiceContextProducer}
     * instance from the JNDI context using the name specified by {@code contextName}.
     * Any errors that occur during unbinding are logged but otherwise ignored.
     */
    @PreDestroy
    public void destroy() {
        try {
            // Get a reference to the naming context
            Context ctx = new InitialContext();

            // Unbind the producer from JNDI
            ctx.unbind(contextName);

        } catch (NamingException e) {
            System.out.println(e.getMessage());
            // Ignore any errors during destruction
        }
    }
}
