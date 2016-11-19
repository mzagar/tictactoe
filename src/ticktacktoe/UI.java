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
public interface UI {
    void illegalMove(Move move);
    void render(Game game);
    Move getMove(Game.Player currentPlayer);
    void gameOver(String message);
}
