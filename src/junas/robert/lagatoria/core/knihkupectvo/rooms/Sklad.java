package junas.robert.lagatoria.core.knihkupectvo.rooms;

import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Trieda do ktorej chodi novy tovar
 * Pracuje nad nim Skladnik aj Predajca
 * sklada sa so sekcii a noveho tovaru
 */
public class Sklad  extends Miestnost {

        private Sekcia[] sekcie;
        private NoveKnihy novyTovar;

    /**
     * pocet sekcii v sklade bude nastaveni na 5 sekcii
     * @param observer sledovatel skladu
     */
        public Sklad(Observer observer){
            super(observer);
            init(defaultSize);
        }

    /**
     * @param velkost pocet sekcii kolko chceme v sklade
     * @param observer sledovatel skladu
     */
        public Sklad(int velkost, Observer observer){
            super(observer);
            init(velkost);
        }

    /**
     * Inicializacia skladu
     * @param velkost pocet sekcii z ktorych chceme vyskladat sklad
     */
        @Override
        protected void init(int velkost){
            katalog = new ArrayList<Kniha>();
            this.velkost = velkost;
            sekcie = new Sekcia[velkost];
            for(int i = 0; i < velkost;i++){
                sekcie[i] = new Sekcia();
            }
        }

        public String objednatKnihy(String path){
            try {
                String res = "";
                novyTovar = new NoveKnihy(path);
                res +="Novy tovar obsahuje: \n";
                for(Kniha K : novyTovar.getZoznamKnih()){
                    katalog.add(K);
                }
                res+= novyTovar.printContent();
                printKatalog();
                return res;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "nepodarilo sa nacitat subor";
            } catch (InvalidFormatException e) {
                return  e.getMessage() + "\npodarilo sa nacitat " + e.getLoadedRows() + " riadkov";
            }
        }

    public String objednatKnihy(NoveKnihy noveKnihy){
        novyTovar = noveKnihy;
        String res = "Novy tovar obsahuje: +\n";
        for(Kniha K : novyTovar.getZoznamKnih()){
            katalog.add(K);
        }
        res += novyTovar.printContent();
        printKatalog();
        return res;
    }


    /**
     * Automaticky umiestni novyTovar do sekcii
     * Knihy sa umiestnia iba vtedy
     * ak ich pocet je mensi alebo rovny
     * najprazdnejsiemu regalu v sekciach
     * @return retazec obsahujuci aka kniha a kde bola vlozena + ci sa podarilo vyhodit paletu
     */
        public String umiestniNovyTovar(){
            if(novyTovar == null) {
                return "Novy tovar nebol objednany";
            }
            String res = "";
            for(int i = 0; i < novyTovar.getZoznamKnih().size(); i++){
                Kniha k = novyTovar.getZoznamKnih().get(i);
                int pocet = novyTovar.getPocetKnih(k.getISBN());
                //umiestni knihy knihu a jej pocet do sekcie
                String ul = umiestniKnihy(k, pocet,najdiMiestoKniham(pocet));
                if(!ul.isEmpty()) {
                    novyTovar.odoberKnihy(k,pocet);
                    i--;
                    res += ul + "\n";
                }
            }
            //pokusi sa vyhodit paletu
            return res + novyTovar.vyhodPaletu();
        }

    /**
     * Funckia na umiestnenie knihy a jej poctu na poziciu v sklade
     * @param kniha ukladana kniha
     * @param pocet pocet kusov
     * @param pozicia array obsahujuci cisla: {sekcia, regal}
     * @return retazec obsahujuci aka kniha a kde bola vlozena
     */
        private String umiestniKnihy(Kniha kniha, int pocet, int[]pozicia){
            if(pozicia != null){
                if(sekcie[pozicia[0]].getRegal(pozicia[1]).pridajKnihy(kniha,pocet) == -1)
                {
                    return "Na paletu sa nedaju dat knihy\n" ;
                };
                return "Knihy { "+kniha.getBasicInfo()[0]+" } su umiestnene v " +pozicia[0]+"-"+pozicia[1] + "\n";
            }
            return "";
        }

    /**
     * Najde miesto kniha,, ktore by sme chceli vlozit do sekcie-regalu
     * @param pocet pocet knih ktore si ziadame ulozit do regalu
     * @return poziciu kam sa knihy zmestia {sekcia,regal} ak sa miesto nenaslo vrati null
     */
        private int[] najdiMiestoKniham(int pocet){

            for(int i = 0; i < sekcie.length;i++){
                int m = sekcie[i].najdiMiesto(pocet);
                if(m > -1){
                    return new int[] {i, m};
                }
            }
            return null;
        }

    /**
     * @return poziciu kde sa nachadza naajprazdnejsii regal {sekcia,pozicia}
     */
        public int[] najdiNajvacsieMiesto() {
            int max = 0;
            int indexS = -1;
            int indexM = -1;
            for(int i = 0; i < sekcie.length;i++){
                int m = sekcie[i].najdiNajvacsieMiesto();
                if(m == -1) continue;
                if(max < sekcie[i].getRegal(m).getMiesto()){
                    max = sekcie[i].getRegal(m).getMiesto();
                    indexM = m;
                    indexS = i;
                }
            }
            if(indexM == -1|| indexS == -1) {
                return null;
            }
            return new int[]{indexS,indexM, max};
        }

    /**
     * @return vrati novyTovar, ktory este nebol umiestneny
     */
        public NoveKnihy getNovyTovar(){
            return novyTovar;
        }

    /**
     * @return vrati sekcie skladu
     */
        public Sekcia[] getSekcie() {return sekcie;}

    /**
     * @param i index sekcie
     * @return vrati sekciu ktora sa nachadza na indexe i
     */
        public Sekcia getSekcie(int i) {return sekcie[i];}

    /**
     * @return rati retazec so sekciami + regalmi a knihami ulozenymi v nich
     */
        public String printSklad(){
            String res = "";
            res += "Sklad:\n";
            for(int i = 0; i < sekcie.length;i++){
                res+=("Sekcia: " + i + "\n");
                res+=sekcie[i].printSekcia();
                res += ("\n");
            }
            if(novyTovar != null)
                res += novyTovar.printContent();
            return res;
        }
}
