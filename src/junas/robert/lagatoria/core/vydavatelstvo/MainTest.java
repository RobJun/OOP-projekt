package junas.robert.lagatoria.core.vydavatelstvo;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;

import java.util.ArrayList;

public class MainTest {

    public static void main(String[] args){
        Manazer manazer = new Manazer("Ferdinand", 331, 20);
        Knihkupectvo knihkupectvo = Knihkupectvo.getInstance();
        Vydavatelstvo vydavatelstvo = new Vydavatelstvo(manazer,20);

        ArrayList<Odoberatel> odoberatels = new ArrayList<>();
        odoberatels.add(Knihkupectvo.getInstance());

        Distributor dis = new Distributor("Ppp",111, 32);
        dis.DajOdoberatlom(odoberatels,new Kniha("dsda","dsadsad",222,321),200);
        dis.DajOdoberatlom(odoberatels,new Kniha("dsdssa","dsadsad",222,321),200);
        dis.DajOdoberatlom(odoberatels,new Kniha("dsdsaaa","dsadsad",222,321),200);
        dis.DajOdoberatlom(odoberatels,new Kniha("dsdwea","dsadsad",222,321),200);

    }
}
