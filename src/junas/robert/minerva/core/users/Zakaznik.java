package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;

public class Zakaznik extends Pouzivatel{
    private ArrayList<Kniha> kosik;
    private HashMap<String,Integer> pocetKnih;

    public Zakaznik(){
        super("Guest", 0);
        kosik = new ArrayList<Kniha>();
        pocetKnih = new HashMap<String,Integer>();

        inlineAkcie.put("kosik", (args, kh) -> vypisKosik());
    }

    @Override
    public void help() {
        super.help();
        System.out.println("---Zakaznicke prikazy---");
        System.out.println("kosik - vypise knihy v kosiku");
    }

    public void vypisKosik(){
        if(kosik.isEmpty()) {
            System.out.println("kosik je prazdny");
            return;
        }
        for(int i = 0; i < kosik.size(); i++){
            kosik.get(i).printContent();
            System.out.print(" [" + pocetKnih.get(kosik.get(i).getISBN()) + "]\n");
        }
    }

    public ArrayList<Kniha> getKosik() { return kosik; }
    public int getPocetKnih(String isbn) {return pocetKnih.get(isbn);}

    public void odoberKnihy(Kniha k, int pocet) {
        int p = pocetKnih.get(k.getISBN());
        if(pocet >= p) {
            kosik.remove(k);
            pocetKnih.remove(k.getISBN());
        }else {
            pocetKnih.replace(k.getISBN(),p-pocet);
        }
    }

    public void pridajKnihy(Kniha k, int pocet){
        kosik.add(k);
        pocetKnih.put(k.getISBN(),pocet);
    }
}
