package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Kelas;
import TugasAkhir.penjadwalan.repository.KelasRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KelasService {
    private KelasRepository kelasRepository;

    public KelasService(KelasRepository kelasRepository) {
        this.kelasRepository = kelasRepository;
    }

    public List<Kelas> findAll(){
        List<Kelas> kelass = new ArrayList<Kelas>();
        kelasRepository.findAll().forEach(kelass::add);
        return kelass;
    }

    public Kelas findOne(int id){
        return kelasRepository.findById(id).get();
    }

    public void save(Kelas kelas){
        kelasRepository.save(kelas);
    }

    public void delete(int id){
        kelasRepository.deleteById(id);
    }
}
