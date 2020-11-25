package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.utils.InlineCommand;
import junas.robert.minerva.core.utils.InputProcess;
import junas.robert.minerva.core.utils.LoggedIn;
import java.util.HashMap;

public abstract class Pouzivatel implements InputProcess {
    protected long id;
    protected String meno;
    private boolean close = false;
    protected HashMap<String, InlineCommand> inlineAkcie;

    Pouzivatel(String m, long id) {
        meno = m;
        this.id =id;

        inlineAkcie = new HashMap<>();
        inlineAkcie.put("help", (args, kh) -> help());
        inlineAkcie.put("exit", (args, kh) -> exit());
        inlineAkcie.put("logout", (args, kh) -> Knihkupectvo.prihlaseny = LoggedIn.NONE);
        inlineAkcie.put("info-me", (args, kh) -> vypisInfo());
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


    public void help(){
        System.out.println("---Vseobecne prikazy---");
        System.out.println("info-me - informacie o mne");
        System.out.println("logout - odhlasit sa");
        System.out.println("help - vypis pomocky");
        System.out.println("exit - vypni system");
    }

    @Override
    public void spracuj(String[] s, Knihkupectvo kh){
            if(inlineAkcie.containsKey(s[0])) {
                inlineAkcie.get(s[0]).process(s,kh);
            }
    }

}
