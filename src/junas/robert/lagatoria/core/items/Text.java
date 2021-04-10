package junas.robert.lagatoria.core.items;

import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.gui.View;

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
    public Text(String nazov,String autor, String jazyk, int dlzka, Kategoria kategoria){
        this.opravene = false;
        this.autor = autor;
        this.nazov = nazov;
        this.jazyk = jazyk;
        this.dlzka = dlzka;
        this.kategoria = kategoria;
    }
    @Override
    public String getInfo() {
        return "\t["+kategoria.toString()+"] "+autor + ": " + nazov + " [" + jazyk+ "]\n";
    }

    public void setNazov(String nazov) {
        this.nazov += nazov;
    }

    public String getNazov() {
        return nazov;
    }

    public String getAutor() {
        return autor;
    }


    /**
     * oznaci text za opraveny
     * @return pocet najdenych chyb v texte
     */
    public int oprav(){
        opravene = true;
        return (int)(Math.random()*(dlzka/2));
    }

    public Boolean isOpravene(){ return opravene; }

    public int getDlzka() {
        return dlzka;
    }

    public void setDlzka(int dlzka) {
        this.dlzka = dlzka;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }
}
