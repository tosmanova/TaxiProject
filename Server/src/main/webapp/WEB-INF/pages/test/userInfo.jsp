
<%@include file="include.jsp"%>

<html>
<head>
    <title>UserInfo</title>
</head>
<body>

<%--<% user transfered = (user) request.getAttribute("user");%>--%>
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
