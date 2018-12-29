package connectDots.game;

public abstract class Game {

    protected Board board;

    public abstract void startGame();



    public static class HumanVsCpuGame extends Game{
        private Player human;

        private Player.ComputerPlayer computer;

        //max difficulty is standard AI
        //each level down is 10% less maxDepth
        private int difficulty;


        //reduce this to reduce run time. this specifies the min depth to look for on any size of board. this helps make the early moves faster
        private final int minDepth = 2;

        //reduce this to reduce run time. this specifies the min possible moves to use the generator. anything below will use max depth
        private final int minPossibleMoves = 10;

        //this specifies the max depth to traverse and it should equal the min possible moves as it doesn't make sense to traverse more
        //this can be reduced to reduce AI power
        private final int maxDepth = minPossibleMoves;



        public HumanVsCpuGame(int width, int height, char humanChar, char computerChar, int difficulty){
            this(width, height, String.valueOf(humanChar), humanChar, String.valueOf(computerChar), computerChar, difficulty);
        }

        public HumanVsCpuGame(int width, int height, String humanName, String computerName, int difficulty){
            this(width, height, humanName, humanName.charAt(0), computerName, computerName.charAt(0), difficulty);
        }

        public HumanVsCpuGame(int width, int height, String humanName, char humanChar, String computerName, char computerChar, int difficulty){
            this.board = new Board(width, height);
            this.human = new Player.HumanPlayer(humanName, humanChar, computerChar);
            this.computer = new Player.ComputerPlayer(computerName, computerChar, humanChar);
            if(difficulty > 10 || difficulty < 1){
                System.out.println("Invalid Difficulty value. Exiting.");
            }
            else
                this.difficulty = difficulty;
        }

        private void checkScores(){
            int humanScore = 0, computerScore = 0;
            for(char squareChar : board.getSquares().values()){
                if(squareChar == human.playerCharacter){
                    humanScore++;
                }
                else if(squareChar == computer.playerCharacter){
                    computerScore++;
                }
            }
            if(humanScore > computerScore){
                System.out.println("Human is the winner with " + humanScore + " squares.");
                System.out.println("Computer only has " + computerScore + " squares.");
            }
            else if(computerScore > humanScore){
                System.out.println("Computer is the winner with " + computerScore + " squares.");
                System.out.println("Human only has " + humanScore + " squares.");
            }
            else{
                System.out.println("The Game is a draw. both players got " + humanScore + " squares.");
            }

        }


        @Override
        public void startGame(){
            int difficultyPenalty = maxDepth - ( maxDepth * (10 - difficulty) / 10 );

            computer.initDepthGenerator(minPossibleMoves, difficultyPenalty, board.getMaxLinks(), minDepth);

            board.printBoard();

            while(true){

                while(true){
                    int squareNum = board.getSquares().size();
                    System.out.println("Human's turn");
                    human.play(board);
                    board.printBoard();
                    if(board.isFullBoard()){
                        System.out.println("Game over");
                        break;
                    }
                    if(board.getSquares().size() == squareNum){
                        break;
                    }
                    System.out.println("Human got a square. Play again.");
                }
                if(board.isFullBoard()){
                    break;
                }


                while(true){
                    int squareNum = board.getSquares().size();
                    System.out.println("Computer's turn");
                    computer.play(board);
                    board.printBoard();
                    if(board.isFullBoard()){
                        System.out.println("Game over");
                        break;
                    }
                    if(board.getSquares().size() == squareNum){
                        break;
                    }
                    System.out.println("Computer got a square. Play again.");
                }
                if(board.isFullBoard()){
                    break;
                }

                /*System.out.println("Human's turn");
                human.play(board);
                board.printBoard();
                if(board.isFullBoard()){
                    System.out.println("Game over");
                    break;
                }

                System.out.println("Computer's turn");
                computer.play(board);
                board.printBoard();
                if(board.isFullBoard()){
                    System.out.println("Game over");
                    break;
                }*/
            }

            checkScores();

        }


    }


}
