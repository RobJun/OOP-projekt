package junas.robert.lagatoria.gui.substages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.Controller;

public class OdoberatelCreation {
    private TextField nazovStanku = new TextField();
    private TextField dalsie = new TextField();

    public OdoberatelCreation(int k, Controller controller){
        Stage subStage = new Stage();
        subStage.setTitle("Pridanie Odoberatela");
        subStage.setResizable(false);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 250, 200);

        Button submit = new Button("Submit");
        if(k == 1)
            submit.setOnMouseClicked(e->controller.createOdoberatelKateg(nazovStanku,dalsie,subStage, "out"));
        else if(k==2)
            submit.setOnMouseClicked(e->controller.createOdoberatelMin(nazovStanku,dalsie,subStage, "out"));
        else
            submit.setOnMouseClicked(e->{ controller.createOdoberatel(nazovStanku, subStage,"out"); });
        root.getChildren().addAll(new Text("Nazov Stanku"), nazovStanku);
        if(k > 0) {
            if(k == 1){
                root.getChildren().add(new Text("kategoria"));
            }else
                root.getChildren().add(new Text("index"));
            root.getChildren().add(dalsie);
        }
        root.getChildren().add(submit);
        subStage.setScene(scene);
        subStage.show();
    }
}
