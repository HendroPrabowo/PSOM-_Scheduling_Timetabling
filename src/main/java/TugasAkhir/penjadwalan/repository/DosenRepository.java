package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Dosen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosenRepository extends CrudRepository<Dosen, Integer>{

}