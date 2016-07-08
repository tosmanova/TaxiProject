<%@include file="../include.jsp"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Online taxi program</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <style>

        .container{
            color: darkorange;
        }
        .btn {
            color: white;
            background-color: orange;
            border-color: #5e5e5e;
        }
        #sidebar{
            margin-top: 100px;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="col-xs-4 col-xs-offset-7" id="register-block">
        <div class="col-xs-12">
            <h3><a href="main-page" id="registerRef">register</a> | <a href="main-page" id="loginRef">login</a></h3>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-3 col-xs-offset-2"><img src="img/yellow-taxi.jpg"></div>
        <div class="col-xs-6 col-xs-offset-1"><h1> Online taxi program </h1></div>
    </div>

    <div class="row">
        <div class="col-xs-4">

            <div id = "sidebar" class="col-xs-12">
                <h3> Invite Drivers!</h3>
                <p> Take special possibilities for work !</p>
                <button type="button" class="btn btn-primary btn-lg" >Start Driving</button>
            </div>
        </div>

        <div class="col-xs-7 col-xs-offset-1">

            <%@include file="../create-order.jsp"%>
        </div>

    </div>
</div>

<div class="row">

</div>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>
