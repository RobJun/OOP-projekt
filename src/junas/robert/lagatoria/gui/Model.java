package junas.robert.lagatoria.gui;

import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;

public class Model {
    private Pouzivatel pouzivatel;
    private Zakaznik zakaznik = new Zakaznik();
    private Predajca predajca = new Predajca("Predavac",22);
    private Skladnik skladnik = new Skladnik("Skladnik", 23);
    private Manazer manazer = new Manazer("Manazer",111,22.4);
    private Distributor distributor = new Distributor("Distributor",123,15);
    private Vydavatelstvo vydavatelstvo = new Vydavatelstvo(manazer,distributor,10);


    private String strategyText = "Vydavanie po jednom";
    private String strategyVText = "Vydaj text";

    Model(){
        Knihkupectvo.deserialize("./res/knihkupectvo_oop.ser");
    }

    public String serialize(){
        return Knihkupectvo.serialize("./res/knihkupectvo_oop.ser");
    }


    public Pouzivatel getPouzivatel() {
        return pouzivatel;
    }

    public String spracuj(String command){
        return pouzivatel.spracuj(command.split(" "),vydavatelstvo);
    }


    public String objednaj(String command){
        if(command.length() == 0){
            return "";
        }
        return pouzivatel.spracuj(("objednaj \"" + command +"\" | n-umiestni").split(" "),null);
    }

    public String[] changeStrategy(){
        if(strategyText.compareTo("Vydavanie po jednom") == 0){
            spracuj("Strategia true");
            strategyText = "Vydavanie vsetkych";
            strategyVText = "Vydaj texty";
        }else{
            spracuj("Strategia false");
            strategyText =  "Vydavanie po jednom";
            strategyVText = "Vydaj text";
        }

        return new String[]{strategyText,strategyVText};
    }

    public String otvorZatvor(){
        if(!Knihkupectvo.getInstance().getPredajna().isOtvorene()){
            spracuj("otvor");
            return "Zavri";
        }else{
            spracuj("zavri");
            return "Otvor";
        }

    }


    public String zakaznikVstup(){
        if(Knihkupectvo.getInstance().getPredajna().getZakaznik() != null) return "";
        if(!Knihkupectvo.getInstance().getPredajna().isOtvorene()){
            return "Predajna je zavreta";
        }else {
            Knihkupectvo.getInstance().getPredajna().setZakaznik(zakaznik);
            return "Vstupil si do predajne";
        }
    }


    public void changeUser(LoggedIn pouzivatel){
        switch (pouzivatel) {
            case SKLADNIK:
                this.pouzivatel = skladnik;
                break;
            case PREDAJCA:
                this.pouzivatel = predajca;
                break;
            case ZAKAZNIK:
                this.pouzivatel = zakaznik;
                break;
            case MANAZER:
                this.pouzivatel = manazer;
                break;
            case DISTRI:
                this.pouzivatel = distributor;
        }
    }


    public String prines(String text) {
        if(pouzivatel instanceof Skladnik) {
            if (text.length() == 0) {
                return "do input: n/ref /pocet //zX-X //uX-X  - s/ref = nazov/isbn/katalogove cislo knihy\n " +
                        "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t moze byt null ak mas knihu v inventari] \n" +
                        "\t\t\t\t\t\t\t\t\t\t- zX-X = pozicia z ktorej beriem \n" +
                        "\t\t\t\t\t\t\t\t\t\t- uX-X = pozicia na ktoru umiestnujeme \n" +
                        "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat";
            }
            return spracuj("premiestni " + text);
        }else if(pouzivatel instanceof Zakaznik){
            if(text.length() == 0) {
                return "do input:  n/ref /pocet  \n" +
                        "\t\t\t\t\t\t\t\t\t\t- s/ref = nazov/isbn/katalogove cislo knihy\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                        "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat";
            }
            return spracuj("zober " + text);
        }else if(pouzivatel instanceof Predajca){
            if(text.length() == 0) {
                return "do input:  n/ref /pocet \n" +
                        "\t\t\t\t\t\t\t\t\t\t- s/ref = nazov/isbn/katalogove cislo knihy\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t moze byt null ak mas knihu v inventari] \n" +
                        "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat";
            }
            return spracuj("prines " + text);
        }
        return "error -- neplatny pouzivatel";
    }

    public String odstranOdoberatela(String text) {
        if(text == null || text.length() == 0 || !text.matches("[0-9]+")){
            return "Nepodarilo sa odstranit odoberatela";
        }
        return spracuj("odstranOdoberatela " + text);
    }

    public String pridajOdoberatela(String text) {
        if( text == null || text.length() == 0){
            return "Nepodarilo sa vytovit odoberatela";
        }
        spracuj("pridajOdoberatela " + text);
        return  "Podarilo sa vytvorit odoberatela";
    }

    public String removeAutor(String text) {
        if(text == null || text.length() == 0 || !text.matches("[0-9]+") ){
            return "Nepodarilo sa vymazat autora";
        }
        spracuj("odoberA " + text);
        return "Odstranenie autora prebehlo uspesne";
    }

    public String createAutor(String meno, String prievzisko, String typ) {
        if( meno == null || meno.length() == 0  || prievzisko == null ||prievzisko.length() == 0 ){
            return "Nepodarilo sa vytvorit autora";
        }
        String typCreate = null;
        if (typ.compareTo("fantazy autor") == 0) {
            typCreate = FantasyAutor.class.getName();
        }else if(typ.compareTo("history autor") == 0){
            typCreate = HistoryAutor.class.getName();
        }else{
            typCreate = PoetryAutor.class.getName();
        }
        spracuj("pridajA " + typCreate + " " + meno + " " + prievzisko);
        return "Podarilo sa vytvorit autora";
    }

    public void deserilize() {
        Knihkupectvo.deserialize("./res/knihkupectvo_oop.ser");
    }
}
