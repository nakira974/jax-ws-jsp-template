package fr.aura.markandweb.gena_server.beans.xml;

import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/** Base bean for designing SOAP client/server interactions inside a JSF/Facelet
 * @see fr.aura.markandweb.gena_server.beans.xml.IXmlBean
 * @author maxim */
public abstract class XmlBeanBase<PortType> implements IXmlBean<PortType>{

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
    public URL getWsdlUrl() {
        return wsdlUrl;
    }

    /**@return The bean WSDL service name*/
    public QName getServiceName() {
        return serviceName;
    }

    /**@return The bean WSDL service instance*/
    public Service getService() {
        return service;
    }

    /**@return The bean WSDL service URL*/
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**@return The bean WSDL service local part*/
    public String getLocalPart() {
        return localPart;
    }


    /**JAX-WS Client bean constructor
     * @param wsdlUrl WSDL fetch url : <p><b><a href="http://localhost:8083/echo?wsd">http://localhost:8083/echo?wsd</a></b></p>
     * @param localPart WSDL service local part
     * @since 17/05/2023*/
    protected XmlBeanBase(@NotNull String wsdlUrl, @NotNull String localPart) throws MalformedURLException, WebServiceException {
        String regex = "\\?wsdl$"; // matches the ?wsdl at the end of the string
        this.localPart = localPart;
        this.serviceUrl = wsdlUrl.replaceAll(regex, "");
        try {
            this.wsdlUrl = new URL(wsdlUrl);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Bad wsdl endpoint format!");
        }
        try {
            this.serviceName = new QName(getServiceUrl(), getLocalPart());
        }catch (IllegalArgumentException ex){
            throw new MalformedURLException("Bad wsdl endpoint format!");
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
    public PortType getPort(Class<PortType> portTypeClass) {
        return service.getPort(portTypeClass);
    }
}
