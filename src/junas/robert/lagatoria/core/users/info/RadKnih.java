package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Knihy a ich pocty ulozene v rade
 * knihy sa vkladaju do BalikKnih
 */
public class RadKnih {
    private Queue<BalikKnih> knihy = new LinkedList<>();


    /**
     * @param kniha vkladana kniha
     * @param pocet, ktory chceme vloziť
     */
    public void addKniha(Kniha kniha, int pocet){
        knihy.add(new BalikKnih(kniha,pocet));
    }

    /**
     * odstrani knihu na vrchu zoznamu
     * @return odstranená kniha
     */
    public BalikKnih popKniha(){
        return knihy.remove();
    }

    /**
     * @return velkost radu
     */
    public int getSize(){
        return knihy.size();
    }


    /**
     * Vlozi celý rad do listu
     * @return vracia vygenerovany list
     */
    public ArrayList<BalikKnih> getKnihy(){
        ArrayList<BalikKnih> result = new ArrayList<>();
        for(BalikKnih k : knihy){
            result.add(k);
        }
        return result;
    }


    /**
     * Vlozi informacie o pocte knih v balikoch do LinkedList-u
     * @return vracia vygenerovany list
     */
    public LinkedList<Integer> getPocty(){
        LinkedList<Integer> result = new LinkedList<>();
        for(BalikKnih k : knihy){
            result.add(k.getPocet());
        }
        return result;
    }

    /**
     * @return ci je rad prazdny alebo nie
     */
    public boolean isEmpty(){
        return knihy.isEmpty();
    }
}
