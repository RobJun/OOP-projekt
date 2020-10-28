package junas.robert.minerva.core.rooms;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.storage.NoveKnihy;
import junas.robert.minerva.core.storage.Regal;
import junas.robert.minerva.core.storage.Sekcia;

public class Sklad  extends Miestnost {

        Sekcia[] sekcie;
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
            this.velkost = velkost;
            sekcie = new Sekcia[velkost];
            for(int i = 0; i < velkost;i++){
                sekcie[i] = new Sekcia(5);
            }
        }

        public void objednajAUmiestni(String path){
            objednatKnihy(path);
            for(int i = 0; i < novyTovar.getZoznamKnih().size(); i++){
                Kniha k = novyTovar.getZoznamKnih().get(i);
                int pocet = novyTovar.getPocetKnih(k.getISBN());
                if(umiestniKnihy(k, pocet,najdiMiestoKniham(pocet))) {
                    novyTovar.setExhaust(i,true);
                }
            }
            if(novyTovar.isExhausted()){
                    novyTovar.VyhodPaletu();
            }

        }


        public void printSklad(){
            for(int i = 0; i < sekcie.length;i++){
                System.out.println("Sekcia: " + i);
                sekcie[i].printSekcia();
            }
        }

        private void objednatKnihy(String path){
             novyTovar = new NoveKnihy(path);
        }

        public boolean umiestniKnihy(Kniha k, int pocet, int[]pozicia){
            if(pozicia != null){
                sekcie[pozicia[0]].getRegal(pozicia[1]).pridajKnihy(k,pocet);
                System.out.println("Knihy su umiestnene v " +pozicia[0]+"-"+pozicia[1]);
                System.out.println();
                return true;
            }
            return false;
        }

        public int[] najdiMiestoKniham(int pocet){

            for(int i = 0; i < sekcie.length;i++){
                Regal[] reg = sekcie[i].getRegale();
                for(int j = 0; j < reg.length;j++){
                    if(pocet <= reg[j].getMiesto()){
                        return new int[] {i,j};
                    }
                }
            }
            return null;
        }
}
