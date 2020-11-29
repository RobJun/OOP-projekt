package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.utils.InlineCommand;

public class Predajca extends Zamestnanec{
    private Kniha kniha;
    private int pocet;

    public Predajca(String meno, long id){
        super(meno, id, 3.8);
        kniha = null;
        pocet =0;

        inlineAkcie.put("otvor", (args, kh) -> otvorPredajnu(kh.getPredajna()));
        inlineAkcie.put("zavri", (args, kh)-> zavriPredajnu(kh.getPredajna()));
        inlineAkcie.put("predajna", (args, kh) -> kh.getPredajna().vypisPredajnu());
        inlineAkcie.put("sklad", (args, kh) -> kh.getSklad().printSklad());
        inlineAkcie.put("predaj", (args,kh)->  predajKnihy(kh.getPredajna().getZakaznik()));
        inlineAkcie.put("prines", ((args, kh) -> prines(args,kh)));
    }

    public void otvorPredajnu(Predajna p){
        p.setOtvorene(true);
    }

    public void zavriPredajnu(Predajna p){
        p.setOtvorene(false);
    }

    public void predajKnihy(Zakaznik z) {
        if(z.getKosik().isEmpty()){
            System.out.println("Zakaznik nema nic v kosiku");
            return;
        }
        for(Kniha k : z.getKosik()){
            int p = z.getPocetKnih(k.getISBN());
            k.predaj(p);
            z.odoberKnihy(k,p);
            System.out.println("Zakaznik si kupil: " + k.getBasicInfo()[0] + " ["+p+ "] ["+ p*k.getCena()+ "]");
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
        int[] zober = new int[] {-1,-1};
        int[] umiestni = new int[] {-1,-1};
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
    }
}
