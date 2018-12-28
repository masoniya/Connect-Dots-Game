package connectDots.core;

import connectDots.game.Board;
import connectDots.game.Player;

public class Main {

    public static void main(String[] args){
        Board board = new Board(3, 3);
        Player human = new Player.HumanPlayer("human");
        human.play(board);
        board.printBoard();

    }
}
