<%@include file="common/header.jspf"%>


<div class="container-fluid">
    <div class="jumbotron">
        <h1 class="text-center">Tambah Dosen</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/dosen-save" method="post">
                <div class="form-group">
                    <label for="inisial">Inisial</label>
                    <input type="text" class="form-control" id="inisial" name="inisial">
                </div>
                <div class="form-group">
                    <label for="nama">Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama">
                </div>
                <input type="submit" class="btn btn-success" value="Simpan">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>