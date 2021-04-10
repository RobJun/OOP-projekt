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

import java.util.*;

public class Vydavatelstvo {

    private Korektor korektor;
    private Dizajner dizajner;
    private Manazer manazer;
    private Distributor distributor;


    interface VydavanieStrategy{
        String vydajKnihy();
    }

    class Korektor extends Zamestnanec {
        private int efektivnost = 31;
        public Korektor(String m, long id, double plat) {
            super(m, id, plat);
        }

        public String najdiChybyVTexte(Text text){
            pridajHodinu();
            return "Najedenych a opravenych " + text.oprav() + efektivnost + " chyb" + "\n";
        }

        private Text skratText(Text text) {
            pridajHodinu();
            text.setDlzka(text.getDlzka() - ((int) (text.getDlzka() * 0.1)+efektivnost));
            return text;
        }
        public Text precitajText(Text text) {
            pridajHodinu();
            return skratText(text);
        }
    }

    class Dizajner extends Zamestnanec {
        private String[] osobnyStyl = {"minimalisticky","fotka","kresba"};
        private String[] obrazky = {"papagaj","dievca","carodejnik","lampa"};
        private String[] farby = {"červena", "biela", "hneda", "zlto-biela", "zlta", "cierna"};
        private String[] material = {"koza","karton"};
        public Dizajner(String m, long id, double plat) {
            super(m, id, plat);
        }

        public Obalka navrhniObalku() {
            pridajHodinu();
            if((int)(Math.random()*2) == 0) {
                return new BrozovanaVazba(osobnyStyl[(int)(Math.random()*osobnyStyl.length)],
                            farby[(int)(Math.random()*farby.length)],
                            obrazky[(int)(Math.random()*obrazky.length)]);
            }else{
                return new PevnaVazba(osobnyStyl[(int)(Math.random()*osobnyStyl.length)],
                        farby[(int)(Math.random()*farby.length)], material[(int)(Math.random()*material.length)]);
            }
        }
    }

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
     * @param distributor distributor, ktory pracuje vo vydavatelstve
     * @param pocetStankov incializacn pocet Odoberatlov typu Stanok
     */
    public Vydavatelstvo(Manazer manazer,Distributor distributor, int pocetStankov){
        nazov = "Dalakan";
        code = "3315";
        group = "80";
        this.manazer = manazer;
        korektor = new Korektor("Jozo",332,10.9);
        dizajner = new Dizajner("Fero", 333, 20);
        this.distributor = distributor;

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
                String res = "";
                ArrayList<Kniha> vytlaceneKnihy = new ArrayList<>();
                ArrayList<Integer> pocetVytlackov = new ArrayList<>();
                if(prijateTexty.isEmpty()){
                    res += "ziadna kniha na vydanie";
                    return res;
                }
                while(!prijateTexty.isEmpty()) {
                    Text vydavana = prijateTexty.remove();

                    res += "Vydava sa kniha: " + vydavana.getNazov() +" ["+ vydavana.getAutor() + "]\n";

                    Obalka obalka = dizajner.navrhniObalku();
                    vydavana = korektor.precitajText(vydavana);

                    double feedback = manazer.ziskajFeedback(vydavana);

                    res += "recenzia textu: " + String.format("%.2f",feedback) + "%\n";

                    int pocet = distributor.urciPocet(feedback);

                    pocetVytlackov.add(pocet);

                    res +="Naplanovany pocet vytlackov: " + pocet + "\n";

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

                    res += ("cena knihy: " + cena + "\n");


                    Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);
                    kniha.getInfo();
                    vytlaceneKnihy.add(kniha);
                }
                res += distributor.DajOdoberatlom(odoberatelia,vytlaceneKnihy,pocetVytlackov);
                return res;
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
    public String dajAutorovManazerovi(){
        String res = "";
        for (Autor autor: autori) {
            try {
                manazer.pridajAutora(autor);
            } catch (AutorExistujeException e) {
                res +=e.getMessage() + "\n";
            }
        }
        return  res;
    }

    /**
     * Prida autora do zoznamu vsetkych autorov
     * @param autor pridavany autor
     */
    public void prijmiAutora(Autor autor){
        autori.add(autor);
    }

    private String vydanie(){
        if(prijateTexty.isEmpty()){

            return "ziadna kniha na vydanie";
        }
        Text vydavana = prijateTexty.remove();

        String res = "Vydava sa kniha: " + vydavana.getNazov() +" ["+ vydavana.getAutor() + "]\n";

        Obalka obalka = dizajner.navrhniObalku();
        res+= korektor.najdiChybyVTexte(vydavana);
        vydavana = korektor.precitajText(vydavana);

        double feedback = manazer.ziskajFeedback(vydavana);

        res += "\trecenzia textu: " + String.format("%.2f",feedback) + "%\n";

        int pocet = distributor.urciPocet(feedback);

        res += "Naplanovany pocet vytlackov: " + pocet + "\n";

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

        res +="cena knihy: " + cena + '\n';


        Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);

        kniha.getInfo();

        res += distributor.DajOdoberatlom(odoberatelia, kniha, pocet);
        return res;
    }

    /**
     * Vyda text ktory sa nachadza na vrchu radu, do ktoreho sa text dostane cez funkciu prijmiText(Text text)
     * @return
     */
    public String vydajKnihy(){
        return strategia.vydajKnihy();
    }


    public String getNazov() {
        return nazov;
    }

    /**
     * Vypise vsetkych autorov
     * ✓ = je na zozname autorov schopnych pisat
     * x = autor nie je schopny pisat / nie je nahlaseny u manazera
     */
    public String vypisAutorov() {
        int i = 0;
        String res = "";
        for(Autor a: autori){
            res += i +": "+a.getMeno() +" "+a.getPrievzisko() + " [" + a.getClass().getSimpleName()+
                    "] Je v zozname: " + ((manazer.existujeAutor(a)) ? "✓": "x") + "\n";
            i++;
        }
        return res + "\n";
    }

    public Manazer getManazer() {
        return manazer;
    }

    public Autor getAutor(int index) {return autori.get(index);};


    /**
     * Vypise este nevydane texty cakajuce na vydanie
     */
    public String vypisTexty(){
        if(prijateTexty.isEmpty()){
            return "Ziadne texty na vydanie\n";
        }

        int i = 1;
        Iterator<Text> it = prijateTexty.iterator();
        String res = "";
        while(it.hasNext()){
            Text current = it.next();
            if(current !=null) {
                res += i + ": ";
                res += current.getInfo();
                i++;
            }
        }
        return res + "\n";
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
    public String odoberOdoberatela(int index) {
        if(odoberatelia.get(index) instanceof Knihkupectvo){
            return "Knihkupectvo sa neda odstranit zo zoznamu";
        }
        if(index >= odoberatelia.size()) {
            return "neplatny index";
        }
        odoberatelia.remove(index);
        return "Podarilo sa odstranit odoberatela";
    }

    public String vypisOdoberatelov() {
        int index = 0;
        String res = "";
        for (Odoberatel o: odoberatelia) {
            res += index+": "+o.getClass().getSimpleName() + ": " + ((o instanceof Knihkupectvo) ? ("Minerva") : ((Stanok)o).getNazov()) + "\n";
            index++;
        }

        return res;
    }

    public Distributor getDistibutor() {
        return distributor;
    }
}
