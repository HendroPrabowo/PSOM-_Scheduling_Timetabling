<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Tambah Mata Kuliah</h1>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <label>Isi Form :</label>
                <form action="/matakuliah-save" method="post">
                    <div class="form-group">
                        <label for="inisial">Inisial</label>
                        <input type="text" class="form-control" id="inisial" name="inisial">
                    </div>
                    <div class="form-group">
                        <label for="nama">Nama</label>
                        <input type="text" class="form-control" id="nama" name="nama">
                    </div>

                    <div class="form-group">
                        <label for="program">Program</label>
                        <select id="program" class="custom-select" name="program">
                            <option value="Sarjana" selected>Sarjana</option>
                            <option value="Diploma">Diploma</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="jenis">Jenis</label>
                        <select id="jenis" class="custom-select" name="jenis">
                            <option value="T" selected>Teori</option>
                            <option value="P">Praktikum</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="jumlahsks">Jumlah SKS</label>
                        <input type="number" class="form-control" id="jumlahsks" name="jumlahsks">
                    </div>

                    <div class="form-group">
                        <label for="dosen1">Dosen 1</label>
                        <select id="dosen1" name="dosen1" class="custom-select">
                            <option value="">Pilih Satu...</option>
                            <c:forEach var="dosen" items="${dosens}">
                                <option value="${dosen.id}">${dosen.nama}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dosen2">Dosen 2</label>
                        <select id="dosen2" name="dosen2" class="custom-select">
                            <option value="">Pilih Satu...</option>
                            <c:forEach var="dosen" items="${dosens}">
                                <option value="${dosen.id}">${dosen.nama}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dosen3">Dosen 3</label>
                        <select id="dosen3" name="dosen3" class="custom-select">
                            <option value="">Pilih Satu...</option>
                            <c:forEach var="dosen" items="${dosens}">
                                <option value="${dosen.id}">${dosen.nama}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dosen4">Dosen 4</label>
                        <select id="dosen4" name="dosen4" class="custom-select">
                            <option value="">Pilih Satu...</option>
                            <c:forEach var="dosen" items="${dosens}">
                                <option value="${dosen.id}">${dosen.nama}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="asistendosen1">Asisten Dosen 1</label>
                        <input type="text" class="form-control" id="asistendosen1" name="asistendosen1">
                    </div>
                    <div class="form-group">
                        <label for="asistendosen2">Asisten Dosen 2</label>
                        <input type="text" class="form-control" id="asistendosen2" name="asistendosen2">
                    </div>
                    <div class="form-group">
                        <label for="asistendosen3">Asisten Dosen 3</label>
                        <input type="text" class="form-control" id="asistendosen3" name="asistendosen3">
                    </div>

                    <input type="submit" class="btn btn-success" value="Simpan">
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>