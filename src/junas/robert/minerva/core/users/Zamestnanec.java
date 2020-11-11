package junas.robert.minerva.core.users;

import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;

abstract class Zamestnanec extends Pouzivatel{
    private double plat;
    private String heslo;
    private double odrobene;


    Zamestnanec(String m, long id, String heslo, double plat) {
        super(m, id);
        this.heslo = heslo;
        this.plat = plat;
        odrobene = 0;
    }


    protected boolean skontrolujHeslo(String h){
        return heslo.equals(h);
    }

    public double vypocitajPlat(){
        return odrobene*plat;
    }

    public void pridajHodinu(){ odrobene++; }

    public double getPlat(){
        return plat;
    }

    public double getOdrobene(){
        return odrobene;
    }

    @Override
    public void spracuj(String s, Sklad sklad, Predajna predajna){
        super.spracuj(s,sklad,predajna);
        pridajHodinu();
        if(s.equals("help")){
            System.out.println("---Zamestnanecke prikazy---");
            System.out.println("plat - zisti svoj plat");
            System.out.println("odrobene - kolko si uz odrobil");
            System.out.println("zarobene - vypocita kolko si uz zarobil");

        } else if(s.equals("plat")){
            System.out.println("Tvoj plat je: " + getPlat());
        }else if(s.equals("zarobene")){
            System.out.println("zarobil si: " + vypocitajPlat());
        }else if(s.equals("odrobene")){
            System.out.println("odrobil si: " + getOdrobene());
        }
    }
}
