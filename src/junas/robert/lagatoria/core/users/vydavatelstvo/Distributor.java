package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.stanky.Stanok;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.stanky.StanokPreKategoriu;
import junas.robert.lagatoria.core.stanky.StanokSMinimom;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.users.info.RadKnih;
import junas.robert.lagatoria.core.utils.enums.Kategoria;

import java.util.ArrayList;
import java.util.HashMap;

public class Distributor extends Zamestnanec {

    private RadKnih knihyPripraveneNaOdoslanie;


    public Distributor(String m, long id, double plat) {
        super(m, id, plat);
        knihyPripraveneNaOdoslanie = new RadKnih();

        inlineAkcie.put("pridajOdoberatela", ((args, kh, vy) -> {vy.pridajOdoberatela(new Stanok(args[1]));return "";}));
        inlineAkcie.put("pridajOdoberatelaKat", ((args, kh, vy) -> {vy.pridajOdoberatela(new StanokPreKategoriu(args[1], Kategoria.valueOf(args[2])));return "";}));
        inlineAkcie.put("pridajOdoberatelaMin", ((args, kh, vy) -> {vy.pridajOdoberatela(new StanokSMinimom(args[1], Integer.parseInt(args[2])));return "";}));
        inlineAkcie.put("vypisOdoberatelov", ((args, kh, vy) -> {return vy.vypisOdoberatelov();}));
        inlineAkcie.put("odstranOdoberatela", ((args, kh, vy) -> {return vy.odoberOdoberatela(Integer.valueOf(args[1]));}));
        inlineAkcie.put("knihyPreKnihkupectvo", ((args, kh, vy) -> {return knihyPripravenePreKnihkupetvo();}));
    }

    private String knihyPripravenePreKnihkupetvo() {
        String res = "";
        if(knihyPripraveneNaOdoslanie.isEmpty())
            return "nie su nachystane ziadne knihy";
        for(BalikKnih k : knihyPripraveneNaOdoslanie.getKnihy()){
            res += k.getInfo() + "\n";
        }
        return res;
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

    public String DajOdoberatlom(ArrayList<Odoberatel> odoberatelia, Kniha kniha, int pocet) {
        HashMap<Odoberatel, Double> pomer = pomerKnih(odoberatelia);
        double kapital = 0;
        int nepredane = pocet;
        String res ="";
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                knihyPripraveneNaOdoslanie.addKniha(kniha,(int)(pocet*pomer.get(o)));
                nepredane -= (int)(pocet*pomer.get(o));
                if(knihyPripraveneNaOdoslanie.getSize() > 4 && Knihkupectvo.getInstance().prijmameTovar()){
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie));
                    double zaplatene = 0;
                    int pocetKnih = 0;
                    int celkovyPocet =0;
                    while(!knihyPripraveneNaOdoslanie.isEmpty()){
                        BalikKnih k = knihyPripraveneNaOdoslanie.popKniha();
                        int p = k.getPocet();
                        celkovyPocet += p;
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi(k.getKniha(),p);
                        pocetKnih++;
                    }
                    res+= "Knihkupectvo prijalo "+pocetKnih+" roznych knih ["+celkovyPocet+"] vsetky knihy a zaplatilo: " + String.format("%.2f",zaplatene)+ "€\n";
                    kapital += zaplatene;
                }
            }else{
                Stanok stanok = (Stanok) o;
                if(stanok.odober(kniha,(int)(pocet*pomer.get(o))) == 1) {
                    nepredane -= (int) (pocet * pomer.get(o));
                    double zaplatene = stanok.zaplatVydavatelovi(kniha, (int) (pocet * pomer.get(o)));
                    res += "Stanok "+ stanok.getNazov() + " prijal knihy [" + (int) (pocet * pomer.get(o)) + "] a zaplatil: "
                            + String.format("%.2f", zaplatene) + "\n";
                    kapital += zaplatene;
                }else{
                    res+= "Stanok " +stanok.getNazov()+ " neprijal knihy\n";
                }
            }
        }
        res += "\nCelkovo zarobene: "+ String.format("%.2f",kapital) + "€\n";
        res += "Nepredalo sa: "+ nepredane + " kusov\n";

        return res;
    }

    public String DajOdoberatlom(ArrayList<Odoberatel> odoberatelia, ArrayList<Kniha> knihy, ArrayList<Integer> pocet) {
        String res ="";
        HashMap<Odoberatel, Double> pomer = pomerKnih(odoberatelia);
        double kapital = 0;
        int nepredane = 0;
        for(Integer p : pocet){
            nepredane += p;
        }
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                for(int i = 0; i < knihy.size();i++) {
                    knihyPripraveneNaOdoslanie.addKniha(knihy.get(i),(int)(pocet.get(i)*pomer.get(o)));
                    nepredane -= (int)(pocet.get(i)*pomer.get(o));
                }
                if (knihyPripraveneNaOdoslanie.getSize() > 4 && Knihkupectvo.getInstance().prijmameTovar()) {
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie));
                    double zaplatene = 0;
                    int pocetKnih = 0;
                    int celkovyPocet = 0;
                    while (!knihyPripraveneNaOdoslanie.isEmpty()) {
                        BalikKnih k = knihyPripraveneNaOdoslanie.popKniha();
                        int p = k.getPocet();
                        celkovyPocet += p;
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi(k.getKniha(), p);
                        pocetKnih++;
                    }
                    res += "Knihkupectvo prijalo " + pocetKnih + " roznych knih [" + celkovyPocet + "] vsetky knihy a zaplatilo: " + String.format("%.2f", zaplatene) + "€\n";
                    kapital += zaplatene;
                    }
            }else{
                Stanok stanok = (Stanok) o;
                double zaplatene = 0;
                int pocetPredanych = 0;
                int pocetNeprijatych = 0;
                for(int i = 0; i < knihy.size();i++) {
                    if(stanok.odober(knihy.get(i), (int)(pocet.get(i)*pomer.get(o))) == 1) {
                        pocetPredanych += (int) (pocet.get(i) * pomer.get(o));
                        zaplatene += stanok.zaplatVydavatelovi(knihy.get(i), (int) (pocet.get(i) * pomer.get(o)));
                        nepredane -= (int) (pocet.get(i) * pomer.get(o));
                    }else{
                        pocetNeprijatych++;
                    }
                }
                res+= "Stanok "+ stanok.getNazov() + " prijal knihy ["+pocetPredanych+"]a zaplatil: " +String.format("%.2f",zaplatene)+ "€\n";
                res+= "\t a neprijal "+pocetNeprijatych +"\n";
                kapital += zaplatene;
            }
        }
        res+= "Celkovo zarobene: "+ String.format("%.2f",kapital) + "€\n";
        res+= "Nepredalo sa: "+ nepredane + " kusov\n";
        return res;
    }


}
