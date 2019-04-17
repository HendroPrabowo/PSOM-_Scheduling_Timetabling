<%@ page import="TugasAkhir.penjadwalan.model.Dosen" %>
<%@ page import="java.util.List" %>
<%@ page import="TugasAkhir.penjadwalan.model.Matakuliah" %>
<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<Dosen> dosens = (List<Dosen>) pageContext.findAttribute("dosens"); %>
<% Matakuliah matakuliah = (Matakuliah)pageContext.findAttribute("matakuliah"); %>

        <div
    class="container-fluid">
    <div class="jumbotron">
        <h1>Assing Mahasiswa</h1>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <label for="tabelMatakuliah">Detail Matakuliah</label>
            <table class="table table-bordered table-sm" id="tabelMatakuliah">
                <tr>
                    <td>Inisial</td>
                    <td>:</td>
                    <td>${matakuliah.inisial}</td>
                </tr>
                <tr>
                    <td>Nama</td>
                    <td>:</td>
                    <td>${matakuliah.nama}</td>
                </tr>
                <tr>
                    <td>SKS</td>
                    <td>:</td>
                    <td>${matakuliah.jumlahsks}</td>
                </tr>
                <tr>
                    <td>Dosen 1</td>
                    <td>:</td>
                    <td>
                        <%
                            if(matakuliah.getDosen1().length() != 0){
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == Integer.parseInt(matakuliah.getDosen1()))
                                        out.print(dosen.getNama());
                            }else {
                                out.print("--");
                            }

                        %>
                    </td>
                </tr>
                <tr>
                    <td>Dosen 2</td>
                    <td>:</td>
                    <td>
                        <%
                            if(matakuliah.getDosen2().length() != 0){
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == Integer.parseInt(matakuliah.getDosen2()))
                                        out.print(dosen.getNama());
                            }else {
                                out.print("--");
                            }

                        %>
                    </td>
                </tr>
                <tr>
                    <td>Dosen 3</td>
                    <td>:</td>
                    <td>
                        <%
                            if(matakuliah.getDosen3().length() != 0){
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == Integer.parseInt(matakuliah.getDosen3()))
                                        out.print(dosen.getNama());
                            }else {
                                out.print("--");
                            }

                        %>
                    </td>
                </tr>
                <tr>
                    <td>Dosen 4</td>
                    <td>:</td>
                    <td>
                        <%
                            if(matakuliah.getDosen4().length() != 0){
                                for(Dosen dosen : dosens)
                                    if(dosen.getId() == Integer.parseInt(matakuliah.getDosen4()))
                                        out.print(dosen.getNama());
                            }else {
                                out.print("--");
                            }

                        %>
                    </td>
                </tr>
            </table>

            <form method="post">
                <table class="table table-bordered table-sm">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>NIM</th>
                        <th>Nama</th>
                        <th>Kelas</th>
                        <th>Angkatan</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="mahasiswa" items="${mahasiswas}">
                        <tr>
                            <td><input type="checkbox" name="id" value="${mahasiswa.id}"></td>
                            <td>${mahasiswa.nim}</td>
                            <td>${mahasiswa.nama}</td>
                            <td>${mahasiswa.kelas}</td>
                            <td>${mahasiswa.angkatan}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <input type="submit" class="btn btn-success" value="Submit">
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>

<%@include file="common/footer.jspf"%>