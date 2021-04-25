package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;

/**
 * Visitor model, definujuci ako rozny autori pisu
 */
public interface Pisanie {

    /**
     * @param autor typ autora
     * @return text, ktory autor napisal
     * @throws InterruptedException ak bol dej preruseny
     */
    Text visit(HistoryAutor autor) throws InterruptedException;

    /**
     * @param autor typ autora
     * @return text, ktory autor napisal
     * @throws InterruptedException ak bol dej preruseny
     */
    Text visit(FantasyAutor autor) throws InterruptedException;

    /**
     * @param autor typ autora
     * @return text, ktory autor napisal
     * @throws InterruptedException ak bol dej preruseny
     */
    Text visit(PoetryAutor autor) throws InterruptedException;
}
