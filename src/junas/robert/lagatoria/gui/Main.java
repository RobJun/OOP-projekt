package junas.robert.lagatoria.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

public class Main extends Application {

    private Button zakaznikButton = new Button("Zákazník");
    private Button skladnikButton = new Button("Skladník");
    private Button predajcaButton = new Button("Predajca");
    private Button manazerButton = new Button("Manažer");

    private Controller controller;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Lagatoria");

        Manazer manazer = new Manazer("Hlavny manazer", 2032, 20);
        Vydavatelstvo vydavatelstvo = new Vydavatelstvo(manazer, 10);
        Knihkupectvo knihkupectvo = Knihkupectvo.getInstance();

        HBox root = new HBox();

        StackPane work = new StackPane();

        controller = new Controller(work);

        VBox menu= new VBox(20);
        menu.setStyle("-fx-border-style: solid;" +
                      "-fx-border-color: black");
        Knihkupectvo.deserialize("./res/knihkupectvo_oop.ser");
        menu.setMaxWidth(130);
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPrefWidth(100);

        zakaznikButton.setMinWidth(menu.getPrefWidth());
        skladnikButton.setMinWidth(menu.getPrefWidth());
        predajcaButton.setMinWidth(menu.getPrefWidth());
        manazerButton.setMinWidth(menu.getPrefWidth());

        Text text = new Text();
        text.setText("prihlasiť sa ako:");

        Text prihlaseny = new Text();
        prihlaseny.setText("(" + controller.getPouzivatel()+")");
        prihlaseny.setTextAlignment(TextAlignment.CENTER);

        menu.getChildren().addAll(text,zakaznikButton,skladnikButton,predajcaButton,manazerButton,prihlaseny);

        zakaznikButton.setOnMouseClicked(e -> {
            controller.update(prihlaseny,LoggedIn.ZAKAZNIK);
        });

        skladnikButton.setOnMouseClicked(e -> {
            controller.update(prihlaseny,LoggedIn.SKLADNIK);
        });

        predajcaButton.setOnMouseClicked(e -> {
            controller.update(prihlaseny,LoggedIn.PREDAJCA);
        });

        manazerButton.setOnMouseClicked(e -> {
            controller.update(prihlaseny,LoggedIn.MANAZER);
        });



        root.getChildren().addAll(menu,work);
        root.setHgrow(menu, Priority.ALWAYS);

        Scene scene = new Scene(root,800,600);

        stage.setOnCloseRequest(e -> {
            Knihkupectvo.serialize("./res/knihkupectvo_oop.ser");
        });

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
