package junas.robert.lagatoria.core.vydavatelstvo;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.items.Obalka;
import junas.robert.lagatoria.core.items.Text;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Ma nastarosti skladanie knih z textov a obalok a udajov potrebnych na vytvorenie spravneho vytlacku
 * a vytvára isbn kód pre texty
 */
public class Tlaciaren {
    private Vydavatelstvo vydavatelstvo;
    private HashMap<Integer, Integer> pouziteKody = new HashMap<Integer, Integer>();

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

    /**
     * vytvori isbn textu
     * @param text vydavany text
     * @param code  kod vydavatelstva
     * @param group skupina do ktorej vydavatel patri
     * @return retazec ktorý tvori ISBN identifikator pre text
     */
    public String vytvorISBN(Text text,String code, String group){
        //vytvori sa ISBN kod
        int titleCode = ((text.getNazov() + text.getAutor()).hashCode() % 10000);
        int i = 0;
        if (pouziteKody.containsKey(titleCode)) {
            i = pouziteKody.get(titleCode) + 1;
            pouziteKody.replace(titleCode, i);
        } else {
            pouziteKody.put(titleCode, 0);
        }
        return  "ISBN-977-" + group + "-" + code + "-" + String.format("%04d", titleCode) + "-" + i;
    }
}
