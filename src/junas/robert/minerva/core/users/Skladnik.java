package junas.robert.minerva.core.users;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.rooms.Predajna;
import junas.robert.minerva.core.rooms.Sklad;
import junas.robert.minerva.core.storage.NoveKnihy;
import junas.robert.minerva.core.storage.Regal;
import org.jetbrains.annotations.NotNull;

public class Skladnik extends Zamestnanec{
    private Kniha kniha;
    private int pocet;


    public Skladnik(String meno, long id){
        super(meno, id, 4.5);
    }

    public void objednajtovar(@NotNull Sklad s,@NotNull String path){
        s.objednatKnihy(path);
    }

    public void umiestniNovyT(Sklad s){
        s.umiestniNovyTovar();
    }


    public void odoberKnihyZRegalu(Sklad s, Kniha k, int pocet, int[] pozicia){
        if(kniha != null) return;
         if(pozicia == null){
             NoveKnihy r = s.getNovyTovar();
             if(r != null && r.existujeKniha(k)) {
                 int p = r.odoberKnihy(k, pocet);
                 pridajKnihy(k, p);
             }
         }else{
             Regal r = s.getSekcie(pozicia[0]).getRegal(pozicia[1]);
             if(r.existujeKniha(k)){
                 int p = r.odoberKnihy(k,pocet);
                 pridajKnihy(k,p);
             }
         }
    }

    public void umiestniKnihyDoRegalu(Sklad s, int[] pozicia, int pocet){
        if(pozicia == null) {
            System.out.println("Neplatna pozicia");
            return;
        }
        if(kniha == null){
            System.out.println("Nemas knihy u seba");
            return;
        }
        if(this.pocet < pocet) pocet = this.pocet;
        int pridane  = s.getSekcie(pozicia[0]).getRegal(pozicia[1]).pridajKnihy(kniha,pocet);
        this.pocet -= pridane;
        if(this.pocet == 0) kniha = null;
    }


    public Kniha najdReferenciuNaKnihu(Sklad s, int i){
        return s.getKatalog().get(i);
    }

    public Kniha najdReferenciuNaKnihu(Sklad s, String id){
        for(Kniha kp : s.getKatalog()){
            if(kp.getISBN().toLowerCase().equals(id) || kp.getBasicInfo()[0].toLowerCase().equals(id)){
                return kp;
                }
        }
        return null;
    }

    private void pridajKnihy(Kniha k,int pocet){
        kniha = k;
        this.pocet = pocet;
    }


    public int getPocetNosenych() {
        return pocet;
    }
    public Kniha getKniha(){
        return kniha;
    }

    @Override
    public void vypisInfo(){
        if(kniha == null) {super.vypisInfo();}
        else {
            System.out.println(meno + " [" + id + "]\n" + "V inventari mas: " + kniha.getBasicInfo()[0] + "[" + pocet + " kusov]");
        }
    }


    @Override
    public void spracuj(String s, Sklad sklad, Predajna predajna){
        super.spracuj(s, null, null);
        if(s.equals("help")){
            System.out.println("---prikazy skladu---");
            System.out.println("objednaj \"path\" - informacie o mne");
            System.out.println("n-umiestni - automacticky najde a umiestni knihy na miesta kde sa zmestia");
            System.out.println("info-n - informacie o novom tovare");
            System.out.println("sklad - vypis cely sklad");
            System.out.println("katalog - vypise vsetky knihy ktore sa nachadzaju/li v sklade");
            System.out.println("premiestni n/ref /pocet //zX-X //uX-X  - s/ref = nazov/isbn/katalogove cislo knihy\n " +
                                "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                                "\t\t\t\t\t\t\t\t\t\t\t moze byt null ak mas knihu v inventari] \n" +
                                "\t\t\t\t\t\t\t\t\t\t- zX-X = pozicia z ktorej beriem \n"+
                                "\t\t\t\t\t\t\t\t\t\t- uX-X = pozicia na ktoru umiestnujeme \n" +
                                "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat");
            System.out.println("");

        }else if(s.equals("sklad")){
            sklad.printSklad();
        }else if(s.contains("objednaj")){
            String f = s.split(" ")[1];
            f = f.substring(f.indexOf("\"")+1, f.lastIndexOf("\""));
            this.objednajtovar(sklad, f);
        }else if(s.contains("n-umiestni")){
            this.umiestniNovyT(sklad);
        }else if(s.contains("info-n")){
            if(sklad.getNovyTovar() != null) {
                sklad.getNovyTovar().printContent();
            }
        }else if(s.equals("katalog")){
            sklad.printKatalog();
        }else if(s.contains("premiestni")){
            String[] com = s.split(" ");
            int[] zober = new int[] {-1,-1};
            int[] umiestni = new int[] {-1,-1};
            int p = -1;
            Kniha k = null;
            for(String f : com){
                if(f.contains("//z")){
                    zober[0] = Integer.parseInt(f.substring(3,f.indexOf('-')));
                    zober[1] = Integer.parseInt(f.substring(f.indexOf('-')+1));
                }else if(f.contains("//u")){
                    umiestni[0] = Integer.parseInt(f.substring(3,f.indexOf('-')));
                    umiestni[1] = Integer.parseInt(f.substring(f.indexOf('-')+1));
                }else if(f.contains("i/")){
                    k = najdReferenciuNaKnihu(sklad, Integer.parseInt(f.substring(2)));
                }else if(f.contains("s/")){
                    k = najdReferenciuNaKnihu(sklad, f.replaceAll("_", " ").substring(2));
                }else if(f.contains("/")){
                    p = Integer.parseInt(f.substring(1));
                }
            }
            if(zober[0] == -1) {
                zober = null;
            }
            if(umiestni[0] == -1) {
                umiestni = null;
            }
            if(p == -1) {p = 0;}


            if(kniha != null && zober != null){
                System.out.println("Uz mas knihy v inventari, najprv umiestni tie");
            }else if(kniha != null && k == null){
                if(p ==0) p = this.pocet;
                umiestniKnihyDoRegalu(sklad,umiestni,p);
            }else if(k == null){
                System.out.println("nezadal si knihu");
            }else if(kniha == null && umiestni == null) {
                odoberKnihyZRegalu(sklad,k,p, zober);
            }else{
                odoberKnihyZRegalu(sklad,k,p, zober);
                umiestniKnihyDoRegalu(sklad,umiestni,p);
            }
        }
    }

}
