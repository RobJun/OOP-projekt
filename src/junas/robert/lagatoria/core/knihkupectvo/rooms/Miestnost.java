package junas.robert.lagatoria.core.knihkupectvo.rooms;

import javafx.collections.ObservableList;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.utils.Observer;

import java.util.ArrayList;

/**
 * Abstraktna treida miestnonsti v Knihkupectve
 *
 */
public abstract class Miestnost implements java.io.Serializable{
    /**
     * prednastavena hodnota velkosti regalu
     */
    public static final int defaultSize = 5;

    /**
     * skutocna velkost miestnosti
     */
    protected int velkost;
    protected ArrayList<Kniha> katalog;

    transient protected Observer observer;

    /**
     * @param observer nastavenie sledovatela miestnosti
     */
    Miestnost(Observer observer){
        this.observer = observer;
    }

    /**
     * @param observer nastavenie noveho sledovatela
     */
    public void setObserver(Observer observer){
        this.observer = observer;
    }

    /**
     * Funkcia by sa mala vyuzivat iba v konstruktoroch derivatov tejto triedy
     * nastavenie hodnot na chcene
     * @param velkost kolko sekcii sa ma vytvorit v miestnosti
     */
    protected abstract void init(int velkost);

    /**
     * @return ArrayList obsahujucu knihy v miestnosti
     */
    public final ArrayList<Kniha> getKatalog() { return katalog;};

    /**
     * @return data spracovatelne tabulkami "id nazov autor isbn vydavatelstvo
     */
    public final String printKatalog() {
        int i = 0;
        String res = "";
        for(Kniha k : katalog){
            String[] s = k.getBasicInfo();
            res +=i++ + "<>" + s[0] + "<>" + s[1] + "<>" +s[2] + "<>" +s[3]+"\n";
        }
        observer.notify(this,res);
        return res;
    }
}
