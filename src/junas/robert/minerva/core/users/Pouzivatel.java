package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.utils.LoggedIn;

public abstract class Pouzivatel {
    protected long id;
    protected String meno;
    private boolean close = false;

    public Pouzivatel(){
        meno = "NOONE";
        id = -1;
    }
    Pouzivatel(String m, long id) {
        meno = m;
        this.id =id;
    };

    ///funkcia ktora povie programu nech hlavny loop programu ukonci
    public void exit(){
        close = true;
    }

    ///funckia ktora zistuje ci sa hlavny loop konci
    public boolean closeCallback(){
        return close;
    }
    ///vypise meno a id uzivatela
    public void vypisInfo(){
        System.out.println(meno + " ["+ id+"]");
    }


    public void commands(String s, Sklad sklad, Predajna predajna){
        if(s.equals("help")){
            System.out.println("---Vseobecne prikazy---");
            System.out.println("info-me - informacie o mne");
            System.out.println("logout - odhlasit sa");
            System.out.println("help - vypis pomocky");
            System.out.println("exit - vypni system");

        } else if(s.equals("exit")){
            exit();
        } else if(s.equals("logout")){
            Knihkupectvo.setPrihlaseny(LoggedIn.NOONE);
        } else if(s.equals("info-me")){
            this.vypisInfo();
        }
    }

}
