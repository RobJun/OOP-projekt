package junas.robert.lagatoria.core.knihkupectvo.storage;

import junas.robert.lagatoria.core.items.Kniha;

import java.util.*;

/**
 * kazdy regal moze obsahovat max 300 kusov knih
 * Obsahuje knihy a ich pocty
 * regal sa agreguje v sekciach
 */
public class Regal implements java.io.Serializable{
    protected ArrayList<Kniha> zoznamKnih;
    //pocty kusov sa ukladaju podla ISBN
    protected HashMap<String, Integer> pocetKnih;
    /**
     * prednastavena hodnota velkosti regalu
     */
    public final static int miesto = 300;
    private int volneMiesto;

    protected Regal(){
        init();
    }


    /**
     * Inicializacia regalu
     */
    private void init(){
        zoznamKnih = new ArrayList<Kniha>();
        pocetKnih = new HashMap<String, Integer>();
        volneMiesto = miesto;
    }

    /**
     * Vlozi knihy a pocet do regalu
     * @param kniha pridavana kniha
     * @param pocet pocet kusov
     * @return vratenie poctu, ktory sa realne ulozil
     */
    public int pridajKnihy(Kniha kniha, int pocet){
        if(pocet == 0) return 0;
        if(volneMiesto < pocet){
            pocet = volneMiesto;
        }
        pridajKnihyP(kniha,pocet);
        volneMiesto -= pocet;

        return pocet;
    }

    /**
     * Odoberie pocet kusov knihy z regalu
     * @param kniha odoberana kniha
     * @param pocet pocet odoberanych kusov
     * @return vracia pocet ktory sa podarilo odobrat z regalu
     */
    public int odoberKnihy(Kniha kniha, int pocet){
        int v = odoberKnihyP(kniha,pocet);
        volneMiesto += v;
        return v;

    }

    /**
     * @return vrati zostavajuce volne miesto
     */
    public int getMiesto(){
        return volneMiesto;
    }

    /**
     * @return vrati knihy ulozene v regali
     */
    public ArrayList<Kniha> getZoznamKnih(){ return zoznamKnih; }

    /**
     * @return vrati mapu vlozenych knih podla ulozenych podla ISBN kodu knih
     */
    public HashMap<String,Integer> getPocetKnih() {return  pocetKnih;}

    /**
     * @param isbn identifikacny kod knihy ISBN
     * @return pocet knih ulozenych pod ISBN
     */
    public int getPocetKnih(String isbn){ return pocetKnih.get(isbn);}

    /**
     * @param kniha kniha o ktorej chceme zisitit pocet
     * @return pocet knih ulozenych pod knihou
     */
    public int getPocetKnih(Kniha kniha){ return pocetKnih.get(kniha.getISBN());}

    /**
     * @return vrati pocet kusov knihy a inforamcii o knihach
     */
    public String printContent(){
        String res = "";
        for(int i = 0; i < zoznamKnih.size(); i++){
            res +=" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n" +
            zoznamKnih.get(i).getInfo();
        }
        return res;
    }

    /**
     * Kontrola ci sa kniha nachadza v regali
     * @param kniha kniha ktoru kontrolujeme ci sa nachadza v zozname
     * @return vrati true ak sa kniha nachadza v regali inak vracia fals
     */
    public boolean existujeKniha(Kniha kniha){
        for(int i = 0 ; i < zoznamKnih.size();i++){
            if(zoznamKnih.get(i).getISBN().equals(kniha.getISBN())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Prida knihu do regalu
     * @param kniha pridavana kniha
     * @param pocet pridavane kusy
     */
    protected void pridajKnihyP(Kniha kniha, int pocet){
        if(!existujeKniha(kniha)){
            zoznamKnih.add(kniha);
            pocetKnih.put(kniha.getISBN(),pocet);
        }else{
            pocetKnih.replace(kniha.getISBN(),pocetKnih.get(kniha.getISBN()) + pocet);
        }
    }

    /**
     * Odstrani knihy z regalu
     * @param kniha odstranovana kniha
     * @param pocet pocet odstranovanych kusov
     * @return pocet vymazanych kusov ak sa nepodarilo odobrat, ak sa kniha nenachadza v regali vrati sa -1
     */
    protected int odoberKnihyP(Kniha kniha, int pocet){
        if(existujeKniha(kniha)){
            int aPocet = getPocetKnih(kniha.getISBN());
            int vymazanych = pocet;
            if(aPocet <= pocet){
                vymazanych = pocet;
                zoznamKnih.remove(kniha);
                pocetKnih.remove(kniha.getISBN());
            }else{
                pocetKnih.replace(kniha.getISBN(),aPocet-pocet);
            }

            return vymazanych;
        }
        return -1;
    }
}
