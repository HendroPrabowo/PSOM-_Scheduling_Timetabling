package TugasAkhir.penjadwalan.controller;

import TugasAkhir.penjadwalan.model.Partikel;

import java.util.Comparator;

public class SortByHari implements Comparator<Partikel> {
    public int compare(Partikel a, Partikel b){
        return (int)a.getPosisihari() - (int)b.getPosisihari();
    }
}
