<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chat App</title>
    <link rel="stylesheet" type="text/css" href="css/app.css">
</head>
<body>

<h1>Welcome to My Chat App!</h1>

<div class="animation-container"></div>

<jsp:include page="chat.jsp" />

<script type="text/javascript">
    function updateAnimation() {
        // Get current hour
        var hour = new Date().getHours();

        // Set time-specific class
        if (hour >= 6 && hour <= 18) {
            document.querySelector('.animation-container').classList.add('day');
        } else {
            document.querySelector('.animation-container').classList.add('night');
        }
    }

    // Call the function when the page is loaded
    window.onload = updateAnimation;
</script>
</body>
</html>