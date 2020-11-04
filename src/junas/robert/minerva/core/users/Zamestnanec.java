package junas.robert.minerva.core.users;

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

    public void pridajHodinu(){
        odrobene++;
    }

    public double getPlat(){
        return plat;
    }

    public double getOdrobene(){
        return odrobene;
    }


}
