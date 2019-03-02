<%@include file="common/header.jspf"%>


<div class="container-fluid">
    <div class="jumbotron">
        <h1>Update Dosen</h1>
    </div>

    <div class="row">
        <div class="col-md-5">
            <form action="/dosen-save" method="post">
                <input type="hidden" name="id" value="${dosen.id}">
                <div class="form-group">
                    <label for="inisial">Inisial</label>
                    <input type="text" class="form-control" id="inisial" name="inisial" value="${dosen.inisial}">
                </div>
                <div class="form-group">
                    <label for="nama">Nama</label>
                    <input type="text" class="form-control" id="nama" name="nama" value="${dosen.nama}">
                </div>
                <input type="submit" class="btn btn-success" value="Update">
            </form>
        </div>
    </div>
</div>

<%@include file="common/footer.jspf"%>