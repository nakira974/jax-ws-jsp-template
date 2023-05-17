package fr.aura.markandweb.gena_server.beans;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/** Base bean for designing SOAP client/server interactions inside a JSF/Facelet
 * @author maxim */
public abstract class XmlBeanBase<PortType> implements IXmlBean<PortType>{

    /**WSDL fetch URL*/
    protected final URL wsdlUrl;

    /**WSDL service name*/
    protected final QName serviceName;

    /**WSDL service instance*/
    protected final Service service;


    /**JAX-WS Client bean default constructor
     * @since 17/05/2023*/
    protected XmlBeanBase(@NotNull String wsdlUrl, @NotNull String localPart) throws MalformedURLException, WebServiceException {
        String regex = "\\?wsdl$"; // matches the ?wsdl at the end of the string
        String serviceUrl = wsdlUrl.replaceAll(regex, "");
        try {
            this.wsdlUrl = new URL(wsdlUrl);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Bad wsdl endpoint format!");
        }
        try {
            this.serviceName = new QName(serviceUrl, localPart);
        }catch (IllegalArgumentException ex){
            throw new MalformedURLException("Bad wsdl endpoint format!");
        }

        this.service = Service.create(this.wsdlUrl, this.serviceName);
    }

    @Override
    public Service createService() {
        return null;
    }
}
