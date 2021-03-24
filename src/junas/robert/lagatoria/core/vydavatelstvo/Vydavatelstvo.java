package junas.robert.lagatoria.core.vydavatelstvo;

import junas.robert.lagatoria.core.items.*;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.Stanok;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.exceptions.AutorExistujeException;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;
import junas.robert.lagatoria.gui.Controller;

import java.util.*;

public class Vydavatelstvo {

    interface VydavanieStrategy{
        void vydajKnihy();
    }

    class Korektor extends Zamestnanec {
        public Korektor(String m, long id, double plat) {
            super(m, id, plat);
        }

        private void najdiChybyVTexte(Text text){
            Controller.printline("Najedenych a opravenych " + text.oprav() + " chyb");
        }

        private Text skratText(Text text){
            text.setDlzka(text.getDlzka() - (int)(text.getDlzka()*0.1));
            return text;
        }

        public Text precitajText(Text text) {
            najdiChybyVTexte(text);
            return skratText(text);
        }
    }

    class Dizajner extends Zamestnanec {
        public Dizajner(String m, long id, double plat) {
            super(m, id, plat);
        }

        public Obalka navrhniObalku() {
            if((int)(Math.random()*2) == 0) {
                return new BrozovanaVazba("nejaky", "biela", "papagaj");
            }else{
                return new PevnaVazba("nejaky","cervena", "drevo");
            }
        }
    }


    private Korektor korektor;
    private Dizajner dizajner;
    private Manazer manazer;
    private Distributor distributor;

    private Tlaciaren tlaciaren;
    private ArrayList<Autor> autori = new ArrayList<Autor>();
    private ArrayList<Odoberatel> odoberatelia = new ArrayList<>();

    private Queue<Text> prijateTexty = new LinkedList<>();
    private HashMap<Integer, Integer> pouziteKody = new HashMap<Integer, Integer>();

    private String nazov;
    private String code;
    private String group;


    private VydavanieStrategy strategia;

    /**
     * Vytvori instanciu Vydavatela Dalakan a zaroven sa prida instancia vydavatelovho knihkupectva
     * @param manazer manazer vydavatelstva je pridavany z vonku
     * @param pocetStankov incializacn pocet Odoberatlov typu Stanok
     */
    public Vydavatelstvo(Manazer manazer, int pocetStankov){
        nazov = "Dalakan";
        code = "3315";
        group = "80";
        this.manazer = manazer;
        korektor = new Korektor("Jozo",332,10.9);
        dizajner = new Dizajner("Fero", 333, 20);
        distributor = new Distributor("Peter",334,30);

        tlaciaren = new Tlaciaren(this);

        autori.add(new HistoryAutor("Jozef", "Kuku",this));
        autori.add(new FantasyAutor("Adam", "Kukuc",this));
        autori.add(new PoetryAutor("Peter", "Nagy",this));

        for(int i = 0; i < pocetStankov;i++){
            odoberatelia.add(new Stanok("stanok"+i));
        }
        odoberatelia.add(Knihkupectvo.getInstance());
        manazer.pridajAutora(autori);

        strategia = this::vydanie;
    }

