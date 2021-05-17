package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.Kniha;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Trieda pre ukladanie kníh pre Zakaznika
 */
public class Kosik {
    private ArrayList<Kniha> knihy = new ArrayList<>();
    private HashMap<String,Integer> pocetKnih = new HashMap<>();

    /**
     * Vlozenie knih do kosika a poctu, pripadne iba poctu
     * @param k kniha ktorá sa má pridať do kosika; ak sa kniha v zozname nachadza tak sa iba pripocita pocet k povodnemu poctu
     * @param pocet kusov knihy ktoru vkladame
     */
    public void add(Kniha k, int pocet){
        if(knihy.contains(k)){
            int z = pocetKnih.get(k.getISBN());
            pocetKnih.replace(k.getISBN(),z+pocet);
        }
        else {
            knihy.add(k);
            pocetKnih.put(k.getISBN(), pocet);
        }
    }


    /**
     * Odstranenie knih z kosika, ak je pocet vacsi alebo rovny poctu kusov knihy v kosiku,
     * tak sa odstrani cela kniha aj s poctom inak sa iba odcita pocet od poctu kusov
     * @param k kniha ktoru chceme odobrat
     * @param pocet aky pocet chceme odstranit z kosika
     */
    public void remove(Kniha k, int pocet){
        int p = pocetKnih.get(k.getISBN());
        if(pocet >= p) {
            knihy.remove(k);
            pocetKnih.remove(k.getISBN());
        }else {
            pocetKnih.replace(k.getISBN(),p-pocet);
        }
    }


    /**
     * @return String, v ktorom su inofrmacie o knihach v kosiku a pocte vlozenych
     */
    public String vypisKosik(){
        String res = "";
        if(knihy.isEmpty()) {
            res += "kosik je prazdny";
            return res;
        }
        for(int i = 0; i < knihy.size(); i++){
        	res += "\t[" + pocetKnih.get(knihy.get(i).getISBN()) + "]\n";
            res += knihy.get(i).getInfo() + '\n';
        }

        return res;
    }


    /**
     * @return informacie o knihach v kosiku
     */
    public ArrayList<Kniha> getKnihy(){
        return knihy;
    }

    /**
     * @param isbn knihy ktoru hladame v kosiku
     * @return pocet knih ulozenych v kosiku
     */
    public int getPocet(String isbn){
        return pocetKnih.get(isbn);
    }
}
