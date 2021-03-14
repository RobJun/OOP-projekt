package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;

public interface Pisanie {

    Text visit(HistoryAutor autor) throws InterruptedException;
    Text visit(FantasyAutor autor) throws InterruptedException;
    Text visit(PoetryAutor autor) throws InterruptedException;
}
