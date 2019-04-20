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
}
