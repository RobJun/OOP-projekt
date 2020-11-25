package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;

abstract class Zamestnanec extends Pouzivatel{
    protected double plat;
    protected double odrobene;


    Zamestnanec(String m, long id, double plat) {
        super(m, id);
        this.plat = plat;
        odrobene = 0;

        inlineAkcie.put("plat", ((args, kh) ->  System.out.println("Tvoj plat je: " + getPlat())));
        inlineAkcie.put("zarobene", ((args, kh) -> System.out.println("zarobil si: " + vypocitajPlat())));
        inlineAkcie.put("odrobene", ((args, kh) -> System.out.println("odrobil si: " + getOdrobene())));
    }

    public double getPlat(){
        return plat;
    }
    public double getOdrobene(){
        return odrobene;
    }
    public void setPlat(double p){ plat = p;}

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
    public void spracuj(String[] s, Knihkupectvo kh){
        super.spracuj(s, kh);
        pridajHodinu();
    }
}
