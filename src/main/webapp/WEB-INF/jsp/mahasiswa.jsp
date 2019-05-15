<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Mahasiswa</h1>
    </div>


    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <a href="#" class="btn btn-primary" style="margin-bottom: 10px">Tambah Mahasiswa</a>
        </div>
        <div class="col-md-3"></div>
    </div>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <label>List All Mahasiswa :</label>
            <table id="table" class="table table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Id</th>
                    <th>NIM</th>
                    <th>Nama</th>
                    <th>Kelas</th>
                    <th>Angkatan</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="mahasiswa" items="${mahasiswas}">
                    <tr>
                        <td>
                            <%
                                out.print(i);
                                i++;
                            %>
                        </td>
                        <td>${mahasiswa.id}</td>
                        <td>${mahasiswa.nim}</td>
                        <td>${mahasiswa.nama}</td>
                        <td>${mahasiswa.kelas}</td>
                        <td>${mahasiswa.angkatan}</td>
                        <td>
                            <a href="#" class="btn btn-warning btn-sm">Update</a>
                            <a href="#" class="btn btn-danger btn-sm">Delete</a>
                        </td>
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