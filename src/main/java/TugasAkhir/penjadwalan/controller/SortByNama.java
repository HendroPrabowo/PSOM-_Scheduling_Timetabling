package TugasAkhir.penjadwalan.controller;

import TugasAkhir.penjadwalan.model.Dosen;

import java.util.Comparator;

public class SortByNama implements Comparator<Dosen> {
    public int compare(Dosen a, Dosen b){
        return a.getInisial().compareTo(b.getInisial());
    }
}
