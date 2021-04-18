package junas.robert.lagatoria.core.stanky;

import junas.robert.lagatoria.core.items.BalikKnih;

import java.util.ArrayList;

public class Inventar {
    private ArrayList<BalikKnih> zoznamKnih = new ArrayList<>();

    public void add(BalikKnih balikKnih){
        zoznamKnih.add(balikKnih);
    }
}
