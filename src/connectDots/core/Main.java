package connectDots.core;

import connectDots.game.Board;
import connectDots.game.Player;

public class Main {

    public static void main(String[] args){
        Board board = new Board(4, 4);
        Player human = new Player.HumanPlayer("human");
        for(int i = 0; i < 4; i++){
            human.play(board);
        }

        board.printBoard();


    }
}
