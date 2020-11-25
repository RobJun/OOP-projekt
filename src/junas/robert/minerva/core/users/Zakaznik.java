package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;

public class Zakaznik extends Pouzivatel{
    private ArrayList<Kniha> kosik;
    private int polozkyVKosiku;
    private HashMap<String,Integer> pocetKnih;

    public Zakaznik(){
        super("Guest", 0);
        kosik = new ArrayList<Kniha>();
        polozkyVKosiku = 0;
        pocetKnih = new HashMap<String,Integer>();
    }

    @Override
    public void help() {
        super.help();
        System.out.println("---Zakaznicke prikazy---");
        System.out.println("kosik - vypise knihy v kosiku");
        System.out.println("dostupnost n/s - zisti ci je kniha dostupna []");
    }


    @Override
    public void spracuj(String[] s, Knihkupectvo kh){
        super.spracuj(s, kh);
    }

    public ArrayList<Kniha> getKosik() { return kosik; }
}
