package TugasAkhir.penjadwalan.controller;

import java.io.*;
import TugasAkhir.penjadwalan.model.*;
import TugasAkhir.penjadwalan.service.*;
import TugasAkhir.penjadwalan.spreadsheet.ApachePOIExcelWrite;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    private Integer jumlahIterasi = 0;

    @Autowired
    private RuanganService ruanganService;
    @Autowired
    private DosenService dosenService;
    @Autowired
    private KelasService kelasService;
    @Autowired
    private MatakuliahService matakuliahService;
    @Autowired
    private PartikelService partikelService;
    @Autowired
    private MahasiswaService mahasiswaService;
    @Autowired
    private AssignMahasiswaService assignMahasiswaService;
    @Autowired
    private ConstraintsService constraintsService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    /*
    Ruangan
     */
    @GetMapping("/ruangan")
    public String ruangan(ModelMap model){
        List<Ruangan> ruangans = new ArrayList<>();
        ruangans = ruanganService.findAll();

        model.put("ruangans", ruangans);
        return "ruangan";
    }

    @GetMapping("/ruangan-add")
    public String ruanganAdd(){
        return "ruangan-form";
    }

    @PostMapping("/ruangan-save")
    public String ruanganSave(@ModelAttribute Ruangan ruangan, BindingResult result){
        ruanganService.save(ruangan);

        return "redirect:/ruangan";
    }

    @GetMapping("/ruangan-update")
    public String ruanganUpdate(@RequestParam int id, ModelMap model){
        Ruangan ruangan = ruanganService.findOne(id);
        model.put("ruangan", ruangan);

        return "ruangan-update";
    }

    @GetMapping("/ruangan-delete")
    public String ruanganDelete(@RequestParam int id){
        ruanganService.delete(id);

        return "redirect:/ruangan";
    }

    /*
    Dosen
     */
    @GetMapping("/dosen")
    public String dosen(ModelMap model){
        List<Dosen> dosens = new ArrayList<>();
        dosens = dosenService.findAll();
        model.put("dosens", dosens);
        return "dosen";
    }

    @GetMapping("/dosen-add")
    public String dosenAdd(){
        return "dosen-form";
    }

    @PostMapping("/dosen-save")
    public String dosenSave(@ModelAttribute Dosen dosen, BindingResult result){
        dosenService.save(dosen);

        return "redirect:/dosen";
    }

    @GetMapping("/dosen-delete")
    public String dosenDelete(@RequestParam int id){
        dosenService.delete(id);

        return "redirect:/dosen";
    }

    @GetMapping("/dosen-update")
    public String dosenUpdate(@RequestParam int id, ModelMap model){
        Dosen dosen = dosenService.findOne(id);
        model.put("dosen", dosen);

        return "dosen-update";
    }

    /*
   Kelas
    */
    @GetMapping("/kelas")
    public String kelas(ModelMap model){
        List<Kelas> kelass = new ArrayList<>();
        kelass = kelasService.findAll();
        model.put("kelass", kelass);
        return "kelas";
    }

    @GetMapping("/kelas-add")
    public String kelasAdd(){
        return "kelas-form";
    }

    @PostMapping("/kelas-save")
    public String kelasSave(@ModelAttribute Kelas kelas, BindingResult result){
        kelasService.save(kelas);

        return "redirect:/kelas";
    }

    @GetMapping("/kelas-delete")
    public String kelasDelete(@RequestParam int id){
        kelasService.delete(id);

        return "redirect:/kelas";
    }

    @GetMapping("/kelas-update")
    public String kelasUpdate(@RequestParam int id, ModelMap model){
        Kelas kelas = kelasService.findOne(id);
        model.put("kelas", kelas);

        return "kelas-update";
    }

    /**
     * Matakuliah
     */
    @GetMapping("/matakuliah")
    public String matakuliah(ModelMap model){
        List<Matakuliah> matakuliahs = new ArrayList<>();
        List<Dosen> dosens = new ArrayList<>();
        List<Kelas> kelass = new ArrayList<>();
        List<AssignMahasiswa> assignMahasiswas = new ArrayList<>();

        matakuliahs = matakuliahService.findAll();
        dosens = dosenService.findAll();
        kelass = kelasService.findAll();
        assignMahasiswas = assignMahasiswaService.findAll();

        model.put("matakuliahs", matakuliahs);
        model.put("dosens", dosens);
        model.put("kelass", kelass);
        model.put("assignMahasiswas", assignMahasiswas);

        return "matakuliah";
    }

    @GetMapping("/matakuliah-add")
    public String matakuliahAdd(ModelMap model){
        List<Dosen> dosens = new ArrayList<>();
        List<Kelas> kelas = new ArrayList<>();
        List<Ruangan> ruangans = new ArrayList<>();

        dosens = dosenService.findAll();
        kelas = kelasService.findAll();
        ruangans = ruanganService.findAll();

        if(dosens.size() == 0 || kelas.size() == 0 || ruangans.size() == 0){
            return "redirect:/error-page?jenis=2";
        }

        Collections.sort(dosens, new SortByNama());

        model.put("dosens", dosens);
        model.put("kelas", kelas);

        return "matakuliah-form";
    }

    @PostMapping("/matakuliah-save")
    public String matakuliahSave(@ModelAttribute Matakuliah matakuliah, BindingResult result){
        int rombonganKelas = 0;
        // Cari jumlah rombongan kelas
        if(matakuliah.getKelas1().length() != 0){
            Kelas kelas = kelasService.findOne(Integer.parseInt(matakuliah.getKelas1()));
            rombonganKelas += kelas.getJumlah();
        }
        if(matakuliah.getKelas2().length() != 0){
            Kelas kelas = kelasService.findOne(Integer.parseInt(matakuliah.getKelas2()));
            rombonganKelas += kelas.getJumlah();
        }
        if(matakuliah.getKelas3().length() != 0){
            Kelas kelas = kelasService.findOne(Integer.parseInt(matakuliah.getKelas3()));
            rombonganKelas += kelas.getJumlah();
        }
        if(matakuliah.getKelas4().length() != 0){
            Kelas kelas = kelasService.findOne(Integer.parseInt(matakuliah.getKelas4()));
            rombonganKelas += kelas.getJumlah();
        }

        matakuliah.setJumlahrombongankelas(rombonganKelas);
        matakuliahService.save(matakuliah);

        return "redirect:/matakuliah";
    }

    @GetMapping("/matakuliah-delete")
    public String matakuliahDelete(@RequestParam int id){
        matakuliahService.delete(id);

        return "redirect:/matakuliah";
    }

    @GetMapping("/matakuliah-update")
    public String matakuliahUpdate(@RequestParam int id, ModelMap model){
        Matakuliah matakuliah = matakuliahService.findOne(id);
        model.put("matakuliah", matakuliah);

        List<Dosen> dosens = new ArrayList<>();
        List<Kelas> kelas = new ArrayList<>();

        dosens = dosenService.findAll();
        kelas = kelasService.findAll();

        Collections.sort(dosens, new SortByNama());

        model.put("dosens", dosens);
        model.put("kelas", kelas);

        return "matakuliah-update";
    }

    @GetMapping("/halaman-jadwal")
    public String halamanJadwal(ModelMap model){
        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        List<Dosen> dosens = dosenService.findAll();
        List<Kelas> kelass = kelasService.findAll();

        model.put("matakuliahs", matakuliahs);
        model.put("dosens", dosens);
        model.put("kelass", kelass);

        return "halaman-jadwal";
    }

    @PostMapping("/halaman-jadwal")
    public String generateJadwal(ModelMap model){
        long startTime = System.nanoTime();

        // Inisialisasi variable awal
        int nilaiPosisi = 1, iterator = 1;
        double nilaiRandom = 0;

        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        List<Ruangan> ruangans = ruanganService.findAll();

        // Hapus dulu smua partikel yang lama
        partikelService.deleteAll();

        // Inisialisasi posisi ruangan
        for(Ruangan ruangan : ruangans){
            System.out.println("Inisialisasi posisi ruangan");
            ruangan.setPosisi(nilaiPosisi);
            ruanganService.save(ruangan);
            nilaiPosisi++;
        }

        System.out.println("lewat if > 0");

        System.out.println("Inisialisasi partikel");
        for(Matakuliah matakuliah : matakuliahs){
            for(int i=0;i<matakuliah.getJumlahsks();i++){
                Partikel partikel = new Partikel(matakuliah.getId(), "partikel"+iterator);

                // Inisialisasi nilai hari awal
                nilaiRandom = generateNilaiPosisiHari();
                partikel.setPosisihari(nilaiRandom);
                partikel.setKecepatanhari(nilaiRandom);

                // Inisialisasi nilai sesi awal
                nilaiRandom = generateNilaiPosisiSesi();
                partikel.setPosisisesi(nilaiRandom);
                partikel.setKecepatansesi(nilaiRandom);

                // Inisialisasi nilai ruangan awal
                nilaiRandom = generateNilaiPosisiRuangan(ruangans.size());
                partikel.setPosisiruangan(nilaiRandom);
                partikel.setKecepatanruangan(nilaiRandom);

                // Inisialisasi local best
                partikel.setNilailocalbest(partikel.getPosisihari());
                partikelService.save(partikel);
                iterator++;
            }
        }

        List<Partikel> partikels = partikelService.findAll();

        cekNilaiFitnessKelas(partikels, matakuliahs);
        updateGlobalBest(partikels);
        // nilai variable learning
        updatePosisi(partikels, ruangans.size());
        // mutasi
        System.out.println("Mutasi");
        mutasi(partikels, ruangans);

        cekNilaiFitnessKelas(partikels, matakuliahs);
        cekKriteria(partikels, matakuliahs, ruangans, 1);

        // FUNGSI HILL CLIMBING
        hillClimbing(partikels);
        cekNilaiFitnessKelas(partikels, matakuliahs);
//        hillClimbing(partikels);
//        cekNilaiFitnessKelas(partikels, matakuliahs);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double second = (double)totalTime / 1000000000.0;
        System.out.println("Waktu eksekusi : "+second);

        // Mengambil nilai partikel yang baru
//        List<Partikel> partikels3 = partikelService.findAll();
        double nilaiFitnessBest = 0;
        for(Partikel partikel : partikels){
            if(partikel.getNilaifitness() == 1)
                nilaiFitnessBest++;
        }
        double akurasi = nilaiFitnessBest/partikels.size()*100;
        System.out.println("Jumlah Matakuliah "+matakuliahs.size());
        System.out.println("Jumlah Partikel "+partikels.size());
        System.out.println("Akurasi = "+akurasi+"%");

        // Kirim variabel ke view
        model.put("waktu", second);
        model.put("akurasi", akurasi);
        model.put("jumlah_matakuliah", matakuliahs.size());
        model.put("jumlah_partikel", partikels.size());

        return "redirect:/ubah-jadwal";
    }

    @GetMapping("/ubah-jadwal")
    public String ubahJadwal(ModelMap model){
        List<Partikel> partikels = partikelService.findAll();

        if(partikels.size() == 0){
            return "redirect:/error-page?jenis=1";
        }

        Collections.sort(partikels, new SortByHari());
        List<Partikel> partikelAlreadySorted = new ArrayList<>();

        for(int i=1;i<9;i++){
            List<Partikel> partikelSort = new ArrayList<>();
            for(Partikel partikel : partikels){
                if((int)partikel.getPosisihari() == i){
                    partikelSort.add(partikel);
                }
            }
            Collections.sort(partikelSort, new SortBySesi());
            for(Partikel partikel : partikelSort){
                partikelAlreadySorted.add(partikel);
            }
        }
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();

        List<Dosen> dosens = dosenService.findAll();
        List<Kelas> kelass = kelasService.findAll();

        model.put("kelass", kelass);
        model.put("partikels", partikelAlreadySorted);
        model.put("ruangans", ruangans);
        model.put("matakuliahs", matakuliahs);
        model.put("dosens", dosens);

        return "ubah-jadwal";
    }

    @PostMapping("/ubah-jadwal")
    public String ubahJadwalCek(@RequestParam("id") int id, ModelMap model){
        Partikel partikel = partikelService.findOne(id);
        List<Partikel> solusi = hillClimbingPartikel(partikel);
        List<Ruangan> ruangans = ruanganService.findAll();

        model.put("partikel", partikel);
        model.put("solusi", solusi);
        model.put("ruangans", ruangans);
        return "ubah-jadwal-choose";
    }

    @GetMapping("/ubah-jadwal-apply")
    public String ubahJadwalApplyGet(@RequestParam int hari, @RequestParam int sesi, @RequestParam int ruangan, @RequestParam int id){
        Partikel partikel = partikelService.findOne(id);
        partikel.setPosisihari(hari);
        partikel.setPosisisesi(sesi);
        partikel.setPosisiruangan(ruangan);
        partikelService.save(partikel);

        return "redirect:/ubah-jadwal";
    }

    @GetMapping("/mahasiswa")
    public String mahasiswa(ModelMap model){
        List<Mahasiswa> mahasiswas = new ArrayList<>();
        mahasiswas = mahasiswaService.findAll();
        model.put("mahasiswas", mahasiswas);
        return "mahasiswa";
    }

    @GetMapping("/assign-mahasiswa")
    public String assignMahsiswa(@RequestParam int id, ModelMap model){
        Matakuliah matakuliah = matakuliahService.findOne(id);
        List<Mahasiswa> mahasiswas = mahasiswaService.findAll();
        List<Dosen> dosens = dosenService.findAll();

        model.put("matakuliah", matakuliah);
        model.put("mahasiswas", mahasiswas);
        model.put("dosens",dosens);

        return "assign-mahasiswa";
    }

    @PostMapping("/assign-mahasiswa")
    public String assignMahasiswaPost(@RequestParam List<Integer> id){
        int id_matakuliah = id.get(0);
        Matakuliah matakuliah = matakuliahService.findOne(id_matakuliah);
        for(int i=1;i<id.size();i++){
            if(assignMahasiswaService.findMahasiswaByMatakuliah(id.get(i), id_matakuliah)){
                AssignMahasiswa assignMahasiswa = new AssignMahasiswa();
                assignMahasiswa.setId_mahasiswa(id.get(i));
                assignMahasiswa.setId_matakuliah(id_matakuliah);
                assignMahasiswaService.save(assignMahasiswa);
            }
        }

        return "redirect:/matakuliah";
    }

    @GetMapping("/assign-mahasiswa-list")
    public String assingMahasiswaList(@RequestParam int id, ModelMap model){
        Matakuliah matakuliah = matakuliahService.findOne(id);

        List<Dosen> dosens = dosenService.findAll();
        List<AssignMahasiswa> assignMahasiswas = assignMahasiswaService.getAllMahasiswaByMatakuliahId(id);

        List<Mahasiswa> mahasiswas = new ArrayList<>();

        for(AssignMahasiswa assignMahasiswa : assignMahasiswas){
            Mahasiswa mahasiswa = mahasiswaService.fincOne(assignMahasiswa.getId_mahasiswa());
            mahasiswas.add(mahasiswa);
        }

        model.put("matakuliah", matakuliah);
        model.put("mahasiswas", mahasiswas);
        model.put("dosens",dosens);

        return "assign-mahasiswa-list";
    }

    @GetMapping("/constraints")
    public String constraints(ModelMap model){
        List<Constraints> constraints = constraintsService.findAll();
        model.put("constraints", constraints);

        return "constraints";
    }

    @GetMapping("/tambah-constraints")
    public String tambahConstraints(ModelMap model){
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Dosen> dosens = dosenService.findAll();
        List<Mahasiswa> mahasiswas = mahasiswaService.findAll();

        model.put("ruangans", ruangans);
        model.put("dosens", dosens);
        model.put("mahasiswas", mahasiswas);

        return "constraints-form";
    }

