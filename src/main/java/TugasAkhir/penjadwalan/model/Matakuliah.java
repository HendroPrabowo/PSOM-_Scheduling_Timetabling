package TugasAkhir.penjadwalan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_matakuliah")
public class Matakuliah {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String inisial;
    private String nama;
    private String program;
    private String jenis;
    private Integer jumlahsks;
    private String dosen1;
    private String dosen2;
    private String dosen3;
    private String dosen4;
    private String asistendosen1;
    private String asistendosen2;
    private String asistendosen3;
    private String kelas1;
    private String kelas2;
    private String kelas3;
    private String kelas4;
    private Integer jumlahrombongankelas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInisial() {
        return inisial;
    }

    public void setInisial(String inisial) {
        this.inisial = inisial;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Integer getJumlahsks() {
        return jumlahsks;
    }

    public void setJumlahsks(Integer jumlahsks) {
        this.jumlahsks = jumlahsks;
    }

    public String getDosen1() {
        return dosen1;
    }

    public void setDosen1(String dosen1) {
        this.dosen1 = dosen1;
    }

    public String getDosen2() {
        return dosen2;
    }

    public void setDosen2(String dosen2) {
        this.dosen2 = dosen2;
    }

    public String getDosen3() {
        return dosen3;
    }

    public void setDosen3(String dosen3) {
        this.dosen3 = dosen3;
    }

    public String getDosen4() {
        return dosen4;
    }

    public void setDosen4(String dosen4) {
        this.dosen4 = dosen4;
    }

    public String getAsistendosen1() {
        return asistendosen1;
    }

    public void setAsistendosen1(String asistendosen1) {
        this.asistendosen1 = asistendosen1;
    }

    public String getAsistendosen2() {
        return asistendosen2;
    }

    public void setAsistendosen2(String asistendosen2) {
        this.asistendosen2 = asistendosen2;
    }

    public String getAsistendosen3() {
        return asistendosen3;
    }

    public void setAsistendosen3(String asistendosen3) {
        this.asistendosen3 = asistendosen3;
    }

    public String getKelas1() {
        return kelas1;
    }

    public void setKelas1(String kelas1) {
        this.kelas1 = kelas1;
    }

    public String getKelas2() {
        return kelas2;
    }

    public void setKelas2(String kelas2) {
        this.kelas2 = kelas2;
    }

    public String getKelas3() {
        return kelas3;
    }

    public void setKelas3(String kelas3) {
        this.kelas3 = kelas3;
    }

    public String getKelas4() {
        return kelas4;
    }

    public void setKelas4(String kelas4) {
        this.kelas4 = kelas4;
    }

    public Integer getJumlahrombongankelas() {
        return jumlahrombongankelas;
    }

    public void setJumlahrombongankelas(Integer jumlahrombongankelas) {
        this.jumlahrombongankelas = jumlahrombongankelas;
    }
}
