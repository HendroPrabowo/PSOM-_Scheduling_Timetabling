package TugasAkhir.penjadwalan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_constraints")
public class Constraints {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nama_constraints;
    private Integer tipe;
    private String subjek;
    private Integer id_subjek;
    private Integer hari;
    private Integer sesi;
    private Integer max_bekerja;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama_constraints() {
        return nama_constraints;
    }

    public void setNama_constraints(String nama_constraints) {
        this.nama_constraints = nama_constraints;
    }

    public Integer getTipe() {
        return tipe;
    }

    public void setTipe(Integer tipe) {
        this.tipe = tipe;
    }

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public Integer getId_subjek() {
        return id_subjek;
    }

    public void setId_subjek(Integer id_subjek) {
        this.id_subjek = id_subjek;
    }

    public Integer getHari() {
        return hari;
    }

    public void setHari(Integer hari) {
        this.hari = hari;
    }

    public Integer getSesi() {
        return sesi;
    }

    public void setSesi(Integer sesi) {
        this.sesi = sesi;
    }

    public Integer getMax_bekerja() {
        return max_bekerja;
    }

    public void setMax_bekerja(Integer max_bekerja) {
        this.max_bekerja = max_bekerja;
    }
}
