package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.storage.NoveKnihy;
import junas.robert.minerva.core.storage.Sekcia;

import java.util.ArrayList;

public class Sklad  extends Miestnost {

        Sekcia[] sekcie;
        private NoveKnihy novyTovar;
        private ArrayList<Kniha> katalog;

        public Sklad(){
            super();
            init(defaultSize);
        }
        public Sklad(int velkost){
            super();
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

        public void objednatKnihy(String path){
            novyTovar = new NoveKnihy(path);
            System.out.println("Novy tovar obsahuje: ");
            for(Kniha K : novyTovar.getZoznamKnih()){
                katalog.add(K);
            }
            novyTovar.printContent();
            System.out.println();
        }

        public void umiestniNovyTovar(){
            if(novyTovar == null) {
                System.out.println("Novy tovar nebol objednany");
                return;
            }
            for(int i = 0; i < novyTovar.getZoznamKnih().size(); i++){
                Kniha k = novyTovar.getZoznamKnih().get(i);
                int pocet = novyTovar.getPocetKnih(k.getISBN());
                if(umiestniKnihy(k, pocet,najdiMiestoKniham(pocet))) {
                    novyTovar.odoberKnihy(k,pocet);
                    i--;
                }
            }
            novyTovar.vyhodPaletu();

        }

        private boolean umiestniKnihy(Kniha k, int pocet, int[]pozicia){
            if(pozicia != null){
                sekcie[pozicia[0]].getRegal(pozicia[1]).pridajKnihy(k,pocet);
                System.out.println("Knihy { "+k.getBasicInfo()[0]+" } su umiestnene v " +pozicia[0]+"-"+pozicia[1]);
                System.out.println();
                return true;
            }
            return false;
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
        public ArrayList<Kniha> getKatalog() {return katalog;}


        public void printKatalog() {
            int i = 0;
            for(Kniha k : katalog){
                String[] s = k.getBasicInfo();
                System.out.println(i++ + ": " + s[0] + " - " + s[1] + " - { " +s[2] + " - " +s[3] + " }");

            }
        }

        public void printSklad(){
            for(int i = 0; i < sekcie.length;i++){
                System.out.println("Sekcia: " + i);
                sekcie[i].printSekcia();
            }
        }
}
