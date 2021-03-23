package junas.robert.lagatoria.core.users;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.gui.Controller;

import javax.sound.sampled.Control;

public abstract class Zamestnanec extends Pouzivatel{
    protected double plat;
    protected double odrobene;


    public Zamestnanec(String m, long id, double plat) {
        super(m, id);
        this.plat = plat;
        odrobene = 0;

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("plat", ((args, kh, vy) ->  Controller.printline("Tvoj plat je: " + getPlat())));
        inlineAkcie.put("zarobene", ((args, kh, vy) -> Controller.printline("zarobil si: " + vypocitajPlat())));
        inlineAkcie.put("odrobene", ((args, kh, vy) -> Controller.printline("odrobil si: " + getOdrobene())));
    }

    public double getPlat(){
        return plat;
    }
    public double getOdrobene(){
        return odrobene;
    }

    public double vypocitajPlat(){
        return odrobene*plat;
    }
    public void pridajHodinu(){ odrobene++; }

    @Override
    public void help() {
        super.help();
        System.out.println("---Zamestnanecke prikazy---");
        System.out.println("plat - zisti svoj plat");
        System.out.println("odrobene - kolko si uz odrobil");
        System.out.println("zarobene - vypocita kolko si uz zarobil");
    }

    @Override
    public void spracuj(String[] args, Vydavatelstvo vydavatelstvo){
        super.spracuj(args, vydavatelstvo);
        pridajHodinu();
    }
}
