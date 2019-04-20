package TugasAkhir.penjadwalan.controller;

import java.io.*;
import TugasAkhir.penjadwalan.model.*;
import TugasAkhir.penjadwalan.service.*;
import TugasAkhir.penjadwalan.spreadsheet.ApachePOIExcelWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/ruangan-save-update")
    public String ruanganSaveUpdate(@ModelAttribute Ruangan ruangan, BindingResult result){
        Ruangan ruangan1 = ruanganService.findOne(ruangan.getId());

        return "redirect:/ruangan";
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
        matakuliahs = matakuliahService.findAll();
        dosens = dosenService.findAll();
        model.put("matakuliahs", matakuliahs);
        model.put("dosens", dosens);

        return "matakuliah";
    }

    @GetMapping("/matakuliah-add")
    public String matakuliahAdd(ModelMap model){
        List<Dosen> dosens = new ArrayList<>();
        dosens = dosenService.findAll();
        model.put("dosens", dosens);
        return "matakuliah-form";
    }

    @PostMapping("/matakuliah-save")
    public String matakuliahSave(@ModelAttribute Matakuliah matakuliah, BindingResult result){
        matakuliah.setJumlahrombongankelas(0);
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

        return "matakuliah-update";
    }

    @GetMapping("/halaman-jadwal")
    public String halamanJadwal(ModelMap model){
        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        model.put("matakuliahs", matakuliahs);

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

        cekNilaiFitness(partikels, matakuliahs);
        updateGlobalBest(partikels);
        // nilai variable learning
        updatePosisi(partikels, ruangans.size());
        // mutasi
        System.out.println("Mutasi");
        mutasi(partikels, ruangans);

        cekNilaiFitness(partikels, matakuliahs);

        cekKriteria(partikels, matakuliahs, ruangans, 1);

        // FUNGSI HILL CLIMBING
//        hillClimbing(partikels);
        cekNilaiFitness(partikels, matakuliahs);

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

        return "generate-jadwal";
    }

    @GetMapping("/generate-jadwal")
    public String generatePenjadwalan(ModelMap model){
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

        System.out.println("Cek nilai fitness");
        cekNilaiFitness(partikels, matakuliahs);
        updateGlobalBest(partikels);
        // nilai variable learning
        updatePosisi(partikels, ruangans.size());
        // mutasi
        System.out.println("Mutasi");
        mutasi(partikels, ruangans);

        cekNilaiFitness(partikels, matakuliahs);

        cekKriteria(partikels, matakuliahs, ruangans, 1);

        // Fungsi Hill Climbing
        hillClimbing(partikels);
        cekNilaiFitness(partikels, matakuliahs);

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

        return "generate-jadwal";
    }

    @GetMapping("/ubah-jadwal")
    public String ubahJadwal(ModelMap model){
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
        List<Ruangan> ruangans = ruanganService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        model.put("partikels", partikelAlreadySorted);
        model.put("ruangans", ruangans);
        model.put("matakuliahs", matakuliahs);
        return "ubah-jadwal";
    }

    @PostMapping("/ubah-jadwal")
    public String ubahJadwalCek(@RequestParam("id") int id, ModelMap model){
        System.out.println(id);
        Partikel partikel = partikelService.findOne(id);
        List<Partikel> solusi = hillClimbingPartikel(partikel);
        List<Ruangan> ruangans = ruanganService.findAll();

        for(Partikel partikel1 : solusi){
            System.out.println(partikel1.getId()+" Nama : "+partikel1.getNama()+" Hari:"+partikel1.getPosisihari()+" Sesi:"+partikel1.getPosisisesi()+" Ruangan:"+partikel1.getPosisiruangan());
        }

//        System.out.println("Partikel yang terpilih : "+id);
//        Partikel partikel = partikelService.findOne(input);
//        List<String> solusi = hillClimbingPartikel(partikel);
//        System.out.println("List solusi");
//        for (String string : solusi){
//            System.out.println(string);
//        }
//        List<Partikel> partikels = partikelService.findAll();
//        model.put("partikels", partikels);
//        model.put("listSolusi", solusi);
        model.put("partikel", partikel);
        model.put("solusi", solusi);
        model.put("ruangans", ruangans);
        return "ubah-jadwal-choose";
    }

    @GetMapping("/ubah-jadwal-apply")
    public String ubahJadwalApplyGet(@RequestParam int hari, @RequestParam int sesi, @RequestParam int ruangan, @RequestParam int id){
        System.out.println(hari+" "+sesi+" "+ruangan+" "+id);
        return "testing";
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

        matakuliah.setJumlahrombongankelas(assignMahasiswaService.countMahasiswa(id_matakuliah));
        matakuliahService.save(matakuliah);

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

    // ======== FUNGSI UMUM ========
    public double generateNilaiPosisiHari(){
        double hasil = 0;
        hasil = 1 + Math.random()*(5);

        if(hasil < 1 || hasil >= 6){
            generateNilaiPosisiHari();
        }

        return hasil;
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

    public void cekNilaiFitness(List<Partikel> partikels, List<Matakuliah> matakuliahs){
        System.out.println("Cek nilai fitness");
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

        System.out.println("Cek nilai fitness selesai");
    }

    public void resetKeterangan(List<Partikel> partikels){
        for (Partikel partikel : partikels){
            partikel.setKeterangan("");
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
                    cekNilaiFitness(partikels, matakuliahs);
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
                        System.out.println(partikel.getNama()+" hari:"+hari+" sesi:"+sesi+" ruangan:"+ruangan);
                        Partikel partikel2 = partikelService.findPartikel(hari, sesi, ruangan);
                        if(
                                partikel2 != null &&
                                partikel.getId() == partikel2.getId() &&
                                partikel2.getNilaifitness() == 1
                        ){
                            loop = 1;
                            break;
                        }else {
                            cekNilaiFitnessPartikel(partikel, i, j, k);
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
                for(int k=1;k<ruangans.size();k++){ // ruangan
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
        String keterangan = null;
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

        for(Partikel partikel : partikels){
            String jenis = matakuliahService.findJenisMatakuliah(percobaan.getIdmatakuliah());
            Matakuliah matakuliah1 = matakuliahService.findOne(percobaan.getIdmatakuliah());
            Matakuliah matakuliah2 = matakuliahService.findOne(partikel.getIdmatakuliah());

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

        // Perhitungan nilai fitness dan simpan nilai fitness
        nilaiFitness = (1.0)/(1.0+pinalti);
        percobaan.setNilaifitness(nilaiFitness);

        if(percobaan.getNilaifitness() == 1){
            return 1;
        }
        return nilaiFitness;
    }

    public void cekNilaiFitnessPartikel(Partikel partikel1, int hari, int sesi, int ruangan){
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
        System.out.println(percobaan.getNama()+" Melanggar : "+percobaan.getKeterangan()+" Pelanggaran : "+pinalti);
    }

    @PostMapping("/testing-hill-climbing-lo")
    public String hillClimb(){
        List<Partikel> partikels = partikelService.findAll();

        hillClimbing(partikels);

        return "testing";
    }

    // ========= FUNGSI TESTING ==========
    @GetMapping("/cek-fitness")
    public String cekFitness(){
        List<Partikel> partikels = partikelService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        List<Ruangan> ruangans = ruanganService.findAll();

        cekNilaiFitness(partikels, matakuliahs);

        return "testing";
    }

    @GetMapping("/hill-climb")
    public String testHillClimb(){
        List<Partikel> partikels = partikelService.findAll();

        hillClimbing(partikels);

        return "testing";
    }

    @GetMapping("/test-excel")
    public String textExcel(){
        List<Partikel> partikels = partikelService.findAll();
        List<Matakuliah> matakuliahs = matakuliahService.findAll();
        List<Ruangan> ruangans = ruanganService.findAll();
        ApachePOIExcelWrite excelWrite = new ApachePOIExcelWrite();
        excelWrite.exportExcel(partikels, matakuliahs, ruangans);

        return "testing";
    }

    @GetMapping("/testing")
    public String testing(){
        return "testing";
    }
}
