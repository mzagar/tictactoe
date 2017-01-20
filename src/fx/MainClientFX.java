/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx;

import fx.FXMLController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ticktacktoe.GameLogic;
import ticktacktoe.GameLogic;
import ticktacktoe.UI;

/**
 *
 * http://docs.oracle.com/javafx/2/css_tutorial/jfxpub-css_tutorial.htm
 * 
 * @author mzagar
 */
public class MainClientFX extends Application {

    private static final boolean client = true; // System.getProperty("isClient") != null;

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        URL resource = MainClientFX.class.getResource("FXML.fxml");

        FXMLLoader loader = new FXMLLoader(resource);
        Pane root = loader.load();

        FXMLController controller = loader.<FXMLController>getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("fx/fxml.css");
        primaryStage.setScene(scene);
        
        Thread gameLogicThread = new Thread(new GameLogic(controller, client));
        gameLogicThread.setDaemon(true);
        gameLogicThread.start();

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
