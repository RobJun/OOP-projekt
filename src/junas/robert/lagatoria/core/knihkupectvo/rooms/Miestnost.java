package junas.robert.lagatoria.core.knihkupectvo.rooms;

import javafx.collections.ObservableList;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.utils.Observer;

import java.util.ArrayList;

public abstract class Miestnost implements java.io.Serializable{
    public static final int defaultSize = 5;
    protected int velkost;
    protected ArrayList<Kniha> katalog;

    transient protected Observer observer;

    Miestnost(Observer observer){
        this.observer = observer;
    }

    public void setObserver(Observer observer){
        this.observer = observer;
    }

    protected abstract void init(int velkost);

    public final ArrayList<Kniha> getKatalog() { return katalog;};

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
