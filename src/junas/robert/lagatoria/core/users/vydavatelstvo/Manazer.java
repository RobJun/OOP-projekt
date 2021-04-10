package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.utils.exceptions.AutorExistujeException;
import junas.robert.lagatoria.core.utils.exceptions.AutorNieJeNaZozname;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.gui.Controller;
import junas.robert.lagatoria.gui.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Manazer extends Zamestnanec {
    ArrayList<Autor> autori = new ArrayList<Autor>();

    public Manazer(String m, long id, double plat) {

        super(m, id, plat);

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("dajNapisat", (args, kh, vy) -> {return dajNapisatKnihu();});
        inlineAkcie.put("vydajKnihy", (args, kh, vy) -> {return vy.vydajKnihy();});
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
        return "";
            });
        inlineAkcie.put("vypisAutor", (args,kh,vy) -> {return vy.vypisAutorov();});
        inlineAkcie.put("Pzoznam", ((args, kh, vy) -> {return vy.dajAutorovManazerovi();}));
        inlineAkcie.put("Queue", ((args, kh, vy) -> {return vy.vypisTexty();}));
        inlineAkcie.put("Strategia", (args,kh,vy) -> {vy.typVydavania(Boolean.valueOf(args[1])); return "";});
        inlineAkcie.put("odoberA", ((args, kh, vy) -> {
            try {
                odoberAutora(vy.getAutor(Integer.valueOf(args[1])));
            } catch (AutorNieJeNaZozname autorNieJeNaZozname) {
                autorNieJeNaZozname.printStackTrace();
                return autorNieJeNaZozname.getMessage();
            }
            return "";
        }));
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

    public void odoberAutora(Autor autor) throws AutorNieJeNaZozname {
        if (!autori.contains(autor)) throw new AutorNieJeNaZozname(autor.getMeno());

        autori.remove(autor);
    }

    public boolean existujeAutor(Autor autor){
        return autori.contains(autor);
    }

    public String dajNapisatKnihu(){
        String res = "";
        res +="Manazer rozposiela ziadosti o knihu\n";
        for (Autor autor: autori) {
            if(autor.prijmiPoziadvku()) {
               res+="\t" + autor.getMeno() + " " + autor.getPrievzisko() +" prijal poziadavku\n";
            }
        }
        return res;
    }


    /**
     * Urci ako by mala byt kniha popularna
     * @param text text o ktorom chcem vediet jeho hodnotu
     * @return  vracia hodnotu textu, resp. hodnotenie od kritikov
     */
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

    /**
     * Cena je dana podla parametru feedback (mozne ceny: 4,99;7,99;9,99;14,99 alebo 19,99)
     * @param feedback hodnota 0-100 ako bude kniha predavana
     * @return vracia navrhnutu cenu knihy
     */
    public double navrhniCenu(double feedback) {
        if(feedback < 30){
            return 4.99;
        } else if(feedback < 50){
            return 7.99;
        } else if(feedback < 75){
            return 9.99;
        } else if(feedback < 80){
            return 14.99;
        } else {
            return 19.99;
        }
    }
}
