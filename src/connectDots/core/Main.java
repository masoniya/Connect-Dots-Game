package connectDots.core;

import connectDots.game.Game;
import connectDots.game.Player;


public class Main {

    public static void main(String[] args){
        Game.HumanVsCpuGame game = new Game.HumanVsCpuGame(5, 5, 'H', 'C');
        game.startGame();

        System.out.println("Iterations : " + Player.iterations);
    }
}
