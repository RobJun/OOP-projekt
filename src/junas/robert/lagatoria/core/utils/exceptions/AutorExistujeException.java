package junas.robert.lagatoria.core.utils.exceptions;

/**
 * Vyhadzuje sa iba vtedy ak manazer uz ma autora vo svojom zozname
 */
public class AutorExistujeException extends Exception{

    public AutorExistujeException(String msg){
        super("autor ("+msg+"), uz je na manazerovom zozname");
    }
}
