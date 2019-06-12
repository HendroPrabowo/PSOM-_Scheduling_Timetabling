<%@include file="common/header.jspf"%>


<div class="container-fluid">
    <div class="jumbotron">
        <h1 class="text-center">Update Mahasiswa</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/update-mahasiswa" method="post">
                <input type="hidden" value="${mahasiswa.id}" name="id">
                <div class="form-group">
                    <label>NIM</label>
                    <input type="text" class="form-control" id="nim" name="nim" value="${mahasiswa.nim}" required>
                </div>
                <div class="form-group">
                    <label>Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama" value="${mahasiswa.nama}" required>
                </div>
                <div class="form-group">
                    <label>Kelas</label>
                    <input type="text" class="form-control" id="kelas" name="kelas" value="${mahasiswa.kelas}" required>
                </div>
                <div class="form-group">
                    <label>Angkatan</label>
                    <input type="text" class="form-control" id="angkatan" name="angkatan" value="${mahasiswa.angkatan}" required>
                </div>
                <input type="submit" class="btn btn-success" value="Update">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>