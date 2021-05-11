package junas.robert.lagatoria.core.odoberatelia.stanky;

import junas.robert.lagatoria.core.items.BalikKnih;

import java.util.ArrayList;

/**
 * inventar Stanku
 */
public class Inventar {
    private ArrayList<BalikKnih> zoznamKnih = new ArrayList<>();

    /**
     * @param balikKnih knihy pridavane do inventara
     */
    public void add(BalikKnih balikKnih){
        zoznamKnih.add(balikKnih);
    }
}
