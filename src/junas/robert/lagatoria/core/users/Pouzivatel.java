package junas.robert.lagatoria.core.users;

import junas.robert.lagatoria.core.utils.InlineCommand;
import junas.robert.lagatoria.core.utils.InputProcess;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Miestnost;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

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
        inlineAkcie.put("help", (args, kh, vy) -> {return help();});
        //inlineAkcie.put("exit", (args, kh, vy) -> {return exit();});
        //inlineAkcie.put("logout", (args, kh, vy) -> kh.setPrihlaseny(LoggedIn.NONE));
        inlineAkcie.put("info-me", (args, kh, vy) -> {return vypisInfo();});
        inlineAkcie.put("katalog", (args, kh, vy) -> {return kh.getSklad().printKatalog();});
    };

    /**
     * @return "meno [id]"
     */
    public String vypisInfo(){
        return  meno + " ["+ id+"]";
    }


    public String help(){
        System.out.println("---Vseobecne prikazy---");
        System.out.println("info-me - informacie o mne");
        System.out.println("katalog - vypise katalog predajne");
        System.out.println("logout - odhlasit sa");
        System.out.println("help - vypis pomocky");
        System.out.println("exit - vypni system");
        return "";
    }

    @Override //Pouzivatel.java
    public String spracuj(String[] args, Vydavatelstvo vydavatelstvo){
        String res = "";
        int index = 0;
            //prejde vsetky slova zadane na vstupe
            while(index < args.length) {
                //ak sa slovo nachadza v zavolatelnych funkciach
                if (inlineAkcie.containsKey(args[index])) {
                    //zavola sa funkcia
                    res += inlineAkcie.get(args[index]).process(args, Knihkupectvo.getInstance(), vydavatelstvo) + "\n";
                }
                int k;
                //najde dalsi prikaz rozdeleny |
                for (k = index + 1; k < args.length; k++) {
                    if (args[k].equals("|")) break;
                }
                index = k + 1;
            }
            return res;
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
