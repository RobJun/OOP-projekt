package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.Pisanie;

/**
 * Autor ktory pise iba historicku literaturu
 */
public class FantasyAutor extends Autor{
    public FantasyAutor(String meno, String prievzisko, Vydavatelstvo vydavatelstvo) {
        super(meno, prievzisko, vydavatelstvo);

    }

    /**
     * @param pisanie akou formou spisovatel pise
     * @return napisany text
     * @throws InterruptedException ak bol dej preruseny
     */
    @Override
    public Text accept(Pisanie pisanie) throws InterruptedException {
        return pisanie.visit(this);
    }

}
