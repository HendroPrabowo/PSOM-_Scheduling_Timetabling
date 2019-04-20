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
    private Integer id_tipe;
    private Integer hari;
    private Integer sesi;

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

    public Integer getId_tipe() {
        return id_tipe;
    }

    public void setId_tipe(Integer id_tipe) {
        this.id_tipe = id_tipe;
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
}
