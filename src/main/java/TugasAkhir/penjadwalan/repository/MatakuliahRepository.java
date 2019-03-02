package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Matakuliah;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatakuliahRepository extends CrudRepository<Matakuliah, Integer>{

}