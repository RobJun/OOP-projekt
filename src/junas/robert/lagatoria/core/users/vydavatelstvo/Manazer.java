package junas.robert.lagatoria.core.users.vydavatelstvo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.utils.exceptions.AutorExistujeException;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.gui.Controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Manazer extends Zamestnanec {
    ArrayList<Autor> autori = new ArrayList<Autor>();

    public Manazer(String m, long id, double plat) {

        super(m, id, plat);
        inlineAkcie.put("dajNapisat", (args, kh, vy) -> dajNapisatKnihu());
        inlineAkcie.put("vydajKnihy", (args, kh, vy) -> vy.vydajKnihy());
        inlineAkcie.put("pridajA", (args,kh,vy) -> {
            try {
                Constructor c = Class.forName(args[1]).getConstructor(String.class, String.class, Vydavatelstvo.class);
                Autor autor = (Autor) c.newInstance(args[2],args[3],vy);
                vy.prijmiAutora(autor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        inlineAkcie.put("vypisAutor", (args,kh,vy) -> vy.vypisAutorov());
        inlineAkcie.put("Pzoznam", ((args, kh, vy) -> vy.dajAutorovManazerovi()));
        inlineAkcie.put("Queue", ((args, kh, vy) -> vy.vypisTexty()));
        inlineAkcie.put("Strategia", (args,kh,vy) -> vy.typVydavania(Boolean.valueOf(args[1])));
    }

    public void pridajAutora(ArrayList<Autor> autori){
        this.autori = (ArrayList<Autor>) autori.clone();
    }

    public void pridajAutora(Autor autor) throws AutorExistujeException {
        if(autori.contains(autor))
            throw new AutorExistujeException(autor.getMeno());
        else
            this.autori.add(autor);
    }

    public boolean existujeAutor(Autor autor){
        return autori.contains(autor);
    }

    public void dajNapisatKnihu(){
        Controller.printline("Manazer rozposiela ziadosti o knihu");
        for (Autor autor: autori) {
            if(autor.prijmiPoziadvku()) {
                Controller.printline(autor.getMeno() + " " + autor.getPrievzisko() +" prijal poziadavku");
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
