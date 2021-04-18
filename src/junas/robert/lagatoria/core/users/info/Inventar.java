package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.Kniha;

public class Inventar {
    private Kniha kniha = null;
    private int pocet = 0;

    public void set(Kniha kniha, int pocet){
        this.kniha = kniha;
        this.pocet = pocet;
    }

    public void resetInventar(){
        kniha = null;
        pocet = 0;
    }

    public void odoberZKnih(int pocet){
        this.pocet -= pocet;
    }

    public Kniha getKniha() {
        return kniha;
    }

    public int getPocet() {
        return pocet;
    }
}
