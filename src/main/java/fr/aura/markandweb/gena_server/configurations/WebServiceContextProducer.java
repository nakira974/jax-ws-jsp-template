package fr.aura.markandweb.gena_server.configurations;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

public abstract class WebServiceContextProducer {
    @Resource(name = "ws-context")
    private WebServiceContext ctx;

    @Produces
    @ApplicationScoped
    public WebServiceContext getWebServiceContext() {
        return ctx;
    }

    public abstract WebServiceContext createWebServiceContext(MessageContext msgContext);
}
