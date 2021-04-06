package junas.robert.lagatoria.core.knihkupectvo;

import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Sklad;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.gui.View;

import java.io.*;

/**
 * Singleton trieda
 */
public class Knihkupectvo implements java.io.Serializable, Odoberatel {
    private static Knihkupectvo instancia = null;
    private Sklad sklad;
    private Predajna predajna;
    private LoggedIn prihlaseny = LoggedIn.NONE;
    public static boolean zmenaUzi = false;


    private Boolean prijma;

    private Knihkupectvo(){
        sklad = new Sklad();
        predajna = new Predajna();

        prijma = false;
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

    public static LoggedIn getPrihlaseny() {return instancia.prihlaseny;}
    public static void setPrihlaseny(LoggedIn prihlaseny) {instancia.prihlaseny = prihlaseny;}


    /**
     * Uklada sa instancia knihkupectva, knihy v nom a ich ulozenie
     * @param path cesta k suboru, kde sa ulozia informacie o knihkupectve
     */
    public static void serialize(String path){
        try{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instancia);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda nacita informacie o knihkupectve do programu
     * @param path cesta k suboru odkial sa maju data o knihkupectve nacitat
     */
    public static void deserialize(String path){
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instancia = (Knihkupectvo) in.readObject();
            in.close();
            fileIn.close();
            return;
        } catch (IOException e) {
            View.printline("Nenasiel sa subor '"+path+"' -- vytvara sa nove knihkupectvo'");
        } catch (ClassNotFoundException e) {
        }
        getInstance();
    }

/*
    public static void main(String[] args) {
        //upcasting
        Pouzivatel p = new Zakaznik();
        Predajca predajca = new Predajca("Karol", 3213213);
        Zakaznik zakaznik = new Zakaznik();
        Skladnik skladnik = new Skladnik("Peter", 232132131);

        Scanner scanner = new Scanner(System.in);
        deserialize("./knihkupectvo.ser");
        Knihkupectvo knihkupectvo = getInstance();

        String command  = null;
        while(!p.closeCallback()) {
            if(zmenaUzi) {
                switch (getPrihlaseny()) {
                    case PREDAJCA:
                        //upcasting
                        p = predajca;
                        zmenaUzi = false;
                        break;
                    case ZAKAZNIK:
                        //upcasting
                        if(!knihkupectvo.predajna.isOtvorene()){
                            System.out.println("Predajna je zavreta");
                            setPrihlaseny(LoggedIn.NONE);
                        }else {
                            p = zakaznik;
                            knihkupectvo.getPredajna().setZakaznik(zakaznik);
                            zmenaUzi = false;
                        }
                        break;
                    case SKLADNIK:
                        //upcasting
                        p = skladnik;
                        zmenaUzi = false;
                        break;
                }
            }
            if(getPrihlaseny() == LoggedIn.NONE){
                System.out.println("Prihlasit sa ako [zakaznik/predajca/skladnik]:");
                command = scanner.nextLine();
                String s = command.toUpperCase();
                if(s.equals("EXIT")) p.exit();
                for (LoggedIn k : LoggedIn.values()) {
                    if (s.equals(k.toString())) {
                        setPrihlaseny(LoggedIn.valueOf(s));
                        zmenaUzi = true;
                    }
                }
            }else {
                System.out.println("Ste prihlaseny ["+getPrihlaseny()+"] - \"help\" pre viac info");
                command = scanner.nextLine().toLowerCase();
                String[] cmd = command.split(" ");
                //upcasting
                p.spracuj(cmd, null);
            }
        }
        serialize("./knihkupectvo.ser");
        System.out.println("System sa vypina");
    }*/


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

}
