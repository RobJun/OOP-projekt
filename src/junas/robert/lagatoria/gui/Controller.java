package junas.robert.lagatoria.gui;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.knihkupectvo.Predajca;
import junas.robert.lagatoria.core.users.knihkupectvo.Skladnik;
import junas.robert.lagatoria.core.users.knihkupectvo.Zakaznik;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.LoggedIn;

public class Controller {
    private Pouzivatel pouzivatel;
    private Zakaznik zakaznik = new Zakaznik();
    private Predajca predajca = new Predajca("Predavac",22);
    private Skladnik skladnik = new Skladnik("Skladnik", 23);
    private Manazer manazer = new Manazer("manazer", 24,8.5);
    private StackPane pane;


    public Controller(StackPane pane){
        this.pane = 
    }



    public String getPouzivatel() {
        if(pouzivatel == null) return "NIKTO";
        return pouzivatel.getClass().getSimpleName().toUpperCase();
    }

    public void update(Text text, LoggedIn user){
        changeUser(user);
        text.setText("(" + getPouzivatel()+")");
    }


    public void changeUser(LoggedIn pouzivatel){
        switch (pouzivatel) {
            case SKLADNIK:
                this.pouzivatel = skladnik;
                break;
            case PREDAJCA:
                this.pouzivatel = predajca;
                break;
            case ZAKAZNIK:
                this.pouzivatel = zakaznik;
                break;
            case MANAZER:
                this.pouzivatel = manazer;
                break;
        }
    }
}
