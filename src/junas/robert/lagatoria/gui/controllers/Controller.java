package junas.robert.lagatoria.gui.controllers;

import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.gui.Model;

import java.util.HashMap;

public abstract class Controller implements Observer {
    protected Observer parent;
    protected Model model;
    protected HashMap<String, Observer> updatable = new HashMap<>();

    Controller(Observer parent, Model model){
        this.parent = parent;
        this.model = model;
    }
}
