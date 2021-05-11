package junas.robert.lagatoria.core.odoberatelia.knihkupectvo.rooms;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage.OrganizovanaSekcia;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Trieda do ktorej moze vojst zakaznik
 * a su v nej ulozene knihy podla urcitej kategorie
 * pracuje nad nou Predajca a Zakaznik
 */
public class Predajna extends Miestnost {
    private HashMap<Kategoria, OrganizovanaSekcia> sekcie;
    private boolean otvorene = false;
    private transient Zakaznik zakaznik;


    /**
     * Velkost predajne bude 5 sekcii
     * @param observer sledovatel predajne
     */
    public Predajna(Observer observer){
        super(observer);
        init(defaultSize);
    }


    /**
     * Inicializacia predajne
     * @param velkost pocet sekcii v predajni
     */
    @Override
    protected void init(int velkost){
        sekcie = new HashMap<Kategoria, OrganizovanaSekcia>();
        Kategoria[] v = Kategoria.values();
        for(int i = 1; i < v.length;i++){
            sekcie.put(v[i], new OrganizovanaSekcia(v[i]));
        }
    }


    /**
     * Vlozi knihy do kategorie do ktorej patrii
     * @param kniha vkladana kniha
     * @param pocet pocet kolko chcem vlozit do policky
     * @return pocet, ktory sa nepodarilo vlozit
     */
    public int umiestniKnihy(Kniha kniha, int pocet){
        Sekcia s = sekcie.get(((Text)(kniha.getSucast(Text.class))).getKategoria());
        for(Regal r : s.getRegal()){
            int pr = r.pridajKnihy(kniha,pocet);
            pocet -=pr;
        }
        return pocet;
    }

    /**
     * Odoberie knihy zo sekcie
     * @param kniha odoberana kniha
     * @param pocet pocet ktory chceme odobrat
     * @return pocet ktory sa podarilo odobrat alebo -1 ak kniha nie je v sekcii
     */
    public int odoberKnihy(Kniha kniha, int pocet){
        Sekcia s = sekcie.get(((Text)(kniha.getSucast(Text.class))).getKategoria());
        for(Regal r : s.getRegal()){
            int pr = r.odoberKnihy(kniha,pocet);
            return pr;
        }
        return -1;
    }

    /**
     * Otovri/zatvori predajnu
     * @param b otvorene (true) / zatvorene(false)
     */
    public void setOtvorene(boolean b) {
        otvorene = b;
        if(b == false)
        setZakaznik(null);
    }

    /**
     * @return ci je predajna otvorena
     */
    public boolean isOtvorene(){
        return otvorene;
    }

    /**
     * @return vrati retazec so sekciami + regalmi a knihami ulozenymi v nich + ci je predajna otvorena
     */
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

    /**
     * Nastavi zakaznika ktory moze nakupovat knihy
     * @param zakaznik ktory vstupil do predajne
     */
    public void setZakaznik(Zakaznik zakaznik){ this.zakaznik = zakaznik; }

    /**
     * @return zakaznika ktory sa nachadza v predajni
     */
    public Zakaznik getZakaznik(){ return  zakaznik;}

    /**
     * Nastavi sa novy katalog
     * @param katalog katalog ktory chceme predajni nastavit
     */
    public void setKatalog(ArrayList<Kniha> katalog) {
        this.katalog = katalog;
    }
}
