package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;

/**
 * Pisanie, ked autor pracuje s normlanou motivaciou
 */
public class NormalnePisanie implements Pisanie{

    /**
     * @param autor typ autora
     * @return text, ktory autor napisal
     * @throws InterruptedException ak bol dej preruseny
     */
    @Override
    public Text visit(HistoryAutor autor) throws InterruptedException {
        Thread.sleep(8000); // cakanie nez autor vymysli knihu
        int pocetStran = (int)(Math.random()*(1200-300+1)+300);
        String meno = autor.getMeno() + " " + autor.getPrievzisko();
        return new Text("Nazov Historickej knihy: ",meno,"Slovensky",pocetStran, Kategoria.HISTORIA);
    }

    /**
     * @param autor typ autora
     * @return text, ktory autor napisal
     * @throws InterruptedException ak bol dej preruseny
     */
    @Override
    public Text visit(FantasyAutor autor) throws InterruptedException {
        Thread.sleep(5000); // cakanie nez autor vymysli knihu
        int pocetStran = (int)(Math.random()*(700-300+1)+300);
        String meno = autor.getMeno() + " " + autor.getPrievzisko();
        return new Text( "Nazov romanu: ",meno,"Slovensky",pocetStran, Kategoria.FANTASY);
    }

    /**
     * @param autor typ autora
     * @return text, ktory autor napisal
     * @throws InterruptedException ak bol dej preruseny
     */
    @Override
    public Text visit(PoetryAutor autor) throws InterruptedException {
        Thread.sleep(7000); // cakanie nez autor vymysli knihu
        int pocetStran = (int)(Math.random()*(200-50+1)+50);
        String meno = autor.getMeno() + " " + autor.getPrievzisko();
        return new Text( "Nazov basniciek: ",meno,"Slovensky",pocetStran, Kategoria.POEZIA);
    }
}
