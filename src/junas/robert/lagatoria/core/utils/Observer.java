package junas.robert.lagatoria.core.utils;

/**
 * implementuju triedy ktore maju sledovat urcite instancie
 */
public interface Observer {
    /**
     * @param caller instancia, ktora vyvolala upovedomovanie
     * @param msg sprava ktoru rozposiela
     */
    void notify(Object caller, Object msg);
}
