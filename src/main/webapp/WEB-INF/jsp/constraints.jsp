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
        <h1 class="text-center">Constraints</h1>
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
                        <td>
                            <%
                                Integer sesi = constraint.getSesi();
                                if(sesi == 1){
                                    out.print("08:00 - 08:50");
                                }else if(sesi == 2){
                                    out.print("09:00 - 09:50");
                                }else if(sesi == 3){
                                    out.print("10:00 - 10:50");
                                }else if(sesi == 4){
                                    out.print("11:00 - 11:50");
                                }else if(sesi == 5){
                                    out.print("13:00 - 13:50");
                                }else if(sesi == 6){
                                    out.print("14:00 - 14:50");
                                }else if(sesi == 7){
                                    out.print("15:00 - 15:50");
                                }else{
                                    out.print("16:00 - 16:50");
                                }
                            %>
                        </td>
                        <td>${constraint.max_bekerja}</td>
                        <td>
                            <form action="/hapus-constraint" method="post">
                                <input type="hidden" value="${constraint.id}" name="id">
                                <button type="submit" class="btn btn-danger btn-sm">Hapus</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>

<%@include file="common/footer.jspf"%>