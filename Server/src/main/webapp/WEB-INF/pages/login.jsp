<%@include file="main-page-head.jsp"%>


<form method="post" action="login" class="form-horizontal">

    <h3> Login: </h3>

    <div class="form-group">
        <label for="myPhone" class="col-xs-6"> My phone:</label>
        <div class="col-xs-8">
            <input type="tel" name="phone" id="myPhone" placeholder="Phone number." class="form-control">
        </div>
    </div>

    <div class="form-group">
        <label for="pass" class="col-xs-6"> Create password:</label>
        <div class="col-xs-8">
            <input id="pass" type="password" name="pass" class="form-control">
        </div>
    </div>

    <div class="form-group">
        <div class="col-xs-8 ">
            <button type="submit" name="register" class="btn btn-primary btn-block">Login</button>
        </div>
    </div>

</form>

<%@include file="main-page-bottom.jsp"%>
