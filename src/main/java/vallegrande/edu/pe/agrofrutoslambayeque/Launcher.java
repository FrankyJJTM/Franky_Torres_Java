package vallegrande.edu.pe.agrofrutoslambayeque;

import javafx.application.Application;
import javafx.stage.Stage;
import vallegrande.edu.pe.agrofrutoslambayeque.view.LoginView;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            LoginView loginView = new LoginView();
            loginView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}