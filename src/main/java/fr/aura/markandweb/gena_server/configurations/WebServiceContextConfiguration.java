package fr.aura.markandweb.gena_server.configurations;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WebServiceContextConfiguration {

    private final String contextName = "java:comp/env/jakarta.xml.ws.WebServiceContext";

    @PostConstruct
    public void init() {
        try {

            // Create an instance of WebServiceContextProducer
            WebServiceContextProducer producer = new WebServiceContextProducer() {
                @Override
                public WebServiceContext createWebServiceContext(MessageContext msgContext) {
                    return (WebServiceContext) msgContext.get("jakarta.xml.ws.WebServiceContext");
                }
            };

            // Bind the producer to JNDI
            new InitialContext().bind(contextName, producer);

        } catch (NamingException e) {
            throw new RuntimeException("Failed to configure WebServiceContext", e);
        }
    }

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
