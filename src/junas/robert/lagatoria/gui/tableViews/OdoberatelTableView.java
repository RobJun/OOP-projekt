package junas.robert.lagatoria.gui.tableViews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import junas.robert.lagatoria.gui.tableViews.data.OdoberatelData;
import junas.robert.lagatoria.gui.tableViews.data.TextyNaVydanieData;

public class OdoberatelTableView extends TableView {



    private TableColumn<TextyNaVydanieData,Integer> id;
    private TableColumn<TextyNaVydanieData,String> typ;
    private TableColumn<TextyNaVydanieData,String> nazov;

    private ObservableList<OdoberatelData> list;

    public OdoberatelTableView(ObservableList<OdoberatelData> data){

        setEditable(false);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        list = data;
        id = new TableColumn("id");
        typ = new TableColumn("typ");
        nazov = new TableColumn("nazov");

        setItems(list);
        getColumns().addAll(id,typ,nazov);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        typ.setCellValueFactory(new PropertyValueFactory<>("typ"));
        nazov.setCellValueFactory(new PropertyValueFactory<>("nazov"));

        id.setSortType(TableColumn.SortType.ASCENDING);

    }
}
