<%@include file="common/header.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Tambah Constraints</h1>
    </div>

    <form method="post">
        <div class="form-group">
            <label>Tipe Constraints</label>
            <select class="custom-select" name="tipe">
                <option value="1">Max Bekerja</option>
                <option value="2">Larangan</option>
                <option value="3">Libur</option>
            </select>
        </div>
        <div class="form-group">
            <label>Subjek</label>
            <select class="custom-select" name="subjek">
                <option value="1">Ruangan</option>
                <option value="2">Mahasiswa</option>
                <option value="3">Dosen</option>
            </select>
        </div>
        <div class="form-group">
            <label>Hari</label>
            <select class="custom-select" name="hari">
                <option value="1">Senin</option>
                <option value="2">Selasa</option>
                <option value="3">Rabu</option>
                <option value="4">Kamis</option>
                <option value="5">Jumat</option>
                <option value="6">All</option>
            </select>
        </div>
        <div class="form-group">
            <label>Max Bekerja</label>
            <input class="form-control" type="text" name="max_bekerja" placeholder="Jumlah Jam Max Bekerja">
        </div>
        <button class="btn btn-success">Save</button>
    </form>
</div>

<%@include file="common/footer.jspf"%>