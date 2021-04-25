package junas.robert.lagatoria.core.utils;

import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

/**
 * definuje akym sposobom pouzivatelia spusatju funkcie
 */
public interface InputProcess {
    /**
     * funkcia na spracovanie vstupov do aplikacnej logiky
     * @param args argumenty, ktore sa daju vykonat
     * @param vydavatelstvo referencia na vydavatelstvo nad ktorym sa robia metody
     * @return retazec vystupu
     */
    String spracuj(String[] args, Vydavatelstvo vydavatelstvo);

    /**
     * @deprecated iba konzolova verzia;
     * vypis funckii, ktore moze uzivatel urobit
     * @return retazec informucjuci o funkciach
     */
    String help();
}
