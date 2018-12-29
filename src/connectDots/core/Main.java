package connectDots.core;

import connectDots.game.Game;


public class Main {

    public static void main(String[] args){
        Game.HumanVsCpuGame game = new Game.HumanVsCpuGame(3, 3, 'H', 'C');
        game.startGame();

    }
}
