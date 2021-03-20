package junas.robert.lagatoria.core.utils;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

/**
 * lambda inteface na spustenie funkcie uzivatelom
 */
public interface InlineCommand {
    void process(String[] args, Knihkupectvo kh, Vydavatelstvo vy);
}
