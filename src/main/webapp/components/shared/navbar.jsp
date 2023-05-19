<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 20/05/2023
  Time: 12:35 am
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head>
    <link rel="stylesheet" type="text/css" href="css/navbar.css">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Economica">
</head>

<div class="navbar-container">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/">
            <img src="img/logo.png" alt="Chat App">
        </a>
    </div>
    <div class="announcements">
        <span class="announcement">New feature: My Robots!</span>
        <span class="announcement">Join our community chat today!</span>
    </div>
    <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/profile">Profile</a>
            <ul class="submenu">
                <li><a href="${pageContext.request.contextPath}/my-robots">My Robots</a></li>
                <li><a href="${pageContext.request.contextPath}/my-bots">My Bots</a></li>
            </ul>
        </li>
        <c:if test="${empty sessionScope.user}">
            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </c:if>
    </ul>
</div>

<script>
    const navLinks = document.querySelectorAll('.nav-links li');
    navLinks.forEach(navLink => {
        navLink.addEventListener('click', () => {
            navLink.classList.toggle('active');
        });
    });
</script>