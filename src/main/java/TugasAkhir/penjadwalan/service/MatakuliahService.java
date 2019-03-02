package TugasAkhir.penjadwalan.service;

import TugasAkhir.penjadwalan.model.Matakuliah;
import TugasAkhir.penjadwalan.repository.MatakuliahRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatakuliahService {
    private MatakuliahRepository matakuliahRepository;

    public MatakuliahService(MatakuliahRepository matakuliahRepository) {
        this.matakuliahRepository = matakuliahRepository;
    }

    public List<Matakuliah> findAll(){
        List<Matakuliah> matakuliahs = new ArrayList<Matakuliah>();
        matakuliahRepository.findAll().forEach(matakuliahs::add);
        return matakuliahs;
    }

    public Matakuliah findOne(int id){
        return matakuliahRepository.findById(id).get();
    }

    public void save(Matakuliah matakuliah){
        matakuliahRepository.save(matakuliah);
    }

    public void delete(int id){
        matakuliahRepository.deleteById(id);
    }

    public String findJenisMatakuliah(int id){
        return matakuliahRepository.findById(id).get().getJenis();

    }
}
