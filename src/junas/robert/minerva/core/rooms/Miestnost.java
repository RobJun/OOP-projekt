package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.storage.Sekcia;

import java.util.ArrayList;

public abstract class Miestnost implements java.io.Serializable{
    public static final int defaultSize = 5;
    protected int velkost;

    protected abstract void init(int velkost);

    public abstract ArrayList<Kniha> getKatalog();
}
