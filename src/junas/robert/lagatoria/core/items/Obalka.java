package junas.robert.lagatoria.core.items;

import junas.robert.lagatoria.gui.Controller;

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
    public void getInfo() {
        Controller.printline("\tObalka:");
        Controller.printline("\t\tdizajn: " + dizajn + ", farba: " + farba);
    }
}
