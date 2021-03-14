package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.utils.Kategoria;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;

public class RychlePisanie implements Pisanie {
    @Override
    public Text visit(HistoryAutor autor) throws InterruptedException {
        Thread.sleep(10000);
        int pocetStran = (int)(Math.random()*(1200-300+1)+300);
        String meno = autor.getMeno() + " " + autor.getPrievzisko();
        return new Text(meno, "Nazov Historickej knihy","Slovensky",pocetStran, Kategoria.HISTORIA);
    }

    @Override
    public Text visit(FantasyAutor autor) throws InterruptedException {
        Thread.sleep(5000);
        int pocetStran = (int)(Math.random()*(700-300+1)+300);
        String meno = autor.getMeno() + " " + autor.getPrievzisko();
        return new Text(meno, "Nazov romanu","Slovensky",pocetStran, Kategoria.FANTASY);
    }

    @Override
    public Text visit(PoetryAutor autor) throws InterruptedException {
        Thread.sleep(7000);
        int pocetStran = (int)(Math.random()*(200-50+1)+50);
        String meno = autor.getMeno() + " " + autor.getPrievzisko();
        return new Text(meno, "Nazov basniciek","Slovensky",pocetStran, Kategoria.POEZIA);
    }
}