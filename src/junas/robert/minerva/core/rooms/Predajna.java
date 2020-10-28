package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.storage.OrganizovanaSekcia;
import junas.robert.minerva.core.storage.Sekcia;
import junas.robert.minerva.core.utils.Kategoria;

import java.util.HashMap;

public class Predajna extends Miestnost{
    private HashMap<String, OrganizovanaSekcia> sekcie;


    public Predajna(){
        super();
        init(defaultSize);
    }


    @Override
    protected void init(int velkost){
        sekcie = new HashMap<String, OrganizovanaSekcia>();
        Kategoria[] v = Kategoria.values();
        for(int i = 1; i < v.length;i++){
            sekcie.put(v[i].toString(), new OrganizovanaSekcia(v[i]));
        }
    }

}
