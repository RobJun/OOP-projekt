package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.utils.Kategoria;

public class Sekcia {
    private Kategoria nazov;
    private Regal[] regale;

    public static final int defaultSize = 3;


    public Sekcia(){init(Kategoria.UNDEFINED,defaultSize);}

    public Sekcia(String nazov){
        init(Kategoria.valueOf(nazov), defaultSize);
    }
    public Sekcia(Kategoria nazov){
        init(nazov, defaultSize);
    }

    public Sekcia(int pocetRegalov){
        init(Kategoria.UNDEFINED, pocetRegalov);
    }

    public Sekcia(int pocetRegalov, String nazov){
        init(Kategoria.valueOf(nazov), pocetRegalov);
    }
    public Sekcia(int pocetRegalov, Kategoria nazov){
        init(nazov, pocetRegalov);
    }



    protected void init(Kategoria kat, int pocet){
        this.nazov = nazov;
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }


    public Regal[] getRegale(){
        return regale;
    }

    public Regal getRegal(int i){
        return regale[i];
    }


    public void printSekcia(){
        for(int i = 0; i < regale.length;i++){
            System.out.println("regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto);
            regale[i].printContent();
        }
        System.out.println();
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
}
