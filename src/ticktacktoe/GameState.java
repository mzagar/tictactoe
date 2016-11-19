/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.io.Serializable;

/**
 *
 * @author mzagar
 */
class GameState implements Serializable {
    private static final int serialVersionUID = 1;
    private final Game.Player[][] board;
    private final Game.Player currentPlayer;

    public GameState(Game.Player[][] board, Game.Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Game.Player[][] getBoard() {
        return board;
    }

    public Game.Player getCurrentPlayer() {
        return currentPlayer;
    }
    
}
