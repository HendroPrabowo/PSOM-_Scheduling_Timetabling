<%@ page import="TugasAkhir.penjadwalan.model.Ruangan" %>
<%@ page import="TugasAkhir.penjadwalan.model.Dosen" %>
<%@ page import="java.util.List" %>
<%@ page import="TugasAkhir.penjadwalan.model.Kelas" %>
<%@ page import="TugasAkhir.penjadwalan.model.Matakuliah" %>
<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% Integer i = 1; %>
<% List<Dosen> dosens = (List<Dosen>)pageContext.findAttribute("dosens"); %>
<% List<Kelas> kelass = (List<Kelas>)pageContext.findAttribute("kelass"); %>


<div class="container-fluid">
    <div class="jumbotron">
        <h1>Generate Jadwal</h1>
    </div>

    <h3>Generate Jadwal</h3>

    <form method="post">
        <button type="submit" class="btn btn-primary" style="float: left">Generate Jadwal berdasarkan Kelas</button>
    </form>
    <form action="/generate-jadwal-per-mahasiswa" method="post">
        <button type="submit" class="btn btn-warning" style="float: left; margin-left: 10px">Generate Jadwal berdasarkan Mahasiswa</button>
    </form>
    <a href="/test-excel" class="btn btn-success" style="margin-left: 10px">Generate Excel</a>

    <p style="margin-top: 20px">Jadwal akan digenerate dengan komposisi matakuliah : </p>

    <table id="tableScrollVertical" class="table table-bordered table-striped display nowrap table-sm" style="width:100%">
        <thead>
        <tr>
            <th>No</th>
            <th>Inisial</th>
            <th>Nama</th>
            <th>Program</th>
            <th>Jenis</th>
            <th>Jumlah SKS</th>
            <th>Dosen 1</th>
            <th>Dosen 2</th>
            <th>Dosen 3</th>
            <th>Dosen 4</th>
            <th>Asisten Dosen 1</th>
            <th>Asisten Dosen 2</th>
            <th>Asisten Dosen 3</th>
            <th>Kelas 1</th>
            <th>Kelas 2</th>
            <th>Kelas 3</th>
            <th>Kelas 4</th>
            <th>Jumlah Rombongan Kelas</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="matakuliah" items="${matakuliahs}">
            <tr>
                <td><%
                    out.print(i);
                    i+=1;
                %></td>
                <td>${matakuliah.inisial}</td>
                <td>${matakuliah.nama}</td>
                <td>${matakuliah.program}</td>
                <td>${matakuliah.jenis}</td>
                <td>${matakuliah.jumlahsks}</td>
                <td>
                    <%
                        Matakuliah matakuliah = (Matakuliah) pageContext.findAttribute("matakuliah");
                        if(matakuliah.getDosen1().length() != 0){
                            for(Dosen dosen : dosens){
                                if(dosen.getId().equals(Integer.parseInt(matakuliah.getDosen1()))){
                                    out.print(dosen.getNama());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>
                    <%
                        if(matakuliah.getDosen2().length() != 0){
                            for(Dosen dosen : dosens){
                                if(dosen.getId().equals(Integer.parseInt(matakuliah.getDosen2()))){
                                    out.print(dosen.getNama());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>
                    <%
                        if(matakuliah.getDosen3().length() != 0){
                            for(Dosen dosen : dosens){
                                if(dosen.getId().equals(Integer.parseInt(matakuliah.getDosen3()))){
                                    out.print(dosen.getNama());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>
                    <%
                        if(matakuliah.getDosen4().length() != 0){
                            for(Dosen dosen : dosens){
                                if(dosen.getId().equals(Integer.parseInt(matakuliah.getDosen4()))){
                                    out.print(dosen.getNama());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>${matakuliah.asistendosen1}</td>
                <td>${matakuliah.asistendosen2}</td>
                <td>${matakuliah.asistendosen3}</td>
                <td>
                    <%
                        if(matakuliah.getKelas1().length() != 0) {
                            for (Kelas kelas : kelass) {
                                if (kelas.getId().equals(Integer.parseInt(matakuliah.getKelas1()))) {
                                    out.print(kelas.getInisial());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>
                    <%
                        if(matakuliah.getKelas2().length() != 0) {
                            for (Kelas kelas : kelass) {
                                if (kelas.getId().equals(Integer.parseInt(matakuliah.getKelas2()))) {
                                    out.print(kelas.getInisial());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>
                    <%
                        if(matakuliah.getKelas3().length() != 0) {
                            for (Kelas kelas : kelass) {
                                if (kelas.getId().equals(Integer.parseInt(matakuliah.getKelas3()))) {
                                    out.print(kelas.getInisial());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>
                    <%
                        if(matakuliah.getKelas4().length() != 0) {
                            for (Kelas kelas : kelass) {
                                if (kelas.getId().equals(Integer.parseInt(matakuliah.getKelas4()))) {
                                    out.print(kelas.getInisial());
                                    break;
                                }
                            }
                        }else {
                            out.print("-");
                        }
                    %>
                </td>
                <td>${matakuliah.jumlahrombongankelas}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<script src="/js/dataTableScript.js"></script>

<%@include file="common/footer.jspf"%>