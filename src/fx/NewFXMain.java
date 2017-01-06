/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ticktacktoe.UI;

/**
 *
 * @author mzagar
 */
public class NewFXMain extends Application  {
    private FXMLController controller;
    private final String title;

    public NewFXMain(){
        app = this;
        title = "unknown";
    }
    
    public NewFXMain(String title) {
        this.title = title;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = NewFXMain.class.getResource("FXML.fxml");
        
        FXMLLoader loader = new FXMLLoader(resource);
        Pane root = loader.load();
        
        controller = loader.<FXMLController>getController();
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        
        latch.countDown();

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static NewFXMain app = null;
    
    public static NewFXMain waitForStart() {
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return app;
    }
    public static UI start() {
        new Thread(() -> launch()).start();
        NewFXMain app = waitForStart();
        return app.controller;
    }
    
}
