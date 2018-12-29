package connectDots.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public abstract class Player {

    protected String name;

    protected char playerCharacter;

    public Player(String name){
        this(name, name.charAt(0));
    }

    public Player(String name, char character){
        this.name = name;
        this.playerCharacter = character;
    }

    public abstract void play(Board board);

    public boolean isWin(Board board){
        HashMap<Character, Integer> playerIndices = new HashMap<>();
        ArrayList<Integer> playerScores = new ArrayList<>();

        getPlayerScores(board, playerIndices, playerScores);

        int index = playerIndices.get(this.playerCharacter);
        int score = playerScores.get(index);

        for(int i = 0; i < playerScores.size(); i++){
            if(i != index) {
                if(score < playerScores.get(i)){
                    return false;
                }
                else if(score == playerScores.get(i)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDraw(Board board){
        HashMap<Character, Integer> playerIndices = new HashMap<>();
        ArrayList<Integer> playerScores = new ArrayList<>();

        getPlayerScores(board, playerIndices, playerScores);

        int index = playerIndices.get(this.playerCharacter);
        int score = playerScores.get(index);

        boolean draw = false;
        for(int i = 0; i < playerScores.size(); i++){
            if(i != index) {
                if(score < playerScores.get(i)){
                    return false;
                }
                else if(score == playerScores.get(i)){
                    draw = true;
                }
            }
        }
        return draw;
    }

    public boolean isLose(Board board){
        HashMap<Character, Integer> playerIndices = new HashMap<>();
        ArrayList<Integer> playerScores = new ArrayList<>();

        getPlayerScores(board, playerIndices, playerScores);

        int index = playerIndices.get(this.playerCharacter);
        int score = playerScores.get(index);

        for(int i = 0; i < playerScores.size(); i++){
            if(i != index) {
                if(score < playerScores.get(i)){
                    return true;
                }
            }
        }
        return false;
    }

    private void getPlayerScores(Board board, HashMap<Character, Integer> playerIndices, ArrayList<Integer> playerScores){
        for(char squareChar : board.getSquares().values()){
            if(!playerIndices.keySet().contains(squareChar)){
                playerIndices.put(squareChar, playerScores.size());
                playerScores.add(1);
            }
            else{
                int index = playerIndices.get(squareChar);
                playerScores.set(index, playerScores.get(index) + 1);
            }
        }
    }



    public static class HumanPlayer extends Player{


        public HumanPlayer(String name){
            super(name);
        }

        public HumanPlayer(String name, char character){
            super(name, character);
        }

        @Override
        public void play(Board board){
            int x1, x2, y1, y2;
            Scanner scan = new Scanner(System.in);

            while(true){

                System.out.println("Enter first point :");
                System.out.print("x1 : ");
                x1 = scan.nextInt();
                if(x1 > board.getWidth()){
                    System.out.println("Invalid Coordinates... x1 Out of Bounds. ReEnter : ");
                    continue;
                }

                System.out.print("y1 : ");
                y1 = scan.nextInt();
                if(y1 > board.getHeight()){
                    System.out.println("Invalid Coordinates... y1 Out of Bounds. ReEnter :");
                    continue;
                }

                System.out.println("Enter second point :");
                System.out.print("x2 : ");
                x2 = scan.nextInt();
                if(x2 > board.getWidth()){
                    System.out.println("Invalid Coordinates... x2 Out of Bounds. ReEnter :");
                    continue;
                }

                System.out.print("y2 : ");
                y2 = scan.nextInt();
                if(y2 > board.getHeight()){
                    System.out.println("Invalid Coordinates... y2 Out of Bounds. ReEnter :");
                    continue;
                }

                if(x1 == x2 && y1 == y2){
                    System.out.println("Invalid Coordinates... Same Point. ReEnter :");
                    continue;
                }

                if(x1 == x2 || y1 == y2){
                    //System.out.println("Valid Coordinates... Adding Link :");
                    if(board.addLink(x1, y1, x2, y2, this.playerCharacter)){
                        //System.out.println("Successfully Entered Link... Returning.");
                        break;
                    }
                    else{
                        System.out.println("Link Already Exists... ReEnter :");
                    }
                }
                else{
                    System.out.println("Invalid Coordinates... Coordinates not adjacent. ReEnter :");
                }
            }
        }


    }



    public static class ComputerPlayer extends Player{
        public ComputerPlayer(String name){
            super(name);
        }

        public ComputerPlayer(String name, char character){
            super(name, character);
        }

        @Override
        public void play(Board board){
            int bestMoveIndex = 0;
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;

            //bestMoveIndex = maxMove

            board.addLink(board.getPossibleMoves().get(0), playerCharacter);
        }




    }
    

    public static class Pair {
        public int choiceEvaluation, choiceIndex;

        public Pair(int choiceEvaluation, int choiceIndex){
            this.choiceEvaluation = choiceEvaluation;
            this.choiceIndex = choiceIndex;
        }
    }

}
