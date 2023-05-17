package fr.aura.markandweb.gena_server.beans;

import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;

public class EchoBean extends XmlBeanBase<EchoServicePortType> {

    protected EchoBean(@NotNull String wsdlUrl, @NotNull String localPart) throws MalformedURLException, WebServiceException {
        super(wsdlUrl, localPart);
    }

    @Override
    public EchoServicePortType getPort() {
        return null;
    }
}
