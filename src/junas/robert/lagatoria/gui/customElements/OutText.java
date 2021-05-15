package junas.robert.lagatoria.gui.customElements;

import javafx.scene.control.TextArea;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.gui.controllers.ViewController;

public class OutText extends TextArea implements Observer {

    public OutText(){
        setPrefWidth(800);
        setPrefHeight(500);

    }


    @Override
    public void notify(Object caller, Object msg) {
        if(caller instanceof ViewController){
            clear();
            return;
        }
            appendText((String) msg + "\n");
    }
}
