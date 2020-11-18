package junas.robert.minerva.core.users;

import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;

public interface InputProcess {
    public void spracuj(String s, Sklad sklad, Predajna predajna);
}
