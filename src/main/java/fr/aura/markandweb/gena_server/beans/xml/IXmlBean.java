package fr.aura.markandweb.gena_server.beans.xml;

/**
 * JAX-WS clients generic implementation
 * @author maxim
 * @param <PortType> Target WSDL
 * */
public interface IXmlBean<PortType> {
    /**@return JAX-WS WSDL port*/
    PortType getPort(Class<PortType> portTypeClass);
}
