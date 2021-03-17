package junas.robert.lagatoria.core.knihkupectvo;

import junas.robert.lagatoria.core.Odoberatel;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Predajna;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Sklad;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.utils.LoggedIn;

import java.io.*;
import java.util.Scanner;

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

    public static void deserialize(String path){
        try {
            FileInputStream fileIn = new FileInputStream("./knihkupectvo.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instancia = (Knihkupectvo) in.readObject();
            in.close();
            fileIn.close();
            return;
        } catch (IOException e) {
            System.out.println("Nenasiel sa subor 'knihkupectvo.ser' -- vytvara sa nove knihkupectvo'");
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
                p.spracuj(cmd, knihkupectvo);
            }
        }
        serialize("./knihkupectvo.ser");
        System.out.println("System sa vypina");
    }*/


    public Boolean prijmameTovar(){
        if(sklad.getNovyTovar() == null) return true;
        return sklad.getNovyTovar().isMinute();
    }


    @Override
    public double zaplatVydavatelovi(Kniha kniha, int pocet) {
        return kniha.getCena()*0.50*pocet;
    }

}
