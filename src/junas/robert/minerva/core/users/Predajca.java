package junas.robert.minerva.core.users;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Predajna;

public class Predajca extends Zamestnanec{
    private Kniha kniha;
    private int pocet;

    public Predajca(String meno, long id){
        super(meno, id,"1234",3.8);
        kniha = null;
        pocet =0;
    }

    public void otvorPredajnu(Predajna p){
        p.setOtvorene(true);
    }

    public void zavriPredajnu(Predajna p){
        p.setOtvorene(false);
    }

}
