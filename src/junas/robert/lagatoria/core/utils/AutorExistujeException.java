package junas.robert.lagatoria.core.utils;

public class AutorExistujeException extends Exception{

    public AutorExistujeException(String msg){
        super("autor ("+msg+"), uz je na manazerovom zozname");
    }
}