//    @PostMapping("/tambah-constraints")
//    public String tambahConstraintsPost(@RequestParam Integer tipeConstraints, @RequestParam Integer subjek, @RequestParam Integer idDosen, @RequestParam Integer hari, @RequestParam Integer max_bekerja){
//
//        return "redirect:/constraints";
//    }

    @PostMapping("/max-bekerja")
    public String maxBekerja(@RequestParam String subjek, @RequestParam Integer hari, @RequestParam Integer max_bekerja){

        String[] input = subjek.split(" ");
        String tipe = input[0];
        Integer id = Integer.parseInt(input[1]);

        if(tipe.equals("dosen")){
            Dosen dosen = dosenService.findOne(id);
            maxBekerjaGeneric(dosen, hari, max_bekerja); // Fungsi Generic Dipanggil
        }
        else if(tipe.equals("mahasiswa")){
            Mahasiswa  mahasiswa = mahasiswaService.fincOne(id);
            maxBekerjaGeneric(mahasiswa, hari, max_bekerja);
        }
        else {
            Ruangan ruangan = ruanganService.findOne(id);
            maxBekerjaGeneric(ruangan, hari, max_bekerja);
        }

        return "redirect:/constraints";
    }

    @PostMapping("/larangan")
    public String larangan(@RequestParam String subjek, @RequestParam Integer hari, @RequestParam Integer sesi){

        String[] input = subjek.split(" ");
        String tipe = input[0];
        Integer id = Integer.parseInt(input[1]);

        if(tipe.equals("dosen")){
            Dosen dosen = dosenService.findOne(id);
            laranganGeneric(dosen, hari, sesi);
        }
        else if(tipe.equals("ruangan")){
            Ruangan ruangan = ruanganService.findOne(id);
            laranganGeneric(ruangan, hari, sesi);
        }

        return "redirect:/constraints";
    }

    @PostMapping("/hapus-constraint")
    public String hapusConstraint(@RequestParam int id){
        constraintsService.delete(id);

        return "redirect:/constraints";
    }

    // ========= FUNGSI JAVA GENERIC =============
    public <T> void genericDisplay (T element)
    {
        System.out.println("Element.getClass = "+element.getClass());
        System.out.println("Element.getClass.getName = "+element.getClass().getName());
        System.out.println("Element.getClass.getSimpleName = "+element.getClass().getSimpleName());
        System.out.println("Element = "+element);
    }

    public <T> void maxBekerjaGeneric(T subjek, Integer hari, Integer max_bekerja){
        String tipe = subjek.getClass().getSimpleName();

        if(tipe.equals("Dosen")){
            Dosen dosen = (Dosen)subjek;

            Constraints constraints = new Constraints();
            constraints.setTipe(1);
            constraints.setNama_constraints("D1");
            constraints.setSubjek(tipe);
            constraints.setId_subjek(dosen.getId());
            constraints.setHari(hari);
            constraints.setMax_bekerja(max_bekerja);
            constraintsService.save(constraints);
        }
        else if(tipe.equals("Mahasiswa")){
            Mahasiswa mahasiswa = (Mahasiswa)subjek;

            Constraints constraints = new Constraints();
            constraints.setTipe(1);
            constraints.setNama_constraints("D1");
            constraints.setSubjek(tipe);
            constraints.setId_subjek(mahasiswa.getId());
            constraints.setHari(hari);
            constraints.setMax_bekerja(max_bekerja);
            constraintsService.save(constraints);
        }
        else {
            Ruangan ruangan = (Ruangan)subjek;

            Constraints constraints = new Constraints();
            constraints.setTipe(1);
            constraints.setNama_constraints("D1");
            constraints.setSubjek(tipe);
            constraints.setId_subjek(ruangan.getId());
            constraints.setHari(hari);
            constraints.setMax_bekerja(max_bekerja);
            constraintsService.save(constraints);
    }

    }

    public <T> void laranganGeneric(T subjek, Integer hari, Integer sesi){
        String tipe = subjek.getClass().getSimpleName();

        if(tipe.equals("Dosen")){
            Dosen dosen = (Dosen)subjek;

            Constraints constraints = new Constraints();
            constraints.setTipe(2);
            constraints.setNama_constraints("D2");
            constraints.setSubjek(tipe);
            constraints.setId_subjek(dosen.getId());
            constraints.setHari(hari);
            constraints.setSesi(sesi);
            constraintsService.save(constraints);
        }else if(tipe.equals("Ruangan")){
            Ruangan ruangan = (Ruangan)subjek;

            Constraints constraints = new Constraints();
            constraints.setTipe(2);
            constraints.setNama_constraints("D2");
            constraints.setSubjek(tipe);
            constraints.setId_subjek(ruangan.getId());
            constraints.setHari(hari);
            constraints.setSesi(sesi);
            constraintsService.save(constraints);
        }
    }

    // =========== FUNGSI UMUM =============
    public double generateNilaiPosisiHari(){
        double hasil = 0;
        hasil = 1 + Math.random()*(5);

        if(hasil < 1 || hasil >= 6){
            generateNilaiPosisiHari();
        }

        return hasil;
    }

    public void cekConstraintsDatabase(){
        List<Constraints> constraints = constraintsService.findAll();

        for(Constraints constraint : constraints){
            Integer tipe = constraint.getTipe();
            if(tipe.equals(1)){
                // Max Bekerja
                System.out.println(constraint.getNama_constraints()+" "+constraint.getSubjek()+" "+constraint.getId_subjek());
            }
            else if(tipe.equals(2)){
                // Larangan
            }
            else if(tipe.equals(3)){
                // Liburan
            }
        }
    }

    public double generateNilaiPosisiSesi(){
        double hasil = 0;
        hasil = 1 + Math.random()*(8);
        if(hasil<1||hasil>8){
            generateNilaiPosisiSesi();
        }

        return hasil;
    }

    public double generateNilaiPosisiRuangan(int panjangRuangan){
        double hasil = 0;
        hasil = 1 + Math.random()*(panjangRuangan);
        if(hasil<1||hasil>panjangRuangan){
            generateNilaiPosisiRuangan(panjangRuangan);
        }

        return hasil;
    }

    public void cekNilaiFitnessKelas(List<Partikel> partikels, List<Matakuliah> matakuliahs){
        System.out.println("Cek nilai fitness berdasarkan kelas");
        resetKeterangan(partikels);

        // Loop sebanyak jumlah partikel
        for(int i=0;i<partikels.size();i++){
            double nilaiFitness = 0, pinalti = 0, pinaltiDosen = 0, pinaltiAsistenDosen = 0;
            String keterangan = null;

            // Partikel1 == i
            Partikel partikel1 = partikels.get(i);
            Matakuliah matakuliah1 = matakuliahService.findOne(partikel1.getIdmatakuliah());

            for(int j=0;j<partikels.size();j++){
                if(i==j || j==i){
                    continue;
                }

                // Partikel2 == j
                Partikel partikel2 = partikels.get(j);
                Matakuliah matakuliah2 = matakuliahService.findOne(partikel2.getIdmatakuliah());

                // Pengecekan partikel, matakuliah yang sama tidak bisa dialokasikan pada hari dan sesi yang sama
                if(
                        partikel1.getIdmatakuliah() == partikel2.getIdmatakuliah() &&
                                Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                                Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi())
                ){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C1:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }

                // Pengecekan partikel, matakuliah berbeda tidak dapat berada pada hari, sesi dan ruangan yang sama
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                                Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                                Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi()) &&
                                Math.floor(partikel1.getPosisiruangan()) == Math.floor(partikel2.getPosisiruangan())
                ){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C2:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }

                /*
                ============ PENGECEKAN MATAKULIAH BEREBDA DENGA KELAS SAMA TIDAK BISA DIALOKASIKAN DI HARI DAN SESI YANG SAMA
                 */
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                                Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                                Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi())
                ){
                    if(matakuliah1.getKelas1().length() != 0){
                        if(matakuliah1.getKelas1().equals(matakuliah2.getKelas1())){
                            keterangan = partikel1.getKeterangan();
                            keterangan = keterangan.concat(" C8:"+partikel2.getIdmatakuliah());
                            partikel1.setKeterangan(keterangan);
                            partikelService.save(partikel1);
                            pinalti++;
                        }
                        if(matakuliah1.getKelas1().equals(matakuliah2.getKelas2())){
                            keterangan = partikel1.getKeterangan();
                            keterangan = keterangan.concat(" C8:"+partikel2.getIdmatakuliah());
                            partikel1.setKeterangan(keterangan);
                            partikelService.save(partikel1);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getKelas2().length() != 0){
                        if(matakuliah1.getKelas2().equals(matakuliah2.getKelas1())){
                            keterangan = partikel1.getKeterangan();
                            keterangan = keterangan.concat(" C8:"+partikel2.getIdmatakuliah());
                            partikel1.setKeterangan(keterangan);
                            partikelService.save(partikel1);
                            pinalti++;
                        }
                        if(matakuliah1.getKelas2().equals(matakuliah2.getKelas2())){
                            keterangan = partikel1.getKeterangan();
                            keterangan = keterangan.concat(" C8:"+partikel2.getIdmatakuliah());
                            partikel1.setKeterangan(keterangan);
                            partikelService.save(partikel1);
                            pinalti++;
                        }
                    }
                }


                String jenis = matakuliahService.findJenisMatakuliah(partikel1.getIdmatakuliah());
                //Pengecekan partikel, matakuliah berbeda, hari sesi sama dicek bentrok antar dosen dan asisten dosen
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                                (int)partikel1.getPosisihari() == (int)partikel2.getPosisihari() &&
                                (int)partikel1.getPosisisesi() == (int)partikel2.getPosisisesi()
                ){
                    if(jenis.equals("T")){
                        // Dosen 1
                        if(matakuliah1.getDosen1().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen1(), matakuliah2, pinaltiDosen);
                        // Dosen 2
                        if(matakuliah1.getDosen2().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen2(), matakuliah2, pinaltiDosen);
                        // Dosen 3
                        if(matakuliah1.getDosen3().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen3(), matakuliah2, pinaltiDosen);
                        // Dosen 4
                        if(matakuliah1.getDosen4().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen4(), matakuliah2, pinaltiDosen);
                    }
                    else if(jenis.equals("P")){
                        // Asisten Dosen 1
                        if(matakuliah1.getAsistendosen1().length() != 0)
                            pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen1(), matakuliah2, pinaltiAsistenDosen);
                        // Asisten Dosen 2
                        if(matakuliah1.getAsistendosen2().length() != 0)
                            pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen2(), matakuliah2, pinaltiAsistenDosen);
                        // Asisten Dosen 3
                        if(matakuliah1.getAsistendosen3().length() != 0)
                            pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen3(), matakuliah2, pinaltiAsistenDosen);
                    }
                }

                // Perhitungan pinalti dosen dan asisten dosen
                if(pinaltiDosen!=0){
                    keterangan = partikels.get(i).getKeterangan();
                    keterangan = keterangan.concat(" C5:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikels.get(i));
                    // reset nilai pinalti dosen
                    pinaltiDosen = 0;
                    pinalti++;
                }
                if(pinaltiAsistenDosen != 0){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C6:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    // reset nilai pinaltiAsistenDosen
                    pinaltiAsistenDosen = 0;
                    pinalti++;
                }
            }

            // PENGECEKAN JIKA MATAKULIAH PRAKTIKUM DIALOKASIKAN DI RUANGAN TEORI
            String jenis = matakuliah1.getJenis();
            if(jenis.equals("P")){
                Ruangan ruang = ruanganService.findByPosisi((int)partikel1.getPosisiruangan());
                if(ruang.getJenis().equals("T")){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C7");
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }
            }else{
                Ruangan ruang = ruanganService.findByPosisi((int)partikel1.getPosisiruangan());
                if(ruang.getJenis().equals("L")){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C7");
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }
            }

            // Pengecekan partikel untuk sesi ibadah
            if((int)partikel1.getPosisihari() == 5 && (int)partikel1.getPosisisesi() == 5){
                keterangan = partikel1.getKeterangan();
                keterangan = keterangan.concat(" C3");
                partikel1.setKeterangan(keterangan);
                partikelService.save(partikel1);
                pinalti++;
            }

            // Cek rombongan kelas dengan kapasitas ruangan
            Integer rombongan = matakuliah1.getJumlahrombongankelas();
            Ruangan ruangan = ruanganService.findByPosisi((int)Math.floor(partikel1.getPosisiruangan()));
            if(rombongan > ruangan.getKapasitas()){
                keterangan = partikel1.getKeterangan();
                keterangan = keterangan.concat(" C4");
                partikel1.setKeterangan(keterangan);
                partikelService.save(partikel1);
                pinalti++;
            }



            // Perhitungan nilai fitness dan simpan nilai fitness
            nilaiFitness = (1.0)/(1.0+pinalti);
            partikel1.setNilaifitness(nilaiFitness);
            partikelService.save(partikel1);
        }

        cekFitnessConstraints();

        System.out.println("Cek nilai fitness selesai");
    }

    public void resetKeterangan(List<Partikel> partikels){
        for (Partikel partikel : partikels){
            partikel.setKeterangan("");
            partikel.setNilaifitness(1);
            partikelService.save(partikel);
        }
    }

    public double cekPinaltiDosen(String dosen, Matakuliah matakuliah, double pinaltiDosen){
        String dosen1 = matakuliah.getDosen1(), dosen2 = matakuliah.getDosen2(), dosen3 = matakuliah.getDosen3(), dosen4 = matakuliah.getDosen4();
        for(int i=1;i<5;i++){
            if(i == 1){
                if(dosen1.length() != 0){
                    if(dosen.equals(dosen1))
                        pinaltiDosen++;
                }
            }
            else if(i == 2){
                if(dosen2.length() != 0){
                    if(dosen.equals(dosen2))
                        pinaltiDosen++;
                }
            }
            else if(i == 3){
                if(dosen3.length() != 0){
                    if(dosen.equals(dosen3))
                        pinaltiDosen++;
                }
            }
            else if(i == 4){
                if(dosen4.length() != 0){
                    if(dosen.equals(dosen4))
                        pinaltiDosen++;
                }
            }
        }

        return pinaltiDosen;
    }

    public double cekPinaltiAsistenDosen(String asistenDosen, Matakuliah matakuliah, double pinaltiAsistenDosen){
        String asistenDosen1 = matakuliah.getAsistendosen1(), asistenDosen2 = matakuliah.getAsistendosen2(), asistenDosen3 = matakuliah.getAsistendosen3();
        for(int i=1;i<4;i++){
            if(i == 1){
                if(matakuliah.getAsistendosen1().length() != 0)
                    if(asistenDosen.equals(asistenDosen1))
                        pinaltiAsistenDosen++;
            }
            else if(i == 2){
                if(matakuliah.getAsistendosen2().length() != 0)
                    if(asistenDosen.equals(asistenDosen2))
                        pinaltiAsistenDosen++;
            }
            else if(i == 3){
                if(matakuliah.getAsistendosen3().length() != 0)
                    if(asistenDosen.equals(asistenDosen3))
                        pinaltiAsistenDosen++;
            }
        }

        return pinaltiAsistenDosen;
    }

    public void updateGlobalBest(List<Partikel> partikels){
        double min = 1000;
        for(Partikel partikel : partikels){
            if(partikel.getNilaifitness() <= min)
                min = partikel.getNilaifitness();
        }
        for(Partikel partikel : partikels){
            partikel.setNilaiglobalbest(min);
            partikelService.save(partikel);

        }
    }

    public void updatePosisi(List<Partikel> partikels, int jumlahRuangan){
        double c1 = 0.1, c2 = 0.8, w = 0.9;
        for(Partikel partikel : partikels){
//            // Update posisi hari
            generateNilaiUpdatePosisiHari(partikel, c1, c2, w);
//            // Update posisi sesi
            generateNilaiUpdatePosisiSesi(partikel, c1, c2, w);
//            // Update posisi ruangan
            generateNilaiUpdatePosisiRuangan(partikel, jumlahRuangan, c1, c2, w);
        }
    }

    public void generateNilaiUpdatePosisiHari(Partikel partikel, double c1, double c2, double w){
        double hasil = 1, posisi = 0;
        hasil = (w*partikel.getKecepatanhari())+(c1*Math.random()*(partikel.getNilailocalbest()-partikel.getPosisihari()))+(c2*Math.random()*(partikel.getNilaiglobalbest()-partikel.getPosisihari()));
        posisi = partikel.getPosisihari() + hasil;
        if(posisi<1.0||posisi>=6.0){
            generateNilaiUpdatePosisiHari(partikel, 0, 0, 0);
        }
        else {
            partikel.setPosisihari(posisi);
            partikelService.save(partikel);
        }
    }

    public void generateNilaiUpdatePosisiSesi(Partikel partikel, double w, double c1, double c2){
        double hasil = 0,posisi = 0;
        hasil = (w*partikel.getKecepatansesi())+(c1*Math.random()*(partikel.getNilailocalbest()-partikel.getPosisisesi()))+(c2*Math.random()*(partikel.getNilaiglobalbest()-partikel.getPosisisesi()));
        posisi = partikel.getPosisisesi()+hasil;
        if(posisi<1.0||posisi>=9.0){
            generateNilaiUpdatePosisiSesi(partikel, 0, 0, 0);
        }
        else{
            partikel.setPosisisesi(posisi);
            partikelService.save(partikel);
        }
    }

    public void generateNilaiUpdatePosisiRuangan(Partikel partikel, int jumlahRuangan, double w, double c1, double c2){
        double hasil = 0,posisi = 0;
        hasil = (w*partikel.getKecepatanruangan())+(c1*Math.random()*(partikel.getNilailocalbest()-partikel.getPosisiruangan()))+(c2*Math.random()*(partikel.getNilaiglobalbest()-partikel.getPosisiruangan()));
        posisi = partikel.getPosisiruangan()+hasil;
        if(posisi<1.0||posisi>=jumlahRuangan+1.0){
            generateNilaiUpdatePosisiRuangan(partikel, jumlahRuangan, 0, 0, 0);
        }
        else{
            partikel.setPosisiruangan(posisi);
            partikelService.save(partikel);
        }
    }

    public void mutasi(List<Partikel> partikels, List<Ruangan> ruangans){
        for (int i=0;i<partikels.size();i++){
            double jumlahMK = 1;
            // komponen i
            Partikel partikel1 = partikels.get(i);
            for (int j=0;j<partikels.size();j++){
                if(j == i){
                    continue;
                }
                // komponen j
                Partikel partikel2 = partikels.get(j);

                // mutasi pada id matakuliah, hari dan sesi yang sama
                if(
                        partikel1.getIdmatakuliah() == partikel2.getIdmatakuliah() &&
                        (int)partikel1.getPosisihari() == (int)partikel2.getPosisihari() &&
                        (int)partikel1.getPosisisesi() == (int)partikel2.getPosisisesi()
                ){
                    int posisiSesi = (int)partikel2.getPosisisesi();
                    if(posisiSesi == 8 || posisiSesi > 8){
                        posisiSesi = 1;
                    }else {
                        posisiSesi++;
                    }
                    partikel2.setPosisisesi(posisiSesi);
                    partikelService.save(partikel2);
                }

                // mutasi pada id matakuliah berbeda, hari sesi dan ruangan sama
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                        (int)partikel1.getPosisihari() == (int)partikel2.getPosisihari() &&
                        (int)partikel1.getPosisisesi() == (int)partikel2.getPosisisesi() &&
                        (int)partikel1.getPosisiruangan() == (int)partikel2.getPosisiruangan()
                ){
                    // ruangan diupdate
                    generateNilaiUpdatePosisiRuangan(partikel2, ruangans.size(), 0.9, 0.1, 1.8);
                }

                // mutasi pada matakulah berturut turut di hari yang sama
                if(partikel1.getIdmatakuliah() == partikel2.getIdmatakuliah() && (int)partikel1.getPosisihari() == (int)partikel2.getPosisihari()){
                    jumlahMK++;
                    if(jumlahMK == 3){
                        int posisiHari =(int)partikel2.getPosisihari();
                        if(posisiHari == 5 || posisiHari > 5)
                            posisiHari = 1;
                        else
                            posisiHari++;
                        partikel2.setPosisihari(posisiHari);
                        partikelService.save(partikel2);
                    }
                }
            }
        }
    }

    public void cekKriteria(List<Partikel> partikels, List<Matakuliah> matakuliahs, List<Ruangan> ruangans, int jumlahIterasiPartikel){
        jumlahIterasi = jumlahIterasiPartikel;
        if(jumlahIterasi <= 5){
            for(Partikel partikel : partikels){
                if(jumlahIterasi <= 5 && partikel.getNilaifitness() != 1.0){
                    updateLocalDanGlobalBest(partikels);
                    // ubah posisi setiap partikel
                    updatePosisi(partikels, ruangans.size());
                    // mutasi
                    mutasi(partikels, ruangans);
                    cekNilaiFitnessKelas(partikels, matakuliahs);
                    System.out.println("Iterasi ke : "+jumlahIterasi);
                    jumlahIterasi++;
                    cekKriteria(partikels, matakuliahs, ruangans, jumlahIterasi);
                }else if(jumlahIterasi > 5){
                    System.out.println("Iterasi Maksimal");
                    continue;
                }
            }
        }else{
            System.out.println("Melewati batas iterasi");
        }
    }

    public void updateLocalDanGlobalBest(List<Partikel> partikels){
        for(Partikel partikel : partikels){
            if(partikel.getPosisihari() <= partikel.getNilaiglobalbest()){
                partikel.setNilailocalbest(partikel.getPosisihari());
                partikelService.save(partikel);
            }
        }
    }

    public void hillClimbing(List<Partikel> partikels){
        System.out.println("Hill Climbing Start");
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Partikel> partikelMelanggar = new ArrayList<>();

        // Mengambil partikel yang masih melanggar
        for(Partikel partikel : partikels){
            if(partikel.getNilaifitness() != 1)
                partikelMelanggar.add(partikel);
        }

        // Looping partikel yang melanggar ke hari, sesi, ruangan lain
        for(Partikel partikel : partikelMelanggar){
            int hari = 0, sesi = 0, ruangan = 0, loop = 0;
            for(int i=1;i<6;i++){
                hari = i;
                for(int j=1;j<9;j++){
                    sesi = j;
                    for(int k=1;k<ruangans.size()+1;k++){
                        ruangan = k;
//                        System.out.println(hari+" "+sesi+" "+ruangan);
//                        System.out.println(partikel.getNama()+" hari:"+hari+" sesi:"+sesi+" ruangan:"+ruangan);
                        Partikel partikel2 = partikelService.findPartikel(hari, sesi, ruangan);
                        if(
                                partikel2 != null &&
                                partikel.getId() == partikel2.getId() &&
                                partikel2.getNilaifitness() == 1
                        ){
                            loop = 1;
                            break;
                        }else {
                            cekNilaiFitnessPartikelPerKelas(partikel, i, j, k);
                        }
                    }
                    if(loop != 0)
                        break;
                }
                if(loop != 0)
                    break;
            }
            if(loop != 0)
                break;
        }

        System.out.println("Hill Climbing Selesai");
    }

    public List<Partikel> hillClimbingPartikel(Partikel partikel){
        System.out.println("Hill Climbing "+partikel.getNama());
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Partikel> solusi = new ArrayList<>();
        int counter = 1;

        for(int i=1;i<6;i++){ // hari
            for(int j=1;j<9;j++){ // sesi
                for(int k=1;k<ruangans.size()+1;k++){ // ruangan
                    Partikel partikel1 = partikelService.findPartikel(i, j, k);
                    if(partikel1 != null){
                        continue;
                    }else {
                        // cek nilai fitnessnya
                        int nilaiFitness = (int)cekNilaiFitnessPartikelReturnInt(partikel, i, j, k);
                        if(nilaiFitness == 1){
                            Partikel solusiPartikel = new Partikel();
                            solusiPartikel.setIdmatakuliah(partikel.getIdmatakuliah());
                            solusiPartikel.setNama("PartikelSolusi"+counter);
                            counter++;
                            solusiPartikel.setPosisihari(i);
                            solusiPartikel.setPosisisesi(j);
                            solusiPartikel.setPosisiruangan(k);
                            solusiPartikel.setNilaifitness(nilaiFitness);
                            solusiPartikel.setKeterangan("");
                            solusi.add(solusiPartikel);
                        }
                    }
                }
            }
        }

        return solusi;
    }

    public double cekNilaiFitnessPartikelReturnInt(Partikel partikel1, int hari, int sesi, int ruangan){
        double nilaiFitness = 0, pinalti = 0, pinaltiDosen = 0, pinaltiAsistenDosen = 0;
        String keterangan = "";
        List<Partikel> partikels = partikelService.findAll();

        // inisialisasi partikel percobaan
        Partikel percobaan = new Partikel(partikel1.getIdmatakuliah(), partikel1.getNama()+"Coba");
        percobaan.setPosisihari(hari);
        percobaan.setPosisisesi(sesi);
        percobaan.setPosisiruangan(ruangan);
        percobaan.setKecepatanhari(partikel1.getKecepatanhari());
        percobaan.setKecepatansesi(partikel1.getKecepatansesi());
        percobaan.setKecepatanruangan(partikel1.getKecepatanruangan());
        percobaan.setNilaifitness(partikel1.getNilaifitness());
        percobaan.setNilailocalbest(partikel1.getNilailocalbest());
        percobaan.setNilaiglobalbest(partikel1.getNilaiglobalbest());
        percobaan.setKeterangan("");

        Partikel partikel2 = partikelService.findPartikel(hari, sesi, ruangan);
        // Slotnya kosong
        if(partikel2 != null){
            return 0;
        }

        /*
        =========== FUNGSI BARU =============
         */
        // AMBIL SEMUA MAHASISWA DI PARTIKEL 1
        List<AssignMahasiswa> assignMahasiswasPartikel1 = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel1.getIdmatakuliah());

        for(Partikel partikel : partikels){
            String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
            Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
            Matakuliah matakuliah2 = matakuliahService.findOne(partikel.getIdmatakuliah());

            /*
============ PENGECEKAN MATAKULIAH BEREBDA DENGA KELAS SAMA TIDAK BISA DIALOKASIKAN DI HARI DAN SESI YANG SAMA
*/
            if(
                    percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                if(matakuliah1.getKelas1().length() != 0){
                    if(matakuliah1.getKelas1().equals(matakuliah2.getKelas1())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    if(matakuliah1.getKelas1().equals(matakuliah2.getKelas2())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
                if(matakuliah1.getKelas2().length() != 0){
                    if(matakuliah1.getKelas2().equals(matakuliah2.getKelas1())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    if(matakuliah1.getKelas2().equals(matakuliah2.getKelas2())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }

            // Pengecekan partikel, matakuliah yang sama tidak bisa dialokasikan pada hari dan sesi yang sama
            if(
                    percobaan.getIdmatakuliah() == partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C1:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }

            //Pengecekan partikel, matakuliah berbeda, hari sesi sama dicek bentrok antar dosen dan asisten dosen
            if(
                percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                (int)percobaan.getPosisihari() == (int)partikel.getPosisihari() &&
                (int)percobaan.getPosisisesi() == (int)partikel.getPosisisesi()
            ){
                if(jenis.equals("T")){
                    // Dosen 1
                    if(matakuliah1.getDosen1().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen1(), matakuliah2, pinaltiDosen);
                    // Dosen 2
                    if(matakuliah1.getDosen2().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen2(), matakuliah2, pinaltiDosen);
                    // Dosen 3
                    if(matakuliah1.getDosen3().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen3(), matakuliah2, pinaltiDosen);
                    // Dosen 4
                    if(matakuliah1.getDosen4().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen4(), matakuliah2, pinaltiDosen);
                }
                if(jenis.equals("P")){
                    // Asisten Dosen 1
                    if(matakuliah1.getAsistendosen1().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen1(), matakuliah2, pinaltiAsistenDosen);
                    // Asisten Dosen 2
                    if(matakuliah1.getAsistendosen2().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen2(), matakuliah2, pinaltiAsistenDosen);
                    // Asisten Dosen 3
                    if(matakuliah1.getAsistendosen3().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen3(), matakuliah2, pinaltiAsistenDosen);
                }
            }

            /*
            =========== FUNGSI BARU =============
             */
            // AMBIL SEMUA MAHASISWA DI PARTIKEL 2
            List<AssignMahasiswa> assignMahasiswasPartikel = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel.getIdmatakuliah());
            // PENGECEKAN APAKAH PADA MATAKULIAH YANG SAMA DAN SESI YANG SAMA MAHASISWA BERTABRAKAN JADWAL
            if(
                    percobaan.getIdmatakuliah() == partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                for(AssignMahasiswa assignMahasiswaPartikel1 : assignMahasiswasPartikel1){
                    for(AssignMahasiswa assignMahasiswaPartikel : assignMahasiswasPartikel){
                        if(assignMahasiswaPartikel1.getId_mahasiswa().equals(assignMahasiswaPartikel.getId_mahasiswa())){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" Matkul:"+partikel.getId()+"=>Mahasiswa:"+assignMahasiswaPartikel1.getId_mahasiswa());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
            }
            // PENGECEKAN PADA MATAKULIAH BERBEDA PADA HARI, SESI YANG SAMA APAKAH MAHASISWA BERTABRAKAN
            if(
                    percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                    (int)percobaan.getPosisihari() == (int)partikel.getPosisihari() &&
                    (int)percobaan.getPosisisesi() == (int)partikel.getPosisisesi()
            ){
                for(AssignMahasiswa assignMahasiswaPartikel1 : assignMahasiswasPartikel1){
                    for(AssignMahasiswa assignMahasiswaPartikel : assignMahasiswasPartikel){
                        if(assignMahasiswaPartikel1.getId_mahasiswa().equals(assignMahasiswaPartikel.getId_mahasiswa())){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" Matkul:"+partikel.getId()+"=>Mahasiswa:"+assignMahasiswaPartikel1.getId_mahasiswa());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
            }

            // Perhitungan pinalti
            if(pinaltiDosen!=0){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C5:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                // reset nilai pinalti dosen
                pinaltiDosen = 0;
                pinalti++;
            }
            if(pinaltiAsistenDosen != 0){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C6:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                // reset nilai pinaltiAsistenDosen
                pinaltiAsistenDosen = 0;
                pinalti++;
            }
        }

        // Pengecekan partikel untuk sesi ibadah
        if((int)percobaan.getPosisihari() == 5 && (int)percobaan.getPosisisesi() == 5){
            keterangan = percobaan.getKeterangan();
            keterangan = keterangan.concat(" C3");
            percobaan.setKeterangan(keterangan);
            pinalti++;
        }

        // Cek rombongan kelas dengan kapasitas ruangan
        int rombongan = matakuliahService.findOne(percobaan.getIdmatakuliah()).getJumlahrombongankelas();
        Ruangan kelas = ruanganService.findByPosisi(ruangan);
        if(rombongan > kelas.getKapasitas()){
            keterangan = percobaan.getKeterangan();
            keterangan = keterangan.concat(" C4");
            percobaan.setKeterangan(keterangan);
            pinalti++;
        }

        // ======= PENGECEKAN CONSTRAINTS TAMBAHAN =========
	/*
	============== CONSTRAINTS MAX BEKERJA ==================
	 */
        List<Constraints> constraints_max_bekerja = constraintsService.getConstraintsMaxBekerjaHari(hari);
        Matakuliah matakuliah = matakuliahService.findOne(percobaan.getIdmatakuliah());
        if(constraints_max_bekerja.size() > 0){
//            System.out.println("Constraints MAX BEKERJA");
            for(Constraints constraint : constraints_max_bekerja){
//                System.out.println("Cek constraints "+constraint.getId());
                if(constraint.getSubjek().equals("Dosen")){
                    Integer jumlahMengajar = 1;
//                    System.out.println("Tipe Dosen");
                    if(matakuliah.getDosen1().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("1. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen1()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen1()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen1()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen2().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("2. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen2()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen2()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen2()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen3().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("3. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen3()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen3()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen3()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen4().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("4. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen4()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen4()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen4()+" mengajar "+jumlahMengajar);
                    }
                    if(jumlahMengajar > constraint.getMax_bekerja()){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    System.out.println("Dosen "+constraint.getId_subjek()+" mengajar "+jumlahMengajar+"x. Sehingga pinalti "+pinalti);
                }
                else if(constraint.getSubjek().equals("Ruangan")){
                    Integer ruanganDigunakan = 0;
                    Ruangan ruangan_dalam_constraint = ruanganService.findOne(constraint.getId_subjek());
                    if((int)percobaan.getPosisiruangan() == ruangan_dalam_constraint.getPosisi()){
                        ruanganDigunakan += ruanganDipakaiPadaHari(constraint.getId_subjek(), hari);
                    }
                    if(ruanganDigunakan > constraint.getMax_bekerja()){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }
        }
	/*
	============== CONSTRAINTS LARANGAN ==================
	 */
        List<Constraints> constraints_larangan = constraintsService.getConstraintsLarangan();
        if(constraints_larangan.size() > 0){
            for(Constraints constraint : constraints_larangan){
                // Jika constraint larangan dosen
                if(constraint.getSubjek().equals("Dosen")){
                    Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
                    // Mencocokkan dosen yang dilarang ke partikel percobaan
                    if(matakuliah1.getDosen1().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen2().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen3().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen4().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
                // Jika constraint larangan ruangan
                else if(constraint.getSubjek().equals("Ruangan")){
                    Ruangan ruangan_percobaan = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());

                    if(
                            constraint.getId_subjek().equals(ruangan_percobaan.getId()) &&
                            constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                            constraint.getSesi().equals((int)percobaan.getPosisisesi())
                    ){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }
        }

        // PENGECEKAN JIKA MATAKULIAH PRAKTIKUM DIALOKASIKAN DI RUANGAN TEORI
        String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
        if(jenis.equals("P")){
            Ruangan ruang = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());
            if(ruang.getJenis().equals("T")){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C7");
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }
        }else{
            Ruangan ruang = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());
            if(ruang.getJenis().equals("P")){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C7");
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }
        }

        // Perhitungan nilai fitness dan simpan nilai fitness
        nilaiFitness = (1.0)/(1.0+pinalti);
        percobaan.setNilaifitness(nilaiFitness);

        if(percobaan.getNilaifitness() == 1){
            return 1;
        }
        return nilaiFitness;
    }

    public void cekNilaiFitnessPartikelPerKelas(Partikel partikel1, int hari, int sesi, int ruangan){

        if(((int)partikel1.getNilaifitness()) == 1){
            return;
        }

        double nilaiFitness = 0, pinalti = 0, pinaltiDosen = 0, pinaltiAsistenDosen = 0;
        String keterangan = null;
        List<Partikel> partikels = partikelService.findAll();

        Partikel partikel2 = partikelService.findPartikel(hari, sesi, ruangan);
        // Slotnya kosong
        if(partikel2 != null){
            if(partikel1.getId() == partikel2.getId() && partikel1.getNilaifitness() == 1){
                return;
            }
            System.out.println("partikel2 tidak kosong");
            return;
        }

        // inisialisasi partikel percobaan
        Partikel percobaan = new Partikel(partikel1.getIdmatakuliah(), partikel1.getNama()+"Coba");
        percobaan.setPosisihari(hari);
        percobaan.setPosisisesi(sesi);
        percobaan.setPosisiruangan(ruangan);
        percobaan.setKecepatanhari(partikel1.getKecepatanhari());
        percobaan.setKecepatansesi(partikel1.getKecepatansesi());
        percobaan.setKecepatanruangan(partikel1.getKecepatanruangan());
        percobaan.setNilaifitness(partikel1.getNilaifitness());
        percobaan.setNilailocalbest(partikel1.getNilailocalbest());
        percobaan.setNilaiglobalbest(partikel1.getNilaiglobalbest());
        percobaan.setKeterangan("");

        for(Partikel partikel : partikels){
            String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
            Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
            Matakuliah matakuliah2 = matakuliahService.findOne(partikel.getIdmatakuliah());

            /*
============ PENGECEKAN MATAKULIAH BEREBDA DENGA KELAS SAMA TIDAK BISA DIALOKASIKAN DI HARI DAN SESI YANG SAMA
 */
            if(
                    percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                if(matakuliah1.getKelas1().length() != 0){
                    if(matakuliah1.getKelas1().equals(matakuliah2.getKelas1())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    if(matakuliah1.getKelas1().equals(matakuliah2.getKelas2())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
                if(matakuliah1.getKelas2().length() != 0){
                    if(matakuliah1.getKelas2().equals(matakuliah2.getKelas1())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    if(matakuliah1.getKelas2().equals(matakuliah2.getKelas2())){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" C8:"+partikel.getIdmatakuliah());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }

            // Pengecekan partikel, matakuliah yang sama tidak bisa dialokasikan pada hari dan sesi yang sama
            if(
                    percobaan.getIdmatakuliah() == partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C1:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }

            // Pengecekan partikel, matakuliah berbeda, hari sesi sama dicek bentrok antar dosen dan asisten dosen
            if(
                    percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                    (int)percobaan.getPosisihari() == (int)partikel.getPosisihari() &&
                    (int)percobaan.getPosisisesi() == (int)partikel.getPosisisesi()
            ){
                if(jenis.equals("T")){
                    // Dosen 1
                    if(matakuliah1.getDosen1().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen1(), matakuliah2, pinaltiDosen);
                    // Dosen 2
                    if(matakuliah1.getDosen2().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen2(), matakuliah2, pinaltiDosen);
                    // Dosen 3
                    if(matakuliah1.getDosen3().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen3(), matakuliah2, pinaltiDosen);
                    // Dosen 4
                    if(matakuliah1.getDosen4().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen4(), matakuliah2, pinaltiDosen);
                }
                if(jenis.equals("P")){
                    // Asisten Dosen 1
                    if(matakuliah1.getAsistendosen1().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen1(), matakuliah2, pinaltiAsistenDosen);
                    // Asisten Dosen 2
                    if(matakuliah1.getAsistendosen2().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen2(), matakuliah2, pinaltiAsistenDosen);
                    // Asisten Dosen 3
                    if(matakuliah1.getAsistendosen3().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen3(), matakuliah2, pinaltiAsistenDosen);
                }
            }

            // Perhitungan pinalti
            if(pinaltiDosen!=0){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C5:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                // reset nilai pinalti dosen
                pinaltiDosen = 0;
                pinalti++;
            }
            if(pinaltiAsistenDosen != 0){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C6:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                // reset nilai pinaltiAsistenDosen
                pinaltiAsistenDosen = 0;
                pinalti++;
            }
        }

        // Pengecekan partikel untuk sesi ibadah
        if((int)percobaan.getPosisihari() == 5 && (int)percobaan.getPosisisesi() == 5){
            keterangan = percobaan.getKeterangan();
            keterangan = keterangan.concat(" C3");
            percobaan.setKeterangan(keterangan);
            pinalti++;
        }

        // Cek rombongan kelas dengan kapasitas ruangan
        int rombongan = matakuliahService.findOne(percobaan.getIdmatakuliah()).getJumlahrombongankelas();
        Ruangan kelas = ruanganService.findByPosisi(ruangan);
        if(rombongan > kelas.getKapasitas()){
            keterangan = percobaan.getKeterangan();
            keterangan = keterangan.concat(" C4");
            percobaan.setKeterangan(keterangan);
            pinalti++;
        }

        // ======= PENGECEKAN CONSTRAINTS TAMBAHAN =========
        /*
        ============== CONSTRAINTS MAX BEKERJA ==================
         */
        List<Constraints> constraints_max_bekerja = constraintsService.getConstraintsMaxBekerjaHari(hari);
        Matakuliah matakuliah = matakuliahService.findOne(percobaan.getIdmatakuliah());
        if(constraints_max_bekerja.size() > 0){
//            System.out.println("Constraints MAX BEKERJA");
            for(Constraints constraint : constraints_max_bekerja){
//                System.out.println("Cek constraints "+constraint.getId());
                if(constraint.getSubjek().equals("Dosen")){
                    Integer jumlahMengajar = 1;
//                    System.out.println("Tipe Dosen");
                    if(matakuliah.getDosen1().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("1. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen1()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen1()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen1()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen2().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("2. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen2()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen2()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen2()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen3().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("3. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen3()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen3()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen3()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen4().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("4. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen4()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen4()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen4()+" mengajar "+jumlahMengajar);
                    }
                    if(jumlahMengajar > constraint.getMax_bekerja()){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    System.out.println("Dosen "+constraint.getId_subjek()+" mengajar "+jumlahMengajar+"x. Sehingga pinalti "+pinalti);
                }
                else if(constraint.getSubjek().equals("Ruangan")){
                    Integer ruanganDigunakan = 0;
                    Ruangan ruangan_dalam_constraint = ruanganService.findOne(constraint.getId_subjek());
                    if((int)percobaan.getPosisiruangan() == ruangan_dalam_constraint.getPosisi()){
                        ruanganDigunakan += ruanganDipakaiPadaHari(constraint.getId_subjek(), hari);
                    }
                    if(ruanganDigunakan > constraint.getMax_bekerja()){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }
        }
        /*
        ============== CONSTRAINTS LARANGAN ==================
         */
        List<Constraints> constraints_larangan = constraintsService.getConstraintsLarangan();
        if(constraints_larangan.size() > 0){
            for(Constraints constraint : constraints_larangan){
                // Jika constraint larangan dosen
                if(constraint.getSubjek().equals("Dosen")){
                    Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
                    // Mencocokkan dosen yang dilarang ke partikel percobaan
                    if(matakuliah1.getDosen1().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen2().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen3().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen4().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4())) &&
                                constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
                // Jika constraint larangan ruangan
                else if(constraint.getSubjek().equals("Ruangan")){
                    Ruangan ruangan_percobaan = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());

                    if(
                            constraint.getId_subjek().equals(ruangan_percobaan.getId()) &&
                            constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                            constraint.getSesi().equals((int)percobaan.getPosisisesi())
                    ){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }
        }

        // PENGECEKAN JIKA MATAKULIAH PRAKTIKUM DIALOKASIKAN DI RUANGAN TEORI
        String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
        if(jenis.equals("P")){
            Ruangan ruang = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());
            if(ruang.getJenis().equals("T")){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C7");
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }
        }else{
            Ruangan ruang = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());
            if(ruang.getJenis().equals("L")){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C7");
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }
        }

        // Perhitungan nilai fitness dan simpan nilai fitness
        nilaiFitness = (1.0)/(1.0+pinalti);
        percobaan.setNilaifitness(nilaiFitness);

        System.out.println(partikel1.getNama()+" "+hari+" "+sesi+" "+ruangan+" NF:"+nilaiFitness+" Ket:"+percobaan.getKeterangan());

        if(percobaan.getNilaifitness() == 1){
            partikel1.setPosisihari(hari);
            partikel1.setPosisisesi(sesi);
            partikel1.setPosisiruangan(ruangan);
            partikel1.setNilaifitness(1);
            partikel1.setKeterangan("");
            partikelService.save(partikel1);
            System.out.println(partikel1.getNama()+" bisa diubah ke hari:"+hari+" sesi:"+sesi+" ruangan:"+ruangan);
            return;
        }
        else if(percobaan.getNilaifitness() > partikel1.getNilaifitness()){
            partikel1.setPosisihari(hari);
            partikel1.setPosisisesi(sesi);
            partikel1.setPosisiruangan(ruangan);
            partikel1.setNilaifitness(percobaan.getNilaifitness());
            partikel1.setKeterangan(keterangan);
            partikelService.save(partikel1);
        }

    }

    // ========= FUNGSI TESTING ==========
    @PostMapping("/testing-hill-climbing-lo")
    public String hillClimb(){
        List<Partikel> partikels = partikelService.findAll();

        hillClimbing(partikels);

        return "testing";
    }

    @GetMapping("/cek-fitness")
    public String cekFitness(){
        List<Partikel> partikels = partikelService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();

        cekNilaiFitnessKelas(partikels, matakuliahs);

        return "testing";
    }

    @PostMapping("/cek-fitness")
    public String cekFitnesssss(){
        List<Partikel> partikels = partikelService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();

        cekNilaiFitnessKelas(partikels, matakuliahs);

        return "testing";
    }

    @GetMapping("/cek-fitness-per-mahasiswa")
    public String cekFitnessPerMahasiswa(){
        List<Partikel> partikels = partikelService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();

        cekNilaiFitnessPerMahasiswa(partikels, matakuliahs);
        return "testing";
    }

    @GetMapping("/hill-climb")
    public String testHillClimb(){
        List<Partikel> partikels = partikelService.findAll();

        hillClimbing(partikels);

        return "testing";
    }

    @PostMapping("/hill-climb-per-mahasiswa")
    public String hillClimbingPerMahasiswa(){
        List<Partikel> partikels = partikelService.findAll();

        hillClimbingPerMahasiswa(partikels);

        return "testing";
    }

    @GetMapping("/test-excel")
    public String textExcel(){
        List<Partikel> partikels = partikelService.findAll();
        Collections.sort(partikels, new SortByHari());
        List<Partikel> partikelAlreadySorted = new ArrayList<>();

        for(int i=1;i<9;i++){
            List<Partikel> partikelSort = new ArrayList<>();
            for(Partikel partikel : partikels){
                if((int)partikel.getPosisihari() == i){
                    partikelSort.add(partikel);
                }
            }
            Collections.sort(partikelSort, new SortBySesi());
            for(Partikel partikel : partikelSort){
                partikelAlreadySorted.add(partikel);
            }
        }

        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Dosen> dosens = dosenService.findAll();
        List<Kelas> kelass = kelasService.findAll();

        ApachePOIExcelWrite excelWrite = new ApachePOIExcelWrite();
        excelWrite.exportExcel(partikelAlreadySorted, matakuliahs, ruangans, dosens, kelass);

        return "testing";
    }

    @GetMapping("/testing")
    public String testing(){
        return "testing";
    }

    @GetMapping("/cek-fitness-constraints")
    public String cekFitnessConstraintTest(){
        cekFitnessConstraints();

        return "testing";
    }

    public void cekFitnessConstraints(){
        List<Constraints> constraints = constraintsService.findAll();

        // Loop semua constraint yang ada di database satu per satu
        for (Constraints constraint : constraints){
            Integer pinalti = 0;
            System.out.println("Cek constraints "+constraint.getId());
            // Kondisi cek fitness untuk constraints max bekerja
            if(constraint.getTipe().equals(1)){
                System.out.println("Pengecekan Constraint MAX BEKERJA");
                // Cek subjeknya apakah dosen, mahasiswa atau ruangan
                // Kondisi jika subjek Dosen
                if(constraint.getSubjek().equals("Dosen")){
                    System.out.println("Subjek Constraint "+constraint.getSubjek());
                    int counter = 0;
                    // Ambil semuua partikel pada hari dalam constraints
                    List<Partikel> partikels1 = partikelService.findPartikelByHari(constraint.getHari());
                    List<Partikel> partikelMelanggar = new ArrayList<>();
                    System.out.println("Partikel hari "+constraint.getHari());
                    for(Partikel partikel : partikels1){
                        System.out.println("Partikel "+partikel.getId());
                        Matakuliah matakuliah = matakuliahService.findOne(partikel.getIdmatakuliah());
                        System.out.println("Matakuliah "+matakuliah.getNama());
                        if(matakuliah.getDosen1().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1()))){
                            partikelMelanggar.add(partikel);
                            counter++;
                        }
                        else if(matakuliah.getDosen2().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2()))){
                            partikelMelanggar.add(partikel);
                            counter++;
                        }
                        else if(matakuliah.getDosen3().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3()))){
                            partikelMelanggar.add(partikel);
                            counter++;
                        }
                        else if(matakuliah.getDosen4().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4()))){
                            partikelMelanggar.add(partikel);
                            counter++;
                        }
                    }
                    System.out.println("Total Counter "+counter);
                    if(counter > constraint.getMax_bekerja()){
                        pinalti++;
                        System.out.println("Constraints "+constraint.getId()+" dilanggar");
                        System.out.println("Total pinalti "+pinalti);
                        for(Partikel partikel : partikelMelanggar){
                            String keterangan = partikel.getKeterangan();
                            Double nilaiFitness = partikel.getNilaifitness();
                            System.out.println("Partikel "+partikel.getId());
                            System.out.println("Nilai Fitness "+nilaiFitness);
                            Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                            System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                            keterangan = keterangan.concat(" D1:"+constraint.getId());
                            partikel.setKeterangan(keterangan);
                            partikel.setNilaifitness(nilaiFitnessBaru);
                            partikelService.save(partikel);
                        }
                    }
                }
                // Kondisi jika subjek Ruangan
                else if(constraint.getSubjek().equals("Ruangan")){
                    System.out.println("Subjek Constraints "+constraint.getSubjek());
                    int counter = 0;
                    // Ambil semua partikel dalam constraints
                    List<Partikel> partikels1 = partikelService.findPartikelByHari(constraint.getHari());
                    // Sediakan partikel penampung untuk partikel yang dialokasikan pada hari itu
                    List<Partikel> partikelMelanggar = new ArrayList<>();
                    Ruangan ruangan = ruanganService.findOne(constraint.getId_subjek());
                    // Loop semuua partikel pada hari tersebut
                    for(Partikel partikel : partikels1){
                        if(ruangan.getPosisi().equals((int)partikel.getPosisiruangan())){
                            partikelMelanggar.add(partikel);
                            counter++;
                        }
                    }

                    System.out.println("Total Counter "+counter);
                    if(counter > constraint.getMax_bekerja()){
                        pinalti++;
                        System.out.println("Constraints "+constraint.getId()+" dilanggar");
                        System.out.println("Total pinalti "+pinalti);
                        for(Partikel partikel : partikelMelanggar){
                            String keterangan = partikel.getKeterangan();
                            Double nilaiFitness = partikel.getNilaifitness();
                            System.out.println("Partikel "+partikel.getId());
                            System.out.println("Nilai Fitness "+nilaiFitness);
                            Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                            System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                            keterangan = keterangan.concat(" D1:"+constraint.getId());
                            partikel.setKeterangan(keterangan);
                            partikel.setNilaifitness(nilaiFitnessBaru);
                            partikelService.save(partikel);
                        }
                    }
                }
            }
            // Cek fitness untuk constraints larangan
            else if(constraint.getTipe().equals(2)){
                System.out.println("Pengecekan Constraint LARANGAN");
                if(constraint.getSubjek().equals("Dosen")){
                    List<Partikel> partikels = partikelService.findPartikelByHariSesi(constraint.getHari(), constraint.getSesi());

                    for(Partikel partikel : partikels){
                        Matakuliah matakuliah = matakuliahService.findOne(partikel.getIdmatakuliah());

                        if(matakuliah.getDosen1().length() != 0){
                            if(constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1()))){
                                pinalti++;
                                String keterangan = partikel.getKeterangan();
                                Double nilaiFitness = partikel.getNilaifitness();
                                System.out.println("Partikel "+partikel.getId());
                                System.out.println("Nilai Fitness "+nilaiFitness);
                                Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                                System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                                keterangan = keterangan.concat(" D2:"+constraint.getId());
                                partikel.setKeterangan(keterangan);
                                partikel.setNilaifitness(nilaiFitnessBaru);
                                partikelService.save(partikel);
                            }
                        }
                        else if(matakuliah.getDosen2().length() != 0){
                            if(constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2()))){
                                pinalti++;
                                String keterangan = partikel.getKeterangan();
                                Double nilaiFitness = partikel.getNilaifitness();
                                System.out.println("Partikel "+partikel.getId());
                                System.out.println("Nilai Fitness "+nilaiFitness);
                                Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                                System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                                keterangan = keterangan.concat(" D2:"+constraint.getId());
                                partikel.setKeterangan(keterangan);
                                partikel.setNilaifitness(nilaiFitnessBaru);
                                partikelService.save(partikel);
                            }
                        }
                        else if(matakuliah.getDosen3().length() != 0){
                            if(constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3()))){
                                pinalti++;
                                String keterangan = partikel.getKeterangan();
                                Double nilaiFitness = partikel.getNilaifitness();
                                System.out.println("Partikel "+partikel.getId());
                                System.out.println("Nilai Fitness "+nilaiFitness);
                                Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                                System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                                keterangan = keterangan.concat(" D2:"+constraint.getId());
                                partikel.setKeterangan(keterangan);
                                partikel.setNilaifitness(nilaiFitnessBaru);
                                partikelService.save(partikel);
                            }
                        }
                        else if(matakuliah.getDosen4().length() != 0){
                            if(constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4()))){
                                pinalti++;
                                String keterangan = partikel.getKeterangan();
                                Double nilaiFitness = partikel.getNilaifitness();
                                System.out.println("Partikel "+partikel.getId());
                                System.out.println("Nilai Fitness "+nilaiFitness);
                                Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                                System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                                keterangan = keterangan.concat(" D2:"+constraint.getId());
                                partikel.setKeterangan(keterangan);
                                partikel.setNilaifitness(nilaiFitnessBaru);
                                partikelService.save(partikel);
                            }
                        }
                    }
                }
                else if(constraint.getSubjek().equals("Ruangan")){
                    List<Partikel> partikels = partikelService.findPartikelByHariSesi(constraint.getHari(), constraint.getSesi());
                    Ruangan ruangan_dilarang = ruanganService.findOne(constraint.getId_subjek());

                    for(Partikel partikel : partikels){
                        Ruangan ruangan = ruanganService.findByPosisi((int)partikel.getPosisiruangan());

                        if(ruangan.getId().equals(ruangan_dilarang.getId())){
                            pinalti++;
                            String keterangan = partikel.getKeterangan();
                            Double nilaiFitness = partikel.getNilaifitness();
                            System.out.println("Partikel "+partikel.getId());
                            System.out.println("Nilai Fitness "+nilaiFitness);
                            Double nilaiFitnessBaru = nilaiFitness / (1.0 + pinalti);
                            System.out.println("Nilai Fitness Baru "+nilaiFitnessBaru);
                            keterangan = keterangan.concat(" D2:"+constraint.getId());
                            partikel.setKeterangan(keterangan);
                            partikel.setNilaifitness(nilaiFitnessBaru);
                            partikelService.save(partikel);
                        }
                    }
                }
            }
        }
    }

    @GetMapping("/reset-keterangan")
    public String resetKeteranganPartikel(){
        List<Partikel> partikels = partikelService.findAll();
        resetKeterangan(partikels);

        return "testing";
    }

    @GetMapping("/cek")
    public String cek(){
        Partikel partikel = partikelService.findOne(179);
        int hari=5,ruangan=1,sesi=1;

        cekNilaiFitnessPartikelPerKelas(partikel, hari, sesi, ruangan);

        return "testing";
    }

    public int dosenMengajarPadaHari(Integer dosen, Integer hari){
        System.out.println(dosen+" "+hari);
        List<Partikel> partikels = partikelService.findPartikelByHari(hari);
        int counter = 0;

        for(Partikel partikel : partikels){
            Matakuliah matakuliah = matakuliahService.findOne(partikel.getIdmatakuliah());
            if((matakuliah.getDosen1().length() != 0 && dosen.equals(Integer.parseInt(matakuliah.getDosen1())))){
                counter++;
            }
            if((matakuliah.getDosen2().length() != 0 && dosen.equals(Integer.parseInt(matakuliah.getDosen2())))){
                counter++;
            }
            if((matakuliah.getDosen3().length() != 0 && dosen.equals(Integer.parseInt(matakuliah.getDosen3())))){
                counter++;
            }
            if((matakuliah.getDosen4().length() != 0 && dosen.equals(Integer.parseInt(matakuliah.getDosen4())))){
                counter++;
            }
        }

        return counter;
    }

    public int ruanganDipakaiPadaHari(Integer id_ruangan, Integer hari){
        System.out.println(id_ruangan+" "+hari);
        List<Partikel> partikels = partikelService.findPartikelByHari(hari);
        int counter = 0;

        for(Partikel partikel : partikels){
            Ruangan ruangan = ruanganService.findByPosisi((int)partikel.getPosisiruangan());
            if(ruangan.getId().equals(id_ruangan)){
                counter++;
            }
        }

        return counter;
    }

    @PostMapping("/generate-jadwal-per-mahasiswa")
    public String generateJadwalPerMahasiswa(ModelMap model){
        long startTime = System.nanoTime();

        // Inisialisasi variable awal
        int nilaiPosisi = 1, iterator = 1;
        double nilaiRandom = 0;

        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        List<Ruangan> ruangans = ruanganService.findAll();

        // Hapus dulu smua partikel yang lama
        partikelService.deleteAll();

        // Inisialisasi posisi ruangan
        for(Ruangan ruangan : ruangans){
            System.out.println("Inisialisasi posisi ruangan");
            ruangan.setPosisi(nilaiPosisi);
            ruanganService.save(ruangan);
            nilaiPosisi++;
        }

        System.out.println("lewat if > 0");

        System.out.println("Inisialisasi partikel");
        for(Matakuliah matakuliah : matakuliahs){
            for(int i=0;i<matakuliah.getJumlahsks();i++){
                Partikel partikel = new Partikel(matakuliah.getId(), "partikel"+iterator);

                // Inisialisasi nilai hari awal
                nilaiRandom = generateNilaiPosisiHari();
                partikel.setPosisihari(nilaiRandom);
                partikel.setKecepatanhari(nilaiRandom);

                // Inisialisasi nilai sesi awal
                nilaiRandom = generateNilaiPosisiSesi();
                partikel.setPosisisesi(nilaiRandom);
                partikel.setKecepatansesi(nilaiRandom);

                // Inisialisasi nilai ruangan awal
                nilaiRandom = generateNilaiPosisiRuangan(ruangans.size());
                partikel.setPosisiruangan(nilaiRandom);
                partikel.setKecepatanruangan(nilaiRandom);

                // Inisialisasi local best
                partikel.setNilailocalbest(partikel.getPosisihari());
                partikelService.save(partikel);
                iterator++;
            }
        }

        List<Partikel> partikels = partikelService.findAll();

        cekNilaiFitnessPerMahasiswa(partikels, matakuliahs);
        updateGlobalBest(partikels);
        // nilai variable learning
        updatePosisi(partikels, ruangans.size());
        // mutasi
        System.out.println("Mutasi");
        mutasi(partikels, ruangans);

        cekNilaiFitnessPerMahasiswa(partikels, matakuliahs);
        cekKriteria(partikels, matakuliahs, ruangans, 1);

        // FUNGSI HILL CLIMBING
        hillClimbingPerMahasiswa(partikels);
        cekNilaiFitnessPerMahasiswa(partikels, matakuliahs);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double second = (double)totalTime / 1000000000.0;
        System.out.println("Waktu eksekusi : "+second);

        // Mengambil nilai partikel yang baru
//        List<Partikel> partikels3 = partikelService.findAll();
        double nilaiFitnessBest = 0;
        for(Partikel partikel : partikels){
            if(partikel.getNilaifitness() == 1)
                nilaiFitnessBest++;
        }
        double akurasi = nilaiFitnessBest/partikels.size()*100;
        System.out.println("Jumlah Matakuliah "+matakuliahs.size());
        System.out.println("Jumlah Partikel "+partikels.size());
        System.out.println("Akurasi = "+akurasi+"%");

        // Kirim variabel ke view
        model.put("waktu", second);
        model.put("akurasi", akurasi);
        model.put("jumlah_matakuliah", matakuliahs.size());
        model.put("jumlah_partikel", partikels.size());

        return "redirect:/ubah-jadwal";
    }

    public void cekNilaiFitnessPerMahasiswa(List<Partikel> partikels, List<Matakuliah> matakuliahs){
        System.out.println("Cek nilai fitness per Mahasiswa");
        resetKeterangan(partikels);

        // Loop sebanyak jumlah partikel
        for(int i=0;i<partikels.size();i++){
            double nilaiFitness = 0, pinalti = 0, pinaltiDosen = 0, pinaltiAsistenDosen = 0;
            String keterangan = null;

            // Partikel1 == i
            Partikel partikel1 = partikels.get(i);
            Matakuliah matakuliah1 = matakuliahService.findOne(partikel1.getIdmatakuliah());

            for(int j=0;j<partikels.size();j++){
                if(i==j || j==i){
                    continue;
                }

                // Partikel2 == j
                Partikel partikel2 = partikels.get(j);
                Matakuliah matakuliah2 = matakuliahService.findOne(partikel2.getIdmatakuliah());

                // Pengecekan partikel, matakuliah yang sama tidak bisa dialokasikan pada hari dan sesi yang sama
                if(
                        partikel1.getIdmatakuliah() == partikel2.getIdmatakuliah() &&
                                Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                                Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi())
                ){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C1:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }

                // Pengecekan partikel, matakuliah berbeda tidak dapat berada pada hari, sesi dan ruangan yang sama
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                                Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                                Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi()) &&
                                Math.floor(partikel1.getPosisiruangan()) == Math.floor(partikel2.getPosisiruangan())
                ){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C2:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }



                /*
                ============= FUNGSI BARU DITAMBAHKAN =============
                PENGECEKAN SETIAP PARTIKEL, MAHASISWA YANG JADWALNYA BERTABRAKAN
                MAHASISWA YANG SAMA DENGAN MATAKULIAH YANG SAMA TIDAK DAPAT MASUK PADA HARI DAN SESI YANG SAMA
                 */
                if(
                        partikel1.getIdmatakuliah() == partikel2.getIdmatakuliah() &&
                        Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                        Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi())
                ){
                    List<AssignMahasiswa> assignMahasiswasPartikel1 = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel1.getIdmatakuliah());
                    List<AssignMahasiswa> assignMahasiswasPartikel2 = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel2.getIdmatakuliah());

                    for(AssignMahasiswa assignMahasiswaPartikel1 : assignMahasiswasPartikel1){
                        for(AssignMahasiswa assignMahasiswaPartikel2 : assignMahasiswasPartikel2){
                            // Jika mahasiswa di kedua partikel sama, maka terjadi pelanggaran
                            if(assignMahasiswaPartikel1.getId_mahasiswa().equals(assignMahasiswaPartikel2.getId_mahasiswa())){
                                keterangan = partikel1.getKeterangan();
                                keterangan = keterangan.concat(" Matkul:"+partikel2.getId()+"=>Mahasiswa:"+assignMahasiswaPartikel1.getId_mahasiswa());
                                partikel1.setKeterangan(keterangan);
                                partikelService.save(partikel1);
                                pinalti++;
                            }
                        }
                    }
                }
                /*
                PENGECEKAN SETIAP PARTIKEL DENGAN MATAKULIAH BERBEDA, MAHASISWA TIDAK BISA MASUK BERSAMAAN DI HARI DAN SESI YANG SAMA
                 */
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                        Math.floor(partikel1.getPosisihari()) == Math.floor(partikel2.getPosisihari()) &&
                        Math.floor(partikel1.getPosisisesi()) == Math.floor(partikel2.getPosisisesi())
                ){
                    List<AssignMahasiswa> assignMahasiswasPartikel1 = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel1.getIdmatakuliah());
                    List<AssignMahasiswa> assignMahasiswasPartikel2 = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel2.getIdmatakuliah());

                    for(AssignMahasiswa assignMahasiswaPartikel1 : assignMahasiswasPartikel1){
                        for(AssignMahasiswa assignMahasiswaPartikel2 : assignMahasiswasPartikel2){
                            // Jika mahasiswa di kedua partikel sama, maka terjadi pelanggaran
                            if(assignMahasiswaPartikel1.getId_mahasiswa().equals(assignMahasiswaPartikel2.getId_mahasiswa())){
                                keterangan = partikel1.getKeterangan();
                                keterangan = keterangan.concat(" Matkul:"+partikel2.getId()+"=>Mahasiswa:"+assignMahasiswaPartikel1.getId_mahasiswa());
                                partikel1.setKeterangan(keterangan);
                                partikelService.save(partikel1);
                                pinalti++;
                            }
                        }
                    }
                }

                String jenis = matakuliahService.findJenisMatakuliah(partikel1.getIdmatakuliah());
                //Pengecekan partikel, matakuliah berbeda, hari sesi sama dicek bentrok antar dosen dan asisten dosen
                if(
                        partikel1.getIdmatakuliah() != partikel2.getIdmatakuliah() &&
                        (int)partikel1.getPosisihari() == (int)partikel2.getPosisihari() &&
                        (int)partikel1.getPosisisesi() == (int)partikel2.getPosisisesi()
                ){
                    if(jenis.equals("T")){
                        // Dosen 1
                        if(matakuliah1.getDosen1().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen1(), matakuliah2, pinaltiDosen);
                        // Dosen 2
                        if(matakuliah1.getDosen2().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen2(), matakuliah2, pinaltiDosen);
                        // Dosen 3
                        if(matakuliah1.getDosen3().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen3(), matakuliah2, pinaltiDosen);
                        // Dosen 4
                        if(matakuliah1.getDosen4().length() != 0)
                            pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen4(), matakuliah2, pinaltiDosen);
                    }
                    if(jenis.equals("P")){
                        // Asisten Dosen 1
                        if(matakuliah1.getAsistendosen1().length() != 0)
                            pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen1(), matakuliah2, pinaltiAsistenDosen);
                        // Asisten Dosen 2
                        if(matakuliah1.getAsistendosen2().length() != 0)
                            pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen2(), matakuliah2, pinaltiAsistenDosen);
                        // Asisten Dosen 3
                        if(matakuliah1.getAsistendosen3().length() != 0)
                            pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen3(), matakuliah2, pinaltiAsistenDosen);
                    }
                }

                // Perhitungan pinalti dosen dan asisten dosen
                if(pinaltiDosen!=0){
                    keterangan = partikels.get(i).getKeterangan();
                    keterangan = keterangan.concat(" C5:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikels.get(i));
                    // reset nilai pinalti dosen
                    pinaltiDosen = 0;
                    pinalti++;
                }
                if(pinaltiAsistenDosen != 0){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C6:"+partikel2.getId());
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    // reset nilai pinaltiAsistenDosen
                    pinaltiAsistenDosen = 0;
                    pinalti++;
                }
            }

            // PENGECEKAN JIKA MATAKULIAH PRAKTIKUM DIALOKASIKAN DI RUANGAN TEORI
            String jenis = matakuliah1.getJenis();
            if(jenis.equals("P")){
                Ruangan ruang = ruanganService.findByPosisi((int)partikel1.getPosisiruangan());
                if(ruang.getJenis().equals("T")){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C7");
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }
            }else{
                Ruangan ruang = ruanganService.findByPosisi((int)partikel1.getPosisiruangan());
                if(ruang.getJenis().equals("L")){
                    keterangan = partikel1.getKeterangan();
                    keterangan = keterangan.concat(" C7");
                    partikel1.setKeterangan(keterangan);
                    partikelService.save(partikel1);
                    pinalti++;
                }
            }

            // Pengecekan partikel untuk sesi ibadah
            if((int)partikel1.getPosisihari() == 5 && (int)partikel1.getPosisisesi() == 5){
                keterangan = partikel1.getKeterangan();
                keterangan = keterangan.concat(" C3");
                partikel1.setKeterangan(keterangan);
                partikelService.save(partikel1);
                pinalti++;
            }

            // Cek rombongan kelas dengan kapasitas ruangan
            Integer rombongan = matakuliah1.getJumlahrombongankelas();
            Ruangan ruangan = ruanganService.findByPosisi((int)Math.floor(partikel1.getPosisiruangan()));
            if(rombongan > ruangan.getKapasitas()){
                keterangan = partikel1.getKeterangan();
                keterangan = keterangan.concat(" C4");
                partikel1.setKeterangan(keterangan);
                partikelService.save(partikel1);
                pinalti++;
            }

            // Perhitungan nilai fitness dan simpan nilai fitness
            nilaiFitness = (1.0)/(1.0+pinalti);
            partikel1.setNilaifitness(nilaiFitness);
            partikelService.save(partikel1);
        }

        cekFitnessConstraints();

        System.out.println("Cek nilai fitness selesai");
    }

    public void hillClimbingPerMahasiswa(List<Partikel> partikels){
        System.out.println("Hill Climbing Per Mahasiswa");
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Partikel> partikelMelanggar = new ArrayList<>();

        // Mengambil partikel yang masih melanggar
        for(Partikel partikel : partikels){
            if(partikel.getNilaifitness() != 1)
                partikelMelanggar.add(partikel);
        }
        // Looping partikel yang melanggar ke hari, sesi, ruangan lain
        for(Partikel partikel : partikelMelanggar){
            int hari = 0, sesi = 0, ruangan = 0, loop = 0;
            for(int i=1;i<6;i++){
                hari = i;
                for(int j=1;j<9;j++){
                    sesi = j;
                    for(int k=1;k<ruangans.size()+1;k++){
                        ruangan = k;
//                        System.out.println(hari+" "+sesi+" "+ruangan);
//                        System.out.println(partikel.getNama()+" hari:"+hari+" sesi:"+sesi+" ruangan:"+ruangan);
                        Partikel partikel2 = partikelService.findPartikel(hari, sesi, ruangan);
                        if(
                                partikel2 != null &&
                                partikel.getId() == partikel2.getId() &&
                                partikel2.getNilaifitness() == 1
                        ){
                            loop = 1;
                            break;
                        }else {
                            cekNilaiFitnessPartikelPerMahasiswa(partikel, i, j, k);
                        }
                    }
                    if(loop != 0)
                        break;
                }
                if(loop != 0)
                    break;
            }
            if(loop != 0)
                break;
        }

        System.out.println("Hill Climbing Selesai");
    }

    public void cekNilaiFitnessPartikelPerMahasiswa(Partikel partikel1, int hari, int sesi, int ruangan){

        if(((int)partikel1.getNilaifitness()) == 1){
            return;
        }

        double nilaiFitness = 0, pinalti = 0, pinaltiDosen = 0, pinaltiAsistenDosen = 0;
        String keterangan = null;
        List<Partikel> partikels = partikelService.findAll();

        Partikel partikel2 = partikelService.findPartikel(hari, sesi, ruangan);
        // Slotnya kosong
        if(partikel2 != null){
            if(partikel1.getId() == partikel2.getId() && partikel1.getNilaifitness() == 1){
                return;
            }
            System.out.println("partikel2 tidak kosong");
            return;
        }

        // inisialisasi partikel percobaan
        Partikel percobaan = new Partikel(partikel1.getIdmatakuliah(), partikel1.getNama()+"Coba");
        percobaan.setPosisihari(hari);
        percobaan.setPosisisesi(sesi);
        percobaan.setPosisiruangan(ruangan);
        percobaan.setKecepatanhari(partikel1.getKecepatanhari());
        percobaan.setKecepatansesi(partikel1.getKecepatansesi());
        percobaan.setKecepatanruangan(partikel1.getKecepatanruangan());
        percobaan.setNilaifitness(partikel1.getNilaifitness());
        percobaan.setNilailocalbest(partikel1.getNilailocalbest());
        percobaan.setNilaiglobalbest(partikel1.getNilaiglobalbest());
        percobaan.setKeterangan("");

        /*
        =========== FUNGSI BARU =============
         */
        // AMBIL SEMUA MAHASISWA DI PARTIKEL 1
        List<AssignMahasiswa> assignMahasiswasPartikel1 = assignMahasiswaService.getAllMahasiswaByMatakuliahId(partikel1.getIdmatakuliah());

        for(Partikel partikel : partikels){
            String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
            Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
            Matakuliah matakuliah2 = matakuliahService.findOne(partikel.getIdmatakuliah());

            // Pengecekan partikel, matakuliah yang sama tidak bisa dialokasikan pada hari dan sesi yang sama
            if(
                    percobaan.getIdmatakuliah() == partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C1:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }

            // Pengecekan partikel, matakuliah berbeda, hari sesi sama dicek bentrok antar dosen dan asisten dosen
            if(
                    percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                    (int)percobaan.getPosisihari() == (int)partikel.getPosisihari() &&
                    (int)percobaan.getPosisisesi() == (int)partikel.getPosisisesi()
            ){
                if(jenis.equals("T")){
                    // Dosen 1
                    if(matakuliah1.getDosen1().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen1(), matakuliah2, pinaltiDosen);
                    // Dosen 2
                    if(matakuliah1.getDosen2().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen2(), matakuliah2, pinaltiDosen);
                    // Dosen 3
                    if(matakuliah1.getDosen3().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen3(), matakuliah2, pinaltiDosen);
                    // Dosen 4
                    if(matakuliah1.getDosen4().length() != 0)
                        pinaltiDosen = cekPinaltiDosen(matakuliah1.getDosen4(), matakuliah2, pinaltiDosen);
                }
                if(jenis.equals("P")){
                    // Asisten Dosen 1
                    if(matakuliah1.getAsistendosen1().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen1(), matakuliah2, pinaltiAsistenDosen);
                    // Asisten Dosen 2
                    if(matakuliah1.getAsistendosen2().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen2(), matakuliah2, pinaltiAsistenDosen);
                    // Asisten Dosen 3
                    if(matakuliah1.getAsistendosen3().length() != 0)
                        pinaltiAsistenDosen = cekPinaltiAsistenDosen(matakuliah1.getAsistendosen3(), matakuliah2, pinaltiAsistenDosen);
                }
            }

            /*
            =========== FUNGSI BARU =============
             */
            // AMBIL SEMUA MAHASISWA DI PARTIKEL 2
            List<AssignMahasiswa> assignMahasiswasPartikel = assignMahasiswaService.getAllMahasiswaByMatakuliahId(matakuliah2.getId());
            // PENGECEKAN APAKAH PADA MATAKULIAH YANG SAMA DAN SESI YANG SAMA MAHASISWA BERTABRAKAN JADWAL
            if(
                    percobaan.getIdmatakuliah() == partikel.getIdmatakuliah() &&
                    Math.floor(percobaan.getPosisihari()) == Math.floor(partikel.getPosisihari()) &&
                    Math.floor(percobaan.getPosisisesi()) == Math.floor(partikel.getPosisisesi())
            ){
                for(AssignMahasiswa assignMahasiswaPartikel1 : assignMahasiswasPartikel1){
                    for(AssignMahasiswa assignMahasiswaPartikel : assignMahasiswasPartikel){
                        if(assignMahasiswaPartikel1.getId_mahasiswa().equals(assignMahasiswaPartikel.getId_mahasiswa())){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" Matkul:"+partikel.getId()+"=>Mahasiswa:"+assignMahasiswaPartikel1.getId_mahasiswa());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
            }
            // PENGECEKAN PADA MATAKULIAH BERBEDA PADA HARI, SESI YANG SAMA APAKAH MAHASISWA BERTABRAKAN
            if(
                    percobaan.getIdmatakuliah() != partikel.getIdmatakuliah() &&
                    (int)percobaan.getPosisihari() == (int)partikel.getPosisihari() &&
                    (int)percobaan.getPosisisesi() == (int)partikel.getPosisisesi()
            ){
                for(AssignMahasiswa assignMahasiswaPartikel1 : assignMahasiswasPartikel1){
                    for(AssignMahasiswa assignMahasiswaPartikel : assignMahasiswasPartikel){
                        if(assignMahasiswaPartikel1.getId_mahasiswa().equals(assignMahasiswaPartikel.getId_mahasiswa())){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" Matkul:"+partikel.getId()+"=>Mahasiswa:"+assignMahasiswaPartikel1.getId_mahasiswa());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
            }

            // Perhitungan pinalti
            if(pinaltiDosen!=0){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C5:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                // reset nilai pinalti dosen
                pinaltiDosen = 0;
                pinalti++;
            }
            if(pinaltiAsistenDosen != 0){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C6:"+partikel.getId());
                percobaan.setKeterangan(keterangan);
                // reset nilai pinaltiAsistenDosen
                pinaltiAsistenDosen = 0;
                pinalti++;
            }
        }

        // Pengecekan partikel untuk sesi ibadah
        if((int)percobaan.getPosisihari() == 5 && (int)percobaan.getPosisisesi() == 5){
            keterangan = percobaan.getKeterangan();
            keterangan = keterangan.concat(" C3");
            percobaan.setKeterangan(keterangan);
            pinalti++;
        }

        // Cek rombongan kelas dengan kapasitas ruangan
        int rombongan = matakuliahService.findOne(percobaan.getIdmatakuliah()).getJumlahrombongankelas();
        Ruangan kelas = ruanganService.findByPosisi(ruangan);
        if(rombongan > kelas.getKapasitas()){
            keterangan = percobaan.getKeterangan();
            keterangan = keterangan.concat(" C4");
            percobaan.setKeterangan(keterangan);
            pinalti++;
        }

        // ======= PENGECEKAN CONSTRAINTS TAMBAHAN =========
        /*
        CONSTRAINTS MAX BEKERJA
         */
//        System.out.println(partikel1.getId()+" dicoba di "+hari+" "+sesi+" "+ruangan);

        List<Constraints> constraints_max_bekerja = constraintsService.getConstraintsMaxBekerjaHari(hari);
        Matakuliah matakuliah = matakuliahService.findOne(percobaan.getIdmatakuliah());
        if(constraints_max_bekerja.size() > 0){
//            System.out.println("Constraints MAX BEKERJA");
            for(Constraints constraint : constraints_max_bekerja){
//                System.out.println("Cek constraints "+constraint.getId());
                if(constraint.getSubjek().equals("Dosen")){
                    Integer jumlahMengajar = 1;
//                    System.out.println("Tipe Dosen");
                    if(matakuliah.getDosen1().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("1. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen1()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen1()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen1()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen2().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("2. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen2()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen2()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen2()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen3().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("3. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen3()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen3()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen3()+" mengajar "+jumlahMengajar);
                    }
                    if(matakuliah.getDosen4().length() != 0 && constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4()))){
                        // Ada constraint yang membatasi dosen pada partikel yang dipilih
//                        System.out.println("4. Constraint "+constraint.getId()+" membatasi dosen "+matakuliah.getDosen4()+" mengajar sebanyak "+constraint.getMax_bekerja());
                        jumlahMengajar += dosenMengajarPadaHari(Integer.parseInt(matakuliah.getDosen4()), hari);
//                        System.out.println("Jumlah dosen "+matakuliah.getDosen4()+" mengajar "+jumlahMengajar);
                    }
                    if(jumlahMengajar > constraint.getMax_bekerja()){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                    System.out.println("Dosen "+constraint.getId_subjek()+" mengajar "+jumlahMengajar+"x. Sehingga pinalti "+pinalti);
                }
            }
        }
        /*
        ============== CONSTRAINTS LARANGAN ==================
         */
        List<Constraints> constraints_larangan = constraintsService.getConstraintsLarangan();
        if(constraints_larangan.size() > 0){
            for(Constraints constraint : constraints_larangan){
                // Jika constraint larangan dosen
                if(constraint.getSubjek().equals("Dosen")){
                    Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
                    // Mencocokkan dosen yang dilarang ke partikel percobaan
                    if(matakuliah1.getDosen1().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen1())) &&
                                        constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                        constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen2().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen2())) &&
                                        constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                        constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen3().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen3())) &&
                                        constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                        constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                    if(matakuliah1.getDosen4().length() != 0){
                        if(
                                constraint.getId_subjek().equals(Integer.parseInt(matakuliah.getDosen4())) &&
                                        constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                        constraint.getSesi().equals((int)percobaan.getPosisisesi())
                        ){
                            keterangan = percobaan.getKeterangan();
                            keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                            percobaan.setKeterangan(keterangan);
                            pinalti++;
                        }
                    }
                }
                // Jika constraint larangan ruangan
                else if(constraint.getSubjek().equals("Ruangan")){
                    Ruangan ruangan_percobaan = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());

                    if(
                            constraint.getId_subjek().equals(ruangan_percobaan.getId()) &&
                                    constraint.getHari().equals((int)percobaan.getPosisihari()) &&
                                    constraint.getSesi().equals((int)percobaan.getPosisisesi())
                    ){
                        keterangan = percobaan.getKeterangan();
                        keterangan = keterangan.concat(" "+constraint.getNama_constraints()+":"+constraint.getId());
                        percobaan.setKeterangan(keterangan);
                        pinalti++;
                    }
                }
            }
        }

        // PENGECEKAN JIKA MATAKULIAH PRAKTIKUM DIALOKASIKAN DI RUANGAN TEORI
        String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
        if(jenis.equals("P")){
            Ruangan ruang = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());
            if(ruang.getJenis().equals("T")){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C7");
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }
        }else{
            Ruangan ruang = ruanganService.findByPosisi((int)percobaan.getPosisiruangan());
            if(ruang.getJenis().equals("L")){
                keterangan = percobaan.getKeterangan();
                keterangan = keterangan.concat(" C7");
                percobaan.setKeterangan(keterangan);
                pinalti++;
            }
        }

        // Perhitungan nilai fitness dan simpan nilai fitness
        nilaiFitness = (1.0)/(1.0+pinalti);
        percobaan.setNilaifitness(nilaiFitness);

        if(percobaan.getNilaifitness() == 1){
            partikel1.setPosisihari(hari);
            partikel1.setPosisisesi(sesi);
            partikel1.setPosisiruangan(ruangan);
            partikel1.setNilaifitness(1);
            partikel1.setKeterangan("");
            partikelService.save(partikel1);
            System.out.println(partikel1.getNama()+" diubah ke hari:"+hari+" sesi:"+sesi+" ruangan:"+ruangan);
            return;
        }
        else if(percobaan.getNilaifitness() > partikel1.getNilaifitness()){
            partikel1.setPosisihari(hari);
            partikel1.setPosisisesi(sesi);
            partikel1.setPosisiruangan(ruangan);
            partikel1.setNilaifitness(percobaan.getNilaifitness());
            partikel1.setKeterangan(keterangan);
            partikelService.save(partikel1);
        }

        System.out.println(percobaan.getNama()+" Melanggar : "+percobaan.getKeterangan()+" Pelanggaran : "+pinalti);
    }

    @GetMapping("/tambah-mahasiswa")
    public String tambahMahasiswa(){
        return "mahasiswa-form";
    }

    @PostMapping("/mahasiswa-save")
    public String saveMahasiswa(@ModelAttribute Mahasiswa mahasiswa, BindingResult result){
        mahasiswaService.save(mahasiswa);

        return "redirect:/mahasiswa";
    }

    @GetMapping("/delete-mahasiswa")
    public String deleteMahasiswa(@RequestParam int id){
        mahasiswaService.delete(id);
        return "redirect:/mahasiswa";
    }

    @GetMapping("/mahasiswa-update")
    public String mahasiswaUpdate(@RequestParam int id, ModelMap model){
        Mahasiswa mahasiswa = mahasiswaService.fincOne(id);
        model.put("mahasiswa", mahasiswa);

        return "mahasiswa-update";
    }

    @PostMapping("/update-mahasiswa")
    public String updateMahasiswa(@RequestParam int id, @RequestParam String nim, @RequestParam String nama, @RequestParam String kelas, @RequestParam String angkatan){
        System.out.println(id+" "+nim+" "+nama+" "+kelas+" "+angkatan);

        Mahasiswa mahasiswa = mahasiswaService.fincOne(id);

        mahasiswa.setNim(nim);
        mahasiswa.setNama(nama);
        mahasiswa.setAngkatan(angkatan);
        mahasiswa.setKelas(kelas);
        mahasiswaService.save(mahasiswa);

        return "redirect:/mahasiswa";
    }

    @GetMapping("/error-page")
    public String error(ModelMap model, @RequestParam int jenis){
        String errMsg = "";
        if(jenis == 1){
            errMsg = "Belum ada data jadwal";
        }
        else if(jenis == 2){
            List<Dosen> dosens = new ArrayList<>();
            List<Kelas> kelas = new ArrayList<>();
            List<Ruangan> ruangans = new ArrayList<>();

            dosens = dosenService.findAll();
            kelas = kelasService.findAll();
            ruangans = ruanganService.findAll();

            if(dosens.size() == 0){
                errMsg = "Belum ada data DOSEN. Silahkan tambah dosen terlebih dahulu";
            }
            else if(kelas.size() == 0){
                errMsg = "Belum ada data KELAS. Silahkah tambah kelas terlebih dahulu";
            }
            else if(ruangans.size() == 0){
                errMsg = "Belum ada data RUANGAN. Silahkan tambah ruangan terlebih dahulu";
            }
        }

        model.put("errMsg", errMsg);
        return "error-page";
    }
}
