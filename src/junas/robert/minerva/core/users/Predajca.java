package junas.robert.minerva.core.users;

import junas.robert.minerva.core.rooms.Predajna;

public class Predajca extends Pouzivatel{

    public Predajca(String meno, long id){
        super(meno, id);

    }

    public void otvorPredajnu(Predajna p){
        p.setOtvorene(true);
    }

    public void zavriPredajnu(Predajna p){
        p.setOtvorene(false);
    }

}
