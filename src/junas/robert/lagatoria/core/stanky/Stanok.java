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

    /**
     * Prijme Knihu a pocet kusov jednej knihy a vlozi ich do inventara ako balik
     * @param kniha ktoru sme prebrali
     * @param pocet kusov ktore sme prebrali
     * @return  vzdy vracia 1
     */
    public int odober(Kniha kniha, int pocet) {
        zoznamKnih.add(new BalikKnih(kniha, pocet));
        return 1;
    }

    /**
     * @return nazov stanku
     */
    public String getNazov() {
        return nazov;
    }
}
