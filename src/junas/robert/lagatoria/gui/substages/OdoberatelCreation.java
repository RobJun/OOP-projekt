package junas.robert.lagatoria.gui.substages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.controllers.Controller;
import junas.robert.lagatoria.gui.controllers.MainController;

public class OdoberatelCreation implements SubStage {
    private TextField nazovStanku = new TextField();
    private TextField dalsie = new TextField();

    private Stage subStage = new Stage();

    private int which;

    public OdoberatelCreation(int which, Controller controller, String text){
        this.which = which;
        subStage.setTitle("Pridanie Odoberatela");
        subStage.setResizable(false);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 250, 200);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(e->{
                //controller.createOdoberatelKateg(nazovStanku,dalsie,subStage, "out")
            controller.notify(this,"out");});

        root.getChildren().addAll(new Text("Nazov Stanku"), nazovStanku);
        if(text != null) {
            root.getChildren().add(new Text(text));
            root.getChildren().add(dalsie);
        }
        root.getChildren().add(submit);
        subStage.setScene(scene);
        subStage.show();
    }

    @Override
    public Stage getSubStage() {
        return subStage;
    }

    public String getNazov(){
        return nazovStanku.getText();
    }

    public String getDalsie(){
        return dalsie.getText();
    }

    public int getWhich(){
        return which;
    }
}
