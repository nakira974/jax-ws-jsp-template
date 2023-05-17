package fr.aura.markandweb.gena_server.servlets;

import java.io.*;
import java.net.URL;

import fr.aura.merkandweb.gena_server.controllers.xml.EchoService;
import fr.aura.merkandweb.gena_server.controllers.xml.EchoServicePortType;
import jakarta.jws.WebService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.xml.ws.Service;

import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String title;
    public void init() {
        title = "Hello World!";
    }


    /**
     * Handles the HTTP GET servletRequest, and send a SOAP servletRequest then print the result
     * @param servletRequest The HTTP servletRequest
     * @param servletResponse The HTTP servletResponse
     */
    public void doGet(@NotNull  HttpServletRequest servletRequest, @NotNull HttpServletResponse servletResponse) throws IOException {
        PrintWriter out = servletResponse.getWriter();
        servletResponse.setContentType("text/html");
        out.println("<html><body>");
        out.println("<h1>" + title + "</h1>");
        try {
            // Create a URL object pointing to the WSDL of the Webservice
            URL wsdlUrl = new URL("http://localhost:8083/echo?wsdl");

            // Create a QName object representing the name of the Webservice
            QName serviceName = new QName("http://localhost:8083/echo", "EchoService");

            // Create a Service object using the Service.create() method
            final Service service;
            try{
                service   =  Service.create(wsdlUrl, serviceName);

            }catch (WebServiceException ex){
                System.err.println(ex.getMessage());
                return;
            }

            // Get the EchoPortType from the Service
            EchoServicePortType echoPort = service.getPort(EchoServicePortType.class);

            // Call the echo() method on the EchoPortType
            String echoedString = echoPort.echo("Hello World!");

            // Print the result
            out.println("<p>Result: " + echoedString + "</p>");
            // Process the SOAP servletResponse
            System.out.println(servletResponse);

        } catch (Exception e) {
            // Handle exception
            System.err.println(e.getMessage());
        }


        // Hello
        out.println("</body></html>");
    }

    /**
     * Handles the HTTP PATCH servletRequest
     * @param servletRequest The HTTP servletRequest
     * @param servletResponse The HTTP servletResponse
     */
    protected void doPost(@NotNull HttpServletRequest servletRequest,@NotNull HttpServletResponse servletResponse)
            throws ServletException, IOException {
        String method = servletRequest.getMethod();
        if (method.equalsIgnoreCase("PATCH")) {
            // Handle PATCH servletRequest
            // ...
        } else {
            servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}