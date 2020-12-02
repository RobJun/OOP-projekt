package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.utils.Kategoria;

public class Sekcia implements java.io.Serializable{
    protected Regal[] regale;

    public static final int defaultSize = 3;


    public Sekcia(){init(defaultSize);}
    public Sekcia(int pocetRegalov){
        init(pocetRegalov);
    }



    private void init(int pocet){
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }


    public Regal[] getRegal(){
        return regale;
    }

    public Regal getRegal(int i){
        return regale[i];
    }

    public int najdiMiesto(int pocet){
        for(int j = 0; j < regale.length;j++){
            if(pocet <= regale[j].getMiesto()){
               return j;
            }
        }
        return -1;
    }

    public int najdiNajvacsieMiesto() {
        int max = 0;
        int index = -1;
        for(int i = 0; i < regale.length;i++){
         if(max < regale[i].getMiesto()){
             max = regale[i].getMiesto();
             index = i;
         }
        }
        return index;
    }


    public void printSekcia(){
        for(int i = 0; i < regale.length;i++){
            System.out.println("regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto);
            regale[i].printContent(false);
        }
        System.out.println();
    }
}
