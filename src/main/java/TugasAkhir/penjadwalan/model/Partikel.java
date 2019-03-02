package TugasAkhir.penjadwalan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_partikel")
public class Partikel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer idmatakuliah;
    private String nama;
    private double posisihari;
    private double posisisesi;
    private double posisiruangan;
    private double kecepatanhari;
    private double kecepatansesi;
    private double kecepatanruangan;
    private double nilaifitness;
    private double nilailocalbest;
    private double nilaiglobalbest;
    private String keterangan = ">";

    public Partikel(){
        super();
    }

    public Partikel(Integer idmatakuliah, String nama) {
        super();
        this.idmatakuliah = idmatakuliah;
        this.nama = nama;
//        this.matakuliah = matakuliahService.findOne(idmatakuliah);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdmatakuliah() {
        return idmatakuliah;
    }

    public void setIdmatakuliah(Integer idmatakuliah) {
        this.idmatakuliah = idmatakuliah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getPosisihari() {
        return posisihari;
    }

    public void setPosisihari(double posisihari) {
        this.posisihari = posisihari;
    }

    public double getPosisisesi() {
        return posisisesi;
    }

    public void setPosisisesi(double posisisesi) {
        this.posisisesi = posisisesi;
    }

    public double getPosisiruangan() {
        return posisiruangan;
    }

    public void setPosisiruangan(double posisiruangan) {
        this.posisiruangan = posisiruangan;
    }

    public double getKecepatanhari() {
        return kecepatanhari;
    }

    public void setKecepatanhari(double kecepatanhari) {
        this.kecepatanhari = kecepatanhari;
    }

    public double getKecepatansesi() {
        return kecepatansesi;
    }

    public void setKecepatansesi(double kecepatansesi) {
        this.kecepatansesi = kecepatansesi;
    }

    public double getKecepatanruangan() {
        return kecepatanruangan;
    }

    public void setKecepatanruangan(double kecepatanruangan) {
        this.kecepatanruangan = kecepatanruangan;
    }

    public double getNilaifitness() {
        return nilaifitness;
    }

    public void setNilaifitness(double nilaifitness) {
        this.nilaifitness = nilaifitness;
    }

    public double getNilailocalbest() {
        return nilailocalbest;
    }

    public void setNilailocalbest(double nilailocalbest) {
        this.nilailocalbest = nilailocalbest;
    }

    public double getNilaiglobalbest() {
        return nilaiglobalbest;
    }

    public void setNilaiglobalbest(double nilaiglobalbest) {
        this.nilaiglobalbest = nilaiglobalbest;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
