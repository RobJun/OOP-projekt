package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.Kniha;

/**
 * ulozenie knihy a jej poctu v Skladnikovy a predajcovi
 */
public class Inventar {
    private Kniha kniha = null;
    private int pocet = 0;

    /**
     * nastavenie inventára na knihu a kvantitu knihy
     * @param kniha kniha ktorú chceme vlozi do inventara
     * @param pocet kolko knih je v inventari
     */
    public void set(Kniha kniha, int pocet){
        this.kniha = kniha;
        this.pocet = pocet;
    }

    /**
     * nastavenie invenatara na inicializacny stav
     */
    public void resetInventar(){
        kniha = null;
        pocet = 0;
    }

    /**
     * @param pocet odpocita sa od poctu v inventari
     */
    public void odoberZKnih(int pocet){
        this.pocet -= pocet;
    }

    /**
     * @return kniha ulozena v invenarti
     */
    public Kniha getKniha() {
        return kniha;
    }

    /**
     * @return pocet knih ulozenzch v inventari
     */
    public int getPocet() {
        return pocet;
    }
}
