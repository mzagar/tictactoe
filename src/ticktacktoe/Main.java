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
        NetworkProtocol networkProtocol = new NetworkProtocol(client);
        
        Game game = new Game();
        Screen screen = new Screen();
        PlayerController playerController = new PlayerController(Player.X);

        screen.render(game);
        
        Player currentPlayer = playerController.current();
        
        while(!game.over()) {
            currentPlayer = playerController.next();

            if (isLocalPlayer(client, currentPlayer)) {
                
                Move move;
                
                while(true) {
                    move = getMove(currentPlayer);
                    if (game.isValidMode(move)) break;
                    screen.illegalMove(move);
                }

                game.move(move, currentPlayer);
                
                System.out.println("Sending local game state to remote...");
                networkProtocol.sendLocalState(new GameState(game.getBoardSnapshot(), currentPlayer));
            } else {
                System.out.println("Waiting for remote move from player: " + currentPlayer);
                GameState remoteState = networkProtocol.waitForRemoteState();
                game.setBoard(remoteState.getBoard());
            }

            screen.render(game);
        }
        
        System.out.println("Game over, player " + currentPlayer + " wins!");
    }

    private static Move getMove(Player currentPlayer) {
        System.out.print("Player " + currentPlayer + ", enter coordinates: x,y --> ");
        String[] input = new Scanner(System.in).next().split(",");
        int x = Integer.valueOf(input[0]);
        int y = Integer.valueOf(input[1]);
        return new Move(x, y);
    }

    private static boolean isLocalPlayer(boolean client, Player currentPlayer) {
        return client && Player.X == currentPlayer || !client && Player.O == currentPlayer;
    }

}

class GameState implements Serializable {
    private static final int serialVersionUID = 1;
    
    private final Player[][] board;
    private final Player currentPlayer;
    public GameState(Player[][] board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Player[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

class NetworkProtocol {

    final Socket socket;
    
    NetworkProtocol(boolean client) throws IOException {
        if (!client) {
            System.out.println("Waiting for client connection on port 12345...");
            socket = new ServerSocket(12345).accept();
            System.out.println("New client connected: " + socket);
        } else {
            System.out.println("Connecting to server on localhost port 12345...");
            socket = new Socket("localhost", 12345);
            System.out.println("Conected to server: " + socket);
        }
    }

    GameState waitForRemoteState() throws IOException, ClassNotFoundException {
        return (GameState) new ObjectInputStream(socket.getInputStream()).readObject();
    }

    void sendLocalState(GameState gameState) throws IOException {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(gameState);
    }
}
