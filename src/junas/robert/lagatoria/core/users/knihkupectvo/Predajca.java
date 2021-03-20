package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.gui.Controller;
import junas.robert.lagatoria.gui.Main;

public class Predajca extends Zamestnanec {
    private Kniha kniha;
    private int pocet;

    public Predajca(String meno, long id){
        super(meno, id, 3.8);
        kniha = null;
        pocet =0;

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("otvor", (args, kh, vy) -> otvorPredajnu(kh.getPredajna()));
        inlineAkcie.put("zavri", (args, kh, vy)-> zavriPredajnu(kh.getPredajna()));
        inlineAkcie.put("predajna", (args, kh, vy) -> kh.getPredajna().vypisPredajnu());
        inlineAkcie.put("sklad", (args, kh, vy) -> kh.getSklad().printSklad());
        inlineAkcie.put("predaj", (args, kh, vy)->  predajKnihy(kh.getPredajna().getZakaznik()));
        inlineAkcie.put("prines", ((args, kh, vy) -> prines(args,kh)));
    }

    public void otvorPredajnu(Predajna p){
        p.setOtvorene(true);
    }

    public void zavriPredajnu(Predajna p){
        p.setOtvorene(false);
    }

    public void predajKnihy(Zakaznik z) {
        if(z == null) {
            Controller.printline("Ziadny zakaznik nie je v predajni");
            return;
        }
        if(z.getKosik().isEmpty()){
            Controller.printline("Zakaznik nema nic v kosiku");
            return;
        }
        while(!z.getKosik().isEmpty()){
            Kniha k = z.getKosik().get(0);
            int p = z.getPocetKnih(k.getISBN());
            k.predaj(p);
            z.odoberKnihy(k,p);
            Controller.printline("Zakaznik si kupil: " + k.getBasicInfo()[0] + " ["+p+ "] ["+ p*k.getCena()+ "]");
        }
    }

    @Override
    public void help(){
        super.help();
        System.out.println("---Prikazy predajcu---");
        System.out.println("otvor - otvor predajnu");
        System.out.println("zatvor - zavri predajnu");
        System.out.println("predajna - vypis predajnu");
        System.out.println("sklad - vypis sklad");
        System.out.println("predaj - preda knihy v kosiku zakaznika");
    }


    private void prines(String[] args, Knihkupectvo kh){
        int p = -1;
        Kniha k = null;
        for(String f : args){
            if(f.contains("i/")){
                k = najdReferenciuNaKnihu(kh.getSklad(), Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/")){
                k = najdReferenciuNaKnihu(kh.getSklad(), f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){
                p = Integer.parseInt(f.substring(1));
            }
        }
        if((k == null && kniha == null) || p < 1){
            Controller.printline("Zadana kniha neexistuje alebo si zadal zly pocet");
            return;
        }
        if(kniha == null) {
            for (Sekcia s : kh.getSklad().getSekcie()) {
                for (Regal r : s.getRegal()) {
                    if (r.existujeKniha(k)) {
                        kniha = k;
                        pocet = r.odoberKnihy(k, p);
                    }
                }
            }
        }
        if(kniha == null || pocet < 1){
            Controller.printline("Kniha nie je v invetnari/nenachadzala sa v sklade)");
            return;
        }
        int umies =  kh.getPredajna().umiestniKnihy(kniha,pocet);
        if(umies == 0){
            pocet = 0;
            kniha = null;
        }
    }
}
