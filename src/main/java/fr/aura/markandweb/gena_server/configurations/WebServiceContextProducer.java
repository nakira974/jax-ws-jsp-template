package fr.aura.markandweb.gena_server.configurations;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.jetbrains.annotations.NotNull;

/**
 * This abstract class is a CDI producer that provides access to the WebServiceContext
 * of a JAX-WS endpoint using the Jakarta EE annotations {@link Resource},
 * {@link Produces}, and {@link ApplicationScoped}. Subclasses must implement the
 * {@link #createWebServiceContext} method to create instances of
 * {@link jakarta.xml.ws.WebServiceContext} from the {@link jakarta.xml.ws.handler.MessageContext}
 * of incoming SOAP messages.
 */
public abstract class WebServiceContextProducer {

    /**
     * The WebServiceContext produced by this instance, injected via the {@link Resource}
     * annotation. This field is private and should not be accessed directly. Instead,
     * use the {@link #getWebServiceContext()} method, which is annotated with {@link Produces}
     * and {@link ApplicationScoped}.
     */
    @Resource(name = "ws-context")
    private WebServiceContext ctx;

    /**
     * Produces an instance of {@link jakarta.xml.ws.WebServiceContext} from this
     * producer's injected field {@link #ctx}. The instance is annotated with
     * {@link Produces} and {@link ApplicationScoped}, indicating that it can be
     * dependency-injected into other CDI beans.
     *
     * @return the WebServiceContext produced by this producer
     */
    @Produces
    @ApplicationScoped
    public @NotNull WebServiceContext getWebServiceContext() {
        return ctx;
    }

    /**
     * Creates an instance of {@link jakarta.xml.ws.WebServiceContext} from the given
     * {@link jakarta.xml.ws.handler.MessageContext}. Subclasses must implement this
     * method to provide context-dependent behavior for the WebServiceContext. The
     * method is called by the {@code getWebServiceContext()} method.
     *
     * @param msgContext the MessageContext from which to create the WebServiceContext
     * @return a new instance of WebServiceContext based on the given MessageContext
     */
    public abstract @NotNull WebServiceContext createWebServiceContext(@NotNull MessageContext msgContext);
}
