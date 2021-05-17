package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.info.Premiestnovanie;
import junas.robert.lagatoria.core.users.info.Kosik;

/**
 * Rozsiruje triedu Pouzivatel o kosik do ktoreho si vklada knihy co si chce kupit
 */
public class Zakaznik extends Pouzivatel implements Premiestnovanie {
    /**
     * Kosik obsahuje vsetky knihy a pocty ake si chce zakaznik kupit
     */
    private Kosik kosik;

    /**
     * Rozsiruje funkcie pouzivatela o funkcie zakaznika.
     * Meno zakaznika je Guest a id je 0
     */
    public Zakaznik(){
        super("Guest", 0);
        kosik = new Kosik();

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("kosik",(args, kh, vy)-> vypisKosik());
        inlineAkcie.put("predajna", (args, kh, vy) -> {if(kh.getPredajna().isOtvorene()){
            return kh.getPredajna().vypisPredajnu();
        }
            return "Predajna je zavreta";
        } );
        inlineAkcie.put("zober", (args, kh, vy) -> {
            if (kh.getPredajna().isOtvorene()) {
                return premiestni(args, kh);
            }
            return "Predajna je zavreta";
    });
    }

    @Override
    public String help() {
        super.help();
        System.out.println("---Zakaznicke prikazy---");
        System.out.println("kosik - vypise knihy v kosiku");
        System.out.println("predajna - vypise predajnu");
        return "";
    }
    /**
     * @return vrati kosik zakaznika
     */
    public Kosik getKosik(){
        return kosik;
    }

    /**
     * @return retazec obbsahujuci knihy v kosiku
     */
    public String vypisKosik(){
            return kosik.vypisKosik();
    }

    /**
     * @return vrati knihy ulozene v kosiku
     */
    //public ArrayList<Kniha> getKosik() { return kosik.getKnihy(); }

    /**
     * @param isbn identifikacny kod knihy
     * @return pocet kusov urcitej knihy urceniej podla isbn
     */
    public int getPocetKnih(String isbn) {return kosik.getPocet(isbn);}

    /**
     * Odstranuje knihy z kosika
     * @param kniha kniha ktoru chceme odstranit z inventara
     * @param pocet pocet knih odstranenych z kosika
     */
    public void odoberKnihy(Kniha kniha, int pocet) {
        kosik.remove(kniha,pocet);
    }

    /**
     * Pridava knihy do kosika
     * @param kniha kniha ktoru chcem epridat
     * @param pocet pocet kusov ktory chceme pridat
     */
    private void pridajKnihy(Kniha kniha, int pocet){
        kosik.add(kniha,pocet);
    }

    /**
     * odoberie knihy z regalu v predajni a vlozi ich do kosika
     * @param args argumenty na premiestnenie
     * @param kh   instancia knihkupectva v ktorej premiestnujeme knihy
     * @return retazec informujuci o uspechu premiestnovania
     */
 @Override
    public String premiestni(String[] args, Knihkupectvo kh){
        Predajna predajna = kh.getPredajna();
        Kniha k = null;
        int p = -1;
        for(String f : args){
            if(f.contains("i/")){//katalogove cislo
                k = najdiReferenciuNaKnihu(predajna, Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/") && !(f.contains("i/"))){ //ISBN alebo nazov knihy
                k = najdiReferenciuNaKnihu(predajna, f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){ //pocet
                p = Integer.parseInt(f.substring(1));
            }
        }

        //ak bol zadany neplatny pocet/ neexistujuca kniha
        if(k == null || p < 1){
            return"Kniha neexistuje alebo si zadal zly pocet";
        }

        //odoberie knihy a vrati pocet odobranych knih
        int z = predajna.odoberKnihy(k,p);
        if(z == -1){ // ak sa zadana kniha nenachadza v predajni
            return "Kniha nie je na predajni";
        }

        //prida knihy do kosika
        pridajKnihy(k,z);
        return "Kniha bola uspesne zobrata";
    }
}
