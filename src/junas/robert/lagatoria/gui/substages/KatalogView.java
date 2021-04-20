package junas.robert.lagatoria.gui.substages;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.TableViews.KatalogTableView;
import junas.robert.lagatoria.gui.TableViews.OdoberatelTableView;
import junas.robert.lagatoria.gui.TableViews.TextTableView;
import junas.robert.lagatoria.gui.TableViews.data.KatalogData;
import junas.robert.lagatoria.gui.TableViews.data.OdoberatelData;
import junas.robert.lagatoria.gui.TableViews.data.TextyNaVydanieData;

public class KatalogView {

    public KatalogView(String title, ObservableList<?> list) {
        Stage stage = new Stage();
        stage.setTitle(title);

        stage.setResizable(false);

        TableView table =  new KatalogTableView((ObservableList<KatalogData>)list);


        table.setPrefWidth(600);
        table.setPrefHeight(600);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 600);

        root.getChildren().add(table);
        stage.setScene(scene);
        stage.show();

    }
}
