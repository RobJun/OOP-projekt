package junas.robert.lagatoria.core.users.info;

public class UdajeOZamestnancovi {
    private double plat;
    private int odrobene;

    public UdajeOZamestnancovi(double plat){
        this.plat = plat;
        this.odrobene = 0;
    }


    public int getOdrobene() {
        return odrobene;
    }

    public double getPlat() {
        return plat;
    }

    public double Vypocitaj_zarobok(){
        return plat*odrobene;
    }

    public void odrobHodinu(){
        odrobene++;
    }
}
