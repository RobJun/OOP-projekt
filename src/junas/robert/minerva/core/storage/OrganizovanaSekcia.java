package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.utils.Kategoria;
import junas.robert.minerva.core.utils.TypZ;

public class OrganizovanaSekcia extends Sekcia{
    TypZ typZoradenia;

    public OrganizovanaSekcia(Kategoria k){
        super();
        init(k,defaultSize);
    }
    public OrganizovanaSekcia(Kategoria k, int pocet){
        super();
        init(k,pocet);
    }
}
