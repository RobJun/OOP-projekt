package junas.robert.lagatoria.core.items;

import junas.robert.lagatoria.core.utils.enums.Kategoria;

/**
 * Cast knihy
 * Tato cast knihy je pisana autorom a teda obsahuje informacie o autorovi
 * Dalej obsahuje inofromacie a pocte stran,nazve, jazyku v akom bola pisana a kategorie do ktorej spada
 */
public class Text implements CastiKnihy {
    private String autor, nazov, jazyk;
    private int dlzka;
    private Boolean opravene;
    private Kategoria kategoria;

    /**
     * Tento sposob sa pouziva pri nacitani noveho tovaru zo suboru
     * @param nazov nazov textu
     * @param autor meno a prievzisko autora
     * @param jazyk napr. Slovensky jazyk
     * @param dlzka pocet Stran
     * @param kategoria Kategoria knihy
     * @param opravene bola opravena?
     */
    public Text(String nazov,String autor, String jazyk, int dlzka, Kategoria kategoria, Boolean opravene){
        this.opravene = opravene;
        this.autor = autor;
        this.nazov = nazov;
        this.jazyk = jazyk;
        this.dlzka = dlzka;
        this.kategoria = kategoria;
    }

    /**
     * Vytvorenie noveho textu v autorovi
     * @param nazov nazov textu
     * @param autor meno a prievzisko autora
     * @param jazyk napr. Slovensky jazyk
     * @param dlzka pocet Stran
     * @param kategoria Kategoria knihy
     */
    public Text(String nazov,String autor, String jazyk, int dlzka, Kategoria kategoria){
        this.opravene = false;
        this.autor = autor;
        this.nazov = nazov;
        this.jazyk = jazyk;
        this.dlzka = dlzka;
        this.kategoria = kategoria;
    }

    /**
     * @return retazec v tvare "\t[KATEGORIA] autor: nazov [jazyk]\n"
     */
    @Override
    public String getInfo() {
        return "\t["+kategoria.toString()+"] "+autor + ": " + nazov + " [" + jazyk+ "]\n";
    }

    /**
     * Prida k nazvu dalsie znaky
     * @param nazov retazec ktory bude pripeveny k povodemu nazvu
     */
    public void setNazov(String nazov) {
        this.nazov += nazov;
    }

    /**
     * @return nazov textu
     */
    public String getNazov() {
        return nazov;
    }

    /**
     * @return meno a prievzisko autora textu
     */
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

    /**
     * @return skontroluje ci uz kniha bola opravena
     */
    public Boolean isOpravene(){ return opravene; }

    /**
     * @return vrati pocet stran
     */
    public int getDlzka() {
        return dlzka;
    }

    /**
     * Nastvenie noveho poctu stran pre text
     * @param dlzka pocet stran, ktore chceme nastavit
     */
    public void setDlzka(int dlzka) {
        this.dlzka = dlzka;
    }

    /**
     * @return vrati kategoriu do ktorej spada text
     */
    public Kategoria getKategoria() {
        return kategoria;
    }
}
