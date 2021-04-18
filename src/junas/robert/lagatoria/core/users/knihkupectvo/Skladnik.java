package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Sklad;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.info.Inventar;

public class Skladnik extends Zamestnanec {

    private Inventar inventar = new Inventar();

    public Skladnik(String meno, long id){
        super(meno, id, 4.5);


        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("sklad", ((args, kh, vy) -> kh.getSklad().printSklad()));
        inlineAkcie.put("n-umiestni", (args, kh, vy) -> umiestniNovyT(kh.getSklad()));
        inlineAkcie.put("info-n", (args, kh, vy) -> {
            if( (kh.getSklad()).getNovyTovar() != null) {
               return  (kh.getSklad()).getNovyTovar().printContent();
            }
            return "problem so skladom";
        }
            );
        inlineAkcie.put("objednaj", ((args, kh, vy)-> {
            String res = objednaj(args,kh.getSklad());
            kh.getPredajna().setKatalog(kh.getSklad().getKatalog());
            return res;
        }));
        inlineAkcie.put("premiestni", ((args, kh, vy)-> premiestni(args,kh.getSklad())));
        inlineAkcie.put("max-miesto", ((args, kh, vy) -> {
            int[] miesto = kh.getSklad().najdiNajvacsieMiesto();
            return "Sekcia:" + miesto[0] + " Polička: " +miesto[1];
        }));

    }

    public String objednajtovar(Sklad s,String path){
        return s.objednatKnihy(path);
    }

    public String umiestniNovyT(Sklad s){
        return s.umiestniNovyTovar();
    }


    public void odoberKnihyZRegalu(Sklad s, Kniha k, int pocet, int[] pozicia){
        if(inventar.getKniha() != null) return;
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

    public String umiestniKnihyDoRegalu(Sklad s, int[] pozicia, int pocet){
        if(pozicia == null) {
            return "Neplatna pozicia";
        }
        if(inventar.getKniha() == null){
            return "Nemas knihy u seba";
        }
        if(inventar.getPocet() < pocet) pocet = inventar.getPocet();
        int pridane  = s.getSekcie(pozicia[0]).getRegal(pozicia[1]).pridajKnihy(inventar.getKniha(),pocet);
        inventar.odoberZKnih(pridane);
        if(inventar.getPocet() == 0) {
            inventar.resetInventar();
            return "vsetky knihy si ulozil";
        }
        return "knihy boli umiestnene a zostali ti knihy v inventari";
    }

    private void pridajKnihy(Kniha k,int pocet){
        inventar.set(k,pocet);
    }


    public int getPocetNosenych() {
        return inventar.getPocet();
    }
    public Kniha getKniha(){
        return inventar.getKniha();
    }

    @Override
    public String vypisInfo(){
        if(inventar.getKniha() == null) { return super.vypisInfo();}
        else {
            return meno + " [" + id + "]\n" + "V inventari mas: " + inventar.getKniha().getBasicInfo()[0] + "[" + inventar.getPocet() + " kusov]";
        }
    }

    @Override
    public String help() {
        super.help();
        System.out.println("---prikazy skladu---");
        System.out.println("objednaj \"path\" - informacie o mne");
        System.out.println("n-umiestni - automacticky najde a umiestni knihy na miesta kde sa zmestia");
        System.out.println("info-n - informacie o novom tovare");
        System.out.println("sklad - vypis cely sklad");
        System.out.println("premiestni n/ref /pocet //zX-X //uX-X  - s/ref = nazov/isbn/katalogove cislo knihy\n " +
                "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                "\t\t\t\t\t\t\t\t\t\t\t moze byt null ak mas knihu v inventari] \n" +
                "\t\t\t\t\t\t\t\t\t\t- zX-X = pozicia z ktorej beriem \n"+
                "\t\t\t\t\t\t\t\t\t\t- uX-X = pozicia na ktoru umiestnujeme \n" +
                "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat");
        System.out.println("max-miesto - najde najvacsie miesto na ulozenie");
        System.out.println("");
        return "";
    }

    private String premiestni(String[] com, Sklad sklad){
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
                k = najdiReferenciuNaKnihu(sklad, Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/")){
                k = najdiReferenciuNaKnihu(sklad, f.replaceAll("_", " ").substring(2));
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


        if(inventar.getKniha() != null && zober != null){
            return "Uz mas knihy v inventari, najprv umiestni tie";
        }else if(inventar.getKniha() != null && k == null){
            if(p ==0) p = inventar.getPocet();
            return umiestniKnihyDoRegalu(sklad,umiestni,p);
        }else if(k == null){
            return "nezadal si knihu";
        }else if(inventar.getKniha() == null && umiestni == null) {
            odoberKnihyZRegalu(sklad,k,p, zober);
            return "odobral si knihy z regalu";
        }else{
            odoberKnihyZRegalu(sklad,k,p, zober);
            return umiestniKnihyDoRegalu(sklad,umiestni,p);
        }
    }

    private String objednaj(String[] s, Sklad sklad){
        String f = s[1];
        f = f.substring(f.indexOf("\"")+1, f.lastIndexOf("\""));
        return this.objednajtovar(sklad, f);
    }
}
