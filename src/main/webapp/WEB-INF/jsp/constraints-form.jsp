<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Tambah Constraints</h1>
    </div>

    <div class="form-group">
        <label>Tipe Constraints</label>
        <select class="custom-select" name="tipe" id="tipe">
            <option value="0" selected>Pilih Satu ....</option>
            <option value="1">Max Bekerja</option>
            <option value="2">Larangan</option>
            <option value="3">Libur</option>
        </select>
    </div>

    <div id="constraints">

    </div>
</div>

<script>
    $(document).ready(function () {
        var tipe = $("#tipe").val();

    });

    $("#tipe").on("change", function(){
        var tipe = $("#tipe").val();
        if(tipe == 0){
            $("#constraints").html("");
        }
        else if(tipe == 1){
            $("#constraints").html("<form action=\"/max-bekerja\" method=\"post\">\n" +
                "\t<div class=\"form-group\">\n" +
                "\t\t<label>Subjek</label>\n" +
                "\t\t<select class=\"custom-select\" name=\"subjek\" required>\n" +
                "\t\t\t<option value=\"\">Pilih Satu ....</option>\n" +

                "\t\t\t<c:forEach var="dosen" items="${dosens}">\n" +
                "\t\t\t\t<option value=\"dosen ${dosen.id}\">Dosen : ${dosen.nama}</option>\n" +
                "\t\t\t</c:forEach>\n" +

                "\t\t\t<c:forEach var="mahasiswa" items="${mahasiswas}">\n" +
 "\t\t\t\t<option value=\"mahasiswa ${mahasiswa.id}\">Mahasiswa : ${mahasiswa.nama} ${mahasiswa.kelas}</option>\n" +
                "\t\t\t</c:forEach>\n" +

                "\t\t\t<c:forEach var="ruangan" items="${ruangans}">\n" +
                "\t\t\t\t<option value=\"ruangan ${ruangan.id}\">Ruangan : ${ruangan.nama}</option>\n" +
                "\t\t\t</c:forEach>\n" +

                "\t\t</select>\n" +
                "\t</div>\n" +
                "\t<div class=\"form-group\" id=\"constraints\"></div>\n" +
                "\t<div class=\"form-group\">\n" +
                "\t\t<label>Hari</label>\n" +
                "\t\t<select class=\"custom-select\" name=\"hari\">\n" +
                "\t\t\t<option value=\"1\">Senin</option>\n" +
                "\t\t\t<option value=\"2\">Selasa</option>\n" +
                "\t\t\t<option value=\"3\">Rabu</option>\n" +
                "\t\t\t<option value=\"4\">Kamis</option>\n" +
                "\t\t\t<option value=\"5\">Jumat</option>\n" +
                "\t\t\t<option value=\"6\">All</option>\n" +
                "\t\t</select>\n" +
                "\t</div>\n" +
                "\t<div class=\"form-group\">\n" +
                "\t\t<label>Max Bekerja</label>\n" +
                "\t\t<input class=\"form-control\" type=\"text\" name=\"max_bekerja\" placeholder=\"Jumlah Jam Max Bekerja\" required>\n" +
                "\t</div>\n" +
                "\t<button class=\"btn btn-success\">Save</button>\n" +
                "</form>");
        }
    });
</script>

<%@include file="common/footer.jspf"%>