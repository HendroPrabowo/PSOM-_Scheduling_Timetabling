<%@include file="common/header.jspf"%>


<div class="container-fluid">
    <div class="jumbotron">
        <h1 class="text-center">Tambah Mahasiswa</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/mahasiswa-save" method="post">
                <div class="form-group">
                    <label>NIM</label>
                    <input type="text" class="form-control" id="nim" name="nim" required>
                </div>
                <div class="form-group">
                    <label>Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama" required>
                </div>
                <div class="form-group">
                    <label>Kelas</label>
                    <input type="text" class="form-control" id="kelas" name="kelas" required>
                </div>
                <div class="form-group">
                    <label>Angkatan</label>
                    <input type="text" class="form-control" id="angkatan" name="angkatan" required>
                </div>
                <input type="submit" class="btn btn-success" value="Simpan">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>