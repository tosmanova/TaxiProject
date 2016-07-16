<%@include file="main-page-head.jsp"%>

<form method="post" action="passenger-register" class="form-horizontal">
    <h3> Please, enter registration info: </h3>

    <h5> Registered passenger take 5% bonus! </h5>

    <div class="form-group">
        <label for="myPhone" class="col-xs-6"> My phone:</label>
        <div class="col-xs-8">
            <input type="tel" name="phone" id="myPhone" placeholder="Phone number." class="form-control">
        </div>
    </div>

    <div class="form-group">
        <label for="myName" class="col-xs-6"> My name:</label>
        <div class="col-xs-8">
            <input id="myName" name="name" placeholder="Name." class="form-control">
        </div>
    </div>

    <div class="form-group">
        <label for="pass" class="col-xs-6"> Create password:</label>
        <div class="col-xs-8">
            <input id="pass" type="password" name="pass" class="form-control">
        </div>
    </div>

    <div class="form-group">
        <label for="homeAddress" class="col-xs-6"> My home address:</label>
        <div class="col-xs-8">
            <input id="homeAddress" name="homeAddress" placeholder="Street home-number" class="form-control">
        </div>
    </div>

    <div class="form-group">
        <div class="col-xs-8 ">
            <button type="submit" name="register" class="btn btn-primary btn-block">Register</button>
        </div>
    </div>

</form>


<%@include file="main-page-bottom.jsp"%>
