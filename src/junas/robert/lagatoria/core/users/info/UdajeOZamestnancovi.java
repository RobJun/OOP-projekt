package junas.robert.lagatoria.core.users.info;

/**
 * Ulozenie dat o zamestnancovi
 * plat a odrobene hodiny
 */
public class UdajeOZamestnancovi {
    private double plat;
    private int odrobene;

    /**
     * Nastavi sa plat na pozadovanú hodnoptu
     * a pocet odrobených hodin sa nastavi na 0
     * @param plat aky plat má zamestnanec
     */
    public UdajeOZamestnancovi(double plat){
        this.plat = plat;
        this.odrobene = 0;
    }


    /**
     * @return cas ktory zamestnanec odrobil
     */
    public int getOdrobene() {
        return odrobene;
    }

    /**
     * @return plat zamestnanca
     */
    public double getPlat() {
        return plat;
    }

    /**
     * @return vrati pocet aky zamestnanec zarobil sa pouzivanie aplikacie
     */
    public double vypocitajZarobok(){
        return plat*odrobene;
    }

    /**
     * prida hodinu do odrobeneho casu
     */
    public void odrobHodinu(){
        odrobene++;
    }
}
