package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Kelas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KelasRepository extends CrudRepository<Kelas, Integer>{

}