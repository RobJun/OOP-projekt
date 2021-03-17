package junas.robert.lagatoria.core.items;

import junas.robert.lagatoria.gui.Controller;

public class BrozovanaVazba extends Obalka{
    private String obrazok;

    public BrozovanaVazba(String dizajn, String farba,String obrazok){
        super(dizajn,farba);
        typObalky = "Brozovana vazba";
        this.obrazok = obrazok;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        Controller.printline("\t\ttyp vazby: " + typObalky + " ["+obrazok+"]");
    }
}
