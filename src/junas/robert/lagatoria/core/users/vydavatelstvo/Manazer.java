package junas.robert.lagatoria.core.users.vydavatelstvo;

import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.exceptions.AutorExistujeException;
import junas.robert.lagatoria.core.utils.exceptions.AutorNieJeNaZozname;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Trieda manazera pracuje s vydavatelstvom, posiela poziadavky o napisanie textov autorov
 * vytvara novych autorovov a zapina vydavanie knih
 */
public class Manazer extends Zamestnanec {
    /**
     * autori cakajuci na pisanie
     */
    private ArrayList<Autor> autori = new ArrayList<Autor>();
    /**
     * sledovatel manazera
     */
    private Observer observer;


    /**
     * Rozsirenie funkcii zamestnanca o funkcie zamestnanca
     * @param meno meno zamestnanca
     * @param id identifikacne cislo
     * @param plat plat zamestnanca
     */
    public Manazer(String meno, long id, double plat) {
        super(meno, id, plat);
        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("dajNapisat", (args, kh, vy) -> {dajNapisatKnihu(); return "";});
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
            return "Odobranie autora prebehlo uspesne";
        }));
    }

    /**
     * nastavenie sledovatela manazera
     * @param observer sledovatel manazera
     */
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * volanie funckie prepise povodny zozname autorov
     * @param autori zoznam autorov ktorych chceme nastavit na cakanie
     */
    public void pridajAutora(ArrayList<Autor> autori){
        this.autori = (ArrayList<Autor>) autori.clone();
    }

    /**
     * Prida autora do zoznamu cakajucich na pisanie
     * @param autor pridavany autor
     * @throws AutorExistujeException ak uz autor bol pridany do zoznamu, aby nevznikol duplikat
     */
    public void pridajAutora(Autor autor) throws AutorExistujeException {
        if(autori.contains(autor))
            throw new AutorExistujeException(autor.getPrievzisko());
        else
            this.autori.add(autor);
    }

    /**
     * Odoberie autora z aktivnej sluzby
     * @param autor odoberany autor
     * @throws AutorNieJeNaZozname ak odstranovany autor sa nenachadza na zozname
     */
    public void odoberAutora(Autor autor) throws AutorNieJeNaZozname {
        if (!autori.contains(autor)) throw new AutorNieJeNaZozname(autor.getMeno());

        autori.remove(autor);
    }

    /**
     * @param autor kontrolovany autor
     * @return true ak autor caka na pisanie knihy; false ak nie
     */
    public boolean existujeAutor(Autor autor){
        return autori.contains(autor);
    }

    /**
     * Upozorni autorov o tom ze treba napisat knihu
     */
    public void dajNapisatKnihu(){
        observer.notify(this,"Manazer rozposiela ziadosti o knihu:");
        for (Autor autor: autori) {
            autor.notify(this,"");
        }
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
