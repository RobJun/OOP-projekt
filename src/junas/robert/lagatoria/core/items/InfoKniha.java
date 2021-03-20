package junas.robert.lagatoria.core.items;

import java.io.Serializable;

/**
 * zakladny inteface pre navrhovy vzor Composit pre Knihy
 */
public interface InfoKniha extends Serializable {
    /**
     * vypisuje informacie o knihe
     */
    void getInfo();
}
