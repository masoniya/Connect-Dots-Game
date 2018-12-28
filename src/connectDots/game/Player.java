package connectDots.game;

import java.util.Scanner;

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



    public static class HumanPlayer extends Player{
        public HumanPlayer(String name){
            super(name);
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
                    System.out.println("Valid Coordinates... Adding Link :");
                    if(board.addLink(x1, y1, x2, y2, this.playerCharacter)){
                        System.out.println("Successfully Entered Link... Returning.");
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

        @Override
        public void play(Board board){

        }

    }

}
