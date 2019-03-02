package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Matakuliah;
import TugasAkhir.penjadwalan.model.Partikel;
import TugasAkhir.penjadwalan.repository.MatakuliahRepository;
import TugasAkhir.penjadwalan.repository.PartikelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartikelService {
    private PartikelRepository partikelRepository;
    private MatakuliahRepository matakuliahRepository;

    public PartikelService(PartikelRepository partikelRepository) {
        this.partikelRepository = partikelRepository;
    }

    public List<Partikel> findAll(){
        List<Partikel> partikels = new ArrayList<Partikel>();
        partikelRepository.findAll().forEach(partikels::add);
        return partikels;
    }

    public Partikel findOne(int id){
        return partikelRepository.findById(id).get();
    }

    public Partikel findByName(String nama){
        List<Partikel> partikels = findAll();
        for(Partikel partikel : partikels){
            if(partikel.getNama() == nama){
                return partikel;
            }
        }
        return null;
    }

    public void save(Partikel partikel){
        partikelRepository.save(partikel);
    }

    public void delete(int id){
        partikelRepository.deleteById(id);
    }

    public void deleteAll(){
        partikelRepository.deleteAll();
    }
}
