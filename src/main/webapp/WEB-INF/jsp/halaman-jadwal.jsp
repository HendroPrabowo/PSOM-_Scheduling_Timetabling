<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% Integer i = 1; %>


<div class="container-fluid">
    <div class="jumbotron">
        <h1>Generate Jadwal</h1>
    </div>

    <h3>Generate Jadwal</h3>
    <a href="/generate-jadwal" class="btn btn-primary">Generate Jadwal</a>

    <p>Jadwal akan digenerate dengan komposisi matakuliah : </p>

    <table id="tableScrollVertical" class="table table-bordered table-striped display nowrap" style="width:100%">
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
                <td>${matakuliah.dosen1}</td>
                <td>${matakuliah.dosen2}</td>
                <td>${matakuliah.dosen3}</td>
                <td>${matakuliah.dosen4}</td>
                <td>${matakuliah.asistendosen1}</td>
                <td>${matakuliah.asistendosen2}</td>
                <td>${matakuliah.asistendosen3}</td>
                <td>${matakuliah.kelas1}</td>
                <td>${matakuliah.kelas2}</td>
                <td>${matakuliah.kelas3}</td>
                <td>${matakuliah.kelas4}</td>
                <td>${matakuliah.jumlahrombongankelas}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<script src="/js/dataTableScript.js"></script>

<%@include file="common/footer.jspf"%>