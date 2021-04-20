package junas.robert.lagatoria.core.knihkupectvo;

import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Sklad;
import junas.robert.lagatoria.core.utils.Observer;

import java.io.*;

/**
 * Singleton trieda
 */
public class Knihkupectvo implements java.io.Serializable, Odoberatel, Observer {
    private static Knihkupectvo instancia = null;
    private Sklad    sklad;
    private Predajna predajna;
    transient Observer observer;

    private Knihkupectvo(){
        sklad = new Sklad(this);
        predajna = new Predajna(this);
    }

    public void printKatalog(){
        sklad.printKatalog();
    }


    public void setObserver(Observer observer) {
        this.observer = observer;
        sklad.setObserver(this);
        predajna.setObserver(this);
    }

    /**
     * @return vracia instanciu na Singleton Knihkupectvo
     */
    public static Knihkupectvo getInstance()
    {
        if (instancia == null)
            instancia = new Knihkupectvo();
        return instancia;
    }

    public Sklad getSklad() { return sklad; }
    public Predajna getPredajna() {return predajna;}


    /**
     * Uklada sa instancia knihkupectva, knihy v nom a ich ulozenie
     * @param path cesta k suboru, kde sa ulozia informacie o knihkupectve
     * @return
     */
    public static String serialize(String path){
        try{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instancia);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "nepodarilo sa najst subor";
        }
        return "Knihkupecvto sa ulozilo do /res/knihkupectvo_oop.ser";
    }

    /**
     * metoda nacita informacie o knihkupectve do programu
     * @param path cesta k suboru odkial sa maju data o knihkupectve nacitat
     */
    public static String deserialize(String path){
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instancia = (Knihkupectvo) in.readObject();
            in.close();
            fileIn.close();
            return "podarilo sa nacitat knihkupetvo";
        } catch (IOException e) {
            return "Nenasiel sa subor '"+path+"' -- vytvara sa nove knihkupectvo'";
        } catch (ClassNotFoundException e) {
        }
        getInstance();
        return "nepodarilo sa nacitat knihkupectvo treba vytvara sa nove";
    }

    /**
     * Zistuje ci je knihkupectvo pripravene prebrat novy tovar od vydavatela
     * @return Ak sa nenachadza ziadny este nepoukladany tovar v sklade tak sa vracia true inak false
     */
    public Boolean prijmameTovar(){
        if(sklad.getNovyTovar() == null) return true;
        return sklad.getNovyTovar().isMinute();
    }


    /**
     * vypocet kolko musi Knihkupectvo zaplatit za knihy vydavatelovi
     * @param kniha kniha za ktoru sa plati
     * @param pocet pocet prebratej knihy
     * @return celkova cena ktoru knihkupectvo zaplatilo
     */
    @Override
    public double zaplatVydavatelovi(Kniha kniha, int pocet) {
        return kniha.getCena()*0.50*pocet;
    }

    @Override
    public void notify(Object o, Object s) {
        observer.notify(o,s);
    }
}
