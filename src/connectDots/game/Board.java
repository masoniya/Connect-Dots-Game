package connectDots.game;


import java.util.*;

public class Board{
    private int width, height;

    private HashSet<Link> links;

    private HashMap<Integer, Character> squares;



    public Board(int width, int height){
        this.width = width;
        this.height = height;

        links = new HashSet<>();
        squares = new HashMap<>();

    }

    //copy consructor
    public Board(Board board)
    {
        this.width = board.width;
        this.height = board.height;
        this.links = new HashSet<>();
        for(Link i : this.links)
        {
            int x1 = i.x1; int x2 = i.x2; int y1 = i.y1; int y2 = i.y2;
            this.links.add(new Link(x1,y1,x2,y2));
        }
    }

    public boolean addLink(int x1, int y1, int x2, int y2, char playerCharacter){
        Link link;

        //horizontal
        if(x1 == x2){
            //first point left
            if(y1 < y2){
                link = new Link(x1, y1, x2, y2);
            }
            //second point left
            else{
                link = new Link(x2, y2, x1, y1);
            }
        }

        //vertical
        else{
            //first point up
            if(x1 < x2){
                link = new Link(x1, y1, x2, y2);
            }
            //second point up
            else{
                link = new Link(x2, y2, x1, y1);
            }
        }

        if(links.contains(link)){
            return false;
        }

        links.add(link);
        checkSquares(link, playerCharacter);

        return true;
    }

    public List<Board> getPossibleMoves() throws CloneNotSupportedException {
        List <Board> boards = new ArrayList<>();
        for(int i=1; i<= height; i++)
            for (int j = 1; j <= width; j++) {
                //corner dot
                if (i == height && j == width)
                    break;

                //check if the current dot is at the final line
                if (i == height && j != width) {
                    if (!links.contains(new Link(i, j, i + 1, j))) {
                        Board board = new Board(this);
                        board.addLink(i, j, i + 1, j, '0');
                        boards.add(board);
                    }
                    continue;
                }
                if( i!= height && j == width)
                {

                    if (!links.contains(new Link(i, j, i, j + 1))) {
                        Board board = new Board(this);
                        System.out.println(this.links);
                        System.out.println(board.links);
                        board.addLink(i, j, i, j + 1, '0');
                        boards.add(board);
                    }
                    continue;
                }
                if (!links.contains(new Link(i, j, i + 1, j))) {
                    Board board = new Board(this);
                    System.out.println(this.links);
                    board.addLink(i, j, i + 1, j, '0');
                    boards.add(board);
                }
                if (!links.contains(new Link(i, j, i, j + 1))) {
                    Board board = new Board(this);
                    System.out.println(this.links);
                    System.out.println(board.links);
                    board.addLink(i, j, i, j + 1, '0');
                    boards.add(board);
                }
            }

        return boards;
        }

    private void checkSquares(Link link, char playerCharacter){
        int x, y, num;

        //horizontal link
        if(link.x1 == link.x2){
            //up
            if(link.x1 != 1){
                if(links.contains(link.topLeft()) && links.contains(link.topRight()) && links.contains(link.topLeft().topRight())){
                    x = link.x1 - 1;
                    y = link.y1;
                    num = getSquareNumber(x, y);
                    squares.put(num, playerCharacter);
                    System.out.println("Adding top square");
                }
            }

            //down
            if(link.x1 != height){
                if(links.contains(link.bottomLeft()) && links.contains(link.bottormRight()) && links.contains(link.bottomLeft().bottormRight())){
                    x = link.x1;
                    y = link.y1;
                    num = getSquareNumber(x, y);
                    squares.put(num, playerCharacter);
                    System.out.println("Adding bottom square");
                }
            }
        }

        //vertical link
        else{
            //left
            if(link.y1 != 1){
                if(links.contains(link.topLeft()) && links.contains(link.bottomLeft()) && links.contains(link.topLeft().bottomLeft())){
                    x = link.x1;
                    y = link.y1 - 1;
                    num = getSquareNumber(x, y);
                    squares.put(num, playerCharacter);
                    System.out.println("Adding left square");
                }
            }

            //right
            if(link.y1 != width){
                if(links.contains(link.topRight()) && links.contains(link.bottormRight()) && links.contains(link.topRight().bottormRight())){
                    x = link.x1;
                    y = link.y1;
                    num = getSquareNumber(x, y);
                    squares.put(num, playerCharacter);
                    System.out.println("Adding right square");
                }
            }
        }

    }

    private int getSquareNumber(int x, int y){
        return (x - 1) * (width - 1) + y;
    }




    public void printBoard(){
        for(int i = 1; i <= height; i++){
            //print the o's line
            for(int j = 1; j <= width; j++){
                //last o
                if(j == width){
                    System.out.println("o");
                }
                else{
                    if(links.contains(new Link(i, j, i, j + 1))){
                        System.out.print("o --- ");
                    }
                    else{
                        System.out.print("o     ");
                    }
                }
            }

            //last row
            if(i == height){
                return;
            }

            //print the |'s line
            for(int j = 1; j <= width; j++){
                // last |
                if(j == width){
                    if(links.contains(new Link(i, j, i + 1, j))){
                        System.out.println("|");
                    }
                    else{
                        System.out.println(" ");
                    }
                }
                else{
                    char playerChar = ' ';
                    int squareNumber;
                    if(squares.containsKey(squareNumber = getSquareNumber(i, j))){
                        playerChar = squares.get(squareNumber);
                    }
                    if(links.contains(new Link(i, j, i + 1, j))){
                        System.out.print("|  " + playerChar + "   ");
                    }
                    else{
                        System.out.print("   " + playerChar + "  ");
                    }

                }
            }
        }

    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }




    public class Link {
        int x1, y1, x2, y2;

        Link(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        Link topRight(){
            if(x1 == x2){
                 return new Link(x2 - 1, y2, x2, y2);
            }
            else{
                return new Link(x1, y1, x1, y1 + 1);
            }
        }

        Link topLeft(){
            if(x1 == x2){
                return new Link(x1 - 1, y1, x1, y1);
            }
            else{
                return new Link(x1, y1 - 1, x1, y1);
            }
        }

        Link bottormRight(){
            if(x1 == x2){
                return new Link(x2, y2, x2 + 1, y2);
            }
            else{
                return new Link(x2, y2, x2, y2 + 1);
            }
        }

        Link bottomLeft(){
            if(x1 == x2){
                return new Link(x1, y1, x1 + 1, y1);
            }
            else{
                return new Link(x2, y2 - 1, x2, y2);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link link = (Link) o;
            return x1 == link.x1 &&
                    y1 == link.y1 &&
                    x2 == link.x2 &&
                    y2 == link.y2;
        }

        @Override
        public int hashCode() {

            return Objects.hash(x1, y1, x2, y2);
        }

    }


}
