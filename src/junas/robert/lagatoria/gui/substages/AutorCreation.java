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

public class AutorCreation implements SubStage {
    private TextField menoAutora = new TextField();
    private TextField prievzisko = new TextField();
    private Stage subStage = new Stage();
    private String autor;
    public AutorCreation(String autor, Controller controller){
        this.autor = autor;
        subStage.setTitle(autor);
        subStage.setResizable(false);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 250, 200);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(e->{
            controller.notify(this,"out");
            //controller.createAutor(menoAutora,prievzisko,autor, subStage,"out");
        });
        root.getChildren().addAll(new Text("Prve meno"), menoAutora);
        root.getChildren().addAll(new Text("Prievzisko"), prievzisko);
        root.getChildren().add(submit);
        subStage.setScene(scene);
        subStage.show();
    }

    public Stage getSubStage(){
        return subStage;
    }

    public String getMeno() {
        return menoAutora.getText();
    }

    public String getPrievzisko(){
        return prievzisko.getText();
    }

    public String getAutor() {
        return autor;
    }
}
