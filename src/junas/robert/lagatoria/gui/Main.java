package junas.robert.lagatoria.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Button zakaznikButton = new Button("Zakaznik");
    private Button skladnikButton = new Button("Skladnik");
    private Button predajcaButton = new Button("predajca");
    private Button manazerButton = new Button("manazer");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Lagatoria");

        FlowPane pane= new FlowPane();
        pane.getChildren().add(zakaznikButton);
        pane.getChildren().add(skladnikButton);
        pane.getChildren().add(predajcaButton);
        pane.getChildren().add(manazerButton);

        Scene scene = new Scene(pane,600,600);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
