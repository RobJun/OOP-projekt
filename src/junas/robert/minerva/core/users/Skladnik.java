package junas.robert.minerva.core.users;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.storage.NoveKnihy;
import junas.robert.minerva.core.storage.Regal;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Skladnik extends Pouzivatel{
    private Kniha kniha;
    private int pocet;


    public Skladnik(String meno, long id){
        super(meno, id);

    }

    public void objednajtovar(@NotNull Sklad s,@NotNull String path){
        s.objednatKnihy(path);
    }

    public void umiestniNovyT(Sklad s){
        s.UmiestniNovyTovar();
    }


    public void odoberKnihyZRegalu(Sklad s, Kniha k, int pocet, int[] pozicia){
        if(kniha != null) return;
         if(pozicia == null){
             NoveKnihy r = s.getNovyTovar();
             if(r != null && r.doesBookExist(k)) {
                 int p = r.odoberKnihy(k, pocet);
                 pridajKnihy(k, p);
             }
         }else{
             Regal r = s.getSekcie(pozicia[0]).getRegal(pozicia[1]);
             if(r.doesBookExist(k)){
                 int p = r.odoberKnihy(k,pocet);
                 pridajKnihy(k,p);
             }
         }
    }

    public void umiestniKnihyDoRegalu(Sklad s, int[] pozicia, int pocet){
        if(pozicia == null) {
            System.out.println("Neplatna pozicia");
            return;
        }
        if(kniha == null){
            System.out.println("Nemas knihy u seba");
            return;
        }
        if(this.pocet < pocet) pocet = this.pocet;
        this.pocet -= pocet;
        s.getSekcie(pozicia[0]).getRegal(pozicia[1]).pridajKnihy(kniha,pocet);
        if(this.pocet == 0) kniha = null;
    }


    public Kniha najdReferenciuNaKnihu(Sklad s, int i){
        return s.getKatalog().get(i);
    }

    public Kniha najdReferenciuNaKnihu(Sklad s, String id){
        for(Kniha kp : s.getKatalog()){
            if(kp.getISBN().equals(id) || kp.getBasicInfo()[0].equals(id)){
                return kp;
                }
        }
        return null;
    }

    private void pridajKnihy(Kniha k,int pocet){
        kniha = k;
        this.pocet = pocet;
    }


    public int getPocetNosenych() {
        return pocet;
    }
    public Kniha getKniha(){
        return kniha;
    }
}
