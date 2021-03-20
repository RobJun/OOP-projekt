package junas.robert.lagatoria.core.vydavatelstvo;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.items.Obalka;
import junas.robert.lagatoria.core.items.Text;

import java.util.Calendar;
import java.util.Date;

public class Tlaciaren {
    private Vydavatelstvo vydavatelstvo;

    public Tlaciaren(Vydavatelstvo vydavatelstvo){
        this.vydavatelstvo = vydavatelstvo;
    }

    /**
     * Vytlaci, resp. spoji, vsetky casti knihy do celku s nazvom Kniha
     * @param text text ktory sa bude tlacit
     * @param obalka obalka knihy
     * @param pocet pocet vytlackov ktore sa maju vytlacit
     * @param isbn  poznavaci kod pre knihu
     * @param cena  cena za ktoru sa bude kniha predavat
     * @return  vracia instanciu vytlacenej knihy
     */
    public Kniha vytlacKnihy(Text text, Obalka obalka, int pocet, String isbn, double cena){
        Kniha kniha = new Kniha(isbn,vydavatelstvo.getNazov(), Calendar.getInstance().get(Calendar.YEAR),cena);
        kniha.pridajSucast(text);
        kniha.pridajSucast(obalka);
        return kniha;
    }
}
