package fr.aura.markandweb.gena_server.servlets;

import com.google.gson.Gson;
import fr.aura.markandweb.gena_server.beans.EchoBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

/**
 * This servlet is responsible for handling the chat feature of the GENA app.
 * It interacts with the {@link fr.aura.markandweb.gena_server.beans.EchoBean} to send and receive messages
 * and store the chat history.
 *
 * <p>Methods:</p>
 * <ul>
 *  <li>{@link #init()} : Initializes the servlet by creating a new instance of the {@link fr.aura.markandweb.gena_server.beans.EchoBean}.</li>
 *  <li>{@link #doPost(HttpServletRequest, HttpServletResponse)} : Receives new chat messages, sends them to EchoBean to be stored, and forwards to the chat.jsp page.</li>
 *  <li>{@link #doGet(HttpServletRequest, HttpServletResponse)} : Retrieves the chat history from EchoBean and sends it as a JSON response.</li>
 * </ul>
 *
 * @since Creation date: 17/05/2023
 * @author maxim
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    /**An instance of EchoBean to handle chat messages*/
    private EchoBean echoBean;

    /**
     * Initializes the servlet by creating a new instance of the {@link fr.aura.markandweb.gena_server.beans.EchoBean}.
     * Throws a ServletException if an error occurs during the creation of the EchoBean instance.
     *
     * @throws ServletException If an error occurs during the creation of the EchoBean instance.
     */
    @Override
    public void init() throws ServletException {
        try {
            echoBean = new EchoBean();
        } catch (MalformedURLException | WebServiceException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Receives new chat messages, sends them to EchoBean to be stored, and forwards to the chat.jsp page.
     * Also retrieves the chat history from EchoBean and sets it as an attribute of the request to be displayed on the page.
     *
     * @param request The HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response The HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If an input or output error occurs while the servlet is handling the POST request.
     * @throws IOException If the request or response cannot be handled.
     */
    @Override
    protected void doPost(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        getChatHistoryInjected(response);

        String message = request.getParameter("message");
        if (message != null) {
            echoBean.echo(message);
        }
        request.setAttribute("chatHistory", echoBean.getChatHistory());
        request.getRequestDispatcher("components/chat.jsp").forward(request, response);

    }

    private void getChatHistoryInjected(@NotNull HttpServletResponse response) throws IOException {
        List<String> chatHistory = echoBean.getChatHistory();
        String json = "";
        try{
            final String tempJson = new Gson().toJson(chatHistory);
            if(tempJson != null && !tempJson.equals("")){
                json = tempJson;
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(json);
    }

    /**
     * Retrieves the chat history from EchoBean and sends it as a JSON response.
     *
     * @param request The HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response The HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If an input or output error occurs while the servlet is handling the GET request.
     * @throws IOException If the request or response cannot be handled.
     */
    @Override
    protected void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        // Send the chat history as a JSON response
        getChatHistoryInjected(response);
    }
}
