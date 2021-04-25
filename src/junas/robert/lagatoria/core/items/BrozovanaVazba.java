package junas.robert.lagatoria.core.items;

/**
 * Rozsiruje abstraktnu triedu Obalka o obrazok
 */
public class BrozovanaVazba extends Obalka{
    private String obrazok;

    /**
     * @param dizajn dizajn obalky
     * @param farba  farba obalky
     * @param obrazok brozovana vazba ma na obalke obrazok a tu ho opisujeme
     */
    public BrozovanaVazba(String dizajn, String farba,String obrazok){
        super(dizajn,farba);
        typObalky = "Brozovana vazba";
        this.obrazok = obrazok;
    }

    /**
     * Vypis informacii o obalke
     */
    @Override
    public String getInfo() {
        String res = super.getInfo();
        res+="\t\ttyp vazby: " + typObalky + " ["+obrazok+"]\n";
        return res;
    }
}
