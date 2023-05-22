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
        var hour = new Date().getHours();
        var container = document.querySelector('.animation-container');

        if (hour >= 6 && hour <= 18) {
            container.classList.remove('night');
            container.classList.add('day');
        } else {
            container.classList.remove('day');
            container.classList.add('night');
        }
    }

    // Call the function when the page is loaded
    window.addEventListener('load', updateAnimation);
</script>
</body>
</html>