package junas.robert.lagatoria.core.utils;

public class InvalidFormatException extends Exception{

    private int loadedRows;
    public InvalidFormatException(String message, int loadedRows){
        super(message);
        this.loadedRows = loadedRows;
    }

    public int getLoadedRows(){
        return loadedRows;
    }
}
