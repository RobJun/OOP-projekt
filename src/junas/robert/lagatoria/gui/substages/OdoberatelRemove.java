package junas.robert.lagatoria.gui.substages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.Controller;

public class OdoberatelRemove {
    private TextField index = new TextField();

    public OdoberatelRemove(Controller controller){
        Stage subStage = new Stage();
        subStage.setTitle("Odstranenie Odoberatela");
        subStage.setResizable(false);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 250, 200);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(e->{ controller.odstranOdoberatela(index, subStage,"out"); });
        index.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    index.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


        root.getChildren().addAll(new Text("Index: "), index);
        root.getChildren().add(submit);
        subStage.setScene(scene);
        subStage.show();
    }
}
