package junas.robert.minerva.core;

import junas.robert.minerva.core.items.Kniha;
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
    private static LoggedIn prihlaseny = LoggedIn.NOONE;
    private static boolean changedUser = false;

    public Knihkupectvo(){
        sklad = new Sklad();
        predajna = new Predajna();
    }

    public static void setPrihlaseny(LoggedIn prihlaseny) {
        Knihkupectvo.prihlaseny = prihlaseny;
        changedUser = true;
    }


    public Sklad getSklad() {
        return sklad;
    }

    public static void main(String[] args) {
        Pouzivatel p = new Zakaznik();
        Scanner scanner = new Scanner(System.in);
        Knihkupectvo knihkupectvo = new Knihkupectvo();
        String command  = null;
        while(!p.closeCallback()) {
            if(changedUser) {
                switch (prihlaseny) {
                    case PREDAJCA:
                        Predajca user = new Predajca("Karol", 3213213);
                        p = user;
                        changedUser = false;
                        break;
                    case ZAKAZNIK:
                        Zakaznik user1 = new Zakaznik();
                        p = user1;
                        changedUser = false;
                        break;
                    case SKLADNIK:
                        Skladnik user2 = new Skladnik("Peter", 232132131);
                        p = user2;
                        changedUser = false;
                        break;
                }
            }
            if(prihlaseny == LoggedIn.NOONE){
                System.out.println("Prihlasit sa ako [zakaznik/predajca/skladnik]:");
                command = scanner.nextLine();
                String s = command.toUpperCase();
                for (LoggedIn k : LoggedIn.values()) {
                    if (s.equals(k.toString())) {
                        setPrihlaseny(LoggedIn.valueOf(s));
                    }
                }
            }else {
                System.out.println("Ste prihlaseny ["+prihlaseny+"] - \"help\" pre viac info");
                command = scanner.nextLine().toLowerCase();
                p.commands(command, knihkupectvo.sklad, knihkupectvo.predajna);
            }
        }
        System.out.println("System sa vypina");
    }

}
