package junas.robert.lagatoria.core;

import junas.robert.lagatoria.core.items.Kniha;

public interface Odoberatel {
    default double zaplatVydavatelovi(Kniha kniha, int pocet){
        return kniha.getCena()*0.77*pocet;
    };
}
