/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.util.Scanner;
import ticktacktoe.Game.Player;

/**
 *
 * @author mzagar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        Screen screen = new Screen();
        PlayerController playerController = new PlayerController(Player.X);

        screen.render(game);
        
        Player currentPlayer = playerController.current();
        
        while(!game.over()) {
            currentPlayer = playerController.next();

            Move move = getMove(currentPlayer);
            
            if (!game.isValidMode(move)) {
                screen.illegalMove(move);
                continue;
            }

            game.move(move, currentPlayer);

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
}
  
