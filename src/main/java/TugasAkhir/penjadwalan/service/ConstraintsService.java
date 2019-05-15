package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Constraints;
import TugasAkhir.penjadwalan.repository.ConstraintsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConstraintsService {
    private ConstraintsRepository constraintsRepository;

    public ConstraintsService(ConstraintsRepository constraintsRepository){
        this.constraintsRepository = constraintsRepository;
    }

    public List<Constraints> findAll(){
        List<Constraints> constraints = new ArrayList<>();
        constraintsRepository.findAll().forEach(constraints::add);
        return constraints;
    }

    public Constraints findOne(int id){
        return constraintsRepository.findById(id).get();
    }

    public void save(Constraints constraints){
        constraintsRepository.save(constraints);
    }

    public void delete(int id){
        constraintsRepository.deleteById(id);
    }

    public List<Constraints> getConstraintsMaxBekerjaHari(int hari){
        List<Constraints> constraints = findAll();
        List<Constraints> constraints_max_bekerja_hari = new ArrayList<>();
        for(Constraints constraint : constraints){
            if(constraint.getTipe().equals(1) && constraint.getHari().equals(hari)){
                constraints_max_bekerja_hari.add(constraint);
            }
        }

        return constraints_max_bekerja_hari;
    }

    public List<Constraints> getConstraintsLarangan(){
        List<Constraints> constraints = findAll();
        List<Constraints> constraints_larangan = new ArrayList<>();

        for(Constraints constraint : constraints){
            if(constraint.getTipe().equals(2)){
                constraints_larangan.add(constraint);
            }
        }

        return constraints_larangan;
    }
}
