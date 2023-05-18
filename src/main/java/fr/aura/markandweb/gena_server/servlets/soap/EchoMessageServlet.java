package fr.aura.markandweb.gena_server.servlets.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.soap.*;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import java.io.*;
import java.util.stream.Collectors;

/**
 * This is a template for xml WS, I'd like to demonstrate how cool and simple is a low level approach
 * to create performing asynchronous xml WS.
 * This kind of JAX-WS using a servlet to catch the main request is quite old I know,
 * but as a Java Web programmer it could be very useful to find a modern template to rely on.
 * @author maxim */
@WebServlet(name = "echoService", urlPatterns = {"/echo"})
@ServiceMode(value = Service.Mode.MESSAGE)
@WebService
public class EchoMessageServlet extends HttpServlet implements Provider<SOAPMessage> {

    /**The WSDL scheme endpoint*/
    public static final @NotNull String WSDL_ENDPOINT ="http://localhost:8083/echo?wsdl";

    /**The namespace URI used in the SOAP message*/
    public static final @NotNull String NAMESPACE_URI = "http://localhost:8083/echo";

    /**The local part used in the SOAP message*/
    public static final @NotNull String LOCAL_PART = "EchoService";

    /**SOAP default MIME format*/
    private static final @NotNull String SOAP_DEFAULT_MIME = "text/xml;charset=utf-8";

    /**
     *
     * @return JAX-WS namespace URI
     */
    public static @NotNull String getNamespaceUri(){
        return NAMESPACE_URI;
    }

    /**
     * Processes the incoming SOAP message, extracts the message content,
     * creates a new SOAP message with the same content as the response,
     * and returns it.
     * @param request The incoming SOAP message
     * @return A new SOAP message with the same content as the response
     */
    @Override
    @Contract("_ -> new")
    public SOAPMessage invoke(@NotNull SOAPMessage request) {
        try {
            // Get the SOAP envelope and body from the incoming message
            SOAPEnvelope envelope = request.getSOAPPart().getEnvelope();
            SOAPBody body = envelope.getBody();

            if (!body.hasFault()) {
                // Extract the message content from the SOAP element
                SOAPElement echoRequest = (SOAPElement) body.getChildElements(
                        new QName(NAMESPACE_URI, "echoRequest")).next();
                String message = echoRequest.getValue();

                // Create a new SOAP message with the same content as the response
                SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
                soapBody.addChildElement(new QName(NAMESPACE_URI, "echoResponse"))
                        .addTextNode(message);

                return soapMessage;
            }
        } catch (SOAPException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    /** Simple WebMethod to show how a client can send data to JAX-WS by being intercepted by the servlet before
     * @param message Message send from SOAP client
     * @return Reponse send to the SOAP client*/
    @WebMethod(operationName = "echo")
    public @WebResult(name = "echoResponse") String echo(@WebParam(name = "echoRequest") String message) {
        return message;
    }

    /**
     * Handles the HTTP POST servletRequest, extracts the SOAP message from the servletRequest,
     * invokes the SOAP service by calling the 'invoke' method, and writes the
     * resulting SOAP message to the servletResponse output stream.
     * @param servletRequest The HTTP servletRequest
     * @param servletResponse The HTTP servletResponse
     */
    @Override
    public void doPost(@NotNull HttpServletRequest servletRequest, @NotNull HttpServletResponse servletResponse) throws IOException {
        try {
            // Get the SOAP message from the servletRequest input stream
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage requestMessage = messageFactory.createMessage(null, servletRequest.getInputStream());

            // Invoke the SOAP service and get the servletResponse message
            SOAPMessage soapResponse = invoke(requestMessage);

            // Set the servletResponse content type and write the servletResponse message to the output stream
            servletResponse.setContentType(SOAP_DEFAULT_MIME);
            servletResponse.setStatus(HttpServletResponse.SC_OK);
            OutputStream networkStream = servletResponse.getOutputStream();
            soapResponse.writeTo(networkStream);
            networkStream.flush();
            networkStream.close();
        } catch (SOAPException e) {
            throw new RuntimeException("Error handling SOAP message", e);
        }
    }

    /**
     * Handles the HTTP GET servletRequest, and send back to the client the WSDL of the JAX-WS.
     * @param servletRequest The HTTP servletRequest
     * @param servletResponse The HTTP servletResponse
     */
    @Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setContentType(SOAP_DEFAULT_MIME);
        PrintWriter out = servletResponse.getWriter();

        // Load the WSDL definition from a file in the resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("echo.wsdl");
        if (inputStream == null) {
            throw new RuntimeException("WSDL not found");
        }

        // Read the contents of the file into a String
        String wsdl = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));

        // Write the WSDL to the servletResponse output stream
        out.write(wsdl);
    }

    
}