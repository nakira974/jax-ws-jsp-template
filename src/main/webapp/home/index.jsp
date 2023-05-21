<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 20/05/2023
  Time: 12:35 am
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chat App</title>
    <link rel="stylesheet" type="text/css" href="../css/index.css">
    <link rel="stylesheet" type="text/css" href="css/sun-moon.css">
</head>
<body>

<div>
    <jsp:include page="../components/shared/navbar.jsp" />
</div>

<h1>Welcome to My Chat App!</h1>

<div class="animation-container"></div>
<div>
    <jsp:include page="../components/chat.jsp" />
</div>

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