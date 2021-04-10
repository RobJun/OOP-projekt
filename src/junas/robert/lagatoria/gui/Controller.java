package junas.robert.lagatoria.gui;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;

import java.util.HashMap;

public class Controller {

    private final Model model;
    private View view;

    Controller(Model model){
        this.model = model;
    }


    private HashMap<String,Node> updatableViews = new HashMap<>();
    private static Node desiredOutput = null;

    static public void updateViews(String s){
        if(desiredOutput == null){
            System.out.println(s);
        }else if(desiredOutput instanceof TextArea){
            TextArea t = (TextArea) desiredOutput;
            t.appendText(s + "\n");
        }else if(desiredOutput instanceof Button){
            ((Button) desiredOutput).setText(s);
        }
    }

    public void addUpdatableView(String key, Node view){
        updatableViews.put(key, view);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void updateView(LoggedIn p){
        model.changeUser(p);
        view.updateTools(model.getPouzivatel());
        view.setPrihlaseny(model.getPouzivatel().getClass().getSimpleName());
    }


    public void objednaj(TextField input, String outKey){
        Controller.desiredOutput = updatableViews.get(outKey);
        if(input.getText().isEmpty()){
            Controller.updateViews("zadajte cestu k suboru");
        }else{
            Controller.updateViews(model.objednaj(input.getText()));
        }
    }

    public void prines(TextField input, String outKey){
        Controller.desiredOutput = updatableViews.get(outKey);
        Controller.updateViews(model.prines(input.getText()));
    }

    public void spracuj(String command, String outKey){
        Controller.desiredOutput = updatableViews.get(outKey);
        Controller.updateViews(model.spracuj(command));
    }
    
    public void serialize(){
        Controller.desiredOutput = null;
        Controller.updateViews(model.serialize());
    }

    public void Zakaznik_vstup(String key){
        Controller.desiredOutput = updatableViews.get(key);
        Controller.updateViews(model.zakaznikVstup());
    }

    public void otvorZatvor(String key){
        Controller.desiredOutput = updatableViews.get(key);
        Controller.updateViews(model.otvorZatvor());
    }

    public void changeStrategy(Button strategia, Button vydaj,String key){
        Controller.desiredOutput = updatableViews.get(key);
        String[] t = model.changeStrategy();
        strategia.setText(t[0]);
        vydaj.setText(t[1]);

    }

    public void odstranOdoberatela(TextField text, Stage stage, String key){
        Controller.desiredOutput = updatableViews.get(key);
        Controller.updateViews(model.odstranOdoberatela(text.getText()));
        stage.close();
    }

    public void createOdoberatel(TextField text, Stage stage, String key){
        Controller.desiredOutput = updatableViews.get(key);
        Controller.updateViews(model.pridajOdoberatela(text.getText()));
        stage.close();
    }

    public void removeAutor(TextField text, Stage stage, String key){
        Controller.desiredOutput = updatableViews.get(key);
        Controller.updateViews(model.removeAutor(text.getText()));
        stage.close();
    }

    public void createAutor(TextField meno, TextField prievzisko, String typ, Stage stage, String key){
        Controller.desiredOutput = updatableViews.get(key);
        Controller.updateViews(model.createAutor(meno.getText(), prievzisko.getText(),typ));
        stage.close();
    }


    public EventHandler<MouseEvent> getInfoHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.desiredOutput = updatableViews.get("out");
                Controller.updateViews(model.spracuj("info-me | plat | zarobene | odrobene"));
            }
        };
    }

    public String getPrihlaseny() {
        Pouzivatel p = model.getPouzivatel();
        if(p == null)
            return "Nikto";
        return p.getClass().getSimpleName();
    }
}
