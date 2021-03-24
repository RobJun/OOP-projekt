package junas.robert.lagatoria.core.users;

import junas.robert.lagatoria.core.utils.InlineCommand;
import junas.robert.lagatoria.core.utils.InputProcess;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Miestnost;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.gui.Controller;

import java.util.HashMap;

public abstract class Pouzivatel implements InputProcess {
    protected long id;
    protected String meno;
    private boolean close = false;
    protected HashMap<String, InlineCommand> inlineAkcie;

    public Pouzivatel(String m, long id) {
        meno = m;
        this.id =id;

        inlineAkcie = new HashMap<>();

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("help", (args, kh, vy) -> help());
        inlineAkcie.put("exit", (args, kh, vy) -> exit());
        inlineAkcie.put("logout", (args, kh, vy) -> kh.setPrihlaseny(LoggedIn.NONE));
        inlineAkcie.put("info-me", (args, kh, vy) -> vypisInfo());
        inlineAkcie.put("katalog", (args, kh, vy) -> kh.getSklad().printKatalog());
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
        Controller.printline(meno + " ["+ id+"]");
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
    public void spracuj(String[] args, Vydavatelstvo vydavatelstvo){
        int index = 0;
            while(index < args.length) {
                if (inlineAkcie.containsKey(args[index])) {
                    inlineAkcie.get(args[index]).process(args, Knihkupectvo.getInstance(), vydavatelstvo);
                }
                int k;
                for (k = index + 1; k < args.length; k++) {
                    if (args[k].equals("|")) break;
                }
                index = k + 1;
            }
    }



    public Kniha najdiReferenciuNaKnihu(Miestnost s, int i){
        return  (s.getKatalog().isEmpty() || i >= s.getKatalog().size() ) ? null : s.getKatalog().get(i);
    }

    public Kniha najdiReferenciuNaKnihu(Miestnost s, String id){
        for(Kniha kp : s.getKatalog()){
            if(kp.getISBN().toLowerCase().equals(id.toLowerCase()) || kp.getBasicInfo()[0].toLowerCase().equals(id.toLowerCase())){
                return kp;
            }
        }
        return null;
    }

}
