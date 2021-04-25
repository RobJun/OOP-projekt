package junas.robert.lagatoria.core.items;

import java.io.Serializable;

/**
 * zakladny inteface pre navrhovy vzor Composite pre Knihy
 */
public interface CastiKnihy extends Serializable {
    /**
     * vypisuje informacie o knihe
     * @return retazec informucjuci o uspechu akcie
     */
    String getInfo();
}
