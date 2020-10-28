package junas.robert.minerva.core;

import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.storage.Sekcia;
import junas.robert.minerva.core.utils.LoggedIn;

public class Knihkupectvo {
    private Sklad sklad;
    private Predajna predajna;
    private boolean otvorene;
    private LoggedIn prihlaseny;

    public Knihkupectvo(){
        sklad = new Sklad();
        predajna = new Predajna();
    }


    public void objednatTovar(){
        sklad.objednajAUmiestni("Z:\\minerva\\novyTovar.txt");
        sklad.printSklad();
    }

    public static void main(String[] args) {

        Knihkupectvo knihkupectvo = new Knihkupectvo();
        knihkupectvo.objednatTovar();
    }


    public boolean isOtvorene(){
        return otvorene;
    }

}
