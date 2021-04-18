package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RadKnih {
    private Queue<BalikKnih> knihy = new LinkedList<>();


    public void addKniha(Kniha k, int pocet){
        knihy.add(new BalikKnih(k,pocet));
    }

    public BalikKnih popKniha(){
        return knihy.remove();
    }

    public int getSize(){
        return knihy.size();
    }


    public ArrayList<BalikKnih> getKnihy(){
        ArrayList<BalikKnih> result = new ArrayList<>();
        for(BalikKnih k : knihy){
            result.add(k);
        }
        return result;
    }


    public LinkedList<Integer> getPocty(){
        LinkedList<Integer> result = new LinkedList<>();
        for(BalikKnih k : knihy){
            result.add(k.getPocet());
        }
        return result;
    }

    public boolean isEmpty(){
        return knihy.isEmpty();
    }
}
