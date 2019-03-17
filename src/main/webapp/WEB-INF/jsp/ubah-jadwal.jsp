<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Ubah Jadwal</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <h3>Form ubah jadwal tertentu</h3>
            <form action="/ubah-jadwal" method="post">
                <div class="form-group">
                    <label for="partikel">Pilih Partikel : </label>
                    <input type="text" class="form-control" id="partikel" name="partikel">
                </div>
                <input type="submit" class="btn btn-success" value="Submit">
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>