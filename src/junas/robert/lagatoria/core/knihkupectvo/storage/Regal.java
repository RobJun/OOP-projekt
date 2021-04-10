package junas.robert.lagatoria.core.knihkupectvo.storage;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.gui.View;

import java.util.*;

public class Regal implements java.io.Serializable{
    protected ArrayList<Kniha> zoznamKnih;
    protected HashMap<String, Integer> pocetKnih;
    public final static int miesto = 300;
    private int volneMiesto;

    protected Regal(){
        init();
    }


    private void init(){
        zoznamKnih = new ArrayList<Kniha>();
        pocetKnih = new HashMap<String, Integer>();
        volneMiesto = miesto;
    }

    public int pridajKnihy(Kniha k, int p){
        if(p == 0) return 0;
        if(volneMiesto < p){
            p = volneMiesto;
        }
        pridajKnihyP(k,p);
        volneMiesto -= p;

        return p;
    }

    public int odoberKnihy(Kniha k, int p){
        int v = odoberKnihyP(k,p);
        volneMiesto += v;
        return v;

    }

    public int getMiesto(){
        return volneMiesto;
    }
    public ArrayList<Kniha> getZoznamKnih(){ return zoznamKnih; }
    public HashMap<String,Integer>getPocetKnih() {return  pocetKnih;}
    public int getPocetKnih(String isbn){ return pocetKnih.get(isbn);}
    public int getPocetKnih(Kniha k){ return pocetKnih.get(k.getISBN());}

    public String printContent(){
        String res = "";
        for(int i = 0; i < zoznamKnih.size(); i++){
            res +=" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n" +
            zoznamKnih.get(i).getInfo();
        }
        return res;
    }

    public boolean existujeKniha(Kniha k){
        for(int i = 0 ; i < zoznamKnih.size();i++){
            if(zoznamKnih.get(i).getISBN().equals(k.getISBN())) {
                return true;
            }
        }
        return false;
    }



    protected void pridajKnihyP(Kniha k, int p){
        if(!existujeKniha(k)){
            zoznamKnih.add(k);
            pocetKnih.put(k.getISBN(),p);
        }else{
            pocetKnih.replace(k.getISBN(),pocetKnih.get(k.getISBN()) + p);
        }
    }

    protected int odoberKnihyP(Kniha k, int p){
        if(existujeKniha(k)){
            int pocet = getPocetKnih(k.getISBN());
            int vymazanych = p;
            if(pocet <= p){
                vymazanych = pocet;
                zoznamKnih.remove(k);
                pocetKnih.remove(k.getISBN());
            }else{
                pocetKnih.replace(k.getISBN(),pocet-p);
            }

            return vymazanych;
        }
        return -1;
    }
}
