package junas.robert.lagatoria.gui;

import java.lang.String;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.gui.CustomElements.InfoPrihlaseny;
import junas.robert.lagatoria.gui.CustomElements.MyPane;
import junas.robert.lagatoria.gui.CustomElements.CustomButton;
import junas.robert.lagatoria.gui.CustomElements.OutText;
import junas.robert.lagatoria.gui.controllers.MainController;
import junas.robert.lagatoria.gui.controllers.ViewController;

public class View {

    private MainController controller;

    private HBox manazerOkno = new HBox();
    private HBox zakaznikOkno = new HBox();
    private HBox predajcaOkno = new HBox();
    private HBox skladnikOkno = new HBox();
    private HBox distibutorOkno = new HBox();
    private MyPane pane = new MyPane(zakaznikOkno,skladnikOkno,predajcaOkno,manazerOkno,distibutorOkno);
    private OutText out = new OutText();
    private TextField inputText = new TextField();
    private InfoPrihlaseny prihlaseny = new InfoPrihlaseny();
    private FlowPane center = new FlowPane();
    private Scene mainScene;

    View(MainController controller){
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
        CustomButton zakaznikButton = new CustomButton ("Zákazník","zakaznik");
        CustomButton  skladnikButton =  new CustomButton("Skladník", "skladnik");
        CustomButton  predajcaButton =  new CustomButton("Predajca", "predajca");
        CustomButton  manazerButton =   new CustomButton("Manažer", "manazer");
        CustomButton  distriButton =    new CustomButton("Distributor", "distributor");

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
        zakaznikButton.setOnMouseClicked(e -> { controller.changePrihlaseny(LoggedIn.ZAKAZNIK); });
        skladnikButton.setOnMouseClicked(e -> { controller.changePrihlaseny(LoggedIn.SKLADNIK); });
        predajcaButton.setOnMouseClicked(e -> { controller.changePrihlaseny(LoggedIn.PREDAJCA); });
        manazerButton.setOnMouseClicked(e -> { controller.changePrihlaseny(LoggedIn.MANAZER); });
        distriButton.setOnMouseClicked(e -> { controller.changePrihlaseny(LoggedIn.DISTRI); });


        menu.setPrefWidth(150);
        root.setLeft(menu);
        root.setCenter(mainWindow);

        mainScene = new Scene(root,1000,600);
    }

    private void createPredajcaButtons(){
        CustomButton otvor = new CustomButton("Otvor", "otvorBtn");
        CustomButton vypis =    new CustomButton("Vypis","vypisPS");
        CustomButton predaj =   new CustomButton("Predaj","predaj");
        CustomButton plat =     new CustomButton("info-me", "info");
        CustomButton katalog =  new CustomButton("Katalog", "katalog");
        CustomButton prines =   new CustomButton("Prines", "prines");
        controller.addUpdatableView("otvorBtn",otvor);


        otvor.setOnMouseClicked(e ->{ controller.notify(otvor, "otvorBtn");});
        vypis.setOnMouseClicked(e -> { controller.notify(vypis,"out"); });
        predaj.setOnMouseClicked(e -> { controller.notify(predaj,"out"); });
        plat.setOnMouseClicked(controller.getInfoHandler());
        katalog.setOnMouseClicked(e -> { controller.open(katalog, ViewController.WindowKeys.TAB_KAT);});
        prines.setOnMouseClicked(e->{ controller.notify(prines,new Object[]{inputText, "out"}); });


        predajcaOkno.setSpacing(20);
        predajcaOkno.getChildren().add(otvor);
        predajcaOkno.getChildren().add(vypis);
        predajcaOkno.getChildren().add(katalog);
        predajcaOkno.getChildren().add(predaj);
        predajcaOkno.getChildren().add(prines);
        predajcaOkno.getChildren().add(plat);

    }

    private void createZakaznikButtons(){
        CustomButton vstup =    new CustomButton("Vstup","vstup");
        CustomButton plat =     new CustomButton("info-me", "info");
        CustomButton katalog =  new CustomButton("Katalog", "katalog");
        CustomButton predaj =   new CustomButton("Predajna", "vypisP");
        CustomButton prines =   new CustomButton("Pridaj", "prines");
        CustomButton kosik =    new CustomButton("Kosik", "kosik");

        vstup.setOnMouseClicked(e ->{ controller.notify(vstup,"out"); });
        plat.setOnMouseClicked(controller.getInfoHandler());
        katalog.setOnMouseClicked(e -> { controller.open(katalog, ViewController.WindowKeys.TAB_KAT);});
        predaj.setOnMouseClicked(e -> { controller.notify(predaj,"out"); });
        prines.setOnMouseClicked(e->{ controller.notify(prines,new Object[]{inputText, "out"}); });
        kosik.setOnMouseClicked(e -> { controller.notify(kosik,"out"); });

        zakaznikOkno.setSpacing(20);
        zakaznikOkno.getChildren().add(vstup);
        zakaznikOkno.getChildren().add(katalog);
        zakaznikOkno.getChildren().add(predaj);
        zakaznikOkno.getChildren().add(kosik);
        zakaznikOkno.getChildren().add(prines);
        zakaznikOkno.getChildren().add(plat);


    }

