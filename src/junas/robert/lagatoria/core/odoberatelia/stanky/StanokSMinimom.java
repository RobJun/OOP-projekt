package junas.robert.lagatoria.core.odoberatelia.stanky;

import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.items.Kniha;

/**
 * Stanok, ktory príjma iba minimálny počet kníh.
 */
public class StanokSMinimom extends Stanok {
    /**
     * minimalny pocet knih, aby stanok prijal knihy
     */
    private int minimum;

    public int getMinimum() {
        return minimum;
    }

    /**
     * @param nazov nazov stánku
     * @param minimum minimalny pocet, ktorý stanok bude príjmat
     */
    public StanokSMinimom(String nazov, int minimum) {
        super(nazov);
        this.minimum = minimum;
    }

    /**
     * @param kniha prijmana kniha
     * @param pocet pocet prijmanych kníh
     * @return ak počet bol väčší alebo rovny minimu tak sa vrati 1 inak sa vracia 0
     */
    @Override
    public int odober(Kniha kniha, int pocet){
        if(pocet >= minimum){
            zoznamKnih.add(new BalikKnih(kniha, pocet));
            return 1;
        }
        return 0;
    }

}
