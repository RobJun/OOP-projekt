package junas.robert.lagatoria.core.knihkupectvo.rooms;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.knihkupectvo.storage.OrganizovanaSekcia;
import junas.robert.lagatoria.core.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Predajna extends Miestnost {
    private HashMap<Kategoria, OrganizovanaSekcia> sekcie;
    private boolean otvorene = false;
    private transient Zakaznik zakaznik;


    public Predajna(Observer observer){
        super(observer);
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
        Sekcia s = sekcie.get(((Text)(k.getSucast(Text.class))).getKategoria());
        for(Regal r : s.getRegal()){
            int pr = r.pridajKnihy(k,pocet);
            pocet -=pr;
        }
        return pocet;
    }

    public int odoberKnihy(Kniha k, int pocet){
        Sekcia s = sekcie.get(((Text)(k.getSucast(Text.class))).getKategoria());
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

    public String  vypisPredajnu(){
            int i = 0;
            String s = "";
            s += "Predajna ["+((isOtvorene()) ?"Otvorene" : "Zatvorene") +"]\n";
            for(Map.Entry<Kategoria,OrganizovanaSekcia> entry : sekcie.entrySet()){
                s += ("Sekcia: " + i + "\n");
                s+= entry.getValue().printSekcia();
                i++;
            }
            return s;
    }

    public void setZakaznik(Zakaznik z ){ zakaznik = z; }

    public Zakaznik getZakaznik(){ return  zakaznik;}

    public void setKatalog(ArrayList<Kniha> katalog) {
        this.katalog = katalog;
    }
}
