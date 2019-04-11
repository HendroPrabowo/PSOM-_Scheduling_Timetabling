<%@ page import="TugasAkhir.penjadwalan.model.Partikel" %>
<%@ page import="java.util.List" %>
<%@ page import="TugasAkhir.penjadwalan.model.Ruangan" %>
<%@ include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>
<% List<Ruangan> ruangans = (List<Ruangan>)pageContext.findAttribute("ruangans"); %>

<style>
    .table td {
        text-align: center;
    }
</style>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Ubah Jadwal</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <h3>Form ubah jadwal tertentu</h3>
            <form action="/ubah-jadwal" method="post">
                <div class="form-group">
                    <table class="table table-bordered table-striped table-sm">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Matakuliah</th>
                                <th>Hari</th>
                                <th>Sesi</th>
                                <th>Ruangan</th>
                                <th>Dosen 1</th>
                                <th>Dosen 2</th>
                                <th>Dosen 3</th>
                                <th>Dosen 4</th>
                                <th>Asdos 1</th>
                                <th>Asdos 2</th>
                                <th>Asdos 3</th>
                                <th>Kelas 1</th>
                                <th>Kelas 2</th>
                                <th>Kelas 3</th>
                                <th>Kelas 4</th>
                                <th>Jenis</th>
                                <th>Nilai Fitness</th>
                                <th>Keterangan</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="partikel" items="${partikels}">
                                <tr>
                                    <td>
                                        <%
                                            out.print(i);
                                            i++;
                                        %>
                                    </td>
                                    <td>${partikel.idmatakuliah}</td>
                                    <td>
                                        <%
                                            Partikel partikel = (Partikel)pageContext.findAttribute("partikel");
                                            Integer hari = (int)partikel.getPosisihari();
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
                                            Integer sesi = (int)partikel.getPosisisesi();
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
                                    <td>
                                        <%
                                            Integer ruangan = (int)partikel.getPosisiruangan();
                                            for(Ruangan kelas : ruangans){
                                                if(ruangan == kelas.getPosisi()){
                                                    out.print(kelas.getNama());
                                                }
                                            }
                                        %>
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>${partikel.nilaifitness}</td>
                                    <td>${partikel.keterangan}</td>
                                    <td><form method="post"><input type="hidden" name="id" value="${partikel.id}"><button type="submit" class="btn btn-danger btn-sm">Pindahkan</button></form></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>
    </div>

    <c:forEach var="solusi" items="${listSolusi}">
        <p>${solusi}</p>
    </c:forEach>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>