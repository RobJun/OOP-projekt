package junas.robert.lagatoria.gui.tableViews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import junas.robert.lagatoria.gui.tableViews.data.TextyNaVydanieData;

public class TextTableView extends TableView{

    //private  TableView table = new TableView();
    private TableColumn<TextyNaVydanieData,Integer> id;
    private TableColumn<TextyNaVydanieData,String> meno;
    private TableColumn<TextyNaVydanieData,String> kategoria;
    private TableColumn<TextyNaVydanieData,String> text;

    private ObservableList<TextyNaVydanieData> list;


    public TextTableView(ObservableList<TextyNaVydanieData> data){
        list = data;
        setEditable(false);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
         id = new TableColumn("Rad");
         kategoria = new TableColumn("Kategoria");
         meno = new TableColumn("Meno Autora");
         text = new TableColumn("Nazov");

        setItems(list);
        getColumns().addAll(id,kategoria,meno,text);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        meno.setCellValueFactory(new PropertyValueFactory<>("meno"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        text.setCellValueFactory(new PropertyValueFactory<>("text"));

        id.setSortType(TableColumn.SortType.ASCENDING);

    }
}
