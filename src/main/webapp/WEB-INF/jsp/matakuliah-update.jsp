<%@include file="common/header.jspf"%>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Update Mata Kuliah</h1>
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
                    <input type="text" class="form-control" id="program" name="program" value="${matakuliah.program}">
                </div>
                <div class="form-group">
                    <label for="jenis">Jenis</label>
                    <input type="text" class="form-control" id="jenis" name="jenis" value="${matakuliah.jenis}">
                </div>

                <div class="form-group">
                    <label for="jumlahsks">Jumlah SKS</label>
                    <input type="text" class="form-control" id="jumlahsks" name="jumlahsks" value="${matakuliah.jumlahsks}">
                </div>
                <div class="form-group">
                    <label for="dosen1">Dosen 1</label>
                    <input type="text" class="form-control" id="dosen1" name="dosen1" value="${matakuliah.dosen1}">
                </div>
                <div class="form-group">
                    <label for="dosen2">Dosen 2</label>
                    <input type="text" class="form-control" id="dosen2" name="dosen2" value="${matakuliah.dosen2}">
                </div>
                <div class="form-group">
                    <label for="dosen3">Dosen 3</label>
                    <input type="text" class="form-control" id="dosen3" name="dosen3" value="${matakuliah.dosen3}">
                </div>

                <div class="form-group">
                    <label for="dosen4">Dosen 4</label>
                    <input type="text" class="form-control" id="dosen4" name="dosen4" value="${matakuliah.dosen4}">
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
                    <label for="kelas1">Kelas 1</label>
                    <input type="text" class="form-control" id="kelas1" name="kelas1" value="${matakuliah.kelas1}">
                </div>

                <div class="form-group">
                    <label for="kelas2">Kelas 2</label>
                    <input type="text" class="form-control" id="kelas2" name="kelas2" value="${matakuliah.kelas2}">
                </div>
                <div class="form-group">
                    <label for="kelas3">Kelas 3</label>
                    <input type="text" class="form-control" id="kelas3" name="kelas3" value="${matakuliah.kelas3}">
                </div>
                <div class="form-group">
                    <label for="kelas4">Kelas 4</label>
                    <input type="text" class="form-control" id="kelas4" name="kelas4" value="${matakuliah.kelas4}">
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