package junas.robert.lagatoria.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

public class Controller {

    class AutorCreation {
        private TextField menoAutora = new TextField();
        private TextField prievzisko = new TextField();
        AutorCreation(String autor){
            Stage subStage = new Stage();
            subStage.setTitle(autor);

            FlowPane root = new FlowPane();
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 250, 200);

            Button submit = new Button("Submit");
            submit.setOnMouseClicked(e->{
                if(menoAutora.getText().length() == 0 || menoAutora.getText() == null || prievzisko.getText() == null ||prievzisko.getText().length() == 0 ){
                    return;
                }
                String typ = null;
                if (autor.compareTo("fantazy autor") == 0) {
                    typ = FantasyAutor.class.getName();
                }else if(autor.compareTo("history autor") == 0){
                    typ = HistoryAutor.class.getName();
                }else{
                    typ = PoetryAutor.class.getName();
                }
                pouzivatel.spracuj(("pridajA " + typ + " " + menoAutora.getText() + " " + prievzisko.getText()).split(" "),vydavatelstvo);
                subStage.close();
            });
            root.getChildren().addAll(new Text("Prve meno"), menoAutora);
            root.getChildren().addAll(new Text("Prievzisko"), prievzisko);
            root.getChildren().add(submit);
            subStage.setScene(scene);
            subStage.show();
        }
    }

    class RemoveAutor {
        private TextField index = new TextField();

        RemoveAutor(){
            Stage subStage = new Stage();
            subStage.setTitle("nepisuci autor");

            FlowPane root = new FlowPane();
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 250, 200);

            Button submit = new Button("Submit");
            submit.setOnMouseClicked(e->{
                if(index == null || index.getText().length() == 0 || !index.getText().matches("[0-9]+") ){
                    return;
                }
                String typ = null;
                pouzivatel.spracuj(("odoberA " + index.getText()).split(" "),vydavatelstvo);
                subStage.close();
            });

            index.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        index.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            root.getChildren().addAll(new Text("index"), index);
            root.getChildren().add(submit);
            subStage.setScene(scene);
            subStage.show();
        }
    }


    class OdoberatelCreation{
        private TextField nazovStanku = new TextField();

        OdoberatelCreation(){
            Stage subStage = new Stage();
            subStage.setTitle("Pridanie Odoberatela");

            FlowPane root = new FlowPane();
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 250, 200);

            Button submit = new Button("Submit");
            submit.setOnMouseClicked(e->{
                if( nazovStanku.getText() == null || nazovStanku.getText().length() == 0){
                    return;
                }
                pouzivatel.spracuj(("pridajOdoberatela " + nazovStanku.getText()).split(" "),vydavatelstvo);
                subStage.close();
            });
            root.getChildren().addAll(new Text("Nazov Stanku"), nazovStanku);
            root.getChildren().add(submit);
            subStage.setScene(scene);
            subStage.show();
        }
    }

    class removeOdoberatel{
        private TextField index = new TextField();

        removeOdoberatel(){
            Stage subStage = new Stage();
            subStage.setTitle("Odstranenie Odoberatela");

            FlowPane root = new FlowPane();
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 250, 200);

            Button submit = new Button("Submit");
            submit.setOnMouseClicked(e->{
                if(index.getText() == null || index.getText().length() == 0 || !index.getText().matches("[0-9]+")){
                    return;
                }
                pouzivatel.spracuj(("odstranOdoberatela " + index.getText()).split(" "),vydavatelstvo);
                subStage.close();
            });
            index.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        index.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });


            root.getChildren().addAll(new Text("Index: "), index);
            root.getChildren().add(submit);
            subStage.setScene(scene);
            subStage.show();
        }
    }


    private Vydavatelstvo vydavatelstvo;

    private Pouzivatel pouzivatel;
    private Zakaznik zakaznik = new Zakaznik();
    private Predajca predajca = new Predajca("Predavac",22);
    private Skladnik skladnik = new Skladnik("Skladnik", 23);
    private Manazer manazer;
    private Distributor distributor;
    private StackPane pane;


    private HBox manazerOkno = new HBox();
    private HBox zakaznikOkno = new HBox();
    private HBox predajcaOkno = new HBox();
    private HBox skladnikOkno = new HBox();
    private HBox distibutorOkno = new HBox();
    private StackPane def = new StackPane();


    public static TextArea out = new TextArea();
    public static TextField input = new TextField();

    private static StringProperty textRecu = new SimpleStringProperty();


    private EventHandler<MouseEvent> info =  new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            pouzivatel.spracuj("info-me | plat | zarobene | odrobene".split(" "),null);
        }
    };


    public Controller(StackPane pane, Vydavatelstvo vydavatelstvo){
        this.pane = pane;
        this.vydavatelstvo = vydavatelstvo;
        manazer = vydavatelstvo.getManazer();
        distributor = vydavatelstvo.getDistibutor();
        input.setPrefWidth(1000);

        out.textProperty().bind(textRecu);

        createPredajcaButtons();
        createZakaznikButtons();
        createManazerButtons();
        createSkladnikButtons();
        createDistributorButtons();

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

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(info);

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

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(info);

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
        zakaznikOkno.getChildren().add(plat);


    }

    private void createManazerButtons(){
        Menu menu = new Menu("Pridaj");
        MenuItem fantasyAutor = new MenuItem("fantazy autor");
        MenuItem historyAutor = new MenuItem("history autor");
        MenuItem poetryAutor = new MenuItem("autor poezie");
        MenuItem addAutors = new MenuItem("Pridaj autorov do zoznamu cakajucich na pisane");
        MenuItem removeAutors = new MenuItem("Odober autora zo zoznamu autorov cakajucich na pisanie");



        EventHandler<ActionEvent> vytvor = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new AutorCreation(((MenuItem)event.getSource()).getText());
            }
        };

        fantasyAutor.setOnAction(vytvor);
        historyAutor.setOnAction(vytvor);
        poetryAutor.setOnAction(vytvor);

        addAutors.setOnAction(e -> {
            pouzivatel.spracuj(new String[]{"Pzoznam"},vydavatelstvo);
        });

        removeAutors.setOnAction(e->{
            new RemoveAutor();
        });

        menu.getItems().addAll(fantasyAutor,historyAutor,poetryAutor,addAutors, removeAutors);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);


        Button vypisAutorov = new Button("Autori");
        Button pisat = new Button("Daj Napisat Knihu");
        Button text = new Button("Texty na vydanie");
        Button vydaj = new Button("Vydaj text");
        Button strategia = new Button("Vydavanie po jednom");


        vypisAutorov.setOnMouseClicked(e->{
            pouzivatel.spracuj(new String[]{"vypisAutor"}, vydavatelstvo);
        });

        pisat.setOnMouseClicked(e->{
            pouzivatel.spracuj(new String[]{"dajNapisat"},vydavatelstvo);
        });

        text.setOnMouseClicked(e->{
            pouzivatel.spracuj(new String[]{"Queue"}, vydavatelstvo);
        });

        vydaj.setOnMouseClicked(e->{
            pouzivatel.spracuj(new String[]{"vydajKnihy"},vydavatelstvo);
        });

        strategia.setOnMouseClicked(e->{
            if(strategia.getText().compareTo("Vydavanie po jednom") == 0){
                pouzivatel.spracuj("Strategia true".split(" "),vydavatelstvo);
                strategia.setText("Vydavanie vsetkych");
                vydaj.setText("Vydaj texty");
            }else{
                pouzivatel.spracuj("Strategia false".split(" "),vydavatelstvo);
                strategia.setText("Vydavanie po jednom");
                vydaj.setText("Vydaj text");
            }
        });

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(info);

        manazerOkno.setSpacing(5);
        manazerOkno.getChildren().add(menuBar);
        manazerOkno.getChildren().add(vypisAutorov);
        manazerOkno.getChildren().add(pisat);
        manazerOkno.getChildren().add(text);
        manazerOkno.getChildren().add(vydaj);
        manazerOkno.getChildren().add(strategia);
        manazerOkno.getChildren().add(plat);
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

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(info);

        skladnikOkno.setSpacing(20);
        skladnikOkno.getChildren().add(predaj);
        skladnikOkno.getChildren().add(prines);
        skladnikOkno.getChildren().add(objednaj);
        skladnikOkno.getChildren().add(miesto);
        skladnikOkno.getChildren().add(plat);

    }


    private void createDistributorButtons(){
        Menu menu2 = new Menu("Odoberatelia");
        MenuItem pridajOdoberatela = new MenuItem("pridaj odoberatela");
        MenuItem vypisOdoberatlov = new MenuItem("vypisOdoberatelov");
        MenuItem odoberOdoberatela = new MenuItem("odober odoberatela");

        pridajOdoberatela.setOnAction(e->{
                new OdoberatelCreation();
        });

        vypisOdoberatlov.setOnAction(e->{
            pouzivatel.spracuj(new String[]{"vypisOdoberatelov"}, vydavatelstvo);
        });

        odoberOdoberatela.setOnAction(e->{
           new removeOdoberatel();
        });

        menu2.getItems().addAll(pridajOdoberatela,odoberOdoberatela,vypisOdoberatlov);


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu2);

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(info);

        distibutorOkno.getChildren().add(menuBar);
        distibutorOkno.getChildren().add(plat);
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
        else if(p instanceof Distributor)
            pane.getChildren().add(distibutorOkno);
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
}