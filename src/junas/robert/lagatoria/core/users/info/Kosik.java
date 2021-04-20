package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;

public class Kosik {
    private ArrayList<Kniha> knihy;
    private HashMap<String,Integer> pocetKnih;


    public void add(Kniha k, int pocet){
        if(knihy.contains(k)){
            int z = pocetKnih.get(k.getISBN());
            pocetKnih.replace(k.getISBN(),z+pocet);
        }
        else {
            knihy.add(k);
            pocetKnih.put(k.getISBN(), pocet);
        }
    }


    public void remove(Kniha k, int pocet){
        int p = pocetKnih.get(k.getISBN());
        if(pocet >= p) {
            knihy.remove(k);
            pocetKnih.remove(k.getISBN());
        }else {
            pocetKnih.replace(k.getISBN(),p-pocet);
        }
    }


    public String vypisKosik(){
        String res = "";
        if(knihy.isEmpty()) {
            res += "kosik je prazdny";
            return res;
        }
        for(int i = 0; i < knihy.size(); i++){
            res += knihy.get(i).getInfo() + '\n';
            res += " [" + pocetKnih.get(knihy.get(i).getISBN()) + "]\n";
        }

        return res;
    }


    public ArrayList<Kniha> getKnihy(){
        return knihy;
    }

    public int getPocet(String isbn){
        return pocetKnih.get(isbn);
    }
}
