package junas.robert.lagatoria.core.utils.exceptions;

/**
 * Vyhadzuje sa vtedy keď chceme odobrať autora,
 * ktorý sa nenachádza na zozname a teda sa nedá odobrať
 */
public class AutorNieJeNaZozname extends Exception {
    public AutorNieJeNaZozname(String msg){
        super("autor ("+msg+"),nie je na manazerovom zozname");
    }
}
