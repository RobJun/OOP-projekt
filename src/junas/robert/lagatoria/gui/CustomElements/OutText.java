package junas.robert.lagatoria.gui.CustomElements;

import javafx.beans.InvalidationListener;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import junas.robert.lagatoria.core.utils.Observer;

public class OutText extends TextArea implements Observer {

    public OutText(){
        setPrefWidth(800);
        setPrefHeight(500);

    }


    @Override
    public void notify(Object o, Object s) {
        appendText((String)s + "\n");
    }
}
