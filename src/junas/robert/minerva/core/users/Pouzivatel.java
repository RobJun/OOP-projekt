package junas.robert.minerva.core.users;

import junas.robert.minerva.core.Knihkupectvo;
import junas.robert.minerva.core.utils.InlineCommand;
import junas.robert.minerva.core.utils.InputProcess;
import junas.robert.minerva.core.utils.LoggedIn;

import java.util.Arrays;
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
        inlineAkcie.put("katalog", (args,kh) -> kh.getSklad().printKatalog());
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
        System.out.println("katalog - vypise katalog predajne");
        System.out.println("logout - odhlasit sa");
        System.out.println("help - vypis pomocky");
        System.out.println("exit - vypni system");
    }

    @Override
    public void spracuj(String[] s, Knihkupectvo kh){
        int index = 0;
            while(index < s.length) {
                if (inlineAkcie.containsKey(s[index])) {
                    inlineAkcie.get(s[index]).process(s, kh);
                }
                int k;
                for (k = index + 1; k < s.length; k++) {
                    if (s[k].equals("|")) break;
                }
                index = k + 1;
            }
    }

}
