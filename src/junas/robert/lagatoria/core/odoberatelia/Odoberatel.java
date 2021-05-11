package junas.robert.lagatoria.core.odoberatelia;

import junas.robert.lagatoria.core.items.Kniha;

/**
 * rozhranie tried ktore prijamju knihy od vydavatela
 */
public interface Odoberatel {
    /**
     * @param kniha kniha ktoru sme prijali
     * @param pocet prijatych knih
     * @return  celkova cena za zaplatenie knih - 77% z plnej ceny knihy
     */
    default double zaplatVydavatelovi(Kniha kniha, int pocet){
        return kniha.getCena()*0.77*pocet;
    };
}
