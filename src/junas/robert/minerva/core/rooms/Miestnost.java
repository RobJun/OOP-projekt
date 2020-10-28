package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.storage.Sekcia;

abstract class Miestnost {
    protected static final int defaultSize = 5;
    protected int velkost;
    protected abstract void init(int velkost);
}
