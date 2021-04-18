package junas.robert.lagatoria.core.stanky;

import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.items.Kniha;

/**
 * Stanok prijma knihy od vydavatela
 */
public class Stanok implements Odoberatel {
    private String nazov;
    protected Inventar zoznamKnih;

    public Stanok(String nazov){
        this.nazov = nazov;
        zoznamKnih = new Inventar();
    }
    public int odober(Kniha kniha, int pocet) {
        zoznamKnih.add(new BalikKnih(kniha, pocet));
        return 1;
    }

    public String getNazov() {
        return nazov;
    }
}
