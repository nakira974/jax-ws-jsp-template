package fr.aura.markandweb.gena_server.beans.xml;

import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is an abstract base class for implementing SOAP client/server interactions inside a JSF/Facelet.
 * It provides the necessary fields and constructor to build a service client, given a WSDL endpoint.
 * In addition, it implements the {@link fr.aura.markandweb.gena_server.beans.xml.IXmlBean} interface.
 *
 * <p>Methods:</p>
 * <ul>
 *  <li>{@link #getWsdlUrl()} : Returns the WSDL fetch URL of this bean.</li>
 *  <li>{@link #getServiceName()} : Returns the WSDL service name of this bean.</li>
 *  <li>{@link #getService()} : Returns the WSDL service instance of this bean.</li>
 *  <li>{@link #getServiceUrl()} : Returns the WSDL service URL of this bean.</li>
 *  <li>{@link #getLocalPart()} : Returns the WSDL service local part of this bean.</li>
 *  <li>{@link #XmlBeanBase(@NotNull String, @NotNull String)} : Constructor for creating a new instance with WSDL endpoint and local part.</li>
 * </ul>
 *
 * @param <PortType> The type of the service port used by this bean.
 * @see fr.aura.markandweb.gena_server.beans.xml.IXmlBean
 * @since Creation date: 17/05/2023
 * @author maxim
 */
public abstract class XmlBeanBase<PortType> implements IXmlBean<PortType>, Serializable {

    /**WSDL fetch URL*/
    private final URL wsdlUrl;

    /**WSDL service name*/
    private final QName serviceName;

    /**WSDL service instance*/
    private final Service service;

    /**WSDL service URL*/
    private final String serviceUrl;

    /**WSDL service local part*/
    private final String localPart;

    /**@return The bean WSDL fetch URL*/
    public @NotNull URL getWsdlUrl() {
        return wsdlUrl;
    }

    /**@return The bean WSDL service name*/
    public @NotNull QName getServiceName() {
        return serviceName;
    }

    /**@return The bean WSDL service instance*/
    public @NotNull Service getService() {
        return service;
    }

    /**@return The bean WSDL service URL*/
    public @NotNull String getServiceUrl() {
        return serviceUrl;
    }

    /**@return The bean WSDL service local part*/
    public @NotNull String getLocalPart() {
        return localPart;
    }


    /**JAX-WS Client bean constructor
     * @param wsdlUrl WSDL fetch url : <p><b><a href="https://localhost:8483/home/echo?wsd">https://localhost:8483/home/echo?wsd</a></b></p>
     * @param localPart WSDL service local part
     * @since 17/05/2023*/
    protected XmlBeanBase(@NotNull String wsdlUrl, @NotNull String localPart) throws MalformedURLException, WebServiceException {
        String regex = "\\?wsdl$"; // matches the ?wsdl at the end of the string
        this.localPart = localPart;
        this.serviceUrl = wsdlUrl.replaceAll(regex, "");
        try {
            this.wsdlUrl = new URL(wsdlUrl);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Bad WSDL endpoint format!");
        }
        try {
            this.serviceName = new QName(getServiceUrl(), getLocalPart());
        }catch (IllegalArgumentException ex){
            throw new MalformedURLException("Bad WSDL endpoint format!");
        }

        this.service = Service.create(getWsdlUrl(), getServiceName());
    }

    /**Default xml bean constructor only to implement : {@link jakarta.enterprise.context.RequestScoped}*/
    public XmlBeanBase() {

        this.wsdlUrl = null;
        this.serviceName = null;
        this.service = null;
        this.localPart = null;
        this.serviceUrl = null;
    }

    @Override
    public  @NotNull PortType getPort( @NotNull Class<PortType> portTypeClass) {
        return service.getPort(portTypeClass);
    }
}
