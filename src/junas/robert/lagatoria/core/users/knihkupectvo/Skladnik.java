package junas.robert.lagatoria.core.users.knihkupectvo;

import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage.Regal;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.rooms.Sklad;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.info.Premiestnovanie;
import junas.robert.lagatoria.core.users.info.Inventar;

/**
 * Trieda skladnika pracuje so skladom Knihkupectva,
 * napr. objednavanie noveho tovaru
 */
public class Skladnik extends Zamestnanec implements Premiestnovanie {

    /**
     * inventar, kde moze zamestanec mat jednu knihu, ale neobmedzeny pocet kusov danej knihy
     */
    private Inventar inventar = new Inventar();

    /**
     * Rozsiruje  funckie zamestnanca o funkcie skladnika
     * Plat predajcu je stanoveny 4.5
     * @param meno meno skladnika
     * @param id cislo
     */
    public Skladnik(String meno, long id){
        super(meno, id, 4.5);

        //pridavanie akcii ktore moze spravit trieda
        inlineAkcie.put("sklad", ((args, kh, vy) -> kh.getSklad().printSklad()));
        inlineAkcie.put("n-umiestni", (args, kh, vy) -> kh.getSklad().umiestniNovyTovar());
        inlineAkcie.put("info-n", (args, kh, vy) -> {
            if( (kh.getSklad()).getNovyTovar() != null) {
               return  (kh.getSklad()).getNovyTovar().printContent();
            }
            return "nebol nacita novy tovar";
        }
            );
        inlineAkcie.put("objednaj", ((args, kh, vy)-> {
            String res = objednaj(args,kh.getSklad());
            kh.getPredajna().setKatalog(kh.getSklad().getKatalog());
            return res;
        }));
        inlineAkcie.put("premiestni", ((args, kh, vy)-> premiestni(args,kh)));
        inlineAkcie.put("max-miesto", ((args, kh, vy) -> {
            int[] miesto = kh.getSklad().najdiNajvacsieMiesto();
            return "Sekcia:" + miesto[0] + " Polička: " +miesto[1];
        }));

    }

    /**
     * Vlozi odobrane knihy do inventara
     * @param sklad z ktoreho odoberame knihy
     * @param kniha odoberana kniha
     * @param pocet pocet odoberanych kusov
     * @param pozicia pozicia v sklade - moze byt null
     */
    public void odoberKnihyZRegalu(Sklad sklad, Kniha kniha, int pocet, int[] pozicia){
        if(inventar.getKniha() != null) return;
         if(pozicia == null){
             NoveKnihy r = sklad.getNovyTovar();
             if(r != null && r.existujeKniha(kniha)) {
                 int p = r.odoberKnihy(kniha, pocet);
                 inventar.set(kniha,p);
             }
         }else{
             Regal r = sklad.getSekcie(pozicia[0]).getRegal(pozicia[1]);
             if(r.existujeKniha(kniha)){
                 int p = r.odoberKnihy(kniha,pocet);
                 inventar.set(kniha,p);
             }
         }
    }

    /**
     * @param sklad sklad do ktoreho ukladame knihy
     * @param pozicia pozicia v sklade {sekcia-regal}
     * @param pocet pocet vkladanych knih
     * @return informaciu o uspechu ukladania
     */
    public String umiestniKnihyDoRegalu(Sklad sklad, int[] pozicia, int pocet){
        if(pozicia == null) {
            return "Neplatna pozicia";
        }
        if(inventar.getKniha() == null){
            return "Nemas knihy u seba";
        }
        if(inventar.getPocet() < pocet) pocet = inventar.getPocet();
        int pridane  = sklad.getSekcie(pozicia[0]).getRegal(pozicia[1]).pridajKnihy(inventar.getKniha(),pocet);
        inventar.odoberZKnih(pridane);
        if(inventar.getPocet() == 0) {
            inventar.resetInventar();
            return "vsetky knihy si ulozil";
        }
        return "knihy boli umiestnene a zostali ti knihy v inventari";
    }

