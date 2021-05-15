package junas.robert.lagatoria.core.vydavatelstvo;

import junas.robert.lagatoria.core.odoberatelia.stanky.StanokPreKategoriu;
import junas.robert.lagatoria.core.items.*;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.odoberatelia.Odoberatel;
import junas.robert.lagatoria.core.odoberatelia.stanky.Stanok;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.utils.exceptions.AutorExistujeException;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;
import junas.robert.lagatoria.core.Model;

import java.util.*;

public class Vydavatelstvo implements Observer {

    interface VydavanieStrategy{
        String vydajKnihy();
    }

    /**
     * Inner class Korektor služi na opravenie chýb v texte
     */
    class Korektor extends Zamestnanec {
        private int efektivnost = 31;
        public Korektor(String m, long id, double plat) {
            super(m, id, plat);
        }

        /**
         * @param text opravovany text
         * @return retazec informujuci o opravenych chybach v texte
         */
        public String najdiChybyVTexte(Text text){
            pridajHodinu();
            return "Najedenych a opravenych " + text.oprav() + efektivnost + " chyb" + "\n";
        }

        /**
         * @param text skracovany text
         * @return text s novym poctom stran
         */
        private Text skratText(Text text) {
            text.setDlzka(text.getDlzka() - ((int) (text.getDlzka() * 0.1)+efektivnost));
            return text;
        }

        /**
         * @param text text ktory ma koreketor precitat
         * @return precitany Text
         */
        public Text precitajText(Text text) {
            pridajHodinu();
            return skratText(text);
        }
    }

    /**
     * inner class dizajner, ma na starosti vymyslenie obalky
     */
    class Dizajner extends Zamestnanec {
        private String[] osobnyStyl = {"minimalisticky","fotka","kresba"};
        private String[] obrazky = {"papagaj","dievca","carodejnik","lampa"};
        private String[] farby = {"červena", "biela", "hneda", "zlto-biela", "zlta", "cierna"};
        private String[] material = {"koza","karton"};
        public Dizajner(String m, long id, double plat) {
            super(m, id, plat);
        }

        /**
         * Navrhnutie obalky
         * @return vracia obalku, ktoru vymyslel
         */
        public Obalka navrhniObalku() {
            pridajHodinu(); //prida odpracovanu hodinu dizajnerovy

            //rozhodnutie ci vazba bude Brozovana alebo Pevna
            if((int)(Math.random()*2) == 0) {
                //nahodny vyber vlastnosti obalky
                return new BrozovanaVazba(osobnyStyl[(int)(Math.random()*osobnyStyl.length)],
                            farby[(int)(Math.random()*farby.length)],
                            obrazky[(int)(Math.random()*obrazky.length)]);
            }else{
                //nahodny vyber vlastnosti obalky
                return new PevnaVazba(osobnyStyl[(int)(Math.random()*osobnyStyl.length)],
                        farby[(int)(Math.random()*farby.length)], material[(int)(Math.random()*material.length)]);
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

    private String nazov;
    private String group;
    private String code;


    private VydavanieStrategy strategia;

    private Model model;

    /**
     * Vytvori instanciu Vydavatela Dalakan a zaroven sa prida instancia vydavatelovho knihkupectva
     * @param manazer manazer vydavatelstva je pridavany z vonku
     * @param distributor distributor, ktory pracuje vo vydavatelstve
     * @param pocetStankov incializacn pocet Odoberatlov typu Stanok
     * @param parent sledovatel Vydavatelstva
     */
    public Vydavatelstvo(Manazer manazer, Distributor distributor, int pocetStankov, Model parent){
        nazov = "Dalakan";
        group = "80";
        code = "499";
        this.manazer = manazer;
        manazer.setObserver(this);
        korektor = new Korektor("Jozo",332,10.9);
        dizajner = new Dizajner("Fero", 333, 20);
        this.distributor = distributor;

        model = parent;

        tlaciaren = new Tlaciaren(this);

        autori.add(new HistoryAutor("Jozef", "Kuku",this));
        autori.add(new FantasyAutor("Adam", "Kukuc",this));
        autori.add(new PoetryAutor("Peter", "Nagy",this));

        for(int i = 0; i < pocetStankov;i++){
            pridajOdoberatela(new Stanok("stanok"+i));
        }

        pridajOdoberatela(new StanokPreKategoriu("StanokSHistorickymiKnihami", Kategoria.HISTORIA));

        pridajOdoberatela(Knihkupectvo.getInstance());
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
                //pokial je este nevydany text
                while(!prijateTexty.isEmpty()) {
                    //vyberie sa text
                    Text vydavana = prijateTexty.remove();

                    res += "Vydava sa kniha: " + vydavana.getNazov() +" ["+ vydavana.getAutor() + "]\n";

                    Obalka obalka = dizajner.navrhniObalku();
                    vydavana = korektor.precitajText(vydavana);

                    //manazer dostane recenziu textu
                    double feedback = manazer.ziskajFeedback(vydavana);

                    res += "recenzia textu: " + String.format("%.2f",feedback) + "%\n";

                    //z coho distributor urci pocet
                    int pocet = distributor.urciPocet(feedback);

                    pocetVytlackov.add(pocet);

                    res +="Naplanovany pocet vytlackov: " + pocet + "\n";

                    //manazer navrhne cenu
                    double cena = manazer.navrhniCenu(feedback);

                    res += ("cena knihy: " + cena + "\n");

                    //vytvori sa ISBN kod
                    String isbn = tlaciaren.vytvorISBN(vydavana,code,group);

                    //vytlaci sa kniha
                    Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);
                    kniha.getInfo();
                    //kniha sa prida na zoznam knih pripravenych na odoslanie
                    vytlaceneKnihy.add(kniha);
                }
                //odosiela sa medzi odoberatelov
                res += distributor.dajOdoberatelom(odoberatelia,vytlaceneKnihy,pocetVytlackov);
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
        model.notify(this, "001::"+vypisTexty());
    }

    /**
     * Snazi sa pridat vsetkych autorov vo vydavatelstve manazerovi
     * Osetruje sa vynimka ked autor uz daneho autora ma
     * @return retazec informucjuci o uspechu akcie
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

    /**
     * vydanie prave jedneho textu, ktory je na vrchu radu.
     * @return vracia output string
     */
    private String vydanie(){
        //ak nie su ziadne texty ktore by sa dali vydat
        if(prijateTexty.isEmpty()){
            return "ziadna kniha na vydanie";
        }
        //vyberie sa text
        Text vydavana = prijateTexty.remove();

        String res = "Vydava sa kniha: " + vydavana.getNazov() +" ["+ vydavana.getAutor() + "]\n";

        Obalka obalka = dizajner.navrhniObalku(); // navrhne sa obalka
        res+= korektor.najdiChybyVTexte(vydavana); // korektor opravi text
        vydavana = korektor.precitajText(vydavana); // a skrati ho

        //manazer dostane recenziu textu
        double feedback = manazer.ziskajFeedback(vydavana);

        res += "\trecenzia textu: " + String.format("%.2f",feedback) + "%\n";

        //z coho distributor urci pocet
        int pocet = distributor.urciPocet(feedback);

        res += "Naplanovany pocet vytlackov: " + pocet + "\n";

        //manazer navrhne cenu
        double cena = manazer.navrhniCenu(feedback);

        res +="cena knihy: " + cena + '\n';

        String isbn = tlaciaren.vytvorISBN(vydavana, code,group);

        //vytlaci sa kniha
        Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);

        kniha.getInfo();

        //posiela sa odoberatelom
        res += distributor.dajOdoberatelom(odoberatelia, kniha, pocet);
        return res;
    }

