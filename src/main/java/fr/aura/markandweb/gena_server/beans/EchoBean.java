package fr.aura.markandweb.gena_server.beans;

import fr.aura.markandweb.gena_server.beans.xml.XmlBeanBase;
import fr.aura.markandweb.gena_server.servlets.soap.EchoMessageServlet;
import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/** Simple Java Bean for interacting with a SOAP web service that provides an <b style="color:#d87d58"><u>echo</u></b> method.
 * The <b style="color:#d87d58"><u>EchoBean</u></b> extends the  {@link XmlBeanBase<EchoServicePortType>} class,
 * which provides the ability to communicate with a SOAP web service using a JAX-WS client.
 * @see fr.aura.markandweb.gena_server.beans.xml.IXmlBean
 * @see fr.aura.markandweb.gena_server.beans.xml.XmlBeanBase
 * @author maxim */
@Named
@RequestScoped
public class EchoBean extends XmlBeanBase<EchoServicePortType> {

    /**String sent to the SOAP endpoint*/
    private String echoedString;

    /**Messages history*/
    private List<String> chatHistory = new ArrayList<>();

    /**EchoBean default constructor
     *@exception MalformedURLException The service namespace is incorrect
     * @exception WebServiceException The JAX-WS client runtime has failed while creating the service*/
    public EchoBean() throws MalformedURLException, WebServiceException {
        super(EchoMessageServlet.WSDL_ENDPOINT, EchoMessageServlet.LOCAL_PART);
        chatHistory = new ArrayList<>();
    }

    /**@param localPart WSDL service local part
     * @param wsdlUrl WSDL fetch endpoint
     * @exception MalformedURLException The service namespace is incorrect
     * @exception WebServiceException The JAX-WS client runtime has failed while creating the service*/
    protected EchoBean(@NotNull String wsdlUrl, @NotNull String localPart) throws MalformedURLException, WebServiceException {
        super(wsdlUrl, localPart);
    }

    /**@return The last response from the SOAP endpoint*/
    public String getEchoedString() {
        return echoedString;
    }

    /** Define the message to send
     * @param echoedString Message to send to the SOAP endpoint*/
    public void setEchoedString(String echoedString) {
        this.echoedString = echoedString;
    }

    /** Send a POST over SOAP to call the "echo" method
     * @param message Message to send to the SOAP endpoint
     * @return Response from the SOAP endpoint
     */
    public String echo(String message) {
        getChatHistory().add(String.format("%s : %s", "User", message));
        setEchoedString(getPort().echo(message));
        getChatHistory().add(String.format("%s : %s", "Server", getEchoedString()));
        return getEchoedString();
    }

    /**@return The JAX-WS Port Type of the current bean*/
    public EchoServicePortType getPort() {
        return super.getPort(EchoServicePortType.class);
    }

    /**@return Messages history between the client and the server*/
    public List<String> getChatHistory() {
        return chatHistory;
    }
}
