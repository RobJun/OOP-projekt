package junas.robert.lagatoria.core.items;

import junas.robert.lagatoria.gui.Controller;

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
    public void getInfo() {
        super.getInfo();
        Controller.printline("\t\ttyp vazby: " + typObalky + " ["+obrazok+"]");
    }
}
