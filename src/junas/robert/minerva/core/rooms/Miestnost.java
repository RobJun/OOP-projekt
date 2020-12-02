package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.storage.Sekcia;

import java.util.ArrayList;

public abstract class Miestnost implements java.io.Serializable{
    public static final int defaultSize = 5;
    protected int velkost;
    protected ArrayList<Kniha> katalog;

    protected abstract void init(int velkost);

    public final ArrayList<Kniha> getKatalog() { return katalog;};

    public final void printKatalog() {
        int i = 0;
        for(Kniha k : katalog){
            String[] s = k.getBasicInfo();
            System.out.println(i++ + ": " + s[0] + " - " + s[1] + " - { " +s[2] + " - " +s[3] + " }");

        }
    }
}
