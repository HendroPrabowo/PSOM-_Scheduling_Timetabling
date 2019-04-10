<%@ include file="common/header.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Ruangan</h1>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"><a href="/ruangan-add" class="btn btn-primary" style="margin-bottom: 10px">Tambah Ruangan</a></div>
        <div class="col-md-2"></div>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <table id="table" class="table table-bordered table-striped table-sm">
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
                        <td><a href="/ruangan-delete?id=${ruangan.id}" class="btn btn-danger btn-sm">Delete</a>  <a class="btn btn-primary btn-sm" href="/ruangan-update?id=${ruangan.id}">Update</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>