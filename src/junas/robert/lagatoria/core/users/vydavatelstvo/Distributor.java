package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.Stanok;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.users.Zamestnanec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Distributor extends Zamestnanec {
    private Queue<Kniha> knihyPripraveneNaOdoslanie;
    private Queue<Integer> poctyKnih;

    public static Object object = new Object();

    public Distributor(String m, long id, double plat) {
        super(m, id, plat);
        knihyPripraveneNaOdoslanie = new LinkedList<>();
        poctyKnih = new LinkedList<>();
    }

    public int urciPocet(double feedback) {
        return (int)(Math.random()*10*feedback) + 300;
    }

    private HashMap<Odoberatel,Integer> pomerKnih(ArrayList<Odoberatel> odoberatelia, int pocet){
        HashMap<Odoberatel,Integer> pomery = new HashMap<Odoberatel,Integer>();
        int final_pocet = pocet;
        Odoberatel kh = null;
        for(Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                int p = (int) (pocet*0.5);
                pomery.put(o, p);
                final_pocet -= p;
                kh = o;
            }else{
                double pomerPreStanky = 0.5/(odoberatelia.size()-1);
                int p = (int)(pocet*pomerPreStanky);
                pomery.put(o,p);
                final_pocet -= p;
            }
        }
        if(final_pocet > 0){
            pomery.replace(kh,pomery.get(kh)+final_pocet);
        }
        return pomery;
    }

    public void DajOdoberatlom(ArrayList<Odoberatel> odoberatelia, Kniha kniha, int pocet) {
        HashMap<Odoberatel, Integer> pomer = pomerKnih(odoberatelia,pocet);
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                knihyPripraveneNaOdoslanie.add(kniha);
                poctyKnih.add(pomer.get(o));
                if(Knihkupectvo.getInstance().prijmameTovar()){
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie,new LinkedList<>(poctyKnih)));
                    double zaplatene = 0;
                    while(!knihyPripraveneNaOdoslanie.isEmpty()){
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi(knihyPripraveneNaOdoslanie.remove(),poctyKnih.remove());
                    }
                }
            }else{
                Stanok stanok = (Stanok) o;
                stanok.odober(kniha,pomer.get(o));
                stanok.zaplatVydavatelovi(kniha,pomer.get(o));
            }
        }
    }
}
