package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.AssignMahasiswa;
import TugasAkhir.penjadwalan.repository.AssignMahasiswaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignMahasiswaService {
    private AssignMahasiswaRepository assignMahasiswaRepository;

    public AssignMahasiswaService(AssignMahasiswaRepository assignMahasiswaRepository){
        this.assignMahasiswaRepository = assignMahasiswaRepository;
    }

    public List<AssignMahasiswa> findAll(){
        List<AssignMahasiswa> assignMahasiswas = new ArrayList<>();
        assignMahasiswaRepository.findAll().forEach(assignMahasiswas::add);
        return assignMahasiswas;
    }

    public AssignMahasiswa fincOne(int id){
        return assignMahasiswaRepository.findById(id).get();
    }

    public void save(AssignMahasiswa mahasiswa){
        assignMahasiswaRepository.save(mahasiswa);
    }

    public void delete(int id){
        assignMahasiswaRepository.deleteById(id);
    }
}
