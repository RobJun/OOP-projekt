package junas.robert.lagatoria.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.LoggedIn;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.lang.reflect.Array;


public class Controller {
    private Pouzivatel pouzivatel;
    private Zakaznik zakaznik = new Zakaznik();
    private Predajca predajca = new Predajca("Predavac",22);
    private Skladnik skladnik = new Skladnik("Skladnik", 23);
    private Manazer manazer = new Manazer("manazer", 24,8.5);
    private StackPane pane;


    private StackPane manazerOkno = new StackPane();
    private HBox zakaznikOkno = new HBox();
    private HBox predajcaOkno = new HBox();
    private HBox skladnikOkno = new HBox();
    private StackPane def = new StackPane();


    public static TextArea out = new TextArea();
    static TextField input = new TextField();

    private static StringProperty textRecu = new SimpleStringProperty();


    public Controller(StackPane pane){
        this.pane = pane;
        input.setPrefWidth(1000);

        out.textProperty().bind(textRecu);

        createPredajcaButtons();
        createZakaznikButtons();
        createManazerButtons();
        createSkladnikButtons();

        Text text = new Text("Prihlaste sa");
        def.getChildren().add(text);
        def.setAlignment(Pos.CENTER);

        pane.getChildren().add(def);


        textRecu.addListener((InvalidationListener) e -> {
            out.selectPositionCaret(out.getLength());
            out.deselect();
        });

    }

    public static void printline(String s){
        if(Main.enabled) {
            if (textRecu.getValue() == null)
                textRecu.setValue(s + '\n');
            else {
                textRecu.setValue(textRecu.getValue() + s + '\n');
            }
        }
        else
            System.out.println(s);
    }

    private void createPredajcaButtons(){
        Button otvor = new Button("Otvor");
        otvor.setOnMouseClicked(e ->{
            if(otvor.getText().compareTo("Otvor") == 0){
                otvor.setText("Zavri");
                pouzivatel.spracuj(new String[]{"otvor"},null);
            }else{
                otvor.setText("Otvor");
                pouzivatel.spracuj(new String[]{"zavri"},null);
            }
        });

        Button vypis = new Button("Vypis");
        vypis.setOnMouseClicked(e -> {
            pouzivatel.spracuj("predajna | sklad".split(" "),null);
        });

        Button predaj = new Button("Predaj");
        predaj.setOnMouseClicked(e -> {
            pouzivatel.spracuj(new String[]{"predaj"},null);
        });

        Button plat = new Button("Plat");
        plat.setOnMouseClicked(e -> {
            pouzivatel.spracuj("plat | zarobene | odrobene".split(" "),null);
        });

        Button katalog = new Button("Katalog");
        katalog.setOnMouseClicked(e -> {
            pouzivatel.spracuj(new String[]{"katalog"},null);
        });

        Button prines = new Button("Prines");
        prines.setOnMouseClicked(e->{
            String command = input.getText();
            input.setText("");
            if(command.isEmpty()){
                printline("do input:  n/ref /pocet \n" +
                        "\t\t\t\t\t\t\t\t\t\t- s/ref = nazov/isbn/katalogove cislo knihy\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t moze byt null ak mas knihu v inventari] \n" +
                        "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat");
            }else
            pouzivatel.spracuj(("prines " + command).split(" "),null);
        });


        predajcaOkno.setSpacing(20);
        predajcaOkno.getChildren().add(otvor);
        predajcaOkno.getChildren().add(vypis);
        predajcaOkno.getChildren().add(katalog);
        predajcaOkno.getChildren().add(predaj);
        predajcaOkno.getChildren().add(prines);
        predajcaOkno.getChildren().add(plat);

    }

