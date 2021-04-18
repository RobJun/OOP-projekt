package junas.robert.lagatoria.core.items;


/**
 * Obalka a cast knihy
 */
public abstract class Obalka implements InfoKniha {
    private String dizajn;
    private String farba;
    protected String typObalky;

    public Obalka(String dizajn, String farba){
        this.dizajn = dizajn;
        this.farba = farba;
    }

    @Override
    public String getInfo() {
        String res = "";
        res+="\tObalka:\n";
        res+="\t\tdizajn: " + dizajn + ", farba: " + farba + "\n";

        return res;
    }
}
