package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.info.Premiestnovanie;
import junas.robert.lagatoria.core.users.info.Kosik;

import java.util.ArrayList;

public class Zakaznik extends Pouzivatel implements Premiestnovanie {
    private Kosik kosik;

    public Zakaznik(){
        super("Guest", 0);
        kosik = new Kosik();

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("kosik",(args, kh, vy)-> vypisKosik());
        inlineAkcie.put("predajna", (args, kh, vy) -> kh.getPredajna().vypisPredajnu() );
        inlineAkcie.put("zober", (args, kh, vy) -> premiestni(args,kh));
    }

    @Override
    public String help() {
        super.help();
        System.out.println("---Zakaznicke prikazy---");
        System.out.println("kosik - vypise knihy v kosiku");
        System.out.println("predajna - vypise predajnu");
        return "";
    }

    public String vypisKosik(){
            return kosik.vypisKosik();
    }

    public ArrayList<Kniha> getKosik() { return kosik.getKnihy(); }
    public int getPocetKnih(String isbn) {return kosik.getPocet(isbn);}

    public void odoberKnihy(Kniha k, int pocet) {
        kosik.remove(k,pocet);
    }

    private void pridajKnihy(Kniha k, int pocet){
        kosik.add(k,pocet);
    }

 @Override
    public String premiestni(String[] args, Knihkupectvo kh){
        Predajna pr = kh.getPredajna();
        Kniha k = null;
        int p = -1;
        for(String f : args){
            if(f.contains("i/")){
                k = najdiReferenciuNaKnihu(pr, Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/")){
                k = najdiReferenciuNaKnihu(pr, f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){
                p = Integer.parseInt(f.substring(1));
            }
        }

        if(k == null || p < 1){
            return"Kniha neexistuje alebo si zadal zly pocet";
        }

        int z = pr.odoberKnihy(k,p);
        if(z == -1){
            return "Kniha nie je na predajni";
        }

        pridajKnihy(k,z);
        return "Kniha bola uspesne zobrata";
    }
}
