package junas.robert.lagatoria.core.items;

public class BalikKnih implements InfoKniha{
    private Kniha kniha;
    private int pocet;

    public BalikKnih(Kniha kniha, int pocet){
        this.kniha = kniha;
        this.pocet = pocet;
    }

    @Override
    public String getInfo() {
        return kniha.getBasicInfo()[0] + "- "+pocet;
    }

    public int getPocet() {
        return pocet;
    }

    public Kniha getKniha() {
        return kniha;
    }
}
