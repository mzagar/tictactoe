/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import ticktacktoe.Game.Player;

/**
 *
 * @author mzagar
 */
public class Main {

    private static boolean client;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        client = System.getProperty("isClient") != null;
        
        Game game = new Game();
        UI ui = createUI(client, game);
        NetworkProtocol networkProtocol = new NetworkProtocol(client);
        PlayerController playerController = new PlayerController(Player.X);

        ui.render(game);
        
        Player currentPlayer = playerController.current();
        
        while(!game.over()) {
            currentPlayer = playerController.next();

            if (isLocalPlayer(client, currentPlayer)) {
                
                Move move;
                
                while(true) {
                    move = ui.getMove(currentPlayer);
                    if (game.isValidMode(move)) break;
                    ui.illegalMove(move);
                }

                game.move(move, currentPlayer);
                
                System.out.println("Sending local game state to remote...");
                networkProtocol.sendLocalState(new GameState(game.getBoardSnapshot(), currentPlayer));
            } else {
                System.out.println("Waiting for remote move from player: " + currentPlayer);
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

    private static UI createUI(boolean client, Game game) {
        return new ConsoleUI();
//        GameForm form = new GameForm(game);
//        form.setVisible(true);
//        form.setTitle("Player: " + (client ? Player.X.name() : Player.O.name()));
//        return form;
    }

}


