package TugasAkhir.penjadwalan.repository;

import TugasAkhir.penjadwalan.model.AssignMahasiswa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignMahasiswaRepository extends CrudRepository<AssignMahasiswa, Integer> {
}
