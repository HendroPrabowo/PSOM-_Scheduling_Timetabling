<%@include file="common/header.jspf"%>

<div class="container-fluid">
    <div class="jumbotron">
        <h1>Testing Page</h1>
    </div>

    <a href="/testing">Testing</a><br>
    <a href="/cek-fitness">Cek Nilai Fitness</a><br>
    <a href="/hill-climb">Hill Climbing Memperbaiki Jadwal</a><br>
    <a href="/test-excel">Test Excel</a><br>
    <a href="/cek-fitness-constraints">Cek Fitness Constraints</a><br>
    <a href="/reset-keterangan">Reset Keterangan</a><br>
    <a href="/cek">Cek</a><br>


    <h2 class="text-center">Just testing</h2>
    <form action="/testing-hill-climbing-lo" method="post">
        <button type="submit" class="btn btn-primary">Hill Climb Testing Dengan Post</button>
    </form>
</div>

<%@include file="common/footer.jspf"%>