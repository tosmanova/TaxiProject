<%@ page import="servlettest.model.User" %><%--
  Created by IntelliJ IDEA.
  User: andrii
  Date: 04.07.16
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% User transfered = (User)request.getAttribute("user");%>
<div class="container">
    <ul>
        <li>
            <div class="column">
                name : <%= transfered.getName()%>
            </div>
        </li>

        <li>
            <div class="column">
                id : <%= transfered.getId()%>
            </div>
        </li>

        <li>
            <div class="column">
                salary : <%= transfered.getSalary()%>
            </div>
        </li>

    </ul>
</div>
</body>
</html>
