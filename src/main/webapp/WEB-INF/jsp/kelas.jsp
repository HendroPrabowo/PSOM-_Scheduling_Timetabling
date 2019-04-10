<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Kelas</h1>
    </div>

    <a href="/kelas-add" class="btn btn-primary" style="margin-bottom: 10px">Tambah Kelas</a>

    <table id="table" class="table table-bordered table-striped table-sm">
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
                <td><a href="/kelas-delete?id=${kelas.id}" class="btn btn-danger btn-sm">Delete</a>  <a class="btn btn-primary btn-sm" href="/kelas-update?id=${kelas.id}">Update</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>