package junas.robert.lagatoria.core.knihkupectvo.items;

public abstract class Obalka implements InfoKniha {
    private String dizajn;
    private String farba;
    protected String typObalky;

    public Obalka(String dizajn, String farba){
        this.dizajn = dizajn;
        this.farba = farba;
    }

    @Override
    public void getInfo() {
        System.out.println("\tObalka:");
        System.out.println("\t\tdizajn: " + dizajn + ", farba: " + farba);
    }
}
