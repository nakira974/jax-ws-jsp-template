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

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function() {
        // Fetch the initial chat history
        updateChatHistory();

        // Submit the message form via AJAX
        $('#messageForm').submit(function(event) {
            event.preventDefault(); // Prevent the form from submitting the traditional way
            $.post($(this).attr('action'), $(this).serialize(), function() {
                // Clear the input field and refresh the chat history
                $('#message').val('');
                updateChatHistory();
            });
        });

        // Function to fetch the updated chat history and update the UI
        function updateChatHistory() {
            $.getJSON('/chat', function(chatHistory) {
                var html = '';
                $.each(chatHistory, function(index, msg) {
                    html += '<li>' + msg + '</li>'
                });
                $('#chatHistory').html(html);
            });
        }
    });
</script>