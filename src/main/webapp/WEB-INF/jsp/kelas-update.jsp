<%@include file="common/header.jspf"%>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Update Kelas</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/kelas-save" method="post">
                <input type="hidden" name="id" value="${kelas.id}">
                <div class="form-group">
                    <label for="inisial">Inisial</label>
                    <input type="text" class="form-control" id="inisial" name="inisial" value="${kelas.inisial}">
                </div>
                <div class="form-group">
                    <label for="nama">Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama" value="${kelas.nama}">
                </div>
                <div class="form-group">
                    <label for="angkatan">Angkatan</label>
                    <input type="text" class="form-control" id="angkatan" name="angkatan" value="${kelas.angkatan}">
                </div>
                <div class="form-group">
                    <label for="jumlah">Jumlah</label>
                    <input type="text" class="form-control" id="jumlah" name="jumlah" value="${kelas.jumlah}">
                </div>
                <input type="submit" class="btn btn-success" value="Update">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>