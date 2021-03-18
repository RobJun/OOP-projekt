package junas.robert.lagatoria.core.vydavatelstvo;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.Stanok;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.items.Obalka;
import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Dizajner;
import junas.robert.lagatoria.core.users.vydavatelstvo.Korektor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.AutorExistujeException;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;
import junas.robert.lagatoria.gui.Controller;

import java.util.*;

public class Vydavatelstvo {
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
            odoberatelia.add(new Stanok());
        }
        odoberatelia.add(Knihkupectvo.getInstance());
        manazer.pridajAutora(autori);
    }

    public void prijmiText(Text text){
        prijateTexty.add(text);
    }

    public void dajAutorovManazerovi(){
        for (Autor autor: autori) {
            try {
                manazer.pridajAutora(autor);
            } catch (AutorExistujeException e) {
                Controller.printline(e.getMessage());
            }
        }
    }

    public void prijmiAutora(Autor autor){
        autori.add(autor);
    }

    public void vydajKnihy(){
        Text vydavana = prijateTexty.remove();

        Obalka obalka = dizajner.navrhniObalku();
        vydavana = korektor.precitajText(vydavana);
        double feedback = manazer.ziskajFeedback(vydavana);
        int pocet = distributor.urciPocet(feedback);
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

        Kniha kniha = tlaciaren.vytlacKnihy(vydavana,obalka,pocet, isbn, cena);

        distributor.DajOdoberatlom(odoberatelia, kniha, pocet);
    }


    public String getNazov() {
        return nazov;
    }

    public void vypisAutorov() {
        for(Autor a: autori){
            Controller.printline(a.getMeno() +" "+a.getPrievzisko() + " [" + a.getClass().getSimpleName()+
                    "] Je v zozname: " + ((manazer.existujeAutor(a)) ? "✓": "x"));
        }
        Controller.printline("");
    }

    public Manazer getManazer() {
        return manazer;
    }

    public void vypisTexty(){
        if(prijateTexty.isEmpty()){
            Controller.printline("Ziadne texty na vydanie");
            return;
        }

        Iterator<Text> it = prijateTexty.iterator();
        while(it.hasNext()){
            Text current = it.next();
            if(current !=null)
            Controller.printline(current.getAutor() + ": " +current.getNazov());
        }
    }
}
