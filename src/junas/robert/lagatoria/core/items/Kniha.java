package junas.robert.lagatoria.core.items;

import java.util.ArrayList;

/**
 * Trieda v ktorej sa kumuluju casti Text a Obalka
 * Hlavny vystup z vydavatelstva a , s ktorym sa obchoduje v knihkupectve
 */
public class Kniha implements CastiKnihy {
    private String  isbn, vydavatelstvo;
    private int rok, predaneKusy;
    private double cena;
    private Boolean bestseller;

    /**
     * Casti knihy ako su Text a Obalka
     * kniha moze mat viac Textov ale iba jednu obalku
     */
    private ArrayList<CastiKnihy> casti;

    /**
     * @param isbn identifikacne oznacenie knihy
     * @param vydavatelstvo meno vydavatelstva ktore knihu vydalo
     * @param rok v akom kniha vysla
     * @param cena cena knihy
     */
    public Kniha(String isbn, String vydavatelstvo, int rok, double cena){
        this.isbn = isbn;
        this.vydavatelstvo = vydavatelstvo;
        this.rok = rok;
        this.cena = cena;
        bestseller = false;
        casti = new ArrayList<>();
    }

    /**
     * @return retazec obsahujucci informacie o castiach knihy a samotnej knihe
     */
    @Override
    public String getInfo() {
        String res = "";
        for(CastiKnihy c : casti){
           res += c.getInfo();
        }
        res += "\tISBN: " + isbn + '\n';
        res += "\tcena: " + String.format("%.2f",cena) + "€ - vydavatel: " + vydavatelstvo + " " + rok +"\n";

        return res;
    }

    /**
     * Moze sa maximalne pridat jedna obalka ale viacero textov s tym
     * ze prvy text v zozname sa povazuje za hlavny
     * @param cast prida sa sucast do knihy do knihy
     */
    public void pridajSucast(CastiKnihy cast){
        if(cast instanceof Obalka){
            for(CastiKnihy c : casti){
                if(c instanceof Obalka){
                    return;
                }
            }
        }
        casti.add(cast);
    }

    /**
     * @param clazz sucast knihy, ktoru chceme najst - Obalka/text
     * @return vracia prvu instanciu triedy ktoru hladame
     */
    public CastiKnihy getSucast(Class<? extends CastiKnihy> clazz){
        for(CastiKnihy c : casti){
            if(clazz == c.getClass()){
                return c;
            }
        }
        return null;
    }

    /**
     * @return identifikaciu knihy
     */
    public String getISBN(){
        return isbn;
    }

    /**
     * @return array zlozeny z {nazvu Textu, Autora Textu, ISBN, Vydavatelstvo}
     */
    public String[] getBasicInfo() {
        for(CastiKnihy c : casti) {
            if(c instanceof Text) {
                Text text = (Text)c;
                return new String[]{text.getNazov(), text.getAutor(), isbn, vydavatelstvo};
            }
        }
        return null;
    }

    /**
     * @return cenu knihy
     */
    public double getCena(){ return cena;}

    /**
     * ak pocet predanych kníh presiahne 100 kusov tak sa nastavi na bestseller
     */
    public void setBestseller(){ if(predaneKusy > 100) bestseller = true; }

    /**
     * Ku predanym kusom sa pripocitaju predane kusy
     * @param pocet predany pocet
     */
    public void predaj(int pocet) {
        this.predaneKusy+= pocet;
        setBestseller();
    }

}
