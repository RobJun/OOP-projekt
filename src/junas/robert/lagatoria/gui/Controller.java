package junas.robert.lagatoria.gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import junas.robert.lagatoria.core.users.Pouzivatel;
import junas.robert.lagatoria.core.users.Zamestnanec;
import junas.robert.lagatoria.core.users.vydavatelstvo.Manazer;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.utils.enums.LoggedIn;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.Autor;
import junas.robert.lagatoria.gui.substages.*;

import java.util.HashMap;

public class Controller implements Observer {

    private final Model model;
    private View view;


    Controller(Model model){
        this.model = model;
    }

    public void notify(Object object, Object out){
        String output = (String)out;
        if(object instanceof Autor || object instanceof Zamestnanec)
            this.updateViews(output,"out");
        else if(object instanceof Vydavatelstvo)
            this.updateViews("Queue<:>"+output,"table");

    }

    private HashMap<String,Observer> updatableViews = new HashMap<>();

     public void updateViews(Object o, String out){
         if(out == null){
             System.out.println((String)o);
             return;
         }
         updatableViews.get(out).notify(this,o);
    }

    public void addUpdatableView(String key, Observer view){
        updatableViews.put(key, view);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void updateView(LoggedIn p){
        model.changeUser(p);
        updatableViews.get("pane").notify(this,model.getPouzivatel());
        updatableViews.get("prih").notify(this,model.getPouzivatel().getClass().getSimpleName());
    }


    public void objednaj(TextField input, String outKey){
        if(input.getText().isEmpty()){
            this.updateViews("zadajte cestu k suboru",outKey);
        }else{
            this.updateViews(model.objednaj(input.getText()),outKey);
        }
    }

    public void prines(TextField input, String outKey){
        this.updateViews(model.prines(input.getText()), outKey);
    }

    public void spracuj(String command, String outKey){
        this.updateViews(model.spracuj(command),outKey);
    }
    
    public void serialize(){
        this.updateViews(model.serialize(),null);
    }

    public void Zakaznik_vstup(String key){
        this.updateViews(model.zakaznikVstup(),null);
    }

    public void otvorZatvor(String key){
        this.updateViews(model.otvorZatvor(),key);
    }

    public void changeStrategy(Button strategia, Button vydaj,String key){
        String[] t = model.changeStrategy();
        strategia.setText(t[0]);
        vydaj.setText(t[1]);

    }

    public void odstranOdoberatela(TextField text, Stage stage, String key){
        this.updateViews(model.odstranOdoberatela(text.getText()),key);
        stage.close();
    }

    public void createOdoberatel(TextField text, Stage stage, String key){
        this.updateViews(model.pridajOdoberatela(text.getText()),key);
        stage.close();
    }

    public void createOdoberatelKateg(TextField nazovStanku, TextField dalsie, Stage subStage, String out) {
        this.updateViews(model.pridajOdoberatelaKategorizovaneho(nazovStanku.getText(),dalsie.getText()),out);
        subStage.close();
    }

    public void createOdoberatelMin(TextField nazovStanku, TextField dalsie, Stage subStage, String out) {
        this.updateViews(model.pridajOdoberatelaMinimum(nazovStanku.getText(),dalsie.getText()),out);
        subStage.close();
    }

    public void removeAutor(TextField text, Stage stage, String key){
        this.updateViews(model.removeAutor(text.getText()),key);
        stage.close();
    }

    public void createAutor(TextField meno, TextField prievzisko, String typ, Stage stage, String key){
        this.updateViews(model.createAutor(meno.getText(), prievzisko.getText(),typ),key);
        stage.close();
    }


    public EventHandler<MouseEvent> getInfoHandler() {
         Controller th = this;
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                th.updateViews(model.spracuj("info-me | plat | zarobene | odrobene"), "out");
            }
        };
    }

    public String getPrihlaseny() {
        Pouzivatel p = model.getPouzivatel();
        if(p == null)
            return "Nikto";
        return p.getClass().getSimpleName();
    }


    public void autorCreateStage(String autor){
        new AutorCreation(autor,this);
    }

    public void autorRemoveStage(){
        new AutorRemove(this);
    }

    public void odoberatelCreateStage(int k){
        new OdoberatelCreation(k,this);
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
