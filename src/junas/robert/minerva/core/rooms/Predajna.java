package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.storage.OrganizovanaSekcia;
import junas.robert.minerva.core.storage.Regal;
import junas.robert.minerva.core.storage.Sekcia;
import junas.robert.minerva.core.users.Zakaznik;
import junas.robert.minerva.core.utils.Kategoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Predajna extends Miestnost {
    private HashMap<Kategoria, OrganizovanaSekcia> sekcie;
    private boolean otvorene;
    private transient Zakaznik zakaznik;
    private ArrayList<Kniha> katalog;


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

    public void setOtvorene(boolean b) {
        otvorene = b;
        removeZakaznik();
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

    public void removeZakaznik(){
        if(!otvorene)
            zakaznik = null;
    }

    public Zakaznik getZakaznik(){ return  zakaznik;}


    public ArrayList<Kniha> getKatalog() {
        return katalog;
    }

    public void setKatalog(ArrayList<Kniha> katalog) {
        this.katalog = katalog;
    }
}
