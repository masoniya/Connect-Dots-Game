package connectDots.game;

public abstract class Game {

    protected Board board;

    public abstract void startGame();



    public static class HumanVsCpuGame extends Game{
        private Player human;
        private Player.ComputerPlayer computer;


        //reduce this to reduce run time
        private final int minDepth = 3;

        //reduce this to reduce run time
        private final int minPossibleMoves = 8;

        //reduce this to reduce run time
        private final int maxDepth = minPossibleMoves;



        public HumanVsCpuGame(int width, int height, char humanChar, char computerChar){
            this(width, height, String.valueOf(humanChar), humanChar, String.valueOf(computerChar), computerChar);
        }

        public HumanVsCpuGame(int width, int height, String humanName, String computerName){
            this(width, height, humanName, humanName.charAt(0), computerName, computerName.charAt(0));
        }

        public HumanVsCpuGame(int width, int height, String humanName, char humanChar, String computerName, char computerChar){
            this.board = new Board(width, height);
            this.human = new Player.HumanPlayer(humanName, humanChar, computerChar);
            this.computer = new Player.ComputerPlayer(computerName, computerChar, humanChar);
            computer.initDepthGenerator(minPossibleMoves, maxDepth, board.getMaxLinks(), minDepth);
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
            //computer.testDepthGenerator();

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
