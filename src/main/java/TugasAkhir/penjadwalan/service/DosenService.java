package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Dosen;
import TugasAkhir.penjadwalan.repository.DosenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DosenService {
    private DosenRepository dosenRepository;

    public DosenService(DosenRepository dosenRepository) {
        this.dosenRepository = dosenRepository;
    }

    public List<Dosen> findAll(){
        List<Dosen> dosens = new ArrayList<Dosen>();
        dosenRepository.findAll().forEach(dosens::add);
        return dosens;
    }

    public Dosen findOne(int id){
        return dosenRepository.findById(id).get();
    }

    public void save(Dosen dosen){
        dosenRepository.save(dosen);
    }

    public void delete(int id){
        dosenRepository.deleteById(id);
    }
}
