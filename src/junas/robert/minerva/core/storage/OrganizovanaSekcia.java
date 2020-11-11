package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.utils.Kategoria;
import junas.robert.minerva.core.utils.TypZ;

public class OrganizovanaSekcia extends Sekcia{
    private Kategoria nazov;
    private TypZ typZoradenia;
    private boolean kategorizovane = false;


    public OrganizovanaSekcia(Kategoria k){
        super();
        init(k,defaultSize);
        kategorizovane = false;
    }
    public OrganizovanaSekcia(Kategoria k, int pocet){
        super();
        init(k,pocet);
    }

    private void init(Kategoria kat,int pocet){
        nazov = kat;
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }

    public int najdiMiesto(String nazov) {
        return 0;
    }

    public Kniha[] skontrolujKategorieKnih(){
        return null;
    }

    public boolean isKategorizovane() {return kategorizovane;}
}
