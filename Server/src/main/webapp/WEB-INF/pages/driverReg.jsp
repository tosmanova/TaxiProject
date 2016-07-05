<%--
  Created by IntelliJ IDEA.
  User: andrii
  Date: 04.07.16
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>driver register form</title>
</head>
<body>

<div class="container">
    <h1>Input Driver register info</h1>
    <form method="post" action="driverReg">

        <ul>
            <li> Input name:
                <input name="name" type="text">
            </li>
        </ul>
        <ul>
            <li> Input phone:
                <input name="phone" type="text">
            </li>
        </ul>
        <ul>
            <li> Input pass:
                <input name="pass" type="text">
            </li>
        </ul>

        <ul>
            <li> Input carModel:
                <input name="carModel" type="text">
            </li>
        </ul>
        <ul>
            <li> Input carColor:
                <input name="carColor" type="text">
            </li>
        </ul>
        <ul>
            <li> Input carNumber:
                <input name="carNumber" type="text">
            </li>
        </ul>

        <ul>
            <li> Submit:
                <input name="carNumber" type="submit">
            </li>
        </ul>

    </form>

</div>

</body>
</html>
