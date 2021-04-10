package junas.robert.lagatoria.core.items;

import javafx.beans.property.StringProperty;
import junas.robert.lagatoria.gui.View;

import java.util.ArrayList;

public class Kniha implements InfoKniha{
    private String  isbn, vydavatelstvo;
    private int rok, predaneKusy;
    private double cena;
    private Boolean bestseller;

    private ArrayList<InfoKniha> casti = new ArrayList<InfoKniha>();

    public Kniha(String isbn, String vydavatelstvo, int rok, double cena){
        this.isbn = isbn;
        this.vydavatelstvo = vydavatelstvo;
        this.rok = rok;
        this.cena = cena;
        bestseller = false;
    }

    public String getISBN(){
        return isbn;
    }
    public String[] getBasicInfo() {
        Text text =  (Text)casti.get(0);
        return new String[] {text.getNazov(),text.getAutor(),isbn,vydavatelstvo};
    }
    public double getCena(){ return cena;}

    public void setBestseller(){ if(predaneKusy > 100) bestseller = true; }
    public void predaj(int pocet) {
        this.predaneKusy+= pocet;
        setBestseller();
    }

    @Override
    public String getInfo() {
        String res = "";
        res += casti.get(0).getInfo();
        res += "\tISBN: " + isbn + '\n';
        res += "\tcena: " + String.format("%.2f",cena) + "€ - vydavatel: " + vydavatelstvo + " " + rok +"\n";
        res += casti.get(1).getInfo();

        return res;
    }

    public void pridajSucast(InfoKniha cast){
        casti.add(cast);
    }
    public InfoKniha getSucast(int index){
        return casti.get(index);
    }
}
