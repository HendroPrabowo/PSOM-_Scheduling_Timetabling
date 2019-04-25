<%@ page import="TugasAkhir.penjadwalan.model.Constraints" %>
<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>

<style>
    .table td {
        text-align: center;
    }
    th{
        text-align: center;
    }
</style>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Constraints</h1>
    </div>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <a href="/tambah-constraints" class="btn btn-primary">Tambah Constraints</a>

            <br>
            <label style="margin-top: 10px">List All Constraints</label>
            <table class="table table-bordered table-striped table-hover table-sm">
                <thead>
                <tr>
                    <th>No</th>
                    <th>Constraints</th>
                    <th>Tipe</th>
                    <th>Subjek</th>
                    <th>Id_Subjek</th>
                    <th>Hari</th>
                    <th>Sesi</th>
                    <th>Max Bekerja</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="constraint" items="${constraints}">
                    <tr>
                        <td>
                            <%
                                out.print(i);
                                i++;
                            %>
                        </td>
                        <td>${constraint.nama_constraints}</td>
                        <td>
                            <%
                                Constraints constraints = (Constraints) pageContext.findAttribute("constraint");
                                Integer tipe = constraints.getTipe();
                                if(tipe.equals(1)){
                                    out.print("Max Bekerja");
                                }else if(tipe.equals(2)){
                                    out.print("Larangan");
                                }else {
                                    out.print("Libur");
                                }
                            %>
                        </td>
                        <td>${constraint.subjek}</td>
                        <td>${constraint.id_subjek}</td>
                        <td>
                            <%
                                Constraints constraint = (Constraints)pageContext.findAttribute("constraint");
                                Integer hari = (int)constraint.getHari();
                                if(hari == 1){
                                    out.print("Senin");
                                }else if(hari == 2){
                                    out.print("Selasa");
                                }else if(hari == 3){
                                    out.print("Rabu");
                                }else if(hari == 4){
                                    out.print("Kamis");
                                }else{
                                    out.print("Jumat");
                                }
                            %>
                        </td>
                        <td>${constraint.sesi}</td>
                        <td>${constraint.max_bekerja}</td>
                        <td><a href="#" class="btn btn-danger btn-sm">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>

<%@include file="common/footer.jspf"%>