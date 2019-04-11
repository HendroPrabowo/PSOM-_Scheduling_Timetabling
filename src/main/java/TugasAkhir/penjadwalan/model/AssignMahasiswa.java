package TugasAkhir.penjadwalan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_assign_mahasiswa")
public class AssignMahasiswa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer id_matakuliah;
    private Integer id_mahasiswa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_matakuliah() {
        return id_matakuliah;
    }

    public void setId_matakuliah(Integer id_matakuliah) {
        this.id_matakuliah = id_matakuliah;
    }

    public Integer getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(Integer id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }
}
