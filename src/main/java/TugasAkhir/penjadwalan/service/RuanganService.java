package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Ruangan;
import TugasAkhir.penjadwalan.repository.RuanganRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuanganService {
    private RuanganRepository ruanganRepository;

    public RuanganService(RuanganRepository ruanganRepository) {
        this.ruanganRepository = ruanganRepository;
    }

    public List<Ruangan> findAll(){
        List<Ruangan> ruangans = new ArrayList<Ruangan>();
        ruanganRepository.findAll().forEach(ruangans::add);
        return ruangans;
    }

    public Ruangan findOne(int id){
        return ruanganRepository.findById(id).get();
    }

    public void save(Ruangan ruangan){
        ruanganRepository.save(ruangan);
    }

    public void delete(int id){
        ruanganRepository.deleteById(id);
    }

    public Ruangan findByPosisi(int posisi){
        List<Ruangan> semuaRuangan = this.findAll();
        for(Ruangan ruangan : semuaRuangan){
            if (ruangan.getPosisi().equals(posisi))
                return ruangan;
        }
        return null;
    }
}
