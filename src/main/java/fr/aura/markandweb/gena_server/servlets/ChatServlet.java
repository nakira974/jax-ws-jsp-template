package fr.aura.markandweb.gena_server.servlets;

import com.google.gson.Gson;
import fr.aura.markandweb.gena_server.beans.EchoBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

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

        // Send the chat history as a JSON response
        List<String> chatHistory = echoBean.getChatHistory();
        String json = new Gson().toJson(chatHistory);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Send the chat history as a JSON response
        List<String> chatHistory = echoBean.getChatHistory();
        String json = new Gson().toJson(chatHistory);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(json);
    }
}
