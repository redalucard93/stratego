package application;

import RmiService.MonService;
import RmiService.MonServiceImpl;
import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by reda on 29/12/2016.
 */
public class Main extends Application{
      public void start(Stage primaryStage) throws Exception {

        Controleur controleur = new Controleur();

    }



    public static void main(String[] args) {
        launch(args);
    }

}
