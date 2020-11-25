package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.storage.Sekcia;

public abstract class Miestnost implements java.io.Serializable{
    public static final int defaultSize = 5;
    protected int velkost;

    protected abstract void init(int velkost);
}
