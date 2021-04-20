package junas.robert.lagatoria.gui.CustomElements;

import javafx.scene.control.Button;
import junas.robert.lagatoria.core.utils.Observer;


public class OtvorButton extends Button implements Observer {

    public OtvorButton(){
        setText("Otvor");
    }

    @Override
    public void notify(Object o, Object s) {
        setText((String)s);
    }
}
