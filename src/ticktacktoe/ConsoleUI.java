/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.util.Scanner;

/**
 *
 * @author mzagar
 */
public class ConsoleUI implements UI {
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    public ConsoleUI() {
    }
    
    public void render(Game game) {
        System.out.println("\n--------------------------");
        Game.Player[][] boardSnapshot = game.getBoardSnapshot();
        
        for(int row = 0; row < boardSnapshot.length; row++) {
            for(int col = 0; col < boardSnapshot[row].length; col++) {
                Game.Player playerMark = boardSnapshot[row][col];
                String tick = Game.Player.X.equals(playerMark) ? "X" : Game.Player.O.equals(playerMark) ? PLAYER_O : ".";
                System.out.print(tick + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------------\n");
    }

    public void illegalMove(Move move) {
        System.out.println("Move " + move + " is illegal" + ", allowed values 0..2 for x,y and position must not be already occupied. Try again");
    }

    @Override
    public Move getMove(Game.Player currentPlayer) {
        System.out.print("Player " + currentPlayer + ", enter coordinates: x,y --> ");
        String[] input = new Scanner(System.in).next().split(",");
        int x = Integer.valueOf(input[0]);
        int y = Integer.valueOf(input[1]);
        return new Move(x, y);
    }

    @Override
    public void gameOver(String message) {
        System.out.println(message);
    }
}
