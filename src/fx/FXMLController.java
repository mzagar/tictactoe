/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ticktacktoe.Game;
import ticktacktoe.GameLogic;
import ticktacktoe.Move;
import ticktacktoe.SwingUI;
import ticktacktoe.UI;

/**
 * FXML Controller class
 *
 * @author mzagar
 */
public class FXMLController implements UI, Initializable {

    @FXML Button button00;
    @FXML Button button01;
    @FXML Button button02;
    @FXML Button button10;
    @FXML Button button11;
    @FXML Button button12;
    @FXML Button button20;
    @FXML Button button21;
    @FXML Button button22;
    
    @FXML MenuItem close;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button00.getStyleClass().add("toggle-button");
    }    
    
    @Override
    public void illegalMove(Move move) {
        Platform.runLater(() -> new Alert(AlertType.WARNING, "Illegal move: " + move).showAndWait());
    }

    @Override
    public void render(Game game) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Game.Player[][] boardSnapshot = game.getBoardSnapshot();
                button00.setText(name(boardSnapshot[0][0]));
                button01.setText(name(boardSnapshot[0][1]));
                button02.setText(name(boardSnapshot[0][2]));
                button10.setText(name(boardSnapshot[1][0]));
                button11.setText(name(boardSnapshot[1][1]));
                button12.setText(name(boardSnapshot[1][2]));
                button20.setText(name(boardSnapshot[2][0]));
                button21.setText(name(boardSnapshot[2][1]));
                button22.setText(name(boardSnapshot[2][2]));
            }

            private String name(Game.Player player) {
                return player == null ? "" : player.name();
            }
        });
    }

    @Override
    public void gameOver(String message) {
        Platform.runLater(() -> new Alert(AlertType.INFORMATION, message).showAndWait());
    }
    
    @FXML 
    public void closeClicked(ActionEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to close?", ButtonType.CANCEL, ButtonType.YES);
        if (a.showAndWait().get().equals(ButtonType.YES)) {
            Stage window = (Stage) button00.getScene().getWindow();
            window.close();
        }
    }
    
    @FXML
    public void buttonClick(ActionEvent event) {
        Object source = event.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        
        Button button = (Button) source;

        String coordinates = button.getId().substring(6, 8);
        int x = coordinates.getBytes()[0] - 0x30;
        int y = coordinates.getBytes()[1] - 0x30;
        
        signalMove(new Move(x, y));
        
        button.setStyle("toggle-button");
        
    }
    
    private Object moveSynchronizer = new Object();
    private Move latestMove;
    
    @Override
    public Move getMove(Game.Player currentPlayer) {
        
        Stage stage = (Stage) button00.getScene().getWindow();
        Platform.runLater(() -> stage.setTitle("Your move!"));
        synchronized(moveSynchronizer) {
            try {
                moveSynchronizer.wait();
                Platform.runLater(() -> stage.setTitle("Wait your turn..."));
                return latestMove;
            } catch (InterruptedException ex) {
                Logger.getLogger(SwingUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    private void signalMove(Move move) {
        synchronized(moveSynchronizer) {
            latestMove = move;
            moveSynchronizer.notifyAll();
        }
    }
}
