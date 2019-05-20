<%@include file="common/header.jspf"%>

<div class="container-fluid">
    <div class="jumbotron">
        <h1 class="text-center">Update Mata Kuliah</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/matakuliah-save" method="post">
                <input type="hidden" name="id" value="${matakuliah.id}">
                <div class="form-group">
                    <label for="inisial">Inisial</label>
                    <input type="text" class="form-control" id="inisial" name="inisial" value="${matakuliah.inisial}">
                </div>
                <div class="form-group">
                    <label for="nama">Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama" value="${matakuliah.nama}">
                </div>

                <div class="form-group">
                    <label for="program">Program</label>
                    <select id="program" class="custom-select" name="program" required>
                        <option value="Sarjana" selected>Sarjana</option>
                        <option value="Diploma">Diploma</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="jenis">Jenis</label>
                    <select id="jenis" class="custom-select" name="jenis" required>
                        <option value="T" selected>Teori</option>
                        <option value="P">Praktikum</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="jumlahsks">Jumlah SKS</label>
                    <input type="number" class="form-control" id="jumlahsks" name="jumlahsks" value="${matakuliah.jumlahsks}" required>
                </div>

                <div class="form-group">
                    <label for="dosen1">Dosen 1</label>
                    <select id="dosen1" name="dosen1" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="dosen" items="${dosens}">
                            <option value="${dosen.id}">${dosen.inisial}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dosen2">Dosen 2</label>
                    <select id="dosen2" name="dosen2" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="dosen" items="${dosens}">
                            <option value="${dosen.id}">${dosen.inisial}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dosen3">Dosen 3</label>
                    <select id="dosen3" name="dosen3" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="dosen" items="${dosens}">
                            <option value="${dosen.id}">${dosen.inisial}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dosen4">Dosen 4</label>
                    <select id="dosen4" name="dosen4" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="dosen" items="${dosens}">
                            <option value="${dosen.id}">${dosen.inisial}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="asistendosen1">Asisten Dosen 1</label>
                    <input type="text" class="form-control" id="asistendosen1" name="asistendosen1" value="${matakuliah.asistendosen1}">
                </div>
                <div class="form-group">
                    <label for="asistendosen2">Asisten Dosen 2</label>
                    <input type="text" class="form-control" id="asistendosen2" name="asistendosen2" value="${matakuliah.asistendosen2}">
                </div>
                <div class="form-group">
                    <label for="asistendosen3">Asisten Dosen 3</label>
                    <input type="text" class="form-control" id="asistendosen3" name="asistendosen3" value="${matakuliah.asistendosen3}">
                </div>

                <div class="form-group">
                    <label>Kelas 1</label>
                    <select name="kelas1" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="value" items="${kelas}">
                            <option value="${value.id}">${value.inisial}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Kelas 2</label>
                    <select name="kelas2" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="value" items="${kelas}">
                            <option value="${value.id}">${value.inisial}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Kelas 3</label>
                    <select name="kelas3" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="value" items="${kelas}">
                            <option value="${value.id}">${value.inisial}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Kelas 4</label>
                    <select name="kelas4" class="custom-select">
                        <option value="">Pilih Satu...</option>
                        <c:forEach var="value" items="${kelas}">
                            <option value="${value.id}">${value.inisial}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="jumlahrombongankelas">Jumlah Rombongan Kelas</label>
                    <input type="text" class="form-control" id="jumlahrombongankelas" name="jumlahrombongankelas" value="${matakuliah.jumlahrombongankelas}">
                </div>


                <input type="submit" class="btn btn-success" value="Update">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>