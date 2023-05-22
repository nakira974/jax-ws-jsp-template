
package fr.aura.merkandweb.gena_server.controllers.xml;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebService(name = "EchoServicePortType", targetNamespace = "http://localhost:8083/home/echo")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EchoServicePortType {


    /**
     * 
     * @param echoRequest
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "echoResponse", targetNamespace = "http://localhost:8083/home/echo", partName = "echoResponse")
    public String echo(
        @WebParam(name = "echoRequest", targetNamespace = "http://localhost:8083/home/echo", partName = "echoRequest")
        String echoRequest);

}
