package junas.robert.minerva.core.users;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Predajna;

import java.util.ArrayList;
import java.util.HashMap;

public class Zakaznik extends Pouzivatel{
    private ArrayList<Kniha> kosik;
    private int polozkyVKosiku;
    private HashMap<String,Integer> pocetKnih;
    private static int pocet = 0;

    public Zakaznik(){
        super("Guest", pocet++);
        kosik = new ArrayList<Kniha>();
        polozkyVKosiku = 0;
        pocetKnih = new HashMap<String,Integer>();
    }


    public boolean isOtvorene(Predajna p){
        return  p.isOtvorene();
    }
}
