package junas.robert.lagatoria.gui.controllers;

import java.lang.String;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.gui.CustomElements.CustomButton;
import junas.robert.lagatoria.gui.Model;
import junas.robert.lagatoria.gui.View;
import junas.robert.lagatoria.gui.substages.SubStage;

import java.util.HashMap;

public class MainController extends Controller implements Observer {

    public MainController(Model model) {
        super(null, model);
        updatable.put("modelC", new ModelController(this, model));
        updatable.put("viewC", new ViewController(this, model));
    }

    public void notify(Object caller, Object out) {
        if(caller == null && ((String)out).compareTo("serialize") == 0){
            model.serialize();
            return;
        }
        if(caller instanceof ModelController
                || caller instanceof ButtonController
                || caller instanceof Model
                || caller instanceof Autor
                || caller instanceof Manazer){
            updatable.get("viewC").notify(caller,out);
            return;
        }
        updatable.get("modelC").notify(caller,out);

    }

    public void addUpdatableView(String key, Observer view) {
        for (Observer up : updatable.values()) {
            if (up instanceof ViewController)
                ((ViewController) up).addUpdatableView(key, view);
        }
    }

    public void setView(View view) {
        updatable.get("viewC").notify(view, view);

    }

    public void changePrihlaseny(LoggedIn p) {
        for (Observer up : updatable.values()) {
            up.notify(this, p);
        }
    }


    public void open(Object caller, ViewController.WindowKeys key) {
        for (Observer up : updatable.values()) {
            up.notify(caller, key);
        }
    }


    public EventHandler<MouseEvent> getInfoHandler() {
        MainController th = this;
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                th.updatable.get("modelC").notify(event.getSource(),"out");
            }
        };
    }

    public EventHandler<ActionEvent> autorCreator() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    updatable.get("viewC").notify(event.getSource(), ViewController.WindowKeys.AUTOR_C);
            }
        };
    }
}
