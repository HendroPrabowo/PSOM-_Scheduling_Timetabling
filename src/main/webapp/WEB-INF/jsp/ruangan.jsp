<%@ include file="common/header.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Ruangan</h1>
    </div>

    <table id="table" class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>No</th>
            <th>Nama</th>
            <th>Kapasitas</th>
            <th>Action</th>
        </tr>
        </thead>
        <c:forEach var="ruangan" items="${ruangans}">
            <tr>
                <td><%
                    out.print(i);
                    i+=1;
                %></td>
                <td>${ruangan.nama}</td>
                <td>${ruangan.kapasitas}</td>
                <td><a href="/ruangan-delete?id=${ruangan.id}" class="btn btn-danger">Delete</a>  <a class="btn btn-primary" href="/ruangan-update?id=${ruangan.id}">Update</a></td>
            </tr>
        </c:forEach>
    </table>

    <a href="/ruangan-add" class="btn btn-primary">Tambah Ruangan</a>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>