package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Mahasiswa;
import TugasAkhir.penjadwalan.repository.MahasiswaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MahasiswaService {
    private MahasiswaRepository mahasiswaRepository;

    public MahasiswaService(MahasiswaRepository mahasiswaRepository){
        this.mahasiswaRepository = mahasiswaRepository;
    }

    public List<Mahasiswa> findAll(){
        List<Mahasiswa> mahasiswas = new ArrayList<>();
        mahasiswaRepository.findAll().forEach(mahasiswas::add);
        return mahasiswas;
    }

    public Mahasiswa fincOne(int id){
        return mahasiswaRepository.findById(id).get();
    }

    public void save(Mahasiswa mahasiswa){
        mahasiswaRepository.save(mahasiswa);
    }

    public void delete(int id){
        mahasiswaRepository.deleteById(id);
    }
}
