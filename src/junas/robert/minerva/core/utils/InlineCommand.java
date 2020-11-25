package junas.robert.minerva.core.utils;

import junas.robert.minerva.core.Knihkupectvo;

public interface InlineCommand {
    void process(String[] args, Knihkupectvo kh);
}
