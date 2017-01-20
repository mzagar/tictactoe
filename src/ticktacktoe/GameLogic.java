/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.io.IOException;

/**
 *
 * @author mzagar
 */
public class GameLogic implements Runnable {
    private final UI ui;
    private final boolean client;

    public GameLogic(UI ui, boolean client) {
        this.ui = ui;
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            runGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void runGame() throws IOException, ClassNotFoundException {
        Game game = new Game();
        PlayerController playerController = new PlayerController(Game.Player.X);
        NetworkProtocol networkProtocol = new NetworkProtocol(client);

        ui.render(game);

        Game.Player currentPlayer = playerController.current();

        while (!game.over()) {
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

    private static boolean isLocalPlayer(boolean client, Game.Player currentPlayer) {
        return client && Game.Player.X == currentPlayer || !client && Game.Player.O == currentPlayer;
    }

    private static Move getValidMove(UI ui, Game game, Game.Player currentPlayer) {
        while (true) {
            Move move = ui.getMove(currentPlayer);
            if (game.isValidMode(move)) {
                return move;
            }
            ui.illegalMove(move);
        }
    }
}
