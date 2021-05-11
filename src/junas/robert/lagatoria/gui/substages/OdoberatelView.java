package junas.robert.lagatoria.gui.substages;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.tableViews.OdoberatelTableView;
import junas.robert.lagatoria.gui.tableViews.data.OdoberatelData;

public class OdoberatelView implements SubStage {
    private Stage stage;

    public OdoberatelView(String title, ObservableList<?> list) {
        stage = new Stage();
        stage.setTitle(title);

        stage.setResizable(false);

        TableView table = new OdoberatelTableView((ObservableList<OdoberatelData>)list);


        table.setPrefWidth(600);
        table.setPrefHeight(600);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 600);

        root.getChildren().add(table);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public Stage getSubStage() {
        return stage;
    }
}
