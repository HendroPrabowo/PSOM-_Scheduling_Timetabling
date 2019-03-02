<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Kelas</h1>
    </div>

    <table id="table" class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Inisial</th>
            <th>Nama</th>
            <th>Angkatan</th>
            <th>Jumlah</th>
            <th>Action</th>
        </tr>
        </thead>
        <c:forEach var="kelas" items="${kelass}">
            <tr>
                <td>${kelas.inisial}</td>
                <td>${kelas.nama}</td>
                <td>${kelas.angkatan}</td>
                <td>${kelas.jumlah}</td>
                <td><a href="/kelas-delete?id=${kelas.id}" class="btn btn-danger">Delete</a>  <a class="btn btn-primary" href="/kelas-update?id=${kelas.id}">Update</a></td>
            </tr>
        </c:forEach>
    </table>

    <a href="/kelas-add" class="btn btn-primary">Tambah Kelas</a>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>