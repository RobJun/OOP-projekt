package junas.robert.lagatoria.gui.customElements;

import javafx.scene.text.Text;
import junas.robert.lagatoria.core.utils.Observer;

public class InfoPrihlaseny extends Text implements Observer {

    public InfoPrihlaseny(){
        setText("(Nikto)");
    }

    @Override
    public void notify(Object caller, Object msg) {
        setText("("+(String) msg +")");
    }
}
