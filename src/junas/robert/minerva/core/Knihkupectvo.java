package junas.robert.minerva.core;

import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.users.Pouzivatel;
import junas.robert.minerva.core.users.Predajca;
import junas.robert.minerva.core.users.Skladnik;
import junas.robert.minerva.core.users.Zakaznik;
import junas.robert.minerva.core.utils.LoggedIn;

import java.util.Scanner;

public class Knihkupectvo {
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


    public static void main(String[] args) {
        Pouzivatel p = new Zakaznik();
        Predajca predajca = new Predajca("Karol", 3213213);
        Zakaznik zakaznik = new Zakaznik();
        Skladnik skladnik = new Skladnik("Peter", 232132131);

        Scanner scanner = new Scanner(System.in);
        Knihkupectvo knihkupectvo = new Knihkupectvo();
        String command  = null;
        while(!p.closeCallback()) {
            if(changedUser) {
                switch (prihlaseny) {
                    case PREDAJCA:
                        p = predajca;
                        changedUser = false;
                        break;
                    case ZAKAZNIK:
                        p = zakaznik;
                        changedUser = false;
                        if(!knihkupectvo.predajna.isOtvorene()){
                            System.out.println("Predajna je zavreta");
                            prihlaseny = LoggedIn.NONE;
                        }
                        break;
                    case SKLADNIK:
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
                p.spracuj(command, knihkupectvo.sklad, knihkupectvo.predajna);
            }
        }
        System.out.println("System sa vypina");
    }

}
