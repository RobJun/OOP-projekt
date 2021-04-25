package junas.robert.lagatoria.core.items;

/**
 * Rozsiruje abstraktnu triedu Obalka o material
 */
public class PevnaVazba extends Obalka{
    private String material;

    /**
     * @param dizajn dizajn obalky
     * @param farba farba obalky
     * @param material material, z ktoreho je obalka vytvorena
     */
    public PevnaVazba(String dizajn, String farba,String material){
        super(dizajn,farba);
        typObalky = "Pevna vazba";
        this.material = material;
    }

    /**
     * @return vrati retazec v tvare "\t\ttyp vazby: #typObalky [#material]\n"
     */
    @Override
    public String getInfo() {
        String res = super.getInfo();
        res += "\t\ttyp vazby: " + typObalky + " ["+material+"]" +"\n";
        return res;
    }
}
