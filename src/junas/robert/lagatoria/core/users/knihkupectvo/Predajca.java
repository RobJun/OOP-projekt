package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.info.Premiestnovanie;
import junas.robert.lagatoria.core.users.info.Inventar;


/**
 * Trieda predajcu pracuje s predajnou a skladom Knihkupectva; a zakaznikom
 */
public class Predajca extends Zamestnanec implements Premiestnovanie {
    private Inventar inventar = new Inventar();

    /**
     * Rozsiruje  funckie zamestnanca o funkcie predajcu
     * Plat predajcu je stanoveny 3.8
     * @param meno meno predajcu
     * @param id cislo
     */
    public Predajca(String meno, long id){
        super(meno, id, 3.8);

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("otvor", (args, kh, vy) -> otvorPredajnu(kh.getPredajna()));
        inlineAkcie.put("zavri", (args, kh, vy)-> zavriPredajnu(kh.getPredajna()));
        inlineAkcie.put("predajna", (args, kh, vy) -> kh.getPredajna().vypisPredajnu());
        inlineAkcie.put("sklad", (args, kh, vy) -> kh.getSklad().printSklad());
        inlineAkcie.put("predaj", (args, kh, vy)->  predajKnihy(kh.getPredajna().getZakaznik(),kh));
        inlineAkcie.put("prines", ((args, kh, vy) -> premiestni(args,kh)));
    }

    /**
     * @param predajna ktoru otvarame
     * @return "otvorene
     */
    public String otvorPredajnu(Predajna predajna){
        predajna.setOtvorene(true);
        return "otovorene";
    }

    /**
     * @param predajna ktoru zatvarame
     * @return "zatvorene"
     */
    public String  zavriPredajnu(Predajna predajna){
        predajna.setOtvorene(false);
        return "zatvorene";
    }

    /**
     * @param zakaznik ktoremu predavame knihy
     * @param knihkupectvo knihkupectvo ktoremu pridavame kapital
     * @return retazec vystupu o uspechu predania knih
     */
    public String predajKnihy(Zakaznik zakaznik, Knihkupectvo knihkupectvo) {
        if(zakaznik == null) {
            return "Ziadny zakaznik nie je v predajni";
        }
        if(zakaznik.getKosik().isEmpty()){
            return "Zakaznik nema nic v kosiku";
        }
        String res = "";
        while(!zakaznik.getKosik().isEmpty()){
            Kniha k = zakaznik.getKosik().get(0);
            int p = zakaznik.getPocetKnih(k.getISBN());
            k.predaj(p);
            zakaznik.odoberKnihy(k,p);
            knihkupectvo.pridajPeniaze(p*k.getCena());
            res+="Zakaznik si kupil: " + k.getBasicInfo()[0] + " ["+p+ "] ["+ p*k.getCena()+ "]\n" ;
        }
        return res;
    }

    @Override
    public String help(){
        super.help();
        System.out.println("---Prikazy predajcu---");
        System.out.println("otvor - otvor predajnu");
        System.out.println("zatvor - zavri predajnu");
        System.out.println("predajna - vypis predajnu");
        System.out.println("sklad - vypis sklad");
        System.out.println("predaj - preda knihy v kosiku zakaznika");
        return "";
    }

    /**
     * Premiestni knihy zo skladu do predajne, pripadne ponecha knihy v inventari
     * @param args argumenty na premiestnenie
     * @param kh   instancia knihkupectva v ktorej premiestnujeme knihy
     * @return retazec obsahujuci vysledok premiestnovania
     */
    @Override
    public String premiestni(String[] args, Knihkupectvo kh){
        int p = -1;
        Kniha k = null;
        //spracuje argumenty v args
        for(String f : args){
            if(f.contains("i/")){ //obsahuje katalogove cislo
                k = najdiReferenciuNaKnihu(kh.getSklad(), Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/") && !(f.contains("i/"))){ //obsahuje referenciu na knihu podla nazvu knihy alebo ISBN
                k = najdiReferenciuNaKnihu(kh.getSklad(), f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){ // pocet kusov
                p = Integer.parseInt(f.substring(1));
            }
        }
        //ak nebola najdena kniha a ani v inventari sa nenachadza kniha alebo zadany pocet bol mensi ako 1
        if((k == null && inventar.getKniha() == null) || p < 1){
            return "Zadana kniha neexistuje alebo si zadal zly pocet";
        }
        //ak sa v inventari nenachadza kniha, tak knihy odoberieme z regalu
        if(inventar.getKniha() == null) {
            for (Sekcia s : kh.getSklad().getSekcie()) {
                for (Regal r : s.getRegal()) {
                    if (r.existujeKniha(k)) {
                        inventar.set(k,r.odoberKnihy(k,p));
                    }
                }
            }
        }
        //ak kniha v inventari je stale 0 alebo pocet knih v inventari je stale menej ako 1
        if(inventar.getKniha() == null || inventar.getPocet() < 1){
            return "Kniha nie je v invetnari/nenachadzala sa v sklade)";
        }
        //umiestnia sa knihy z inventara
        int umies =  kh.getPredajna().umiestniKnihy(inventar.getKniha(),inventar.getPocet());
        //ak boli umiestnene vsetky knihy
        if(umies == 0){
            inventar.resetInventar();
        }
        return "podarilo sa zobrat knihu";
    }
}
