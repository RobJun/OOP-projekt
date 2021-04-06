package junas.robert.lagatoria.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;

public class Controller {

    private final Model model;
    private View view;

    Controller(Model model){
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void updateView(LoggedIn p){
        model.changeUser(p);
        view.updateTools(model.getPouzivatel());
        view.setPrihlaseny(model.getPouzivatel().getClass().getSimpleName());
    }


    public void objednaj(TextField input){
        if(!model.objednaj(input.getText())){
            View.printline("zadajte cestu k suboru");
        }
    }

    public void prines(TextField input){
            View.printline(model.prines(input.getText()));
    }

    public void spracuj(String command){
        model.spracuj(command);
    }

    public void serialize(){
        model.serialize();
    }

    public void Zakaznik_vstup(){
      View.printline(model.zakaznikVstup());
    }

    public void otvorZatvor(Button otvor){
        otvor.setText(model.otvorZatvor());
    }

    public void changeStrategy(Button strategia, Button vydaj){
        String[] t = model.changeStrategy();
        strategia.setText(t[0]);
        vydaj.setText(t[1]);

    }

    public void odstranOdoberatela(TextField text){
        View.printline(model.odstranOdoberatela(text.getText()));
    }

    public void createOdoberatel(TextField text){
        View.printline(model.pridajOdoberatela(text.getText()));
    }

    public void removeAutor(TextField text){
        View.printline(model.removeAutor(text.getText()));
    }

    public void createAutor(TextField meno, TextField prievzisko, String typ){
        View.printline(model.createAutor(meno.getText(), prievzisko.getText(),typ));
    }


    public EventHandler<MouseEvent> getInfoHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                model.spracuj("info-me | plat | zarobene | odrobene");
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
