package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;

import java.util.ArrayList;

public class Manazer extends Zamestnanec {
    ArrayList<Autor> autori = new ArrayList<Autor>();

    public Manazer(String m, long id, double plat) {
        super(m, id, plat);
    }

    public void pridajAutora(ArrayList<Autor> autori){
        this.autori = autori;
    }

    public void pridajAutora(Autor autor){
        this.autori.add(autor);
    }

    public void dajNapisatKnihu(){
        System.out.println("Manazer rozposiela ziadosti o knihu");
        for (Autor autor: autori) {
            if(autor.prijmiPoziadvku()) {
                System.out.println(autor.getMeno() + " " + autor.getPrievzisko() +" prijal poziadavku");
            }
        }
    }


    public double ziskajFeedback(Text text) {
        switch(text.getKategoria()){
            case POEZIA:
                return Math.random()*50;
            case FANTASY:
                return Math.random()*50 + 30;
            case HISTORIA:
                return  Math.random()*30;
            case PRE_MLADEZ:
                return Math.random()*70 +30;
            default:
                return  Math.random()*100;
        }
    }

    public double navrhniCenu(double feedback) {
        if(feedback < 50){
            return 4.99;
        }else if(feedback < 75){
            return 9.99;
        }else if(feedback < 80){
            return 14.99;
        }else {
            return 19.99;
        }
    }
}
