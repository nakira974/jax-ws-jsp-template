package fr.aura.markandweb.gena_server.servlets;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.aura.markandweb.gena_server.servlets.soap.EchoService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.xml.soap.*;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private final WebServiceContext wsContext;
    private String title;

    @Inject
    public HelloServlet(WebServiceContext ctx){
        wsContext = ctx;
    }
    public void init() {
        title = "Hello World!";
    }


    public void doGet(@NotNull  HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException {
        EchoService echoService = new EchoService();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");
        out.println("<h1>" + title + "</h1>");
        try {
            // Create a new SOAP message with the echoRequest element
            SOAPMessage soapRequest = MessageFactory.newInstance().createMessage();
            SOAPEnvelope envelope = soapRequest.getSOAPPart().getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement echoRequest = body.addChildElement(new QName(EchoService.getNamespaceUri(), "echoRequest"));
            echoRequest.addTextNode("Test string");

            // Set the SOAP message in the request message context
            try {
                MessageContext messageContext = wsContext.getMessageContext();
                if (messageContext != null) {
                    messageContext.put(MessageContext.MESSAGE_OUTBOUND_PROPERTY, soapRequest);
                } else {
                    System.err.println("MessageContext is null.");
                    return;
                }
            }catch (IllegalStateException ex){
                System.err.println(ex.getMessage());
                return;
            }

            // Make an HTTP POST request to invoke the service
            URL url = new URL("http://localhost:8080/EchoService");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");

            // Read the response message from the connection input stream
            InputStream inputStream = connection.getInputStream();
            SOAPMessage soapResponse = MessageFactory.newInstance().createMessage(null, inputStream);

            // Extract the response content and print it
            String responseContent = soapResponse.getSOAPBody().getChildNodes().item(0).getTextContent();
            out.println("<p>Response from EchoService: " + responseContent+"</p>");
        } catch (SOAPException ex) {
            System.err.println(ex.getMessage());
            return;
        }


        // Hello
        out.println("</body></html>");
    }

    protected void doPost(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        if (method.equalsIgnoreCase("PATCH")) {
            // Handle PATCH request
            // ...
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    public void destroy() {
    }
}