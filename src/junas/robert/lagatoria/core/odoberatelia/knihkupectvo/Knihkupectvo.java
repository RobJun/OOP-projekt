package junas.robert.lagatoria.core.odoberatelia.knihkupectvo;

import junas.robert.lagatoria.core.odoberatelia.Odoberatel;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.rooms.Sklad;
import junas.robert.lagatoria.core.utils.Observer;

import java.io.*;

/**
 * Singleton trieda
 */
public class Knihkupectvo implements java.io.Serializable, Odoberatel, Observer {
    /**
     * singleton instancia
     */
    private static Knihkupectvo instancia = null;
    private Sklad    sklad;
    private Predajna predajna;
    /**
     * sledovatel knihkupectva
     */
    transient private Observer observer;
    /**
     * kolko penazi ma knihkupectvo k dispozicii
     */
    private double kapital = 100000; //kolko penazi ma knihkupectvo k dispozicii

    /**
     * konstruktor pre vytvorenie singleton instancie
     */
    private Knihkupectvo(){
        sklad = new Sklad(this);
        predajna = new Predajna(this);
    }

    /**
     * nuti sklad o odoslanie upozprnenia o zmene katalogu
     */
    public void printKatalog(){
        sklad.printKatalog();
    }

    /**
     * Nastavenie sledovatela knihkupectva a nastavenie knihkupectva ako sledovatela skladu a predajne
     * @param observer sledovatel knihkupectva
     */
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

    /**
     * @return vrati sklad
     */
    public Sklad getSklad() { return sklad; }

    /**
     * @return vrati predajnu
     */
    public Predajna getPredajna() {return predajna;}


    /**
     * Uklada sa instancia knihkupectva, knihy v nom a ich ulozenie
     * @param path cesta k suboru, kde sa ulozia informacie o knihkupectve
     * @return retazec o uspechu serializacie
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
     * @return retazec informucjuci o uspechu akcie
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
        kapital -= kniha.getCena()*0.50*pocet;
        return kniha.getCena()*0.50*pocet;
    }

    /**
     * Odosle data o iniciatorovy a sprave svojmu sledovatelovi
     * @param caller iniciator upozornenia
     * @param msg sprava ktoru iniciator posiela
     */
    @Override
    public void notify(Object caller, Object msg) {
        observer.notify(caller, msg);
    }


    /**
     * Prida do kapitalu hodnotu z parametrov
     * @param kapital pocet penazi ktore sme zarobili
     */
    public void pridajPeniaze(double kapital){
        this.kapital += kapital;
    }
}
