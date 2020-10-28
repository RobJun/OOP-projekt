package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.items.Kniha;

import java.util.*;

public class Regal {
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

    public void pridajKnihy(Kniha k, int p){
        addBooks(k,p);
        volneMiesto -= p;
    }

    public void odoberKnihy(Kniha k, int p){
        //removebooks(k,p);
    }

    public int getMiesto(){
        return volneMiesto;
    }

    public ArrayList<Kniha> getZoznamKnih(){ return zoznamKnih; }
    public HashMap<String,Integer>getPocetKnih() {return  pocetKnih;}
    public int getPocetKnih(String isbn){ return pocetKnih.get(isbn);}
    public void printContent(){
        for(int i = 0; i < zoznamKnih.size(); i++){
            zoznamKnih.get(i).printContent();
            System.out.print(" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n");
        }
    }

    private boolean doesBookExist(Kniha k){
        for(int i = 0 ; i < zoznamKnih.size();i++){
            if(zoznamKnih.get(i).getISBN().equals(k.getISBN())) {
                return true;
            }
        }
        return false;
    }



    protected void addBooks(Kniha k, int p){
        if(!doesBookExist(k)){
            zoznamKnih.add(k);
            pocetKnih.put(k.getISBN(),p);
        }else{
            pocetKnih.replace(k.getISBN(),pocetKnih.get(k.getISBN()) + p);
        }
    }
}
