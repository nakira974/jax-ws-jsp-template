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