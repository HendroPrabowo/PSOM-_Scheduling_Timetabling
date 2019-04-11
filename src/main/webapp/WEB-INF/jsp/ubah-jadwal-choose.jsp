<%@ page import="TugasAkhir.penjadwalan.model.Partikel" %>
<%@ page import="TugasAkhir.penjadwalan.model.Ruangan" %>
<%@ page import="java.util.List" %>
<%@ include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<Ruangan> ruangans = (List<Ruangan>)pageContext.findAttribute("ruangans"); %>
<% int i = 1; %>

<style>
    .table td {
        text-align: center;
    }
</style>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Pilih Slot Jadwal</h1>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-3">
            <label>Partikel yang akan dipindahkan : </label>
            <table class="table table-bordered table-sm">
                <tr>
                    <td style="text-align: left">Partikel</td>
                    <td style="text-align: left">:</td>
                    <td style="text-align: left">${partikel.nama}</td>
                </tr>
                <tr>
                    <td style="text-align: left">Hari</td>
                    <td style="text-align: left">:</td>
                    <td style="text-align: left">
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
                </tr>
                <tr>
                    <td style="text-align: left">Sesi</td>
                    <td style="text-align: left">:</td>
                    <td style="text-align: left">
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
                </tr>
                <tr>
                    <td style="text-align: left">Ruangan</td>
                    <td style="text-align: left">:</td>
                    <td style="text-align: left">
                        <%
                            Integer ruangan = (int)partikel.getPosisiruangan();
                            for(Ruangan kelas : ruangans){
                                if(ruangan == kelas.getPosisi()){
                                    out.print(kelas.getNama());
                                }
                            }
                        %>
                    </td>
                </tr>
            </table>
        </div>
        <div class="col-md-5"></div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <label>List Slot yang Available :</label>
            <table class="table table-bordered table-striped table-sm table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Hari</th>
                    <th>Sesi</th>
                    <th>Ruangan</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="partikelSolusi" items="${solusi}">
                    <tr>
                        <td>
                            <%
                                out.print(i);
                                i++;
                            %>
                        </td>
                        <td>
                            <%
                                Partikel partikelSolusi = (Partikel)pageContext.findAttribute("partikelSolusi");
                                Integer hariSolusi = (int)partikelSolusi.getPosisihari();
                                if(hariSolusi == 1){
                                    out.print("Senin");
                                }else if(hariSolusi == 2){
                                    out.print("Selasa");
                                }else if(hariSolusi == 3){
                                    out.print("Rabu");
                                }else if(hariSolusi == 4){
                                    out.print("Kamis");
                                }else{
                                    out.print("Jumat");
                                }
                            %>
                        </td>
                        <td>
                            <%
                                Integer sesiSolusi = (int)partikelSolusi.getPosisisesi();
                                if(sesiSolusi == 1){
                                    out.print("08:00 - 08:50");
                                }else if(sesiSolusi == 2){
                                    out.print("09:00 - 09:50");
                                }else if(sesiSolusi == 3){
                                    out.print("10:00 - 10:50");
                                }else if(sesiSolusi == 4){
                                    out.print("11:00 - 11:50");
                                }else if(sesiSolusi == 5){
                                    out.print("13:00 - 13:50");
                                }else if(sesiSolusi == 6){
                                    out.print("14:00 - 14:50");
                                }else if(sesiSolusi == 7){
                                    out.print("15:00 - 15:50");
                                }else{
                                    out.print("16:00 - 16:50");
                                }
                            %>
                        </td>
                        <td>
                            <%
                                Integer ruanganSolusi = (int)partikelSolusi.getPosisiruangan();
                                for(Ruangan kelas : ruangans){
                                    if(ruanganSolusi == kelas.getPosisi()){
                                        out.print(kelas.getNama());
                                    }
                                }
                            %>
                        </td>
                        <td>
                            <a href="/ubah-jadwal-apply?hari=<% out.print(hariSolusi); %>&sesi=<% out.print(sesiSolusi); %>&ruangan=<% out.print(ruanganSolusi);%>&id=${partikel.id}" class="btn btn-success btn-sm">Pilih</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>

<%@include file="common/footer.jspf"%>