package junas.robert.lagatoria.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.gui.CustomElements.InfoPrihlaseny;
import junas.robert.lagatoria.gui.CustomElements.MyPane;
import junas.robert.lagatoria.gui.CustomElements.OtvorButton;
import junas.robert.lagatoria.gui.CustomElements.OutText;

public class View {


    private Controller controller;
    private Model model;



    private HBox manazerOkno = new HBox();
    private HBox zakaznikOkno = new HBox();
    private HBox predajcaOkno = new HBox();
    private HBox skladnikOkno = new HBox();
    private HBox distibutorOkno = new HBox();

    private MyPane pane = new MyPane(zakaznikOkno,skladnikOkno,predajcaOkno,manazerOkno,distibutorOkno);


    private OutText out = new OutText();
    private TextField inputText = new TextField();

    private TableView tableView = new TableView();

    private StringProperty textRecu = new SimpleStringProperty();

    private InfoPrihlaseny prihlaseny = new InfoPrihlaseny();

    private FlowPane center = new FlowPane();


    private Scene mainScene;

    View(Controller controller){
        this.controller = controller;

        controller.addUpdatableView("out",out);
        controller.addUpdatableView("pane",pane);
        controller.addUpdatableView("prih",prihlaseny);

        createMainScene();
        createSkladnikButtons();
        createManazerButtons();
        createDistributorButtons();
        createZakaznikButtons();
        createPredajcaButtons();
    }

    public Scene getMainScene() {
        return mainScene;
    }
    
    void createMainScene(){
        Button zakaznikButton = new Button("Zákazník");
        Button skladnikButton = new Button("Skladník");
        Button predajcaButton = new Button("Predajca");
        Button manazerButton = new Button("Manažer");
        Button distriButton = new Button("Distributor");

        BorderPane root = new BorderPane();

        BorderPane mainWindow = new BorderPane();

        mainWindow.setTop(pane);

        BorderPane center = new BorderPane();
        HBox input = new HBox();
        Text textInput = new Text("Input:");
        input.getChildren().addAll(textInput, inputText);
        HBox.setMargin(textInput, new Insets(0,10,0,20));

        inputText.setPrefWidth(800);
        this.center.setPadding(new Insets(5,0,5,0));
        this.center.prefWidth(850);
        this.center.prefHeight(500);
        this.center.setAlignment(Pos.CENTER);
        this.center.getChildren().add(out);
        center.setTop(input);
        center.setCenter(this.center);

        mainWindow.setCenter(center);

        VBox menu= new VBox(20);
        menu.setStyle("-fx-border-style: solid;" +
                "-fx-border-color: black");
        menu.setMaxWidth(150);
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPrefWidth(100);

        zakaznikButton.setMinWidth(menu.getPrefWidth());
        skladnikButton.setMinWidth(menu.getPrefWidth());
        predajcaButton.setMinWidth(menu.getPrefWidth());
        manazerButton.setMinWidth(menu.getPrefWidth());
        distriButton.setMinWidth(menu.getPrefWidth());

        Text text = new Text();
        text.setText("prihlasiť sa ako:");


        menu.getChildren().addAll(text,zakaznikButton,skladnikButton,predajcaButton,manazerButton,distriButton,prihlaseny);

        zakaznikButton.setOnMouseClicked(e -> { controller.updateView(LoggedIn.ZAKAZNIK); });

        skladnikButton.setOnMouseClicked(e -> { controller.updateView(LoggedIn.SKLADNIK); });

        predajcaButton.setOnMouseClicked(e -> { controller.updateView(LoggedIn.PREDAJCA); });

        manazerButton.setOnMouseClicked(e -> { controller.updateView(LoggedIn.MANAZER); });

        distriButton.setOnMouseClicked(e -> { controller.updateView(LoggedIn.DISTRI); });


        menu.setPrefWidth(150);
        root.setLeft(menu);
        root.setCenter(mainWindow);

        mainScene = new Scene(root,1000,600);
    }

