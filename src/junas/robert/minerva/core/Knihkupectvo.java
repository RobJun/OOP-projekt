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

    public Knihkupectvo(){
        sklad = new Sklad();
        predajna = new Predajna();
    }


    public static void main(String[] args) {
        Knihkupectvo knihk = new Knihkupectvo();
        Skladnik user =new Skladnik("Jozo", 21312321);
        user.objednajtovar(knihk.sklad, "./novyTovar.txt");

        knihk.sklad.getNovyTovar().printContent();
        user.umiestniNovyT(knihk.sklad);

        knihk.sklad.getNovyTovar().printContent();

       while(knihk.sklad.getNovyTovar().getZoznamKnih().size() != 0){

            Kniha k = knihk.sklad.getNovyTovar().getZoznamKnih().get(0);
            user.odoberKnihyZRegalu(knihk.sklad,k,knihk.sklad.getNovyTovar().getPocetKnih(k.getISBN()),null);

            System.out.println();
            knihk.sklad.getNovyTovar().printContent();

            while(user.getPocetNosenych() > 0) {
                int[] miesto = knihk.sklad.najdiNajvacsieMiesto();
                user.umiestniKnihyDoRegalu(knihk.sklad, miesto, miesto[2]);
            }
        }
        knihk.sklad.getNovyTovar().VyhodPaletu();

        knihk.sklad.printSklad();

        knihk.sklad.printKatalog();



        Kniha ref = user.najdReferenciuNaKnihu(knihk.sklad,"ISBN-978-80-556-0637-8");
        user.odoberKnihyZRegalu(knihk.sklad, ref,100,new int[]{3,1});
        user.umiestniKnihyDoRegalu(knihk.sklad,new int[]{2,2},100);

        knihk.sklad.printSklad();

        user.pridajHodinu();
        user.pridajHodinu();
        user.pridajHodinu();
        user.pridajHodinu();

        System.out.println(user.vypocitajPlat());


       // mainloop();
    }

    private static void mainloop(){
        Pouzivatel p = new Zakaznik();
        Scanner scanner = new Scanner(System.in);
        Knihkupectvo knihkupectvo = new Knihkupectvo();

        String command;
        while(!p.closeCallback()) {
            switch (prihlaseny) {
                case PREDAJCA:
                    Predajca user = new Predajca("Karol",3213213);
                    p = user;
                    command = scanner.nextLine().toLowerCase();

                    if(command.equals("exit")){
                        user.exit();
                    }
                    break;
                case ZAKAZNIK:
                    Zakaznik user1 = new Zakaznik();
                    p = user1;
                    command = scanner.nextLine().toLowerCase();

                    if(command.equals("exit")){
                        user1.exit();
                    }
                    break;
                case SKLADNIK:
                    System.out.println("Ste prihlaseny ako skladnik \"help\" pre viac info");
                    Skladnik user2 = new Skladnik("Peter", 232132131);
                    p = user2;

                    command = scanner.nextLine().toLowerCase();

                    if(command.equals("exit")){
                        p.exit();
                    }else if(command.equals("help")){

                        System.out.println("objednaj \"path\" - informacie o mne");
                        System.out.println("n-umiestni - automacticky najde a umiestni knihy na miesta kde sa zmestia");
                        System.out.println("info-n - informacie o novom tovare");
                        System.out.println("info-me - informacie o mne");
                        System.out.println("sklad - vypis cely sklad");
                        System.out.println("katalog - vypise vsetky knihy ktore sa nachadzaju/li v sklade");
                        System.out.println("logout - odhlasit sa");
                        System.out.println("help - vypis pomocky");
                        System.out.println("exit - vypni system");

                    }else if(command.equals("sklad")){
                        knihkupectvo.sklad.printSklad();
                    }else if(command.equals("info-me")){
                        p.vypisInfo();
                    }else if(command.equals("logout")){
                        prihlaseny = LoggedIn.NOONE;
                    }else if(command.contains("objednaj")){
                        String s = command.split("\"")[1];
                        user2.objednajtovar(knihkupectvo.sklad, s);
                    }else if(command.contains("n-umiestni")){
                        user2.umiestniNovyT(knihkupectvo.sklad);
                    }else if(command.contains("info-n")){
                        if(knihkupectvo.sklad.getNovyTovar() != null) {
                            knihkupectvo.sklad.getNovyTovar().printContent();
                        }
                    }else if(command.equals("katalog")){
                        knihkupectvo.sklad.printKatalog();
                    }
                    break;
                case NOONE:
                    System.out.println("Prihlasit sa ako [zakaznik/predajca/skladnik]:");
                    command = scanner.nextLine().toUpperCase();
                    if(command.equals("EXIT")){
                        p.exit();
                    }
                    for(LoggedIn k : LoggedIn.values()){
                        if(command.equals(k.toString())){
                            prihlaseny = LoggedIn.valueOf(command);
                        }
                    }
                    break;
            };
        }
        System.out.println("System sa vypina");


    }

    public void changeUser(LoggedIn user){
        prihlaseny = user;
    }

}
