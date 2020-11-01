package junas.robert.minerva.core.users;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.storage.NoveKnihy;
import junas.robert.minerva.core.storage.Regal;

import java.util.ArrayList;

public class Skladnik extends Pouzivatel{
    private Kniha kniha;
    private int pocet;


    public Skladnik(String meno, long id){
        super(meno, id);

    }

    public void objednajtovar(Sklad s, String path){
        s.objednatKnihy(path);
    }

    public void umiestniNovyT(Sklad s){
        s.UmiestniNovyTovar();
    }


    public void odoberKnihyZRegalu(Sklad s, Kniha k, int pocet, int[] pozicia){
        if(kniha != null) return;
         if(pozicia == null){
             NoveKnihy r = s.getNovyTovar();
             if(r.doesBookExist(k)) {
                 r.odoberKnihy(k, pocet);
                 pridajKnihy(k, pocet);
             }
         }else{
             Regal r = s.getSekcie(pozicia[0]).getRegal(pozicia[1]);
             if(r.doesBookExist(k)){
                 r.odoberKnihy(k,pocet);
                 pridajKnihy(k,pocet);
             }
         }
    }

    private void pridajKnihy(Kniha k,int pocet){
        kniha = k;
        this.pocet = pocet;
    }
}
