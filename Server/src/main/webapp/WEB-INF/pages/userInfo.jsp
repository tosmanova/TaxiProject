<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>UserInfo</title>
</head>
<body>

<%--<% User transfered = (User) request.getAttribute("user");%>--%>
<c:set var="transfered" value="${requestScope.user}"/>

<div class="container">
    <ul>
        <li>
            <div class="column">
                name : ${transfered.name}
            </div>
        </li>

        <li>
            <div class="column">
                phone : ${transfered.phone}
            </div>
        </li>

        <li>
            <div class="column">
                pass : ${transfered.pass}
            </div>
        </li>

    </ul>
</div>
</body>
</html>
