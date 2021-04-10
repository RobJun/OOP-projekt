package junas.robert.lagatoria.core.utils;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

import java.util.Collection;

/**
 * lambda inteface na spustenie funkcie uzivatelom
 */
public interface InlineCommand {
    String process(String[] args, Knihkupectvo kh, Vydavatelstvo vy);
}
