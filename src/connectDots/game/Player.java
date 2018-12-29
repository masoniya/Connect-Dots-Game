package connectDots.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public abstract class Player {
    public static int iterations = 0;

    public static int totalIterations = 0;

    protected String name;

    protected char playerCharacter;

    protected char otherPlayerCharacter;

    public Player(String name, char otherPlayerCharacter){
        this(name, name.charAt(0), otherPlayerCharacter);
    }

    public Player(String name, char character, char otherPlayerCharacter){
        this.name = name;
        this.playerCharacter = character;
        this.otherPlayerCharacter = otherPlayerCharacter;
    }

    public abstract void play(Board board);

    public boolean isWin(Board board){
        if(!board.isFullBoard())
            return false;

        HashMap<Character, Integer> playerIndices = new HashMap<>();
        ArrayList<Integer> playerScores = new ArrayList<>();

        getPlayerScores(board, playerIndices, playerScores);


        int index, score;
        //player has not scored so he didn't win
        if(!playerIndices.containsKey(this.playerCharacter)){
            return false;
        }

        index = playerIndices.get(this.playerCharacter);
        score = playerScores.get(index);

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
        if(!board.isFullBoard())
            return false;

        HashMap<Character, Integer> playerIndices = new HashMap<>();
        ArrayList<Integer> playerScores = new ArrayList<>();

        getPlayerScores(board, playerIndices, playerScores);


        int index, score;

        //nobody scored
        if(!playerIndices.containsKey(this.playerCharacter)){
            return playerScores.size() == 0;
        }

        index = playerIndices.get(this.playerCharacter);
        score = playerScores.get(index);

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
        if(!board.isFullBoard())
            return false;

        HashMap<Character, Integer> playerIndices = new HashMap<>();
        ArrayList<Integer> playerScores = new ArrayList<>();

        getPlayerScores(board, playerIndices, playerScores);



        int index, score;

        //player didn't score loses if anyone else scored
        if(!playerIndices.containsKey(this.playerCharacter)){
            return playerScores.size() != 0;
        }

        index = playerIndices.get(this.playerCharacter);
        score = playerScores.get(index);

        for(int i = 0; i < playerScores.size(); i++){
            if(i != index) {
                if(score < playerScores.get(i)){
                    return true;
                }
            }
        }
        return false;
    }

    public int getCurrentScore(Board board){
        int score = 0;
        for(char squareChar : board.getSquares().values())
            if(squareChar == this.playerCharacter)
                score++;
        return score;
    }

    public int getOthersCombinedScore(Board board){
        int score = 0;
        for(char squareChar : board.getSquares().values())
            if(squareChar != this.playerCharacter)
                score++;
        return score;
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


        public HumanPlayer(String name, char otherPlayerCharacter){
            super(name, otherPlayerCharacter);
        }

        public HumanPlayer(String name, char character, char otherPlayerCharacter){
            super(name, character, otherPlayerCharacter);
        }

        @Override
        public void play(Board board){
            int x1, x2, y1, y2;
            Scanner scan = new Scanner(System.in);

            while(true){

                System.out.println("Enter first point :");
                System.out.print("x1 : ");
                x1 = scan.nextInt();
                if(x1 > board.getHeight()){
                    System.out.println("Invalid Coordinates... x1 Out of Bounds. ReEnter : ");
                    continue;
                }

                System.out.print("y1 : ");
                y1 = scan.nextInt();
                if(y1 > board.getWidth()){
                    System.out.println("Invalid Coordinates... y1 Out of Bounds. ReEnter :");
                    continue;
                }

                System.out.println("Enter second point :");
                System.out.print("x2 : ");
                x2 = scan.nextInt();
                if(x2 > board.getHeight()){
                    System.out.println("Invalid Coordinates... x2 Out of Bounds. ReEnter :");
                    continue;
                }

                System.out.print("y2 : ");
                y2 = scan.nextInt();
                if(y2 > board.getWidth()){
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

        private DepthGenerator generator;

        public ComputerPlayer(String name, char otherPlayerCharacter){
            super(name, otherPlayerCharacter);
        }

        public ComputerPlayer(String name, char character, char otherPlayerCharacter){
            super(name, character, otherPlayerCharacter);
        }

        @Override
        public void play(Board board){
            int bestMoveIndex = 0;
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            int maxDepth = (int)generator.generateDepth(board.getPossibleMoves().size());

            bestMoveIndex = maxMove(board, 0, alpha, beta, maxDepth).choiceIndex;
            Board.Link bestLink = board.getPossibleMoves().get(bestMoveIndex);
            board.addLink(bestLink, playerCharacter);

            System.out.println("Iterations for this move : " + iterations);
            iterations = 0;
        }

        public void initDepthGenerator(int minPos, int maxDepth, int maxPos, int minDepth){
            generator = new DepthGenerator(minPos, maxDepth, maxPos, minDepth);
        }

        public int evaluate(Board board, int depth){
            int evaluation = 0;
            if(isWin(board)){
                evaluation = Integer.MAX_VALUE - depth;
            }
            else if(isLose(board)){
                evaluation = Integer.MIN_VALUE + depth;
            }
            else if(isDraw(board)){
                evaluation = 0;
            }
            /*else if(getCurrentScore(board) >= (board.getMaxSquares() / 2 + 1)){
                evaluation = Integer.MAX_VALUE - depth;
            }
            else if(getOthersCombinedScore(board) >= (board.getMaxSquares() / 2 + 1)){
                evaluation = Integer.MIN_VALUE - depth;
            }*/
            else{
                int scoreDifference = getCurrentScore(board) - getOthersCombinedScore(board);
                evaluation = scoreDifference * board.getMaxLinks() * 10;
            }
            return evaluation;
        }

        public Pair minMove(Board board, int depth,int alpha, int beta, int maxDepth) {
            iterations++;
            totalIterations++;

            int bestMoveIndex = 0;

            //stop if the game is over
            if(board.isFullBoard()){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }

            /*//stop if the player has more than half
            if(getCurrentScore(board) >= (board.getMaxSquares() / 2 + 1)){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }

            //stop if the other players have more than half (will be changed when more players are introduced)
            if(getOthersCombinedScore(board) >= (board.getMaxSquares() / 2 + 1)){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }*/

            //only search up to a certain point
            if(depth >= maxDepth){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }


            int minEvaluation = Integer.MAX_VALUE;
            List<Board.Link> possibleLinks = board.getPossibleMoves();
            for(int i = 0; i < possibleLinks.size(); i++){
                Board nextBoard = new Board(board);
                int squareNum = nextBoard.getSquares().size();
                nextBoard.addLink(possibleLinks.get(i), this.otherPlayerCharacter);

                int nextBoardEvaluation;
                //don't play again
                if(squareNum == nextBoard.getSquares().size()){
                    nextBoardEvaluation = maxMove(nextBoard, depth + 1, alpha, beta, maxDepth).choiceEvaluation;
                }
                //play again
                else{
                    nextBoardEvaluation = minMove(nextBoard, depth + 1, alpha, beta, maxDepth).choiceEvaluation;
                }

                //nextBoardEvaluation = maxMove(nextBoard, depth + 1, alpha, beta, maxDepth).choiceEvaluation;

                if(nextBoardEvaluation < minEvaluation){
                    minEvaluation = nextBoardEvaluation;
                    bestMoveIndex = i;
                }

                //alpha beta pruning
                beta = Math.min(beta, nextBoardEvaluation);
                if(alpha > beta){
                    return new Pair(minEvaluation, bestMoveIndex);
                }
            }
            return new Pair(minEvaluation, bestMoveIndex);
        }

        public Pair maxMove(Board board, int depth, int alpha, int beta, int maxDepth) {
            iterations++;
            totalIterations++;

            int bestMoveIndex = 0;

            //stop if the game is over
            if(board.isFullBoard()){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }

            /*//stop if the player has more than half
            if(getCurrentScore(board) >= (board.getMaxSquares() / 2 + 1) ){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }

            //stop if the other players have more than half (will be changed when more players are introduced)
            if(getOthersCombinedScore(board) >= (board.getMaxSquares() / 2 + 1)){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }*/

            //only search up to a certain point
            if(depth >= maxDepth){
                return new Pair(evaluate(board, depth), bestMoveIndex);
            }


            int maxEvaluation = Integer.MIN_VALUE;
            List<Board.Link> possibleLinks = board.getPossibleMoves();
            for(int i = 0; i < possibleLinks.size(); i++){
                Board nextBoard = new Board(board);
                int squareNum = nextBoard.getSquares().size();
                nextBoard.addLink(possibleLinks.get(i), this.playerCharacter);

                int nextBoardEvaluation;
                //don't play again
                if(squareNum == nextBoard.getSquares().size()){
                    nextBoardEvaluation = minMove(nextBoard, depth + 1, alpha, beta, maxDepth).choiceEvaluation;
                }
                //play again
                else{
                    nextBoardEvaluation = maxMove(nextBoard, depth + 1, alpha, beta, maxDepth).choiceEvaluation;
                }

                //nextBoardEvaluation = minMove(nextBoard, depth + 1, alpha, beta, maxDepth).choiceEvaluation;

                if(nextBoardEvaluation > maxEvaluation){
                    maxEvaluation = nextBoardEvaluation;
                    bestMoveIndex = i;
                }

                //alpha beta pruning
                alpha = Math.max(alpha, nextBoardEvaluation);
                if(alpha > beta){
                    return new Pair(maxEvaluation, bestMoveIndex);
                }
            }
            return new Pair(maxEvaluation, bestMoveIndex);
        }

        private class DepthGenerator {
            double a;
            double b;
            double minDepth;
            double maxDepth;
            double maxPos;

            public DepthGenerator(int minPos, int maxDepth, int maxPos, int minDepth){
                double x1 = minPos;
                double y1 = maxDepth;
                double x2 = maxPos;
                double y2 = minDepth;

                this.a = (y1 - y2)/Math.log(x1/x2);
                this.b = Math.exp((y2 * Math.log(x1) - y1 * Math.log(x2))/(y1 - y2));
                this.minDepth = minDepth;
                this.maxDepth = maxDepth;
                this.maxPos = maxPos;
            }

            public double generateDepth(int possibleMoves){
                if(possibleMoves > maxPos){
                    return Math.round(minDepth);
                }
                double depth = a * Math.log(b * possibleMoves);
                return Math.min(Math.round(depth), maxDepth);
            }

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
