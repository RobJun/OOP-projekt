package junas.robert.lagatoria.gui.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.gui.Model;

public class ButtonController extends Controller{


    ButtonController(Observer parent, Model model) {
        super(parent, model);
    }

    @Override
    public void notify(Object caller, Object msg) {
        Button button = (Button)caller;
        decideButton(button,msg);
    }

    private void decideButton(Button call, Object msg){
        if(call.getId().compareTo("autori") == 0){
            spracuj("vypisAutor", (String)msg);
            return;
        }
        if(call.getId().compareTo("vydaj") == 0){
            spracuj("vydajKnihy",(String)msg);
            return;
        }
        if(call.getId().compareTo("pisat") == 0){
            spracuj("dajNapisat", (String)msg);
            return;
        }
        if(call.getId().compareTo("info") == 0){
            spracuj("info-me | plat | zarobene | odrobene",(String)msg);
            return;
        }
        if(call.getId().compareTo("otvorBtn") == 0){
            otvorZatvor((String) msg);
            return;
        }
        if(call.getId().compareTo("strategia") == 0){
            String[] keys = (String[])msg;
            changeStrategy(keys[0],keys[1]);
            return;
        }
        if(call.getId().contains("vypis")){
            if(call.getId().compareTo("vypisS") == 0){
                spracuj("info-n",(String)msg);
            }
            String id = call.getId().replace("vypis", "");
            String spra = ((id.contains("P")) ? ("predajna") : (""))
                    + (id.contains ("PS") ? (" | "): (""))
                    + ( (id.contains("S")) ? ("sklad") : (""));
            spracuj(spra,(String) msg);
            return;
        }
        if(call.getId().compareTo("predaj") == 0){
            spracuj("predaj",(String)msg);
            return;
        }
        if(call.getId().compareTo("prines") == 0){
            prines((TextField)(((Object[])msg)[0]),(String)(((Object[])msg)[1]));
            return;
        }
        if(call.getId().compareTo("predaj") == 0){
            spracuj("predaj",(String)msg);
            return;
        }
        if(call.getId().compareTo("vstup") == 0){
            zakaznik_vstup((String)msg);
            return;
        }
        if(call.getId().compareTo("kosik") == 0){
            spracuj("kosik",(String)msg);
            return;
        }
        if(call.getId().compareTo("objednaj") == 0){
            objednaj((TextField)((Object[])msg)[0],(String)((Object[])msg)[1]);
            return;
        }
        if(call.getId().compareTo("miesto") == 0){
            spracuj("max-miesto",(String)msg);
            return;
        }if(call.getId().compareTo("knihyPreK") == 0){
            spracuj("knihyPreKnihkupectvo",(String)msg);
            return;
        }
    }



    public void objednaj(TextField input, String outKey) {
        if (input.getText().isEmpty()) {
            parent.notify(this,new String[]{"zadajte cestu k suboru",outKey});
            //this.updateViews("zadajte cestu k suboru", outKey);
        } else {
            //this.updateViews(model.objednaj(input.getText()), outKey);
            parent.notify(this,new String[]{model.objednaj(input.getText()),outKey});
        }
    }

    public void spracuj(String command, String outKey) {
        parent.notify(this,new String[]{model.spracuj(command),outKey});
    }

    public void prines(TextField input, String outKey) {

        parent.notify(this, new String[]{model.prines(input.getText()),outKey});
    }

    public void serialize() {

        parent.notify(this,new String[]{model.serialize(), null});
    }

    public void zakaznik_vstup(String key) {
        parent.notify(this,new String[]{model.zakaznikVstup(),key});
    }

    public void otvorZatvor(String key) {
        parent.notify(this, new String[]{model.otvorZatvor(),key});
    }

    public void changeStrategy(String key1, String key2) {
        String[] t = model.changeStrategy();
        parent.notify(this, new String[]{t[0],key1,t[1],key2});
    }
}
