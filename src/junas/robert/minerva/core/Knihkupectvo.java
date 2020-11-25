package junas.robert.minerva.core;

import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.users.Pouzivatel;
import junas.robert.minerva.core.users.Predajca;
import junas.robert.minerva.core.users.Skladnik;
import junas.robert.minerva.core.users.Zakaznik;
import junas.robert.minerva.core.utils.LoggedIn;

import java.io.*;
import java.util.Scanner;

public class Knihkupectvo implements java.io.Serializable{
    private Sklad sklad;
    private Predajna predajna;
    public static LoggedIn prihlaseny = LoggedIn.NONE;
    public static boolean changedUser = false;

    public Knihkupectvo(){
        sklad = new Sklad();
        predajna = new Predajna();
    }

    public Sklad getSklad() { return sklad; }
    public Predajna getPredajna() {return predajna;}

    public void serialize(String path){
        try{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Knihkupectvo deserialize(String path){
        try {
            FileInputStream fileIn = new FileInputStream("./knihkupectvo.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Knihkupectvo knihkupectvo = (Knihkupectvo) in.readObject();
            in.close();
            fileIn.close();
            return knihkupectvo;
        } catch (IOException e) {
            return new Knihkupectvo();
        } catch (ClassNotFoundException e) {
            return new Knihkupectvo();
        }
    }


    public static void main(String[] args) {
        //upcasting
        Pouzivatel p = new Zakaznik();
        Predajca predajca = new Predajca("Karol", 3213213);
        Zakaznik zakaznik = new Zakaznik();
        Skladnik skladnik = new Skladnik("Peter", 232132131);

        Scanner scanner = new Scanner(System.in);
        Knihkupectvo knihkupectvo = deserialize("./knihkupectvo.ser");

        String command  = null;
        while(!p.closeCallback()) {
            if(changedUser) {
                switch (prihlaseny) {
                    case PREDAJCA:
                        //upcasting
                        p = predajca;
                        changedUser = false;
                        break;
                    case ZAKAZNIK:
                        //upcasting
                        p = zakaznik;
                        changedUser = false;
                        if(!knihkupectvo.predajna.isOtvorene()){
                            System.out.println("Predajna je zavreta");
                            prihlaseny = LoggedIn.NONE;
                        }
                        break;
                    case SKLADNIK:
                        //upcasting
                        p = skladnik;
                        changedUser = false;
                        break;
                }
            }
            if(prihlaseny == LoggedIn.NONE){
                System.out.println("Prihlasit sa ako [zakaznik/predajca/skladnik]:");
                command = scanner.nextLine();
                String s = command.toUpperCase();
                for (LoggedIn k : LoggedIn.values()) {
                    if (s.equals(k.toString())) {
                        prihlaseny = LoggedIn.valueOf(s);
                        changedUser = true;
                    }
                }
            }else {
                System.out.println("Ste prihlaseny ["+prihlaseny+"] - \"help\" pre viac info");
                command = scanner.nextLine().toLowerCase();
                String[] cmd = command.split(" ");
                //upcasting
                p.spracuj(cmd, knihkupectvo);
            }
        }
        knihkupectvo.serialize("./knihkupectvo.ser");
        System.out.println("System sa vypina");
    }

}
