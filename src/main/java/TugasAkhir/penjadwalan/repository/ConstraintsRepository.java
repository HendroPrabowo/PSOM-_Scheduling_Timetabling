package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Constraints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstraintsRepository extends CrudRepository<Constraints, Integer> {
}