    /**
     * @return vrati knihu ktoru ma skladnik v inventari
     */
    public Kniha getKniha(){
        return inventar.getKniha();
    }

    /**
     * @return retayec obsahujuci obsahujuci informacie o skladnikovy pripade obsahu jeho inventara
     */
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

    /**
     * Premiestni knihy z regalu do inych regalov,
     * pripadne z regalu do invenataru,
     * z inventaru do regalu
     * a aj z novehoTovaru do inventartu/regalu
     * @param args argumenty na premiestnenie
     * @param kh   instancia knihkupectva v ktorej premiestnujeme knihy
     * @return informacie o uspechu premiestnovania
     */
    @Override
    public String premiestni(String[] args, Knihkupectvo kh){
        Sklad sklad = kh.getSklad();
        int[] zober = new int[] {-1,-1};
        int[] umiestni = new int[] {-1,-1};
        int p = -1;
        Kniha k = null;
        for(String f : args){
            //pozicia z akej berieme knihy
            if(f.contains("//z")){
                zober[0] = Integer.parseInt(f.substring(3,f.indexOf('-')));
                zober[1] = Integer.parseInt(f.substring(f.indexOf('-')+1));
            }else if(f.contains("//u")){ //pozicia do ktorej vkladame
                umiestni[0] = Integer.parseInt(f.substring(3,f.indexOf('-')));
                umiestni[1] = Integer.parseInt(f.substring(f.indexOf('-')+1));
            }else if(f.contains("i/")){ //katalogove cislo knihy
                k = najdiReferenciuNaKnihu(sklad, Integer.parseInt(f.substring(2)));
            }else if(f.contains("s/") && !(f.contains("i/"))){ //ISBN alebo nazov knihy
                k = najdiReferenciuNaKnihu(sklad, f.replaceAll("_", " ").substring(2));
            }else if(f.contains("/")){ //pocet kusov
                p = Integer.parseInt(f.substring(1));

            }
        }

        //ak neboli zadane hodnoty kam a kde umiestnit knihy
        if(zober[0] == -1) {
            zober = null;
        }
        if(umiestni[0] == -1) {
            umiestni = null;
        }
        //nebol zadany pocet
        if(p == -1) {p = 0;}

        if(inventar.getKniha() != null && zober != null){//ak mal skladnik v inventari knihu ale uz zobral dalsiu
            return "Uz mas knihy v inventari, najprv umiestni tie";
        }else if(inventar.getKniha() != null && k == null){ //ak v inventari bola kniha a pouzivatel kniha nezadal
            if(p ==0) p = inventar.getPocet(); // ak zadany pocet je 0 tak pocet bude pocet knih v invenatari
            return umiestniKnihyDoRegalu(sklad,umiestni,p);
        }else if(k == null){ //nebola zadana kniha a vinventari tiez nie je
            return "nezadal si knihu";
        }else if(inventar.getKniha() == null && umiestni == null) { //ak nebolo urcene kam sa ma kniha ulozit
            odoberKnihyZRegalu(sklad,k,p, zober); // vlozi sa do invenatara
            return "odobral si knihy z regalu";
        }else{ //ak bolo zadane vsetko potrebne
            odoberKnihyZRegalu(sklad,k,p, zober);
            return umiestniKnihyDoRegalu(sklad,umiestni,p);
        }
    }

    /**
     * Spracuje string tak aby sa z neho dalo vyhldat v systeme
     * @param args argumenty vstupujuce do funkcie
     * @param sklad sklad do ktoreho nacitavame objednavany tovar
     * @return informacie o objednamom tovare
     */
    private String objednaj(String[] args, Sklad sklad){
        String f = args[1];
        if(f.matches("\\\"(.*)\\\"")){
            f = f.substring(f.indexOf("\"")+1, f.lastIndexOf("\""));
            return sklad.objednatKnihy(f);
        }

        return  "vstup v zlom formate\n";
    }

}
