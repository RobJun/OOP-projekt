package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.storage.OrganizovanaSekcia;
import junas.robert.minerva.core.storage.Sekcia;
import junas.robert.minerva.core.utils.Kategoria;

import java.util.HashMap;

public class Predajna extends Miestnost{
    private HashMap<Kategoria, OrganizovanaSekcia> sekcie;
    private boolean otvorene;

    public Predajna(){
        super();
        init(defaultSize);
    }


    @Override
    protected void init(int velkost){
        sekcie = new HashMap<Kategoria, OrganizovanaSekcia>();
        Kategoria[] v = Kategoria.values();
        for(int i = 1; i < v.length;i++){
            sekcie.put(v[i], new OrganizovanaSekcia(v[i]));
        }
    }

    public void setOtvorene(boolean b) {
        otvorene = b;
    }

    public boolean isOtvorene(){
        return otvorene;
    }

    public void vypisPredajnu(){

    }


}
