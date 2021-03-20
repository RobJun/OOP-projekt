package junas.robert.lagatoria.core;

import junas.robert.lagatoria.core.items.Kniha;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class Stanok implements Odoberatel {
    public String nazov;
    private ArrayList<Kniha> zoznamKnih;
    private HashMap<String, Integer> pocetKnih;

    public Stanok(String nazov){
        this.nazov = nazov;
        zoznamKnih = new ArrayList<>();
        pocetKnih = new HashMap<>();
    }
    public void odober(Kniha kniha, int pocet) {
        zoznamKnih.add(kniha);
        pocetKnih.put(kniha.getISBN(),pocet);
    }

    public String getNazov() {
        return nazov;
    }
}