    private void createPredajcaButtons(){
        OtvorButton otvor = new OtvorButton();
        controller.addUpdatableView("otvorBtn",otvor);
        otvor.setOnMouseClicked(e ->{ controller.otvorZatvor("otvorBtn"); });

        Button vypis = new Button("Vypis");
        vypis.setOnMouseClicked(e -> { controller.spracuj("predajna | sklad","out"); });

        Button predaj = new Button("Predaj");
        predaj.setOnMouseClicked(e -> { controller.spracuj("predaj","out"); });

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(controller.getInfoHandler());

        Button katalog = new Button("Katalog");
        katalog.setOnMouseClicked(e -> { controller.openKatalog();});

        Button prines = new Button("Prines");
        prines.setOnMouseClicked(e->{ controller.prines(inputText,"out");});


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
        vstup.setOnMouseClicked(e ->{ controller.Zakaznik_vstup("out"); });

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(controller.getInfoHandler());

        Button katalog = new Button("Katalog");
        katalog.setOnMouseClicked(e -> { controller.openKatalog(); });

        Button predaj = new Button("Predajna");
        predaj.setOnMouseClicked(e -> { controller.spracuj("predajna","out"); });

        Button prines = new Button("Pridaj");
        prines.setOnMouseClicked(e->{
                controller.prines(inputText,"out");
        });

        Button kosik = new Button("Kosik");
        kosik.setOnMouseClicked(e -> {
            controller.spracuj("kosik","out");
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
                controller.autorCreateStage(((MenuItem)event.getSource()).getText());
            }
        };

        fantasyAutor.setOnAction(vytvor);
        historyAutor.setOnAction(vytvor);
        poetryAutor.setOnAction(vytvor);

        addAutors.setOnAction(e -> {
            controller.spracuj("Pzoznam","out");
        });

        removeAutors.setOnAction(e->{
            controller.autorRemoveStage();
        });

        menu.getItems().addAll(fantasyAutor,historyAutor,poetryAutor,addAutors, removeAutors);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);


        Button vypisAutorov = new Button("Autori");
        Button pisat = new Button("Daj Napisat Knihu");
        Button text = new Button("Texty na vydanie");
        Button vydaj = new Button("Vydaj text");
        Button strategia = new Button("Vydavanie po jednom");


        vypisAutorov.setOnMouseClicked(e->{ controller.spracuj("vypisAutor","out"); });

        pisat.setOnMouseClicked(e->{ controller.spracuj("dajNapisat","out"); });

        text.setOnMouseClicked(e->{ controller.openTextyNaVydanie(); });

        vydaj.setOnMouseClicked(e->{ controller.spracuj("vydajKnihy","out"); });

        strategia.setOnMouseClicked(e->{ controller.changeStrategy(strategia,vydaj, "out"); });

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(controller.getInfoHandler());

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
        katalog.setOnMouseClicked(e -> { controller.openKatalog(); });

        Button predaj = new Button("Sklad");
        predaj.setOnMouseClicked(e -> { controller.spracuj("sklad | info-n","out"); });

        Button prines = new Button("Premiestni");
        prines.setOnMouseClicked(e->{ controller.prines(inputText,"out"); });

        Button objednaj = new Button("Objednaj");
        objednaj.setOnMouseClicked(e->{ controller.objednaj(inputText,"out");});

        Button miesto = new Button("najdiMiesto");
        miesto.setOnMouseClicked(e->{ controller.spracuj("max-miesto","out"); });

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(controller.getInfoHandler());

        skladnikOkno.setSpacing(20);
        skladnikOkno.getChildren().add(katalog);
        skladnikOkno.getChildren().add(predaj);
        skladnikOkno.getChildren().add(prines);
        skladnikOkno.getChildren().add(objednaj);
        skladnikOkno.getChildren().add(miesto);
        skladnikOkno.getChildren().add(plat);

    }


    private void createDistributorButtons(){
        Menu menu2 = new Menu("Odoberatelia");
        MenuItem pridajOdoberatela = new MenuItem("pridaj odoberatela");
        MenuItem pridajOdoberatelaK = new MenuItem("pridaj odoberatela so specifikovanou kategoriu");
        MenuItem pridajOdoberatelaM = new MenuItem("pridaj odoberatela so specifikovanym minimom");

        MenuItem vypisOdoberatlov = new MenuItem("vypisOdoberatelov");
        MenuItem odoberOdoberatela = new MenuItem("odober odoberatela");
        Button knihy = new Button("Knihy pre Knihkupetvo");
        knihy.setOnMouseClicked(e->{ controller.spracuj("knihyPreKnihkupectvo","out"); });

        pridajOdoberatela.setOnAction(e->{ controller.odoberatelCreateStage(0); });
        pridajOdoberatelaK.setOnAction(e-> controller.odoberatelCreateStage(1));
        pridajOdoberatelaM.setOnAction(e-> controller.odoberatelCreateStage(2));

        vypisOdoberatlov.setOnAction(e->{ controller.openOdoberatelovNaVydanie(); });

        odoberOdoberatela.setOnAction(e->{
           controller.odoberaltelRemoveStage();
        });

        menu2.getItems().addAll(pridajOdoberatela,pridajOdoberatelaK,pridajOdoberatelaM,odoberOdoberatela,vypisOdoberatlov);


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu2);

        Button plat = new Button("info-me");
        plat.setOnMouseClicked(controller.getInfoHandler());

        distibutorOkno.getChildren().add(menuBar);
        distibutorOkno.getChildren().add(plat);
        distibutorOkno.getChildren().add(knihy);
    }
}
