package junas.robert.lagatoria.core.knihkupectvo.items;

import junas.robert.lagatoria.core.utils.Kategoria;

public class Text implements InfoKniha{
    private String autor, nazov, jazyk;
    private int dlzka;
    private Boolean opravene;
    private Kategoria kategoria;

    public Text(String nazov,String autor, String jazyk, int dlzka, Kategoria kategoria, Boolean opravene){
        this.opravene = opravene;
        this.autor = autor;
        this.nazov = nazov;
        this.jazyk = jazyk;
        this.dlzka = dlzka;
        this.kategoria = kategoria;
    }
    public Text(String autor, String nazov, String jazyk, int dlzka, Kategoria kategoria){
        this.opravene = false;
        this.autor = autor;
        this.nazov = nazov;
        this.jazyk = jazyk;
        this.dlzka = dlzka;
        this.kategoria = kategoria;
    }
    @Override
    public void getInfo() {
        System.out.println("\t["+kategoria.toString()+"] "+autor + ": " + nazov + " [" + jazyk+ "]");
    }

    public void oprav(){ opravene = true;}

    public Boolean isOpravene(){ return opravene; }
}
