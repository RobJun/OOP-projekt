package junas.robert.lagatoria.core.utils;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

public interface InputProcess {
    void spracuj(String[] s, Vydavatelstvo vy);
    void help();
}
