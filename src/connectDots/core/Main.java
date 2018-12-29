package connectDots.core;

import connectDots.game.Game;
import connectDots.game.Player;


public class Main {

    static double a;
    static double b;

    public static void main(String[] args){
        Game.HumanVsCpuGame game = new Game.HumanVsCpuGame(5, 5, 'H', 'C');

        /*initDepthGenerator(15, 15, 40, 3);
        testDepthGenerator();*/

        game.startGame();

        System.out.println("Iterations : " + Player.iterations);
    }

    /*public static void initDepthGenerator(int minPos, int maxDepth, int maxPos, int minDepth){
        double x1 = minPos;
        double y1 = maxDepth;
        double x2 = maxPos;
        double y2 = minDepth;

        a = (y1 - y2)/Math.log(x1/x2);
        b = Math.exp((y2 * Math.log(x1) - y1 * Math.log(x2))/(y1 - y2));
    }

    public static double generateDepth(int possibleMoves){
        return a * Math.log(b * possibleMoves);
    }

    public static void testDepthGenerator(){
        for(int i = 1; i <= 40; i++){
            System.out.println(i + " : " + Math.min(Math.round(generateDepth(i)), 15));
        }
    }*/


}
