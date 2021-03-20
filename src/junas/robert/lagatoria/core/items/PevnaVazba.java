package junas.robert.lagatoria.core.items;

import junas.robert.lagatoria.gui.Controller;

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

    @Override
    public void getInfo() {
        super.getInfo();
        Controller.printline("\t\ttyp vazby: " + typObalky + " ["+material+"]");
    }
}
