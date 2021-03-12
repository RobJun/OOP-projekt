package junas.robert.lagatoria.core.utils;

import junas.robert.lagatoria.core.Knihkupectvo;

public interface InlineCommand {
    void process(String[] args, Knihkupectvo kh);
}
