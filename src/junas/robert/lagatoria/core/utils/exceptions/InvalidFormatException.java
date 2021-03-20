package junas.robert.lagatoria.core.utils.exceptions;

/**
 * Vyhadzuje sa prave vtedy ked citani subor obsahuje knihu v zle zadanom formate
 * format: nazov///Autor///ISBN///pocet Kusov///pocet Stran///cena///Kategoria///jazyk///vazba///vydavatel///rok
 * kontroluje sa ci pocet Kusov, Stran,rok ci su zadane ako Int a cena ci je typu float
 */
public class InvalidFormatException extends Exception{

    private int loadedRows;

    /**
     * @param message chybove hlasenie
     * @param loadedRows cislo riadku s chybou
     */
    public InvalidFormatException(String message, int loadedRows){
        super(message);
        this.loadedRows = loadedRows;
    }


    /**
     * @return vrati pocet nacitanych riadkov vratane chyboveho
     */
    public int getLoadedRows(){
        return loadedRows;
    }
}
