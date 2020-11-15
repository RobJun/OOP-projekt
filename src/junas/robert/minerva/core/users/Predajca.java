package junas.robert.minerva.core.users;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;

public class Predajca extends Zamestnanec{
    private Kniha kniha;
    private int pocet;

    public Predajca(String meno, long id){
        super(meno, id, 3.8);
        kniha = null;
        pocet =0;
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
    public void spracuj(String s, Sklad sklad, Predajna predajna){
        super.spracuj(s,null,null);

        if(s.equals("help")){
            System.out.println("---predajna prikazy---");
            System.out.println("zavri - vypise knihy v kosiku");
            System.out.println("otvor - zisti ci je kniha dostupna []");
        }else if(s.equals("otvor")){
            otvorPredajnu(predajna);
        }
        else if(s.equals("zavri")){
            zavriPredajnu(predajna);
        }
    }

}
