
package fr.aura.merkandweb.gena_server.controllers.xml;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebServiceClient(name = "EchoService", targetNamespace = "http://localhost:8083/echo", wsdlLocation = "file:/C:/Users/maxim/IdeaProjects/jax-ws-template/src/main/resources/echo.wsdl")
public class EchoService
    extends Service
{

    private static final URL ECHOSERVICE_WSDL_LOCATION;
    private static final WebServiceException ECHOSERVICE_EXCEPTION;
    private static final QName ECHOSERVICE_QNAME = new QName("http://localhost:8083/echo", "EchoService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/maxim/IdeaProjects/jax-ws-template/src/main/resources/echo.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ECHOSERVICE_WSDL_LOCATION = url;
        ECHOSERVICE_EXCEPTION = e;
    }

    public EchoService() {
        super(__getWsdlLocation(), ECHOSERVICE_QNAME);
    }

    public EchoService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ECHOSERVICE_QNAME, features);
    }

    public EchoService(URL wsdlLocation) {
        super(wsdlLocation, ECHOSERVICE_QNAME);
    }

    public EchoService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ECHOSERVICE_QNAME, features);
    }

    public EchoService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EchoService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns EchoServicePortType
     */
    @WebEndpoint(name = "EchoServicePort")
    public EchoServicePortType getEchoServicePort() {
        return super.getPort(new QName("http://localhost:8083/echo", "EchoServicePort"), EchoServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EchoServicePortType
     */
    @WebEndpoint(name = "EchoServicePort")
    public EchoServicePortType getEchoServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://localhost:8083/echo", "EchoServicePort"), EchoServicePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ECHOSERVICE_EXCEPTION!= null) {
            throw ECHOSERVICE_EXCEPTION;
        }
        return ECHOSERVICE_WSDL_LOCATION;
    }

}
