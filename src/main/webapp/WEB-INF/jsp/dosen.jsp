<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>All Dosen</h1>
    </div>

    <a href="/dosen-add" class="btn btn-primary" style="margin-bottom: 10px">Tambah Dosen</a>

    <table id="table" class="table table-striped table-bordered table-sm">
        <thead>
            <tr>
                <th>No</th>
                <th>Inisial</th>
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
                    <td>${dosen.nama}</td>
                    <td><a class="btn btn-danger btn-sm" href="/dosen-delete?id=${dosen.id}">Delete</a>  <a href="/dosen-update?id=${dosen.id}" class="btn btn-primary  btn-sm">Update</a> </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>