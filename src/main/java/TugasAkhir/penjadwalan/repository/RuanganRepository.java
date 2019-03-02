package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Ruangan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuanganRepository extends CrudRepository<Ruangan, Integer>{

}