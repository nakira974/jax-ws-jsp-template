package fr.aura.markandweb.gena_server.beans.xml;

import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;

/**
 * JAX-WS clients generic implementation
 * @author maxim
 * @param <PortType> Target WSDL
 * */
public interface IXmlBean<PortType> {
    /**@return JAX-WS WSDL port*/
    PortType getPort();
    /**@return Created JAX-WS WSDL Service*/
    jakarta.xml.ws.Service createService();
}
