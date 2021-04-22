package junas.robert.lagatoria.gui.CustomElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.users.vydavatelstvo.Distributor;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.Observer;

import java.util.ArrayList;

public class MyPane extends StackPane implements Observer {

    ArrayList<HBox> changable;


    public MyPane(HBox... changable){

        this.changable = new ArrayList<>();

        for(HBox box: changable){
            this.changable.add(box);
        }

        setPadding(new Insets(5,0,5,20));

        Text text2 = new Text("Prihlaste sa");
        StackPane def = new StackPane();
        def.getChildren().add(text2);
        def.setAlignment(Pos.CENTER);

        getChildren().add(def);
    }

    @Override
    public void notify(Object caller, Object msg) {
        getChildren().remove(0);
        if(msg instanceof Zakaznik)
            getChildren().add(changable.get(0));
        else if(msg instanceof Skladnik)
            getChildren().add(changable.get(1));
        else if(msg instanceof Predajca)
            getChildren().add(changable.get(2));
        else if(msg instanceof Manazer)
            getChildren().add(changable.get(3));
        else if(msg instanceof Distributor)
            getChildren().add(changable.get(4));
    }
}
