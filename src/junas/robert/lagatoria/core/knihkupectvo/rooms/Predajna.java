package junas.robert.lagatoria.core.knihkupectvo.rooms;

import junas.robert.lagatoria.core.knihkupectvo.storage.OrganizovanaSekcia;
import junas.robert.lagatoria.core.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.users.Zakaznik;
import junas.robert.lagatoria.core.utils.Kategoria;
import junas.robert.lagatoria.core.knihkupectvo.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Predajna extends Miestnost {
    private HashMap<Kategoria, OrganizovanaSekcia> sekcie;
    private boolean otvorene;
    private transient Zakaznik zakaznik;


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


    public int umiestniKnihy(Kniha k, int pocet){
        Sekcia s = sekcie.get(k.getKategoria());
        for(Regal r : s.getRegal()){
            int pr = r.pridajKnihy(k,pocet);
            pocet -=pr;
        }
        return pocet;
    }

    public int odoberKnihy(Kniha k, int pocet){
        Sekcia s = sekcie.get(k.getKategoria());
        for(Regal r : s.getRegal()){
            int pr = r.odoberKnihy(k,pocet);
            return pr;
        }
        return -1;
    }

    public void setOtvorene(boolean b) {
        otvorene = b;
        if(b == false)
        setZakaznik(null);
    }

    public boolean isOtvorene(){
        return otvorene;
    }

    public void vypisPredajnu(){
            int i = 0;
            for(Map.Entry<Kategoria,OrganizovanaSekcia> entry : sekcie.entrySet()){
                System.out.println("Sekcia: " + i);
                entry.getValue().printSekcia();
                i++;
            }
    }

    public void setZakaznik(Zakaznik z ){ zakaznik = z; }

    public Zakaznik getZakaznik(){ return  zakaznik;}

    public void setKatalog(ArrayList<Kniha> katalog) {
        this.katalog = katalog;
    }
}