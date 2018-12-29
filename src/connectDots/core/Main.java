package connectDots.core;

import connectDots.game.Board;
import connectDots.game.Player;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        Board board = new Board(4, 4);
        Player human = new Player.HumanPlayer("human");
        human.play(board);
        board.getPossibleMoves();

        board.printBoard();


    }
}
