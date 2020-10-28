package junas.robert.minerva.core.items;

import junas.robert.minerva.core.utils.Kategoria;

public class Kniha {
    private String nazov, autor, isbn;
    private Kategoria kategoria;
    private int pocetStran;
    private float cena;

    public Kniha(String Nazov, String Autor, String ISBN, int PocetStran, float cena, Kategoria kategoria){
        nazov = Nazov;
        autor = Autor;
        isbn = ISBN;
        pocetStran = PocetStran;
        this.cena = cena;
        this.kategoria = kategoria;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public String getISBN(){
        return isbn;
    }

    public void printContent() {
        System.out.print("\t"+ kategoria.toString() +": "+ autor + ": " + nazov +" ("+ isbn + ") - cena: " + cena + "€ " + pocetStran + " stran  ");
    }
}
