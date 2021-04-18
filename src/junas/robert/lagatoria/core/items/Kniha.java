package junas.robert.lagatoria.core.items;

public class Kniha implements InfoKniha{
    private String  isbn, vydavatelstvo;
    private int rok, predaneKusy;
    private double cena;
    private Boolean bestseller;

    private Text text;
    private Obalka obalka;

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
        res += text.getInfo();
        res += "\tISBN: " + isbn + '\n';
        res += "\tcena: " + String.format("%.2f",cena) + "€ - vydavatel: " + vydavatelstvo + " " + rok +"\n";
        res += obalka.getInfo();

        return res;
    }

    public void pridajSucast(InfoKniha cast){
        if(cast instanceof Text){
            text = (Text)cast;
        }else if(cast instanceof Obalka){
            obalka = (Obalka)cast;
        }
    }
    public InfoKniha getSucast(Class t){
        if(t == text.getClass()){
            return text;
        }else if(t == obalka.getClass()){
            return obalka;
        }
        return null;
    }
}
