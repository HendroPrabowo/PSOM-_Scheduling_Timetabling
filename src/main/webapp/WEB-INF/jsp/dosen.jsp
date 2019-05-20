<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1 class="text-center">Dosen</h1>
    </div>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <a href="/dosen-add" class="btn btn-primary" style="margin-bottom: 10px">Tambah Dosen</a>
        </div>
        <div class="col-md-3"></div>
    </div>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <table id="table" class="table table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Inisial</th>
                    <th>Nama</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="dosen" items="${dosens}">
                    <tr>
                        <td><%
                            out.print(i);
                            i+=1;
                        %>
                        </td>
                        <td>${dosen.inisial}</td>
                        <td>${dosen.nama}</td>
                        <td><a class="btn btn-danger btn-sm" href="/dosen-delete?id=${dosen.id}">Delete</a>  <a href="/dosen-update?id=${dosen.id}" class="btn btn-primary  btn-sm">Update</a> </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-3"></div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>