    private void createZakaznikButtons(){
        Button vstup = new Button("Vstup");
        vstup.setOnMouseClicked(e ->{
            if(Knihkupectvo.getInstance().getPredajna().getZakaznik() != null) return;
            if(!Knihkupectvo.getInstance().getPredajna().isOtvorene()){
                printline("Predajna je zavreta");
            }else {
                Knihkupectvo.getInstance().getPredajna().setZakaznik(zakaznik);
                printline("Vstupil si do predajne");
            }
        });

        Button katalog = new Button("Katalog");
        katalog.setOnMouseClicked(e -> {
            pouzivatel.spracuj(new String[]{"katalog"},null);
        });

        Button predaj = new Button("Predajna");
        predaj.setOnMouseClicked(e -> {
            pouzivatel.spracuj(new String[]{"predajna"},null);
        });

        Button prines = new Button("Pridaj");
        prines.setOnMouseClicked(e->{
            String command = input.getText();
            input.setText("");
            if(command.isEmpty()){
                printline("do input:  n/ref /pocet  \n" +
                        "\t\t\t\t\t\t\t\t\t\t- s/ref = nazov/isbn/katalogove cislo knihy\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                        "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat");
            }else
                pouzivatel.spracuj(("zober " + command).split(" "),null);
        });

        Button kosik = new Button("Kosik");
        kosik.setOnMouseClicked(e -> {
            pouzivatel.spracuj(new String[]{"kosik"},null);
        });

        zakaznikOkno.setSpacing(20);
        zakaznikOkno.getChildren().add(vstup);
        zakaznikOkno.getChildren().add(katalog);
        zakaznikOkno.getChildren().add(predaj);
        zakaznikOkno.getChildren().add(kosik);
        zakaznikOkno.getChildren().add(prines);


    }

    private void createManazerButtons(){

    }

    private void createSkladnikButtons(){
        Button katalog = new Button("Katalog");
        katalog.setOnMouseClicked(e -> {
            pouzivatel.spracuj(new String[]{"katalog"},null);
        });

        Button predaj = new Button("Sklad");
        predaj.setOnMouseClicked(e -> {
            pouzivatel.spracuj("sklad | info-n".split(" "),null);
        });

        Button prines = new Button("Premiestni");
        prines.setOnMouseClicked(e->{
            String command = input.getText();
            input.setText("");
            if(command.isEmpty()){
                printline("do input: n/ref /pocet //zX-X //uX-X  - s/ref = nazov/isbn/katalogove cislo knihy\n " +
                        "\t\t\t\t\t\t\t\t\t\t\t [s/ = nazov/isbn [medzery == _]; i/ = katalogove cislo;\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t moze byt null ak mas knihu v inventari] \n" +
                        "\t\t\t\t\t\t\t\t\t\t- zX-X = pozicia z ktorej beriem \n"+
                        "\t\t\t\t\t\t\t\t\t\t- uX-X = pozicia na ktoru umiestnujeme \n" +
                        "\t\t\t\t\t\t\t\t\t\t- pocet = kolko kniha z policky zobrat");
            }else
                pouzivatel.spracuj(("premiestni " + command).split(" "),null);
        });

        Button objednaj = new Button("Objednaj");
        objednaj.setOnMouseClicked(e->{
            String command = input.getText();
            input.setText("");
            if(command.isEmpty()){
                printline("zadajte cestu k suboru");
            }else
                pouzivatel.spracuj(("objednaj \"" + command +"\" | n-umiestni").split(" "),null);
        });

        Button miesto = new Button("najdiMiesto");
        miesto.setOnMouseClicked(e->{
            pouzivatel.spracuj(new String[]{"max-miesto"},null);
        });

        skladnikOkno.setSpacing(20);
        skladnikOkno.getChildren().add(predaj);
        skladnikOkno.getChildren().add(prines);
        skladnikOkno.getChildren().add(objednaj);
        skladnikOkno.getChildren().add(miesto);

    }



    public String getPouzivatel() {
        if(pouzivatel == null) return "NIKTO";
        return pouzivatel.getClass().getSimpleName().toUpperCase();
    }

    public void update(Text text, LoggedIn user){
        changeUser(user);
        text.setText("(" + getPouzivatel()+")");
        changePane(pouzivatel);
    }

    private void changePane(Pouzivatel p){
        pane.getChildren().remove(0);
        if(p instanceof Zakaznik)
            pane.getChildren().add(zakaznikOkno);
        else if(p instanceof Skladnik)
            pane.getChildren().add(skladnikOkno);
        else if(p instanceof Predajca)
            pane.getChildren().add(predajcaOkno);
        else if(p instanceof Manazer)
            pane.getChildren().add(manazerOkno);
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
        }
    }
}
