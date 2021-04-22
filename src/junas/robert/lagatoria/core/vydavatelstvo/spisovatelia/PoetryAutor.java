package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.Pisanie;

public class PoetryAutor extends Autor{
    public PoetryAutor(String meno, String prievzisko, Vydavatelstvo vydavatelstvo) {
        super(meno, prievzisko, vydavatelstvo);
    }


    public Text accept(Pisanie pisanie) throws InterruptedException {
        return pisanie.visit(this);
    }

}
