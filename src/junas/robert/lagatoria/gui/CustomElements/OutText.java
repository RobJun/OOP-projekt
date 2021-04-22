package junas.robert.lagatoria.gui.CustomElements;

import javafx.scene.control.TextArea;
import junas.robert.lagatoria.core.utils.Observer;

public class OutText extends TextArea implements Observer {

    public OutText(){
        setPrefWidth(800);
        setPrefHeight(500);

    }


    @Override
    public void notify(Object caller, Object msg) {
        appendText((String) msg + "\n");
    }
}
