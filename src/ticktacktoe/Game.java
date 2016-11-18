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
class Game {

    public enum Player {
        X, O
    }
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private Player[][] board = new Player[ROWS][COLS];

    public Game() {
        init(board);
    }

    boolean over() {
        for (int row = 0; row < ROWS; row++) {
            if (sameOnRow(row)) {
                return true;
            }
        }
        for (int col = 0; col < COLS; col++) {
            if (sameOnCol(col)) {
                return true;
            }
        }
        if (sameOnDiagonal()) {
            return true;
        }
        return false;
    }

    private void init(Player[][] board) {
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                board[x][y] = null;
            }
        }
    }

    public void move(Move move, Player playerMark) {
        board[move.getX()][move.getY()] = playerMark;
    }

    public Player[][] getBoardSnapshot() {
        Player[][] copy = new Player[ROWS][COLS];
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                copy[x][y] = board[x][y];
            }
        }
        return copy;
    }

    private boolean sameOnRow(int row) {
        Player playerMark = board[row][0];
        if (playerMark == null) {
            return false;
        }
        for (int col = 0; col < COLS; col++) {
            if (!playerMark.equals(board[row][col])) {
                return false;
            }
        }
        return true;
    }

    private boolean sameOnCol(int col) {
        Player playerMark = board[0][col];
        if (playerMark == null) {
            return false;
        }
        for (int row = 0; row < ROWS; row++) {
            if (!playerMark.equals(board[row][col])) {
                return false;
            }
        }
        return true;
    }

    private boolean sameOnDiagonal() {
        Player playerMark = board[0][0];
        if (playerMark == null) {
            return false;
        }
        if (playerMark.equals(board[1][1]) && playerMark.equals(board[2][2])) {
            return true;
        }
        playerMark = board[0][2];
        if (playerMark == null) {
            return false;
        }
        if (playerMark.equals(board[1][1]) && playerMark.equals(board[2][0])) {
            return true;
        }
        return false;
    }

    boolean isValidMode(Move move) {
        if (move.getX() < 0 || move.getX() >= ROWS) {
            return false;
        }
        if (move.getY() < 0 || move.getY() >= COLS) {
            return false;
        }
        return board[move.getX()][move.getY()] == null;
    }
    
}
