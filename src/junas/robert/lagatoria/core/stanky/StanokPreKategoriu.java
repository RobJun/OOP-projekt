package junas.robert.lagatoria.core.stanky;

import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.stanky.Stanok;
import junas.robert.lagatoria.core.utils.enums.Kategoria;

/**
 * Stanok ktory prijma iba knihy urcitej Kategorie
 */
public class StanokPreKategoriu extends Stanok {
    private Kategoria kategoria;

    /**
     * @param nazov nazov stanku
     * @param kategoria kategoriu ktoru bude stanok prijmat
     */
    public StanokPreKategoriu(String nazov, Kategoria kategoria) {
        super(nazov);
        this.kategoria = kategoria;
    }

    /**
     * @param kniha kniha, ktoru prijmame
     * @param pocet knih na prijatie
     * @return ak kategoria knihy je rôzna od kategorie, tak knihy neprijme (0) inak vracia 1
     */
    @Override
    public int odober(Kniha kniha, int pocet) {
        if(((Text)kniha.getSucast(Text.class)).getKategoria() == kategoria) {
            zoznamKnih.add(new BalikKnih(kniha,pocet));
            return 1;
        }
        return 0;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }
}
