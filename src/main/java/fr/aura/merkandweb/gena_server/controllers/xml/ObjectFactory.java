
package fr.aura.merkandweb.gena_server.controllers.xml;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.aura.merkandweb.gena_server.controllers.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _EchoRequest_QNAME = new QName("http://localhost:8083/echo/", "echoRequest");
    private static final QName _EchoResponse_QNAME = new QName("http://localhost:8083/echo/", "echoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.aura.merkandweb.gena_server.controllers.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://localhost:8083/echo/", name = "echoRequest")
    public JAXBElement<String> createEchoRequest(String value) {
        return new JAXBElement<>(_EchoRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://localhost:8083/echo/", name = "echoResponse")
    public JAXBElement<String> createEchoResponse(String value) {
        return new JAXBElement<>(_EchoResponse_QNAME, String.class, null, value);
    }

}
