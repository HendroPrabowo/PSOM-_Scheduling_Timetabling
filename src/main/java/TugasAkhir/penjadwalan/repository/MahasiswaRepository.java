package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.Mahasiswa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository extends CrudRepository<Mahasiswa, Integer> {
}
