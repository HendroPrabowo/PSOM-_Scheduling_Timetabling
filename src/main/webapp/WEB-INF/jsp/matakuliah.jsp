<%@ page import="TugasAkhir.penjadwalan.model.Matakuliah" %>
<%@ page import="java.util.List" %>
<%@ page import="TugasAkhir.penjadwalan.model.Dosen" %>
<%@ include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<Dosen> dosens = (List<Dosen>) pageContext.findAttribute("dosens"); %>

        <div
    class="container-fluid">
    <div class="jumbotron">
        <h1>Mata Kuliah</h1>
    </div>

    <% Integer i = 1; %>

    <a href="/matakuliah-add" class="btn btn-primary" style="margin-bottom: 10px">Tambah Mata Kuliah</a>

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
            <th>Jumlah Rombongan Kelas</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="matakuliah" items="${matakuliahs}">
                <%
                    Matakuliah matakuliah = (Matakuliah)pageContext.findAttribute("matakuliah");
                    int dosen1 = 0, dosen2 = 0, dosen3 = 0, dosen4 = 0;
                    if(matakuliah.getDosen1().length() != 0)
                        dosen1 = Integer.parseInt(matakuliah.getDosen1());
                    if(matakuliah.getDosen2().length() != 0)
                        dosen2 = Integer.parseInt(matakuliah.getDosen2());
                    if(matakuliah.getDosen3().length() != 0)
                        dosen3 = Integer.parseInt(matakuliah.getDosen3());
                    if(matakuliah.getDosen4().length() != 0)
                        dosen4 = Integer.parseInt(matakuliah.getDosen4());
                %>
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
                            if(dosen1 != 0)
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == dosen1)
                                        out.print(dosen.getNama());
                        %>
                    </td>
                    <td>
                        <%
                            if(dosen2 != 0)
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == dosen2)
                                        out.print(dosen.getNama());
                        %>
                    </td>
                    <td>
                        <%
                            for(Dosen dosen : dosens)
                                if(dosen.getId() == dosen3)
                                    out.print(dosen.getNama());
                        %>
                    </td>
                    <td>
                        <%
                            if(dosen4 != 0)
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == dosen4)
                                        out.print(dosen.getNama());
                        %>
                    </td>
                    <td>${matakuliah.asistendosen1}</td>
                    <td>${matakuliah.asistendosen2}</td>
                    <td>${matakuliah.asistendosen3}</td>
                    <td class="text-center">${matakuliah.jumlahrombongankelas} <a href="#" class="badge badge-warning badge-pill">View</a></td>

                    <td>
                        <a href="/assign-mahasiswa?id=${matakuliah.id}" class="btn btn-success btn-sm">Assign Mahasiswa</a>
                        <a href="/matakuliah-delete?id=${matakuliah.id}" class="btn btn-danger btn-sm">Delete</a>
                        <a class="btn btn-primary btn-sm" href="/matakuliah-update?id=${matakuliah.id}">Update</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/dataTableScript.js" type="text/javascript"></script>

<%@include file="common/footer.jspf"%>