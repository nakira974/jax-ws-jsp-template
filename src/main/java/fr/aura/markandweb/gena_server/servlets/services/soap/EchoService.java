package fr.aura.markandweb.gena_server.servlets.services.soap;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.soap.*;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

import javax.xml.namespace.QName;
import java.io.IOException;

@WebServlet(urlPatterns = {"/EchoService"})
@ServiceMode(value = Service.Mode.MESSAGE)
public class EchoService extends HttpServlet implements Provider<SOAPMessage> {
    // The namespace URI and local part used in the SOAP message
    private static final String NAMESPACE_URI = "http://example.org/echo";
    private static final String LOCAL_PART = "echoRequest";

    /**
     *
     * @return Service's namespace
     */
    public static String getNamespaceUri(){
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
    public SOAPMessage invoke(SOAPMessage request) {
        try {
            // Get the SOAP envelope and body from the incoming message
            SOAPEnvelope envelope = request.getSOAPPart().getEnvelope();
            SOAPBody body = envelope.getBody();

            if (!body.hasFault()) {
                // Extract the message content from the SOAP element
                SOAPElement echoRequest = (SOAPElement) body.getChildElements(
                        new QName(NAMESPACE_URI, LOCAL_PART)).next();
                String message = echoRequest.getValue();

                // Create a new SOAP message with the same content as the response
                SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
                soapBody.addChildElement(new QName(NAMESPACE_URI, "echoResponse"))
                        .addTextNode(message);

                return soapMessage;
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Handles the HTTP POST request, extracts the SOAP message from the request,
     * invokes the SOAP service by calling the 'invoke' method, and writes the
     * resulting SOAP message to the response output stream.
     * @param req The HTTP request
     * @param resp The HTTP response
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Get the WebServiceContext and MessageContext
        WebServiceContext wsContext = (WebServiceContext) req.getAttribute(WebServiceContext.class.getName());
        MessageContext msgContext = wsContext.getMessageContext();

        // Extract the SOAP message from the request
        SOAPMessage request = (SOAPMessage) msgContext.get(MessageContext.SERVLET_REQUEST);

        // Invoke the SOAP service and get the resulting SOAP message
        SOAPMessage response = invoke(request);

        if (response != null) {
            try {
                // Set the response HTTP status and content type headers
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("text/xml;charset=utf-8");

                // Write the SOAP message to the response output stream
                response.writeTo(resp.getOutputStream());
            } catch (SOAPException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}