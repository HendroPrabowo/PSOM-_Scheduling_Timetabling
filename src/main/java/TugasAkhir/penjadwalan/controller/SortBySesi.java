package TugasAkhir.penjadwalan.controller;

import TugasAkhir.penjadwalan.model.Partikel;

import java.util.Comparator;

public class SortBySesi implements Comparator<Partikel> {
    public int compare(Partikel a, Partikel b){
        return (int)a.getPosisisesi() - (int)b.getPosisisesi();
    }
}
