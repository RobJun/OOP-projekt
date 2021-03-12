package junas.robert.lagatoria.core.knihkupectvo.items;

import junas.robert.lagatoria.core.utils.Kategoria;
import junas.robert.lagatoria.core.utils.Vazba;

import java.util.ArrayList;

public class Kniha implements InfoKniha{
    private String nazov, autor, isbn, jazyk, vydavatelstvo;
    private Kategoria kategoria;
    private Vazba vazba;
    private int pocetStran, rok, predaneKusy;
    private float cena;

    private ArrayList<InfoKniha> casti = new ArrayList<InfoKniha>();


    public Kniha(Kategoria k){
        init("Sdsda","dasd","ISBN9321231",300, (float) 10.99,k,"Slovensk",Vazba.BROZOVANA,"dasdsad",2000);
    }


    public Kniha(String[] data){
        init(data[0], data[1],data[2],
                Integer.parseInt(data[4]), Float.parseFloat(data[5]), Kategoria.valueOf(data[6]),
                data[7],Vazba.valueOf(data[8]),data[9],Integer.parseInt(data[10]));
    }

    public Kniha(String nazov, String autor,String isbn, String vydavatelstvo, int rok, float cena){
        this.nazov = nazov;
        this.autor = autor;
        this.isbn = isbn;
        this.vydavatelstvo = vydavatelstvo;
        this.rok = rok;
        this.cena = cena;
    }


    private void init(String Nazov, String Autor, String ISBN,
                      int PocetStran, float cena, Kategoria kategoria,
                      String Jazyk, Vazba v, String vydavatelstvo,int rok){
        nazov = Nazov;
        autor = Autor;
        isbn = ISBN;
        pocetStran = PocetStran;
        this.cena = cena;
        this.kategoria = kategoria;
        jazyk = Jazyk;
        vazba = v;
        this.vydavatelstvo = vydavatelstvo;
        this.rok = rok;
        this.predaneKusy =0;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }
    public String getISBN(){
        return isbn;
    }
    public String[] getBasicInfo() { return new String[] {nazov,autor,isbn,vydavatelstvo};}
    public float getCena(){ return cena;}

    public void setBestseller(){
        if(predaneKusy > 100) kategoria = Kategoria.BESTSELLER;
    }
    public void predaj(int pocet) {
        this.predaneKusy+= pocet;
        setBestseller();
    }

    public void printContent() {
        /*System.out.print("\t"+ kategoria.toString() +": ("+jazyk+") "+ autor + ": " +
                nazov +" ("+ isbn + ") - cena: " + cena + "€ - " + pocetStran +
                " stran - " + vazba.toString() + " vazba - vydavatel: " + vydavatelstvo + " " + rok);*/

        getInfo();
    }

    @Override
    public void getInfo() {
        casti.get(0).getInfo();
        System.out.println("\tcena: " + cena + "€ - vydavatel: " + vydavatelstvo + " " + rok);
        casti.get(1).getInfo();
    }

    public void pridajSucast(InfoKniha cast){
        casti.add(cast);
    }
    public InfoKniha getSucast(int index){
        return casti.get(index);
    }
}