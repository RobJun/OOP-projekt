package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.odoberatelia.Odoberatel;
import junas.robert.lagatoria.core.odoberatelia.stanky.Stanok;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.odoberatelia.stanky.StanokPreKategoriu;
import junas.robert.lagatoria.core.odoberatelia.stanky.StanokSMinimom;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.items.BalikKnih;
import junas.robert.lagatoria.core.users.info.RadKnih;
import junas.robert.lagatoria.core.utils.enums.Kategoria;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ma na starostie odosielanie knih odoberatelom a pocet kolko vytlackov sa ma vytlacit
 */
public class Distributor extends Zamestnanec {

    /**
     * Knihy, ktore cakaju na poslanie knihkupectvu
     */
    private RadKnih knihyPripraveneNaOdoslanie;


    /**
     * Rozsiruje funkcie zamestnanca o funkcie distributora
     * @param meno meno zamestnanca
     * @param id identifikacne cislo
     * @param plat plat zamestnanca
     */
    public Distributor(String meno, long id, double plat) {
        super(meno, id, plat);
        knihyPripraveneNaOdoslanie = new RadKnih();

        inlineAkcie.put("pridajOdoberatela", ((args, kh, vy) -> {vy.pridajOdoberatela(new Stanok(args[1]));return "";}));
        inlineAkcie.put("pridajOdoberatelaKat", ((args, kh, vy) -> {vy.pridajOdoberatela(new StanokPreKategoriu(args[1], Kategoria.valueOf(args[2])));return "";}));
        inlineAkcie.put("pridajOdoberatelaMin", ((args, kh, vy) -> {vy.pridajOdoberatela(new StanokSMinimom(args[1], Integer.parseInt(args[2])));return "";}));
        inlineAkcie.put("vypisOdoberatelov", ((args, kh, vy) -> {return vy.vypisOdoberatelov();}));
        inlineAkcie.put("odstranOdoberatela", ((args, kh, vy) -> {return vy.odoberOdoberatela(Integer.valueOf(args[1]));}));
        inlineAkcie.put("knihyPreKnihkupectvo", ((args, kh, vy) -> {return knihyPripravenePreKnihkupetvo();}));
    }

    /**
     * @return retazec obsahujuci knihy pripravene na odoslanie
     */
    private String knihyPripravenePreKnihkupetvo() {
        String res = "";
        if(knihyPripraveneNaOdoslanie.isEmpty())
            return "nie su nachystane ziadne knihy";
        for(BalikKnih k : knihyPripraveneNaOdoslanie.getKnihy()){
            res += k.getInfo() + "\n";
        }
        return res;
    }

    /**
     * Urci pocet knih podla toho aky je o ne zaujem
     * @param feedback zaujem o knihu
     * @return pocet knih (minimum 300)
     */
    public int urciPocet(double feedback) {
        return (int)(Math.random()*10*feedback) + 300;
    }

