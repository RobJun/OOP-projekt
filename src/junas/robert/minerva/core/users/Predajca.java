package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Predajna;

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
    }

    public void otvorPredajnu(Predajna p){
        p.setOtvorene(true);
    }

    public void zavriPredajnu(Predajna p){
        p.setOtvorene(false);
    }

    public void predajKnihy(Zakaznik z) {

    }

@Override
    public void help(){
        super.help();
        System.out.println("---Prikazy predajcu---");
        System.out.println("otvor - otvor predajnu");
        System.out.println("zatvor - zavri predajnu");
        System.out.println("predajna - vypis predajnu");
    }


}
