/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

/**
 *
 * @author mzagar
 */
public class Screen {
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

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

    void illegalMove(Move move) {
        System.out.println("Move " + move + " is illegal" + ", allowed values 0..2 for x,y and position must not be already occupied. Try again");
    }

}