    /**
     * Funkcia urci strategiu akou sa budu knihy vydavat (Bud vsetky naraz alebo iba jedna)
     * Strategia ma dopad na pocet vytlackov aj cenu knihy
     * pri tlaceni vsetkych sa zhorsuje kvalita teda aj pocet vytlackov sa zhorsuje
     * @param vydajVsetko ak je parameter true tak sa vydaju vsetky knihy v rade, inak sa vydava po jednej
     */
    public void typVydavania(boolean vydajVsetko){
        if(vydajVsetko){
            strategia = () -> {
                ArrayList<Kniha> vytlaceneKnihy = new ArrayList<>();
                ArrayList<Integer> pocetVytlackov = new ArrayList<>();
                if(prijateTexty.isEmpty()){
                    Controller.printline("ziadna kniha na vydanie");
                    return;
                }
                while(!prijateTexty.isEmpty()) {
                    Text vydavana = prijateTexty.remove();

                    Controller.printline("Vydava sa kniha: " + vydavana.getNazov() +" ["+ vydavana.getAutor() + "]");

                    Obalka obalka = dizajner.navrhniObalku();
                    vydavana = korektor.precitajText(vydavana);

                    double feedback = manazer.ziskajFeedback(vydavana);

                    Controller.printline("recenzia textu: " + String.format("%.2f",feedback) + "%");

                    int pocet = distributor.urciPocet(feedback);

                    pocetVytlackov.add(pocet);

                    Controller.printline("Naplanovany pocet vytlackov: " + pocet);

                    int titleCode = ((vydavana.getNazov() + vydavana.getAutor()).hashCode()%10000) ;
                    int i = 0;
                    if(pouziteKody.containsKey(titleCode)){
                        i = pouziteKody.get(titleCode)+1;
                        pouziteKody.replace(titleCode,i);
                    }else{
                        pouziteKody.put(titleCode, 0);
                    }
                    String isbn = "ISBN-977-"+group+"-"+ String.format("%04d",titleCode) + "-" + i;
                    double cena = manazer.navrhniCenu(feedback);

                    Controller.printline("cena knihy: " + cena);


                    Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);
                    kniha.getInfo();
                    vytlaceneKnihy.add(kniha);
                }
                distributor.DajOdoberatlom(odoberatelia,vytlaceneKnihy,pocetVytlackov);
            };
        }else{
            strategia = this::vydanie;
        }
    }

    /**
     * Prida text do radu, z ktoreho sa neskor kniha vyda
     * @param text text na pridanie do radu prijatychtextov
     */
    public synchronized void prijmiText(Text text){
        prijateTexty.add(text);
    }

    /**
     * Snazi sa pridat vsetkych autorov vo vydavatelstve manazerovi
     * Osetruje sa vynimka ked autor uz daneho autora ma
     */
    public void dajAutorovManazerovi(){
        for (Autor autor: autori) {
            try {
                manazer.pridajAutora(autor);
            } catch (AutorExistujeException e) {
                Controller.printline(e.getMessage());
            }
        }
    }

    /**
     * Prida autora do zoznamu vsetkych autorov
     * @param autor pridavany autor
     */
    public void prijmiAutora(Autor autor){
        autori.add(autor);
    }

    private void vydanie(){
        if(prijateTexty.isEmpty()){
            Controller.printline("ziadna kniha na vydanie");
            return;
        }
        Text vydavana = prijateTexty.remove();

        Controller.printline("Vydava sa kniha: " + vydavana.getNazov() +" ["+ vydavana.getAutor() + "]");

        Obalka obalka = dizajner.navrhniObalku();
        vydavana = korektor.precitajText(vydavana);

        double feedback = manazer.ziskajFeedback(vydavana);

        Controller.printline("recenzia textu: " + String.format("%.2f",feedback) + "%");

        int pocet = distributor.urciPocet(feedback);

        Controller.printline("Naplanovany pocet vytlackov: " + pocet);

        int titleCode = ((vydavana.getNazov() + vydavana.getAutor()).hashCode()%10000) ;
        int i = 0;
        if(pouziteKody.containsKey(titleCode)){
            i = pouziteKody.get(titleCode)+1;
            pouziteKody.replace(titleCode,i);
        }else{
            pouziteKody.put(titleCode, 0);
        }
        String isbn = "ISBN-977-"+group+"-"+ String.format("%04d",titleCode) + "-" + i;
        double cena = manazer.navrhniCenu(feedback);

        Controller.printline("cena knihy: " + cena);


        Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);

        kniha.getInfo();

        distributor.DajOdoberatlom(odoberatelia, kniha, pocet);
    }

    /**
     * Vyda text ktory sa nachadza na vrchu radu, do ktoreho sa text dostane cez funkciu prijmiText(Text text)
     */
    public void vydajKnihy(){
        strategia.vydajKnihy();
    }


    public String getNazov() {
        return nazov;
    }

    /**
     * Vypise vsetkych autorov
     * ✓ = je na zozname autorov schopnych pisat
     * x = autor nie je schopny pisat / nie je nahlaseny u manazera
     */
    public void vypisAutorov() {
        int i = 0;
        for(Autor a: autori){
            Controller.printline(i +": "+a.getMeno() +" "+a.getPrievzisko() + " [" + a.getClass().getSimpleName()+
                    "] Je v zozname: " + ((manazer.existujeAutor(a)) ? "✓": "x"));
            i++;
        }
        Controller.printline("");
    }

    public Manazer getManazer() {
        return manazer;
    }

    public Autor getAutor(int index) {return autori.get(index);};


    /**
     * Vypise este nevydane texty cakajuce na vydanie
     */
    public void vypisTexty(){
        if(prijateTexty.isEmpty()){
            Controller.printline("Ziadne texty na vydanie");
            return;
        }

        int i = 1;
        Iterator<Text> it = prijateTexty.iterator();
        while(it.hasNext()){
            Text current = it.next();
            if(current !=null)
            Controller.printline(i +": " + current.getAutor() + ": " +current.getNazov());
            i++;
        }
    }


    /**
     * Odobertal bude prijmat knihy od vydavatela
     * @param odoberatel pridavany odoberatel
     */
    public void pridajOdoberatela(Odoberatel odoberatel){
        odoberatelia.add(odoberatel);
    }

    /**
     * Odstrani odoberatela zo zoznamu odoberatelov knih (ak sa na indexe nachadza knihkupectvo tak to sa nevymaze)
     * @param index index na ktorom sa nachaza odstranovany odoberatel
     */
    public void odoberOdoberatela(int index) {
        if(odoberatelia.get(index) instanceof Knihkupectvo){
            Controller.printline("Knihkupectvo sa neda odstranit zo zoznamu");
            return;
        }
        if(index >= odoberatelia.size()) {
            Controller.printline("neplatny index");
            return;
        }
        odoberatelia.remove(index);
    }

    public void vypisOdoberatelov() {
        int index = 0;
        for (Odoberatel o: odoberatelia) {
            Controller.printline(index+": "+o.getClass().getSimpleName() + ": " + ((o instanceof Knihkupectvo) ? ("Minerva") : ((Stanok)o).getNazov()));
            index++;
        }
    }

    public Distributor getDistibutor() {
        return distributor;
    }
}
