package junas.robert.lagatoria.core.items;

/**
 * Ulozenie jednej casti Knihy a poctu v akom sa cast nachadza v baliku
 */
public class BalikKnih implements CastiKnihy {
    private CastiKnihy cast;
    private int pocet;

    /**
     * @param cast CastKnihy je podporovana,ale pre spravne fungovanie by mala byt Kniha
     * @param pocet kusov nachadzajucich sa v baliku
     */
    public BalikKnih(CastiKnihy cast, int pocet){
        this.cast = cast;
        this.pocet = pocet;
    }

    /**
     * Zakladne informacie o vlozenej knihe a pocte
     * @return vracia "'nazov_knihy' - 'pocet'"
     */
    @Override
    public String getInfo() {
        return ((Kniha)cast).getBasicInfo()[0] + "- "+pocet;
    }

    /**
     * @return pocet kusov casti aky sa nachadza v baliku
     */
    public int getPocet() {
        return pocet;
    }

    /**
     * @return cast ktora sa v baliku nachadza
     */
    public CastiKnihy getKniha() {
        return cast;
    }
}
