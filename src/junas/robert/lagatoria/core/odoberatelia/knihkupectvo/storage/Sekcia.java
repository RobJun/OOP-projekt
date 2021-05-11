package junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage;


/**
 * Kumuluje v sebe regale;
 * nachadza sa v sklade
 */
public class Sekcia implements java.io.Serializable{
    protected Regal[] regale;

    /**
     * predanastavena hodnota poctu regalov v sekcii
     */
    public static final int defaultSize = 3;


    /**
     * inicializuje pocet sekcii podla predanstavenej hodnoty
     */
    public Sekcia(){init(defaultSize);}

    /**
     * @param pocetRegalov pocet regalov ktore bude sekia obsahovat
     */
    public Sekcia(int pocetRegalov){
        init(pocetRegalov);
    }


    /**
     * Inicializcia sekcie
     * @param pocet pocet regalov ktore sa vytvoria
     */
    private void init(int pocet){
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }


    /**
     * @return vrati vsetky regale v sekcii
     */
    public Regal[] getRegal(){
        return regale;
    }

    /**
     * @param i index regalu
     * @return regal na indexe
     */
    public Regal getRegal(int i){
        return regale[i];
    }

    /**
     * Najde miesto do ktoreho sa zmestia knihy
     * @param pocet pocet vkladanych knih
     * @return vracia index prveho regalu do ktoreho sa pocet zmesti ak sa take miesto nenajde vracia -1
     */
    public int najdiMiesto(int pocet){
        for(int j = 0; j < regale.length;j++){
            if(pocet <= regale[j].getMiesto()){
               return j;
            }
        }
        return -1;
    }

    /**
     * @return vrati index najprazdnejsieho regalu pripadne -1 ak kazdy je vyplneny
     */
    public int najdiNajvacsieMiesto() {
        int max = 0;
        int index = -1;
        for(int i = 0; i < regale.length;i++){
         if(max < regale[i].getMiesto()){
             max = regale[i].getMiesto();
             index = i;
         }
        }
        return index;
    }


    /**
     * @return vracia retazec obsahujuci regale ich volne miesto a obsah regalov
     */
    public String printSekcia(){
        String res = "";
        for(int i = 0; i < regale.length;i++){
            res += "regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto + "\n" +
            regale[i].printContent();
        }
        res += "\n";
        return res;
    }
}
