package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.info.Premiestnovanie;
import junas.robert.lagatoria.core.users.info.Inventar;

public class Predajca extends Zamestnanec implements Premiestnovanie {
    private Inventar inventar = new Inventar();

    public Predajca(String meno, long id){
        super(meno, id, 3.8);

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("otvor", (args, kh, vy) -> otvorPredajnu(kh.getPredajna()));
        inlineAkcie.put("zavri", (args, kh, vy)-> zavriPredajnu(kh.getPredajna()));
        inlineAkcie.put("predajna", (args, kh, vy) -> kh.getPredajna().vypisPredajnu());
        inlineAkcie.put("sklad", (args, kh, vy) -> kh.getSklad().printSklad());
        inlineAkcie.put("predaj", (args, kh, vy)->  predajKnihy(kh.getPredajna().getZakaznik()));
        inlineAkcie.put("prines", ((args, kh, vy) -> premiestni(args,kh)));
    }

    public String otvorPredajnu(Predajna p){
        p.setOtvorene(true);
        return "otovorene";
    }

    public String  zavriPredajnu(Predajna p){
        p.setOtvorene(false);
        return "zatvorene";
    }

    public String predajKnihy(Zakaznik z) {
        if(z == null) {
            return "Ziadny zakaznik nie je v predajni";
        }
        if(z.getKosik().isEmpty()){
            return "Zakaznik nema nic v kosiku";
        }
        String res = "";
        while(!z.getKosik().isEmpty()){
            Kniha k = z.getKosik().get(0);
            int p = z.getPocetKnih(k.getISBN());
            k.predaj(p);
            z.odoberKnihy(k,p);
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

    @Override
    public String premiestni(String[] args, Knihkupectvo kh){
        int p = -1;
        Kniha k = null;
        for(String f : args){
            if(f.contains("i/")){
                k = najdiReferenciuNaKnihu(kh.getSklad(), Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/")){
                k = najdiReferenciuNaKnihu(kh.getSklad(), f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){
                p = Integer.parseInt(f.substring(1));
            }
        }
        if((k == null && inventar.getKniha() == null) || p < 1){
            return "Zadana kniha neexistuje alebo si zadal zly pocet";
        }
        if(inventar.getKniha() == null) {
            for (Sekcia s : kh.getSklad().getSekcie()) {
                for (Regal r : s.getRegal()) {
                    if (r.existujeKniha(k)) {
                        inventar.set(k,r.odoberKnihy(k,p));
                    }
                }
            }
        }
        if(inventar.getKniha() == null || inventar.getPocet() < 1){
            return "Kniha nie je v invetnari/nenachadzala sa v sklade)";
        }
        int umies =  kh.getPredajna().umiestniKnihy(inventar.getKniha(),inventar.getPocet());
        if(umies == 0){
            inventar.resetInventar();
        }
        return "podarilo sa zobrat knihu";
    }
}