    /**
     * Vyda text ktory sa nachadza na vrchu radu, do ktoreho sa text dostane cez funkciu prijmiText(Text text)
     * @return vysledok vydavania, resp. kolko aky stanok prijal a aká kniha bola vydaná a v akom mnozstve + zarobok
     */
    public String vydajKnihy(){
        String result = strategia.vydajKnihy();
        model.notify(this, "001::"+vypisTexty());
        return result;
    }


    public String getNazov() {
        return nazov;
    }

    /**
     * Vypise vsetkych autorov
     * ✓ = je na zozname autorov schopnych pisat
     * x = autor nie je schopny pisat / nie je nahlaseny u manazera
     * @return retazec obsahujuci informacie o autoroch
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
     * @return retazec obsahujuci informacie o textoch
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
                res += i + "|";
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
        vypisOdoberatelov();
    }

    /**
     * Odstrani odoberatela zo zoznamu odoberatelov knih (ak sa na indexe nachadza knihkupectvo tak to sa nevymaze)
     * @param index index na ktorom sa nachaza odstranovany odoberatel
     * @return retazec informucjuci o uspechu akcie
     */
    public String odoberOdoberatela(int index) {
        if(odoberatelia.get(index) instanceof Knihkupectvo){
            return "Knihkupectvo sa neda odstranit zo zoznamu";
        }
        if(index >= odoberatelia.size()) {
            return "neplatny index";
        }
        odoberatelia.remove(index);
        vypisOdoberatelov();
        return "Podarilo sa odstranit odoberatela";
    }

    /**
     * @return vrati retazec obsahujuci informacie o odoberateloch
     */
    public String vypisOdoberatelov() {
        int index = 0;
        String res = "";
        for (Odoberatel o: odoberatelia) {
            res += index+": "+o.getClass().getSimpleName() + ": " + ((o instanceof Knihkupectvo) ? ("Minerva") : ((Stanok)o).getNazov()) + "\n";
            index++;
        }
        model.notify(this,"002::"+res);
        return res;
    }

    /**
     * @param object instancia, ktora vyvolala upovedomovanie
     * @param msg    sprava ktoru rozposiela
     */
    public void notify(Object object, Object msg) {
        //ak prisiel text tak ho prijme
        if(msg instanceof Text){
            prijmiText((Text) msg);
            return;
        }
        //prebublava spravu na povrch
        model.notify(object, msg);
    }

}
