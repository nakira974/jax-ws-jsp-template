package fr.aura.markandweb.gena_server.servlets;

import fr.aura.markandweb.gena_server.beans.EchoBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceException;

import java.io.IOException;
import java.net.MalformedURLException;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private EchoBean echoBean;

    @Override
    public void init() throws ServletException {
        try {
            echoBean = new EchoBean();
        } catch (MalformedURLException | WebServiceException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        if (message != null) {
            echoBean.echo(message);
        }
        request.setAttribute("chatHistory", echoBean.getChatHistory());
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }
}
