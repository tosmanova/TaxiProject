<%@include file="main-page-head.jsp" %>

<c:set var="transfered" value="${requestScope.order}"/>

<form method="post" action="create-order" class="form-horizontal">
    <h3> Find taxi in Kiev: </h3>

    <div class="form-group">
        <label class="col-xs-12">My phone: ${transfered.userPhone}</label>
    </div>

    <div class="form-group">
        <label class="col-xs-12">I want go from: ${transfered.from}</label>
    </div>

    <div class="form-group">
        <label class="col-xs-12">I want go to: </label>
    </div>

    <div class="form-group">
        <label class="col-xs-12"> Distance:</label>
    </div>

    <div class="form-group">
        <label class="col-xs-12"> Price:</label>
    </div>

    <div class="form-group">
        <label for="addPrice" class="col-xs-6"> Add to Price:</label>
        <div class="col-xs-8">
            <div class="col-xs-6">
                <input id="addPrice" name="addPrice" placeholder="0" class="form-control">
            </div>
            <div class="col-xs-4">
                <button type="submit" name="button" value="addPrice" class="btn btn-primary btn-block">
                    Add price
                </button>
            </div>
        </div>
    </div>

    <div class="form-group">
            <label class="col-xs-6"> Order created:</label>
            <label class="col-xs-6"> Wait time:</label>
    </div>


</form>

<%@include file="main-page-bottom.jsp" %>