    /**
     * @param odoberatelia zoznam odoberatelov dostavajucich knihy
     * @return mapu zodpovedajucu pomerom v akych odoberatelia dostanu knihy
     */
    private HashMap<Odoberatel,Double> pomerKnih(ArrayList<Odoberatel> odoberatelia){
        HashMap<Odoberatel,Double> pomery = new HashMap<Odoberatel,Double>();
        Odoberatel kh = null;
        for(Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){
                //Knihkupectvo ziska polovicu vsetkych vytlackov
                pomery.put(o, 0.5);
            }else{
                //zvysne sa rozdelia medzi stanky
                double pomerPreStanky = 0.5/(odoberatelia.size()-1);
                pomery.put(o,pomerPreStanky);
            }
        }
        return pomery;
    }

    /**
     * Rozposielanie jedinej knihy
     * @param odoberatelia zoznam odoberatelov
     * @param kniha rozposielana kniha
     * @param pocet pocet vytlackov
     * @return informacie a zisku a kolko aky odoberatel prijal a zaplatil
     */
    public String dajOdoberatelom(ArrayList<Odoberatel> odoberatelia, Kniha kniha, int pocet) {
        HashMap<Odoberatel, Double> pomer = pomerKnih(odoberatelia); //vypocit sa pomer v akom sa knihy budu distribuovat
        double kapital = 0;
        int nepredane = pocet;
        String res ="";
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){

                //kniha sa prida na zoznam cakajucich knih
                knihyPripraveneNaOdoslanie.addKniha(kniha,(int)(pocet*pomer.get(o)));
                nepredane -= (int)(pocet*pomer.get(o));
                //ak pocet kniha na zozname je vacsi ako 4 a knihkupectvo je schopne prijat novyTovar
                if(knihyPripraveneNaOdoslanie.getSize() > 4 && Knihkupectvo.getInstance().prijmameTovar()){
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie));
                    double zaplatene = 0;
                    int pocetKnih = 0;
                    int celkovyPocet =0;

                    //vypocet kolko knihkupectvo zaplati za dodani tovar
                    while(!knihyPripraveneNaOdoslanie.isEmpty()){
                        BalikKnih k = knihyPripraveneNaOdoslanie.popKniha();
                        int p = k.getPocet();
                        celkovyPocet += p;
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi((Kniha)k.getKniha(),p);
                        pocetKnih++;
                    }
                    res+= "Knihkupectvo prijalo "+pocetKnih+" roznych knih ["+celkovyPocet+"] vsetky knihy a zaplatilo: " + String.format("%.2f",zaplatene)+ "€\n";
                    kapital += zaplatene;
                }
            }
            else{
                Stanok stanok = (Stanok) o;
                //ak stanok prijal knihu do svojej predajne
                if(stanok.odober(kniha,(int)(pocet*pomer.get(o))) == 1) {
                    nepredane -= (int) (pocet * pomer.get(o));
                    double zaplatene = stanok.zaplatVydavatelovi(kniha, (int) (pocet * pomer.get(o)));
                    res += "Stanok "+ stanok.getNazov() + " prijal knihy [" + (int) (pocet * pomer.get(o)) + "] a zaplatil: "
                            + String.format("%.2f", zaplatene) + "\n";
                    kapital += zaplatene;
                }else{ //ak stanok neprijal
                    res+= "Stanok " +stanok.getNazov()+ " neprijal knihy\n";
                }
            }
        }
        res += "\nCelkovo zarobene: "+ String.format("%.2f",kapital) + "€\n";
        res += "Nepredalo sa: "+ nepredane + " kusov\n";

        return res;
    }

    /**
     * Rozposielanie vsetkych knih pripravenych na odoslanie
     * @param odoberatelia zoznam odoberatelov
     * @param knihy rozposielane knihy
     * @param pocet pocet kusov jednotlivych rozposielanych knih
     * @return informacie a zisku a kolko aky odoberatel prijal a zaplatil
     */
    public String dajOdoberatelom(ArrayList<Odoberatel> odoberatelia, ArrayList<Kniha> knihy, ArrayList<Integer> pocet) {
        String res ="";
        HashMap<Odoberatel, Double> pomer = pomerKnih(odoberatelia);//vypocit sa pomer v akom sa knihy budu distribuovat
        double kapital = 0;
        int nepredane = 0;
        for(Integer p : pocet){
            nepredane += p;
        }
        for (Odoberatel o : odoberatelia){
            if(o instanceof Knihkupectvo){

                //vsetky knihy sa pridaju na zoznam cakajucich
                for(int i = 0; i < knihy.size();i++) {
                    knihyPripraveneNaOdoslanie.addKniha(knihy.get(i),(int)(pocet.get(i)*pomer.get(o)));
                    nepredane -= (int)(pocet.get(i)*pomer.get(o));
                }

                //ak pocet kniha na zozname je vacsi ako 4 a knihkupectvo je schopne prijat novyTovar
                if (knihyPripraveneNaOdoslanie.getSize() > 4 && Knihkupectvo.getInstance().prijmameTovar()) {
                    Knihkupectvo.getInstance().getSklad().objednatKnihy(new NoveKnihy(knihyPripraveneNaOdoslanie));
                    double zaplatene = 0;
                    int pocetKnih = 0;
                    int celkovyPocet = 0;
                    //vypocet kolko knihkupectvo zaplati za dodani tovar
                    while (!knihyPripraveneNaOdoslanie.isEmpty()) {
                        BalikKnih k = knihyPripraveneNaOdoslanie.popKniha();
                        int p = k.getPocet();
                        celkovyPocet += p;
                        zaplatene += Knihkupectvo.getInstance().zaplatVydavatelovi((Kniha)k.getKniha(), p);
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
                //rozposielaju sa vsetky knihy
                for(int i = 0; i < knihy.size();i++) {
                    //ak stanok prijal knihu do svojej predajne
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
