package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.utils.Kategoria;
import junas.robert.minerva.core.utils.TypZ;

import java.util.ArrayList;

public class OrganizovanaSekcia extends Sekcia{
    private Kategoria nazov;
    private TypZ typZoradenia;
    private boolean kategorizovane = false;


    public OrganizovanaSekcia(Kategoria k){
        super();
        init(k,defaultSize);
        kategorizovane = false;
    }
    public OrganizovanaSekcia(Kategoria k, int pocet){
        super();
        init(k,pocet);
    }

    private void init(Kategoria kat,int pocet){
        nazov = kat;
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }

    public int najdiMiesto(String nazov) {
        return 0;
    }


    ///vracia arraylist zle ulozenych knih
    ///ak su knihy spravnej kategorie vracia null
    public ArrayList<Kniha> skontrolujKategorieKnih(){
        ArrayList<Kniha> zle = new ArrayList<>();
        for(Regal r : regale){
            for(Kniha k : r.getZoznamKnih()){
                if(k.getKategoria() != nazov){
                    zle.add(k);
                }
            }
        }

        if(zle.isEmpty()) {
            kategorizovane = true;
            return null;
        }
        kategorizovane = false;
        return zle;
    }

    public boolean isKategorizovane() {return kategorizovane;}

    @Override
    public void printSekcia(){
        System.out.println("Typ: " + nazov);
        for(int i = 0; i < regale.length;i++){
            System.out.println("regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto);
            regale[i].printContent();
        }
        System.out.println();
    }
}
