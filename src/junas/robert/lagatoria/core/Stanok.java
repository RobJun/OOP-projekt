package junas.robert.lagatoria.core;

import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;

public class Stanok implements Odoberatel {
    private ArrayList<Kniha> zoznamKnih;
    private HashMap<String, Integer> pocetKnih;

    public void odober(Kniha kniha, int pocet) {
        zoznamKnih.add(kniha);
        pocetKnih.put(kniha.getISBN(),pocet);
    }

}