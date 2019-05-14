<%@include file="common/header.jspf"%>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Tambah Ruangan</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/ruangan-save" method="post">
                <div class="form-group">
                    <label for="nama">Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama">
                </div>
                <div class="form-group">
                    <label for="jenis">Jenis</label>
                    <select name="jenis" id="jenis" class="form-control">
                        <option value="T" selected>Teori</option>
                        <option value="P">Praktikum</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="kapasitas">Kapasitas</label>
                    <input type="text" class="form-control" id="kapasitas" name="kapasitas">
                </div>

                <input type="submit" class="btn btn-success" value="Simpan">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>