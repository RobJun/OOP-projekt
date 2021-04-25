package junas.robert.lagatoria.core.items;


/**
 * Obalka a cast knihy
 */
public abstract class Obalka implements CastiKnihy {
    private String dizajn;
    private String farba;
    protected String typObalky;

    /**
     * @param dizajn aky ma obalka dizajn
     * @param farba obalky
     */
    public Obalka(String dizajn, String farba){
        this.dizajn = dizajn;
        this.farba = farba;
    }

    /**
     * @return retazec obsahujuci dizajn a farbu obalky
     */
    @Override
    public String getInfo() {
        String res = "";
        res+="\tObalka:\n";
        res+="\t\tdizajn: " + dizajn + ", farba: " + farba + "\n";

        return res;
    }
}
