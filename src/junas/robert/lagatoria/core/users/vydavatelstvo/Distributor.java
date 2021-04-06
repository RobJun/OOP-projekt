package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.Stanok;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.gui.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Distributor extends Zamestnanec {
    private Queue<Kniha> knihyPripraveneNaOdoslanie;
    private Queue<Integer> poctyKnih;


    public Distributor(String m, long id, double plat) {
        super(m, id, plat);
        knihyPripraveneNaOdoslanie = new LinkedList<>();
        poctyKnih = new LinkedList<>();

        inlineAkcie.put("pridajOdoberatela", ((args, kh, vy) -> vy.pridajOdoberatela(new Stanok(args[1]))));
        inlineAkcie.put("vypisOdoberatelov", ((args, kh, vy) -> vy.vypisOdoberatelov()));
        inlineAkcie.put("odstranOdoberatela", ((args, kh, vy) -> vy.odoberOdoberatela(Integer.valueOf(args[1]))));
    }

    public int urciPocet(double feedback) {
        return (int)(Math.random()*10*feedback) + 300;
    }

    private HashMap<Odoberatel,Double> pomerKnih(ArrayList<Odoberatel> odoberatelia){
        HashMap<Odoberatel,Double> pomery = new HashMap<Odoberatel,Double>();
        Odoberatel kh = null;
        for(Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                pomery.put(o, 0.5);
            }else{
                double pomerPreStanky = 0.5/(odoberatelia.size()-1);
                pomery.put(o,pomerPreStanky);
            }
        }
        return pomery;
    }

    public void DajOdoberatlom(ArrayList<Odoberatel> odoberatelia, Kniha kniha, int pocet) {
        HashMap<Odoberatel, Double> pomer = pomerKnih(odoberatelia);
        double kapital = 0;
        int nepredane = pocet;
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                knihyPripraveneNaOdoslanie.add(kniha);
                nepredane -= (int)(pocet*pomer.get(o));
                poctyKnih.add((int)(pocet*pomer.get(o)));
                if(knihyPripraveneNaOdoslanie.size() == 4 && Knihkupectvo.getInstance().prijmameTovar()){
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie,new LinkedList<>(poctyKnih)));
                    double zaplatene = 0;
                    int pocetKnih = 0;
                    int celkovyPocet =0;
                    while(!knihyPripraveneNaOdoslanie.isEmpty()){
                        int p = poctyKnih.remove();
                        celkovyPocet += p;
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi(knihyPripraveneNaOdoslanie.remove(),p);
                        pocetKnih++;
                    }
                    View.printline("Knihkupectvo prijalo "+pocetKnih+"knih ["+celkovyPocet+"] vsetky knihy a zaplatilo: " + String.format("%.2f",zaplatene)+ "€");
                    kapital += zaplatene;
                }
            }else{
                Stanok stanok = (Stanok) o;
                stanok.odober(kniha,(int)(pocet*pomer.get(o)));
                nepredane -= (int)(pocet*pomer.get(o));
                double zaplatene = stanok.zaplatVydavatelovi(kniha,(int)(pocet*pomer.get(o)));
                View.printline("Stanok prijal knihy ["+(int)(pocet*pomer.get(o))+"] a zaplatil: " +String.format("%.2f",zaplatene)+ "€");
                kapital += zaplatene;
            }
        }
        View.printline("Celkovo zarobene: "+ String.format("%.2f",kapital) + "€");
        View.printline("Nepredalo sa: "+ nepredane + " kusov");
    }

    public void DajOdoberatlom(ArrayList<Odoberatel> odoberatelia, ArrayList<Kniha> knihy, ArrayList<Integer> pocet) {
        HashMap<Odoberatel, Double> pomer = pomerKnih(odoberatelia);
        double kapital = 0;
        int nepredane = 0;
        for(Integer p : pocet){
            nepredane += p;
        }
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                for(int i = 0; i < knihy.size();i++) {
                    knihyPripraveneNaOdoslanie.add(knihy.get(i));
                    poctyKnih.add((int)(pocet.get(i)*pomer.get(o)));
                    nepredane -= (int)(pocet.get(i)*pomer.get(o));
                }
                if (knihyPripraveneNaOdoslanie.size() > 4 && Knihkupectvo.getInstance().prijmameTovar()) {
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie, new LinkedList<>(poctyKnih)));
                    double zaplatene = 0;
                    int pocetKnih = 0;
                    int celkovyPocet = 0;
                    while (!knihyPripraveneNaOdoslanie.isEmpty()) {
                        int p = poctyKnih.remove();
                        celkovyPocet += p;
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi(knihyPripraveneNaOdoslanie.remove(), p);
                        pocetKnih++;
                    }
                    View.printline("Knihkupectvo prijalo " + pocetKnih + "knih [" + celkovyPocet + "] vsetky knihy a zaplatilo: " + String.format("%.2f", zaplatene) + "€");
                    kapital += zaplatene;
                    }
            }else{
                Stanok stanok = (Stanok) o;
                double zaplatene = 0;
                int pocetPredanych = 0;
                for(int i = 0; i < knihy.size();i++) {
                    pocetPredanych+=(int)(pocet.get(i)*pomer.get(o));
                    stanok.odober(knihy.get(i), (int)(pocet.get(i)*pomer.get(o)));
                    zaplatene += stanok.zaplatVydavatelovi(knihy.get(i), (int)(pocet.get(i)*pomer.get(o)));
                    nepredane -= (int)(pocet.get(i)*pomer.get(o));
                }
                View.printline("Stanok prijal knihy ["+pocetPredanych+"]a zaplatil: " +String.format("%.2f",zaplatene)+ "€");
                kapital += zaplatene;
            }
        }
        View.printline("Celkovo zarobene: "+ String.format("%.2f",kapital) + "€");
        View.printline("Nepredalo sa: "+ nepredane + " kusov");
    }


}
