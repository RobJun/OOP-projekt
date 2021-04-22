package junas.robert.lagatoria.gui.controllers;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.gui.Model;
import junas.robert.lagatoria.gui.View;
import junas.robert.lagatoria.gui.substages.*;

public class ViewController extends Controller {

    public enum WindowKeys{
        AUTOR_C,
        AUTOR_R,
        ODOBER_C,
        ODOBERM_C,
        ODOBERK_C,
        ODOBER_R,
        TAB_TEXT,
        TAB_KAT,
        TAB_ODOB
    }

    private View view;

    ViewController(Observer parent, Model model) {
        super(parent, model);
    }

    @Override
    public void notify(Object caller, Object msg){
        if(caller instanceof Manazer
                || caller instanceof Autor
           || caller instanceof Vydavatelstvo) {
            updatable.get("out").notify(caller, (String) msg);
            return;
        }
        if(caller instanceof ModelController
                || caller instanceof ButtonController){
            for(int i = 0; i < ((String[])msg).length;i+=2)
            updatable.get(((String[])msg)[i+1]).notify(caller,((String[])msg)[i]);
        }
        if(caller instanceof SubStage){
            parent.notify(caller,msg);
            ((SubStage)caller).getSubStage().close();
        }if(msg instanceof View){
            this.view = (View)msg;
        }else if(msg instanceof LoggedIn){
            updatable.get("pane").notify(this,model.getPouzivatel());
            updatable.get("prih").notify(this,model.getPouzivatel().getClass().getSimpleName());
        }if(msg instanceof WindowKeys){
            String msg2 = "";
            if(caller instanceof Button)
                msg2 = ((Button) caller).getText();
            else if(caller instanceof MenuItem)
                msg2 = ((MenuItem)caller).getText();
           whichWindow((WindowKeys)msg,(msg2));
        }
    }

    public void whichWindow(WindowKeys key, String msg) {
        switch (key) {
            case AUTOR_C:
                autorCreateStage(msg);
                break;
            case AUTOR_R:
                autorRemoveStage();
                break;
            case ODOBER_C:
                odoberatelCreateStage(0);
                break;
            case ODOBERM_C:
                odoberatelCreateStage(2);
                break;
            case ODOBERK_C:
                odoberatelCreateStage(1);
                break;
            case ODOBER_R:
                odoberaltelRemoveStage();
                break;
            case TAB_TEXT:
                openTextyNaVydanie();
                break;
            case TAB_KAT:
                openKatalog();
                break;
            case TAB_ODOB:
                openOdoberatelovNaVydanie();
                break;
        }
    }

    public void addUpdatableView(String key, Observer view) {
        updatable.put(key, view);
    }

    public void autorCreateStage(String autor){
        new AutorCreation(autor,this);
    }

    public void autorRemoveStage(){
        new AutorRemove(this);
    }

    public void odoberatelCreateStage(int k){
        new OdoberatelCreation(k,this, (k == 0) ? null : ((k == 1) ? "kategoria" : "minimum"));
    }

    public void odoberaltelRemoveStage(){
        new OdoberatelRemove(this);
    }

    public void openTextyNaVydanie() {
        new TabulkaView("Texty na vydanie",model.getTexty());
    }
    public void openOdoberatelovNaVydanie(){ new OdoberatelView("Odoberatelia", model.getData()); }

    public void openKatalog(){ new KatalogView("Katalog", model.getKatalogData()); }
}
