package junas.robert.lagatoria.core.knihkupectvo.storage;

import junas.robert.lagatoria.core.utils.Kategoria;

public class OrganizovanaSekcia extends Sekcia{
    private Kategoria nazov;
    private boolean kategorizovane = false;


    public OrganizovanaSekcia(Kategoria k){
        super();
        init(k,defaultSize);
        kategorizovane = false;
    }
    public OrganizovanaSekcia(Kategoria k, int pocet){
        super();
        init(k,pocet);
    }

    private void init(Kategoria kat,int pocet){
        nazov = kat;
        this.regale = new Regal[pocet];
        for(int i = 0; i < pocet; i++){
            regale[i] = new Regal();
        }
    }

    @Override
    public int najdiMiesto(int pocet) {
        return 0;
    }

    @Override
    public void printSekcia(){
        System.out.println("Typ: " + nazov);
        for(int i = 0; i < regale.length;i++){
            System.out.println("regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto);
            regale[i].printContent();
        }
        System.out.println();
    }
}
