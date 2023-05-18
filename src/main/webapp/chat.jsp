<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Chat App</title>
</head>

<body>

<jsp:useBean id="echoBean" class="fr.aura.markandweb.gena_server.beans.EchoBean" scope="request"/>

<form>
    <input type="text" id="message" name="message" value="${echoBean.echoedString}">
    <button type="submit" id="submit">Send</button>
</form>

<h2>Chat history:</h2>

<ul id="chatHistory">
    <c:forEach var="msg" items="${echoBean.chatHistory}">
        <li>${msg}</li>
    </c:forEach>
</ul>

<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>

<script type='text/javascript'>
    $(document).ready(function() {
        $('form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                url: '/chat',
                type: 'POST',
                data: {'message': $('#message').val()},
                success: function(response) {
                    $('#chatHistory').empty();
                    $.each(response, function(index, message) {
                        $('<li>').text(message).appendTo('#chatHistory');
                    });
                    $('#message').val('');
                },
                error: function(xhr, status, error) {
                    alert('Error sending message!');
                    console.log(error);
                }
            });
        });
    });
</script>

</body>
</html>