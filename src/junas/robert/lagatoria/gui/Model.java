package junas.robert.lagatoria.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Miestnost;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.FantasyAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.HistoryAutor;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.PoetryAutor;
import junas.robert.lagatoria.gui.TableViews.data.KatalogData;
import junas.robert.lagatoria.gui.TableViews.data.OdoberatelData;
import junas.robert.lagatoria.gui.TableViews.data.TextyNaVydanieData;
import junas.robert.lagatoria.gui.controllers.MainController;

public class Model implements Observer {

    private ObservableList<TextyNaVydanieData> texty = FXCollections.observableArrayList();
    private ObservableList<OdoberatelData> data = FXCollections.observableArrayList();
    private ObservableList<KatalogData> katalogData = FXCollections.observableArrayList();

    private Pouzivatel pouzivatel;
    private Zakaznik zakaznik = new Zakaznik();
    private Predajca predajca = new Predajca("Predavac",22);
    private Skladnik skladnik = new Skladnik("Skladnik", 23);
    private Manazer manazer = new Manazer("Manazer",111,22.4);
    private Distributor distributor = new Distributor("Distributor",123,15);
    private Vydavatelstvo vydavatelstvo = new Vydavatelstvo(manazer,distributor,10, this);


    private String strategyText = "Vydavanie po jednom";
    private String strategyVText = "Vydaj text";

    private MainController controller;

    Model(){
        deserialize();
    }

    public String serialize(){ return Knihkupectvo.serialize("./res/knihkupectvo_oop.ser"); }


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

    private boolean containsKategoria(String text){
        for(Kategoria c : Kategoria.values()){
            if(c.name().equals(text))
                return true;
        }
        return false;
    }

    public String pridajOdoberatelaKategorizovaneho(String text, String text1) {
        if( text == null || text.length() == 0 || text1 == null || text1.length() == 0 || !containsKategoria(text1.toUpperCase())){
            return "Nepodarilo sa vytovit odoberatela";
        }
        spracuj("pridajOdoberatelaKat " + text + " " + text1.toUpperCase());
        return  "Podarilo sa vytvorit odoberatela";
    }

    public String pridajOdoberatelaMinimum(String text, String text1) {
        if( text == null || text.length() == 0 || text1 == null || text1.length() == 0 || !text1.matches("[0-9]+")){
            return "Nepodarilo sa vytovit odoberatela";
        }
        spracuj("pridajOdoberatelaMin " + text + " " + text1);
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

    public void deserialize() {
        Knihkupectvo.deserialize("./res/knihkupectvo_oop.ser");
        Knihkupectvo.getInstance().setObserver(this);
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void notify(Object objekt, Object msg) {
        String vypisTexty = (String)msg;
        if(objekt instanceof Miestnost){
            String[] strings = vypisTexty.split("\n");
            katalogData.remove(0,katalogData.size());
            for(String d : strings){
                katalogData.add(KatalogData.parseText(d));
            }
        }
        else if(vypisTexty.contains("001::")){
            String data = vypisTexty.replace("001::", "");
            texty.remove(0,texty.size());
            String[] strings = data.split("\n");
            if(data.contains("Ziadne texty na vydanie"))
                return;
            for(String d : strings){
                texty.add(TextyNaVydanieData.parseText(d));
            }
            return;
        }else if(vypisTexty.contains("002::")){
            String data = vypisTexty.replace("002::","");
            this.data.remove(0,this.data.size());
            String[] strings = data.split("\n");
            if(data.contains("Ziadne texty na vydanie"))
                return;
            for(String d : strings){
                this.data.add(OdoberatelData.parseText(d));
            }
            return;
        }
        controller.notify(objekt,vypisTexty);
    }

    public ObservableList<TextyNaVydanieData> getTexty() {
        return texty;
    }

    public ObservableList<OdoberatelData> getData() {
        return data;
    }

    public ObservableList<KatalogData> getKatalogData() {
        Knihkupectvo.getInstance().printKatalog();
        return katalogData;}

}
