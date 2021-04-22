package junas.robert.lagatoria.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import junas.robert.lagatoria.gui.controllers.MainController;

public class Main extends Application {

    public static boolean enabled = false;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Model model = new Model();
        MainController controller = new MainController(model);
        model.setController(controller);
        View view = new View(controller);
        controller.setView(view);

        enabled = true;

        stage.setOnCloseRequest(e -> {
            controller.notify(null,"serialize");
        });

        stage.setScene(view.getMainScene());
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}
