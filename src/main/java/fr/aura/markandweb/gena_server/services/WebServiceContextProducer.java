package fr.aura.markandweb.gena_server.services;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.xml.ws.WebServiceContext;

import javax.naming.NamingException;


@ApplicationScoped
public class WebServiceContextProducer {

    @Resource(lookup = "java:comp/env/ws-context")
    WebServiceContext webServiceContext;

    @Produces
    public WebServiceContext createWebServiceContext() throws NamingException {
        return this.webServiceContext;
    }
}

