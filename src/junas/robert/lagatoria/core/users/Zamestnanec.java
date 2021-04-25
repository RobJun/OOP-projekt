package junas.robert.lagatoria.core.users;

import junas.robert.lagatoria.core.users.info.UdajeOZamestnancovi;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

/**
 * Zakladna trieda zamestnanca
 * obsahuje udajeOZamestnancovi
 */
public abstract class Zamestnanec extends Pouzivatel{
    private UdajeOZamestnancovi udaje;


    /**
     * Prida zakladne funkcie zamestnanca ku zakladnym funkciam pouzivatela
     * @param meno meno zamestnanca
     * @param id identifikacne cislo
     * @param plat plat zamestnanca
     */
    public Zamestnanec(String meno, long id, double plat) {
        super(meno, id);
        udaje = new UdajeOZamestnancovi(plat);

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("plat", ((args, kh, vy) -> { return "Tvoj plat je: " + getPlat();}));
        inlineAkcie.put("zarobene", ((args, kh, vy) -> {return "zarobil si: " + vypocitajPlat();}));
        inlineAkcie.put("odrobene", ((args, kh, vy) -> {return "odrobil si: " + getOdrobene();}));
    }

    /**
     * @return vrati plat zamestnanca
     */
    public double getPlat(){
        return udaje.getPlat();
    }

    /**
     * @return vrati pocet odrobenych hodin
     */
    public double getOdrobene(){
        return udaje.getOdrobene();
    }

    /**
     * @return vrati zarobok aky zamestnanec za beh aplikacie zarobil
     */
    public double vypocitajPlat(){
        return udaje.vypocitajZarobok();
    }

    /**
     * prida hodinu zamestnancovi
     */
    public void pridajHodinu(){ udaje.odrobHodinu(); }

    /**
     * @deprecated sluzi na vypis do konzole
     * @return prazdny string
     */
    @Override
    public String help() {
        super.help();
        System.out.println("---Zamestnanecke prikazy---");
        System.out.println("plat - zisti svoj plat");
        System.out.println("odrobene - kolko si uz odrobil");
        System.out.println("zarobene - vypocita kolko si uz zarobil");
        return "";
    }

    /**
     * Nadstavba spracovania funkcii pouzivatela s pridavanim odrobenych hodin
     * @param args          argumenty, ktore sa daju vykonat
     * @param vydavatelstvo referencia na vydavatelstvo nad ktorym sa robia metody
     * @return retazec vystupu z mapy funkcii
     */
    @Override
    public String spracuj(String[] args, Vydavatelstvo vydavatelstvo){
        String res = super.spracuj(args, vydavatelstvo);
        pridajHodinu();
        return res;
    }
}
