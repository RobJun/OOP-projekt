package junas.robert.lagatoria.gui.TableViews;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.gui.TableViews.data.KatalogData;
import junas.robert.lagatoria.gui.TableViews.data.TextyNaVydanieData;

public class KatalogTableView extends TableView {
    private TableColumn<KatalogData,Integer> id;
    private TableColumn<KatalogData,String> nazov;
    private TableColumn<KatalogData,String> autor;
    private TableColumn<KatalogData,String> isbn;
    private TableColumn<KatalogData,String> vydavatel;

    private ObservableList<KatalogData> list;


    public KatalogTableView(ObservableList<KatalogData> data){
        list = data;
        setEditable(false);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        id = new TableColumn<>("id");
        nazov = new TableColumn<>("nazov");
        autor = new TableColumn<>("autor");
        isbn = new TableColumn<>("isbn");
        vydavatel = new TableColumn<>("vydavatel");

        setItems(list);
        getColumns().addAll(id,nazov,autor,isbn,vydavatel);


        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nazov.setCellValueFactory(new PropertyValueFactory<>("nazov"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        vydavatel.setCellValueFactory(new PropertyValueFactory<>("vydavatelstvo"));

        id.setSortType(TableColumn.SortType.ASCENDING);

    }

}
