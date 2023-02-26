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

    public int countMahasiswa(int idMatakuliah){
        List<AssignMahasiswa> assignMahasiswas = findAll();
        int count = 0;
        for(AssignMahasiswa assignMahasiswa : assignMahasiswas){
            if(assignMahasiswa.getId_matakuliah() == idMatakuliah){
                count++;
            }
        }
        return count;
    }

    public boolean findMahasiswaByMatakuliah(int idMahasiswa, int idMatakuliah){
        List<AssignMahasiswa> assignMahasiswas = findAll();
        for(AssignMahasiswa assignMahasiswa : assignMahasiswas){
            if(assignMahasiswa.getId_matakuliah().equals(idMatakuliah) && assignMahasiswa.getId_mahasiswa().equals(idMahasiswa)){
                return false;
            }
        }
        return true;
    }

    public List<AssignMahasiswa> getAllMahasiswaByMatakuliahId(int idMatakuliah){
        List<AssignMahasiswa> mahasiswas = findAll();
        List<AssignMahasiswa> mahasiswaByIdMatakuliah = new ArrayList<>();
        for(AssignMahasiswa mahasiswa : mahasiswas){
            if(mahasiswa.getId_matakuliah().equals(idMatakuliah)){
                mahasiswaByIdMatakuliah.add(mahasiswa);
            }
        }
        return mahasiswaByIdMatakuliah;
    }
}
