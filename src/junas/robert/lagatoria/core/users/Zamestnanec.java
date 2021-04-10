package junas.robert.lagatoria.core.users;

import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

public abstract class Zamestnanec extends Pouzivatel{
    protected double plat;
    protected double odrobene;


    public Zamestnanec(String m, long id, double plat) {
        super(m, id);
        this.plat = plat;
        odrobene = 0;

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("plat", ((args, kh, vy) -> { return "Tvoj plat je: " + getPlat();}));
        inlineAkcie.put("zarobene", ((args, kh, vy) -> {return "zarobil si: " + vypocitajPlat();}));
        inlineAkcie.put("odrobene", ((args, kh, vy) -> {return "odrobil si: " + getOdrobene();}));
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
    public String help() {
        super.help();
        System.out.println("---Zamestnanecke prikazy---");
        System.out.println("plat - zisti svoj plat");
        System.out.println("odrobene - kolko si uz odrobil");
        System.out.println("zarobene - vypocita kolko si uz zarobil");
        return "";
    }

    @Override //Zamestnanec.java
    public String spracuj(String[] args, Vydavatelstvo vydavatelstvo){
        String res = super.spracuj(args, vydavatelstvo);
        pridajHodinu();
        return res;
    }
}
