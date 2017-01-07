/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import fx.NewFXMain;
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
public class Main {

    private static final boolean client = System.getProperty("isClient") != null;
    private static final boolean consoleUI = System.getProperty("consoleUI") != null;
    private static final boolean swingUI = System.getProperty("swingUI") != null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        UI ui = createUI();
        PlayerController playerController = new PlayerController(Player.X);
        NetworkProtocol networkProtocol = new NetworkProtocol(client);

        ui.render(game);
        
        Player currentPlayer = playerController.current();
        
        while(!game.over()) {
            currentPlayer = playerController.next();

            if (isLocalPlayer(client, currentPlayer)) {
                Move move = getValidMove(ui, game, currentPlayer);

                game.move(move, currentPlayer);
                
                System.out.println("Sending local game state to remote...");
                networkProtocol.sendLocalState(new GameState(game.getBoardSnapshot(), currentPlayer));
            } else {
                System.out.println("Waiting for remote state from player: " + currentPlayer);
                GameState remoteState = networkProtocol.waitForRemoteState();
                game.setBoard(remoteState.getBoard());
            }

            ui.render(game);
        }
        
        ui.gameOver("Game over, player " + currentPlayer + " wins!");
    }

    private static boolean isLocalPlayer(boolean client, Player currentPlayer) {
        return client && Player.X == currentPlayer || !client && Player.O == currentPlayer;
    }

    private static UI createUI() {
        if (consoleUI) {
            return new ConsoleUI();
        }
        
        if (swingUI) {
            SwingUI form = new SwingUI();
            form.setVisible(true);
            form.setTitle("Player: " + (client ? Player.X.name() : Player.O.name()));
            return form;
        }

        return NewFXMain.start("Player: " + (client ? Player.X.name() : Player.O.name()));
    }

    private static Move getValidMove(UI ui, Game game, Player currentPlayer) {
        while(true) {
            Move move = ui.getMove(currentPlayer);
            if (game.isValidMode(move)) return move;
            ui.illegalMove(move);
        }
    }
}


