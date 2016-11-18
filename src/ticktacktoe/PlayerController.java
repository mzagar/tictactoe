/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import ticktacktoe.Game.Player;

/**
 *
 * @author mzagar
 */
public class PlayerController {
    private final Player startingPlayer;
    private Player currentPlayer = null;
    
    public PlayerController(Player startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public Player next() {
        if (currentPlayer == null) { currentPlayer = startingPlayer; return startingPlayer; }
        
        currentPlayer = currentPlayer == null || currentPlayer == Player.O ? Player.X : Player.O;
        
        return currentPlayer;
    }

    public Player current() {
        return currentPlayer;
    }
    
}
