package junas.robert.lagatoria.core.knihkupectvo.rooms;

import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Sklad  extends Miestnost {

        private Sekcia[] sekcie;
        private NoveKnihy novyTovar;

        public Sklad(Observer observer){
            super(observer);
            init(defaultSize);
        }
        public Sklad(int velkost, Observer observer){
            super(observer);
            init(velkost);
        }

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


        public String umiestniNovyTovar(){
            if(novyTovar == null) {
                return "Novy tovar nebol objednany";
            }
            String res = "";
            for(int i = 0; i < novyTovar.getZoznamKnih().size(); i++){
                Kniha k = novyTovar.getZoznamKnih().get(i);
                int pocet = novyTovar.getPocetKnih(k.getISBN());
                String ul = umiestniKnihy(k, pocet,najdiMiestoKniham(pocet));
                if(!ul.isEmpty()) {
                    novyTovar.odoberKnihy(k,pocet);
                    i--;
                    res += ul + "\n";
                }
            }
            return res + novyTovar.vyhodPaletu();
        }

        private String umiestniKnihy(Kniha k, int pocet, int[]pozicia){
            if(pozicia != null){
                if(sekcie[pozicia[0]].getRegal(pozicia[1]).pridajKnihy(k,pocet) == -1)
                {
                    return "Na paletu sa nedaju dat knihy\n" ;
                };
                return "Knihy { "+k.getBasicInfo()[0]+" } su umiestnene v " +pozicia[0]+"-"+pozicia[1] + "\n";
            }
            return "";
        }

        private int[] najdiMiestoKniham(int pocet){

            for(int i = 0; i < sekcie.length;i++){
                int m = sekcie[i].najdiMiesto(pocet);
                if(m > -1){
                    return new int[] {i, m};
                }
            }
            return null;
        }

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

        public NoveKnihy getNovyTovar(){
            return novyTovar;
        }
        public Sekcia[] getSekcie() {return sekcie;}
        public Sekcia getSekcie(int i) {return sekcie[i];}

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
