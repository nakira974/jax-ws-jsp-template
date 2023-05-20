<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 20/05/2023
  Time: 12:35 am
--%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Chat App</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/chat.css">
</head>
<body>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <jsp:useBean id="echoBean" class="fr.aura.markandweb.gena_server.beans.EchoBean" scope="request"/>

    <div id="chatContainer">
            <h2>Chat history:</h2>

            <ul id="chatHistory">
                <c:forEach var="msg" items="${echoBean.chatHistory}">
                    <li>${msg}</li>
                </c:forEach>
            </ul>

            <form id="messageForm" method="post" action="${pageContext.request.contextPath}/chat">
                <label for="message">Type your message:</label>
                <input type="text" id="message" name="message" value="${echoBean.echoedString}">
                <button type="submit" id="submit">Send</button>
            </form>
    </div>

</body>
<script type="module" src="${pageContext.request.contextPath}/ts/dist/chat.js">
</script>
</html>