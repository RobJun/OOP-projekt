package junas.robert.lagatoria.gui.CustomElements;

import javafx.scene.control.Button;
import junas.robert.lagatoria.core.utils.Observer;


public class CustomButton extends Button implements Observer {

    public CustomButton(String text, String id){
        setText(text);
        setId(id);
    }

    @Override
    public void notify(Object caller, Object msg) {
        setText((String) msg);
    }
}
