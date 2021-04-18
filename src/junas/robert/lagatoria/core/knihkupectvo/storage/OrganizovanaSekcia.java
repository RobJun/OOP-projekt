package junas.robert.lagatoria.core.knihkupectvo.storage;

import junas.robert.lagatoria.core.utils.enums.Kategoria;

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
    public String printSekcia(){
        String res = "";
        res += "Typ: " + nazov +"\n";
        for(int i = 0; i < regale.length;i++){
            res += "regal: " + i + " : " + regale[i].getMiesto() + "/" + Regal.miesto +
            regale[i].printContent() + "\n";
        }
        res += '\n';

        return res;
    }
}
