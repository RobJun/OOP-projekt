package junas.robert.lagatoria.gui.substages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.Controller;

public class AutorCreation {
    private TextField menoAutora = new TextField();
    private TextField prievzisko = new TextField();
    public AutorCreation(String autor, Controller controller){
        Stage subStage = new Stage();
        subStage.setTitle(autor);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 250, 200);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(e->{
            controller.createAutor(menoAutora,prievzisko,autor, subStage,"out");
        });
        root.getChildren().addAll(new Text("Prve meno"), menoAutora);
        root.getChildren().addAll(new Text("Prievzisko"), prievzisko);
        root.getChildren().add(submit);
        subStage.setScene(scene);
        subStage.show();
    }
}
