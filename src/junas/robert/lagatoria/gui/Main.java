package junas.robert.lagatoria.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;

public class Main extends Application {

    private Button zakaznikButton = new Button("Zákazník");
    private Button skladnikButton = new Button("Skladník");
    private Button predajcaButton = new Button("Predajca");
    private Button manazerButton = new Button("Manažer");
    private Button distriButton = new Button("Distributor");


    public static boolean enabled = false;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller,model);

        enabled = true;
        controller.setView(view);

        stage.setOnCloseRequest(e -> {
            controller.serialize();
        });

        stage.setScene(view.getMainScene());
        stage.sizeToScene();
        stage.show();
    }
}
