package junas.robert.lagatoria.gui.controllers;


import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.gui.Model;
import junas.robert.lagatoria.gui.substages.*;
import java.lang.String;

public class ModelController extends Controller {

    ButtonController buttons;

    ModelController(Observer parent, Model model) {
        super(parent, model);
        buttons = new ButtonController(this,model);
        model.setController(this);
    }

    @Override
    public void notify(Object caller, Object msg) {
        if(caller instanceof ButtonController){
            parent.notify(caller, msg);
            return;
        }
        if(caller instanceof MenuItem) {
            if(msg instanceof String) {
                buttons.spracuj("Pzoznam", (String) msg);
            }
            return;
        }
        if(caller instanceof Button){
            buttons.notify(caller, msg);
            return;
        }
        if(caller instanceof SubStage){
            decideSubStage((SubStage) caller, (String)msg);
            return;
        }
        if(caller instanceof MainController && msg instanceof LoggedIn){
            model.changeUser((LoggedIn)msg);
            return;
        }
        parent.notify(caller,msg);
    }


    private void decideSubStage(SubStage caller, String msg){
        if(caller instanceof AutorCreation){
            AutorCreation call = (AutorCreation)caller;
            String output = model.createAutor(call.getMeno(),call.getPrievzisko(), call.getAutor());
            parent.notify(this,new String[]{output,msg});
        }else if(caller instanceof AutorRemove){
            AutorRemove call = (AutorRemove)caller;
            String output = model.removeAutor(call.getIndex());
            parent.notify(this,new String[]{output,msg});
        }else if(caller instanceof OdoberatelCreation){
            OdoberatelCreation call = (OdoberatelCreation)caller;
            String output = "";
            if(call.getWhich() == 0) {
                output = model.pridajOdoberatela(call.getNazov());
            }else if(call.getWhich() == 1){
                output = model.pridajOdoberatelaKategorizovaneho(call.getNazov(), call.getDalsie());
            }else if(call.getWhich() == 2){
                output = model.pridajOdoberatelaMinimum(call.getNazov(),call.getDalsie());
            }
            parent.notify(this, new String[]{output,msg});
        }else if(caller instanceof OdoberatelRemove){
            OdoberatelRemove call = (OdoberatelRemove) caller;
            String output = model.odstranOdoberatela(call.getIndex());
            parent.notify(this,new String[]{output,msg});
        }
    }

}
