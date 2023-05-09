package fr.aura.markandweb.gena_server.servlets;

import java.io.*;

import fr.aura.markandweb.gena_server.servlets.services.soap.EchoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.xml.soap.*;

import javax.xml.namespace.QName;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EchoService echoService = new EchoService();
        try {
            // Create a sample SOAP message with a test string as the message content
            SOAPMessage msg = MessageFactory.newInstance().createMessage();
            SOAPBody body = msg.getSOAPPart().getEnvelope().getBody();
            SOAPElement echoRequest = body.addChildElement(new QName(EchoService.getNamespaceUri(), "echoRequest"));
            echoRequest.addTextNode("Test string");

            // Invoke the EchoService with the sample SOAP message
            SOAPMessage soapMessage = echoService.invoke(msg);

            // Extract the response content and print it
            String responseContent = soapMessage.getSOAPBody().getChildNodes().item(0).getTextContent();
            System.out.println("<p>Response from EchoService: " + responseContent + "</p>");
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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