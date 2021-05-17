package junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage;

import junas.robert.lagatoria.core.utils.enums.Kategoria;

/**
 * Rozsiruje triedu Sekcia o kategoriu
 */
public class OrganizovanaSekcia extends Sekcia{
    private Kategoria nazov;

    /**
     * Nastavi pocet regalov v sekcii na {@link int} defaultsize = 3
     * @param kategoria kategoria knih ktore budu vkladane do sekcie
     */
    public OrganizovanaSekcia(Kategoria kategoria){
        super();
        init(kategoria,defaultSize);
    }

    /**
     * @param kategoria kategoria knih ktore budu vkladane do sekcie
     * @param pocet pocet regalov, z ktorych sa bude sekcia skladat
     */
    public OrganizovanaSekcia(Kategoria kategoria, int pocet){
        super();
        init(kategoria,pocet);
    }

    /**
     * Inicializacia organizovanej sekcie
     * @param kategoria kategoria knih ktore budu vkladane do sekcie
     * @param pocet pocet regalov, z ktorych sa bude sekcia skladat
     */
    private void init(Kategoria kategoria,int pocet){
        nazov = kategoria;
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }

    /**
     * @deprecated
     * @param pocet pocet knih
     * @return vzdy vracia 0
     */
    @Override
    public int najdiMiesto(int pocet) {
        return 0;
    }

    /**
     * @return retazec obsahujuci kategoriu sekcie a obsah regalov a volneho miesta v regali
     */
    @Override
    public String printSekcia(){
        String res = "";
        res += "Typ: " + nazov +"\n";
        for(int i = 0; i < regale.length;i++){
            res += "regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto + "\n" +
            regale[i].printContent();
        }
        res += '\n';

        return res;
    }
}