    private void createSkladnikButtons(){
        CustomButton katalog =        new CustomButton("Katalog","katalog");
        CustomButton predaj =         new CustomButton("Sklad", "vypisS");
        CustomButton prines =         new CustomButton("Premiestni", "prines");
        CustomButton objednaj =       new CustomButton("Objednaj", "objednaj");
        CustomButton miesto =         new CustomButton("najdiMiesto", "miesto");
        CustomButton plat =           new CustomButton("info-me", "info");

        katalog.setOnMouseClicked(e -> { controller.open(katalog, ViewController.WindowKeys.TAB_KAT); });
        predaj.setOnMouseClicked(e -> { controller.notify(predaj, "out"); });
        prines.setOnMouseClicked(e->{ controller.notify(prines,new Object[]{inputText,"out"}); });
        objednaj.setOnMouseClicked(e->{ controller.notify(objednaj, new Object[]{inputText,"out"}); });
        miesto.setOnMouseClicked(e->{ controller.notify(miesto,"out"); });


        plat.setOnMouseClicked(controller.getInfoHandler());

        skladnikOkno.setSpacing(20);
        skladnikOkno.getChildren().add(katalog);
        skladnikOkno.getChildren().add(predaj);
        skladnikOkno.getChildren().add(prines);
        skladnikOkno.getChildren().add(objednaj);
        skladnikOkno.getChildren().add(miesto);
        skladnikOkno.getChildren().add(plat);

    }

    private void createManazerButtons(){
        CustomButton vypisAutorov = new CustomButton("Autori","autori");
        CustomButton pisat =        new CustomButton("Daj Napisat Knihu","pisat");
        CustomButton text =         new CustomButton("Texty na vydanie", "vydVyp");
        CustomButton plat =         new CustomButton("info-me", "info");
        CustomButton vydaj =        new CustomButton("Vydaj text", "vydaj");
        CustomButton strategia =    new CustomButton("Vydavanie po jednom", "strategia");

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Pridaj");
        MenuItem fantasyAutor = new MenuItem("fantazy autor");
        fantasyAutor.setId("fantasyAut");
        MenuItem historyAutor = new MenuItem("history autor");
        fantasyAutor.setId("historyAut");
        MenuItem poetryAutor = new MenuItem("autor poezie");
        fantasyAutor.setId("poetryAut");
        MenuItem addAutors = new MenuItem("Pridaj autorov do zoznamu cakajucich na pisane");
        addAutors.setId("addAutor");

        MenuItem removeAutors = new MenuItem("Odober autora zo zoznamu autorov cakajucich na pisanie");


        fantasyAutor.setOnAction(controller.autorCreator());
        historyAutor.setOnAction(controller.autorCreator());
        poetryAutor.setOnAction(controller.autorCreator());

        addAutors.setOnAction(e -> { controller.notify(addAutors,"out"); });
        removeAutors.setOnAction(e->{ controller.open(removeAutors, ViewController.WindowKeys.AUTOR_R); });

        controller.addUpdatableView("vydaj",vydaj);
        controller.addUpdatableView("strategia",strategia);

        vypisAutorov.setOnMouseClicked(e->{ controller.notify(vypisAutorov,"out"); });
        pisat.setOnMouseClicked(e->{ controller.notify(pisat, "out"); });
        text.setOnMouseClicked(e->{ controller.open(text, ViewController.WindowKeys.TAB_TEXT); });
        vydaj.setOnMouseClicked(e->{ controller.notify(vydaj,"out"); });

        strategia.setOnMouseClicked(e->{ controller.notify(strategia,new String[]{"strategia","vydaj"}); });
        plat.setOnMouseClicked(controller.getInfoHandler());
        menu.getItems().addAll(fantasyAutor,historyAutor,poetryAutor,addAutors, removeAutors);
        menuBar.getMenus().addAll(menu);

        manazerOkno.setSpacing(5);
        manazerOkno.getChildren().add(menuBar);
        manazerOkno.getChildren().add(vypisAutorov);
        manazerOkno.getChildren().add(pisat);
        manazerOkno.getChildren().add(text);
        manazerOkno.getChildren().add(vydaj);
        manazerOkno.getChildren().add(strategia);
        manazerOkno.getChildren().add(plat);
    }


    private void createDistributorButtons(){
        CustomButton knihy =   new CustomButton("Knihy pre Knihkupetvo", "knihyPreK");
        CustomButton plat =    new CustomButton("info-me", "info");

        MenuBar menuBar = new MenuBar();
        Menu menu2 = new Menu("Odoberatelia");
        MenuItem pridajOdoberatela = new MenuItem("pridaj odoberatela");
        MenuItem pridajOdoberatelaK = new MenuItem("pridaj odoberatela so specifikovanou kategoriu");
        MenuItem pridajOdoberatelaM = new MenuItem("pridaj odoberatela so specifikovanym minimom");
        MenuItem vypisOdoberatlov = new MenuItem("vypis odoberatelov");
        MenuItem odoberOdoberatela = new MenuItem("odober odoberatela");

        knihy.setOnMouseClicked(e->{ controller.notify(knihy,"out"); });
        pridajOdoberatela.setOnAction(e->{ controller.open(pridajOdoberatela, ViewController.WindowKeys.ODOBER_C); });
        pridajOdoberatelaK.setOnAction(e-> controller.open(pridajOdoberatelaK, ViewController.WindowKeys.ODOBERK_C));
        pridajOdoberatelaM.setOnAction(e-> controller.open(pridajOdoberatelaM, ViewController.WindowKeys.ODOBERM_C));
        vypisOdoberatlov.setOnAction(e->{ controller.open(vypisOdoberatlov, ViewController.WindowKeys.TAB_ODOB); });
        odoberOdoberatela.setOnAction(e->{ controller.open(odoberOdoberatela, ViewController.WindowKeys.ODOBER_R); });

        menu2.getItems().addAll(pridajOdoberatela,pridajOdoberatelaK,pridajOdoberatelaM,odoberOdoberatela,vypisOdoberatlov);
        menuBar.getMenus().addAll(menu2);

        plat.setOnMouseClicked(controller.getInfoHandler());

        distibutorOkno.getChildren().add(menuBar);
        distibutorOkno.getChildren().add(plat);
        distibutorOkno.getChildren().add(knihy);
    }
}
