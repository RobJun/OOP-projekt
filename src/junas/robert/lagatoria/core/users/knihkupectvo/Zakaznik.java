package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.users.Pouzivatel;

import java.util.ArrayList;
import java.util.HashMap;

public class Zakaznik extends Pouzivatel {
    private ArrayList<Kniha> kosik;
    private HashMap<String,Integer> pocetKnih;

    public Zakaznik(){
        super("Guest", 0);
        kosik = new ArrayList<Kniha>();
        pocetKnih = new HashMap<String,Integer>();

        inlineAkcie.put("kosik",(args, kh, vy)-> vypisKosik());
        inlineAkcie.put("predajna", (args, kh, vy) -> kh.getPredajna().vypisPredajnu() );
        inlineAkcie.put("zober", (args, kh, vy) -> dajDoKosika(args,kh.getPredajna()));
    }

    @Override
    public void help() {
        super.help();
        System.out.println("---Zakaznicke prikazy---");
        System.out.println("kosik - vypise knihy v kosiku");
        System.out.println("predajna - vypise predajnu");
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

    private void pridajKnihy(Kniha k, int pocet){
        if(kosik.contains(k)){
            int z = pocetKnih.get(k.getISBN());
            pocetKnih.replace(k.getISBN(),z+pocet);
        }
        else {
            kosik.add(k);
            pocetKnih.put(k.getISBN(), pocet);
        }
    }

    private void dajDoKosika(String[] args, Predajna pr){
        Kniha k = null;
        int p = -1;
        for(String f : args){
            if(f.contains("i/")){
                k = najdReferenciuNaKnihu(pr, Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/")){
                k = najdReferenciuNaKnihu(pr, f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){
                p = Integer.parseInt(f.substring(1));
            }
        }

        if(k == null || p < 1){
            System.out.println("Kniha neexistuje alebo si zadal zly pocet");
            return;
        }

        int z = pr.odoberKnihy(k,p);
        if(z == -1){
            System.out.println("Kniha nie je na predajni");
            return;
        }

        pridajKnihy(k,z);
    }

}
