package junas.robert.lagatoria.core.utils.exceptions;

public class AutorNieJeNaZozname extends Exception {
    public AutorNieJeNaZozname(String msg){
        super("autor ("+msg+"),nie je na manazerovom zozname");
    }
}
