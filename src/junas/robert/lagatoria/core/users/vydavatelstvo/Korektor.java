package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.gui.Controller;

public class Korektor extends Zamestnanec {
    public Korektor(String m, long id, double plat) {
        super(m, id, plat);
    }

    private void najdiChybyVTexte(Text text){
        Controller.printline("Najedenych a opravenych " + (int)(Math.random()*100) + " chyb");
    }

    private Text skratText(Text text){
        text.setDlzka(text.getDlzka() - (int)(text.getDlzka()*0.1));
        return text;
    }

    public Text precitajText(Text text) {
        najdiChybyVTexte(text);
        return skratText(text);
    }
}
