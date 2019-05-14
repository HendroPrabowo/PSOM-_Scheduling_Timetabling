<%@ page import="java.util.List" %>
<%@ page import="TugasAkhir.penjadwalan.model.*" %>
<%@ include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Integer i = 1; %>
<% List<Ruangan> ruangans = (List<Ruangan>)pageContext.findAttribute("ruangans"); %>
<% List<Matakuliah> matakuliahs = (List<Matakuliah>)pageContext.findAttribute("matakuliahs"); %>
<% List<Dosen> dosens = (List<Dosen>)pageContext.findAttribute("dosens"); %>
<% List<Kelas> kelass = (List<Kelas>) pageContext.findAttribute("kelass"); %>

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
        <h1>Ubah Jadwal</h1>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <h3>Form ubah jadwal tertentu</h3>
            <form action="/ubah-jadwal" method="post">
                <div class="form-group">
                    <table class="table table-bordered table-striped table-sm">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Id</th>
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
                                <td>${partikel.id}</td>
                                <td>
                                    <%
                                        Partikel partikel = (Partikel)pageContext.findAttribute("partikel");
                                        for(Matakuliah matakuliah : matakuliahs){
                                            if(partikel.getIdmatakuliah().equals(matakuliah.getId())){
                                                out.print(matakuliah.getInisial());
                                            }
                                        }
                                    %>
                                </td>
                                <td>
                                    <%
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
                                <td>
                                    <%
                                        Integer idMatakuliah = partikel.getIdmatakuliah();
                                        Matakuliah matakuliah = new Matakuliah();
                                        for(Matakuliah item : matakuliahs){
                                            if(item.getId().equals(idMatakuliah)){
                                                matakuliah = item;
                                            }
                                        }
                                        if(matakuliah.getDosen1().length() != 0){
                                            for(Dosen dosen : dosens){
                                                if(dosen.getId().equals(Integer.parseInt(matakuliah.getDosen1()))){
                                                    out.print(dosen.getNama());
                                                    break;
                                                }
                                            }
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
                                        }
                                    %>
                                </td>
                                <td><% out.print(matakuliah.getAsistendosen1()); %></td>
                                <td><% out.print(matakuliah.getAsistendosen2()); %></td>
                                <td><% out.print(matakuliah.getAsistendosen3()); %></td>
                                <td>
                                    <%
                                        if(matakuliah.getKelas1().length() != 0){
                                            for(Kelas kelas : kelass){
                                                if(kelas.getId().equals(Integer.parseInt(matakuliah.getKelas1()))){
                                                    out.print(kelas.getInisial());
                                                }
                                            }
                                        }
                                    %>
                                </td>
                                <td>
                                    <%
                                        if(matakuliah.getKelas2().length() != 0){
                                            for(Kelas kelas : kelass){
                                                if(kelas.getId().equals(Integer.parseInt(matakuliah.getKelas2()))){
                                                    out.print(kelas.getInisial());
                                                }
                                            }
                                        }
                                    %>
                                </td>
                                <td>
                                    <%
                                        if(matakuliah.getKelas3().length() != 0){
                                            for(Kelas kelas : kelass){
                                                if(kelas.getId().equals(Integer.parseInt(matakuliah.getKelas3()))){
                                                    out.print(kelas.getInisial());
                                                }
                                            }
                                        }
                                    %>
                                </td>
                                <td>
                                    <%
                                        if(matakuliah.getKelas4().length() != 0){
                                            for(Kelas kelas : kelass){
                                                if(kelas.getId().equals(Integer.parseInt(matakuliah.getKelas4()))){
                                                    out.print(kelas.getInisial());
                                                }
                                            }
                                        }
                                    %>
                                </td>
                                <td><% out.print(matakuliah.getJenis()); %></td>
                                <td>${partikel.nilaifitness}</td>
                                <td>${partikel.keterangan}</td>
                                <td>
                                    <form method="post">
                                        <input type="hidden" name="id" value="${partikel.id}">
                                        <button type="submit" class="btn btn-danger btn-sm">Pindahkan</button>
                                    </form>
                                </td>
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