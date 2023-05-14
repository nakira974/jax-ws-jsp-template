package fr.aura.markandweb.gena_server.services;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;


@WebService
public class SoapEchoService {

    @Resource(name = "wsContext")
    private WebServiceContext wsContext;

    @WebMethod
    public String echo(@WebParam(name = "text") String text) {
        return text;
    }
}

