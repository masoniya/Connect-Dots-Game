package connectDots.core;

import connectDots.game.Game;
import java.util.Scanner;
import connectDots.game.Player;


public class Main {


    public static void main(String[] args){
        int difficulty;

        int width, height;

        Scanner scan = new Scanner(System.in);
        System.out.println("Select Difficulty : (1 - 10)");
        difficulty = scan.nextInt();

        System.out.println("Enter width : ");
        width = scan.nextInt();

        System.out.println("Enter height : ");
        height = scan.nextInt();

        Game.HumanVsCpuGame game = new Game.HumanVsCpuGame(width, height, 'H', 'C', difficulty);
        game.startGame();

        System.out.println("Total iterations : " + Player.totalIterations);
    }


}
