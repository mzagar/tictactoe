/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.util.logging.Level;
import java.util.logging.Logger;
import ticktacktoe.Game.Player;

/**
 * To run server: java -jar dist/ticktacktoe.jar 
 * To run client: java -DisClient -jar dist/ticktacktoe.jar
 * Client connects to server running on localhost port 12345.
 * 
 * @author mzagar
 */
public class MainServerSwing {

    private static final boolean client = false; // System.getProperty("isClient") != null;
    private static final boolean consoleUI = System.getProperty("consoleUI") != null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        UI ui = createUI();
        
        Thread gameLogicThread = new Thread(new GameLogic(ui, client));
        gameLogicThread.start();
        gameLogicThread.join();
    }

    private static UI createUI() {
        if (consoleUI) {
            return new ConsoleUI();
        } else {
            SwingUI form = new SwingUI();
            form.setVisible(true);
            form.setTitle("Player: " + (client ? Player.X.name() : Player.O.name()));
            return form;
        }
    }
}


