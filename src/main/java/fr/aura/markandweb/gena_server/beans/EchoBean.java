package fr.aura.markandweb.gena_server.beans;

import fr.aura.markandweb.gena_server.beans.xml.XmlBeanBase;
import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;

@Named
@RequestScoped
public class EchoBean extends XmlBeanBase<EchoServicePortType> {

    private String echoedString;

    public EchoBean() {
        super();
    }

    public String getEchoedString() {
        return echoedString;
    }

    public void setEchoedString(String echoedString) {
        this.echoedString = echoedString;
    }

    public String echo(String message) {
        this.echoedString = getPort().echo(message);
        return null; // to avoid navigation
    }

    protected EchoBean(@NotNull String wsdlUrl, @NotNull String localPart) throws MalformedURLException, WebServiceException {
        super(wsdlUrl, localPart);
    }

    @Override
    public EchoServicePortType getPort() {
        return null;
    }
}
