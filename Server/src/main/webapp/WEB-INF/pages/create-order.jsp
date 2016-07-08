
<%@include file="main-page-head.jsp"%>

<form method="post" action="create-order" class="form-horizontal">
    <h3> Find taxi in Kiev: </h3>

    <div class="form-group">
        <label for="goFrom" class="col-md-6">I want go from:</label>
        <div class="col-xs-12">
            <input id="goFrom" name="goFrom" placeholder="Input street and house num." class="form-control">
        </div>
    </div>

    <div class="form-group">
        <label for="goTo" class="col-xs-6">I want go to:</label>
        <div class="col-xs-12">
            <input id="goTo" name="goTo" placeholder="Input street and house num." class="form-control">
        </div>
    </div>

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
        <div class="col-xs-8 checkbox">
            <label><input name="rememberMe" type="checkbox"> Remember me</label>
        </div>
    </div>

    <div class="form-group">
        <div class="col-xs-8 ">
            <button type="submit" name="calculateButton" class="btn btn-primary btn-block">Calculate</button>
        </div>
    </div>
</form>

<%@include file="main-page-bottom.jsp"%>