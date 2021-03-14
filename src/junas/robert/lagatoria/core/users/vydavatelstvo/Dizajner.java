package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.items.BrozovanaVazba;
import junas.robert.lagatoria.core.items.Obalka;
import junas.robert.lagatoria.core.items.PevnaVazba;
import junas.robert.lagatoria.core.users.Zamestnanec;

public class Dizajner extends Zamestnanec {
    public Dizajner(String m, long id, double plat) {
        super(m, id, plat);
    }

    public Obalka navrhniObalku() {
        if((int)(Math.random()*2) == 0) {
            return new BrozovanaVazba("nejaky", "biela", "papagaj");
        }else{
            return new PevnaVazba("nejaky","cervena", "drevo");
        }
    }
}
