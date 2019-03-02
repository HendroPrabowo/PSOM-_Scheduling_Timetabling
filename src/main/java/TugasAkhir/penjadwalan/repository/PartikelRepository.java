package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Partikel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartikelRepository extends CrudRepository<Partikel, Integer>{

}