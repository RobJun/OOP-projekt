package junas.robert.lagatoria.gui.substages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 250, 200);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(e->{
            controller.notify(this,"out");
            //controller.createAutor(menoAutora,prievzisko,autor, subStage,"out");
        });
        root.add(new Text("Prve meno"), 0,0);
        root.add(menoAutora,1,0);
        root.add(new Text("Prievzisko"), 0,1);
        root.add(prievzisko,1,1);
        root.add(submit,0,2);
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
