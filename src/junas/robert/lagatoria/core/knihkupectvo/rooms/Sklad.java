package junas.robert.lagatoria.core.knihkupectvo.rooms;

import junas.robert.lagatoria.core.knihkupectvo.storage.NoveKnihy;
import junas.robert.lagatoria.core.knihkupectvo.storage.Sekcia;
import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.gui.View;

import java.util.ArrayList;

public class Sklad  extends Miestnost {

        private Sekcia[] sekcie;
        private NoveKnihy novyTovar;

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
            View.printline("Novy tovar obsahuje: ");
            for(Kniha K : novyTovar.getZoznamKnih()){
                katalog.add(K);
            }
            novyTovar.printContent();
            System.out.println();
        }

    public void objednatKnihy(NoveKnihy noveKnihy){
        novyTovar = noveKnihy;
        View.printline("Novy tovar obsahuje: ");
        for(Kniha K : novyTovar.getZoznamKnih()){
            katalog.add(K);
        }
        //novyTovar.printContent();
        System.out.println();
    }


        public void umiestniNovyTovar(){
            if(novyTovar == null) {
                View.printline("Novy tovar nebol objednany");
                return;
            }
            for(int i = 0; i < novyTovar.getZoznamKnih().size(); i++){
                Kniha k = novyTovar.getZoznamKnih().get(i);
                int pocet = novyTovar.getPocetKnih(k.getISBN());
                if(umiestniKnihy(k, pocet,najdiMiestoKniham(pocet)) == 1) {
                    novyTovar.odoberKnihy(k,pocet);
                    i--;
                }
            }
            novyTovar.vyhodPaletu();

        }

        private int umiestniKnihy(Kniha k, int pocet, int[]pozicia){
            if(pozicia != null){
                sekcie[pozicia[0]].getRegal(pozicia[1]).pridajKnihy(k,pocet);
                View.printline("Knihy { "+k.getBasicInfo()[0]+" } su umiestnene v " +pozicia[0]+"-"+pozicia[1]);
                View.printline("");
                return 1;
            }
            return 0;
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

        public void printSklad(){
            View.printline("Sklad:");
            for(int i = 0; i < sekcie.length;i++){
                View.printline("Sekcia: " + i);
                sekcie[i].printSekcia();
                View.printline("");
            }
        }
}
