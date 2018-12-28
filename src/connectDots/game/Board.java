package connectDots.game;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Board {
    private int width;
    private int height;



    private HashSet<Link> links;
    private HashMap<Integer, String> squares;

    public Board(int width, int height){
        this.width = width;
        this.height = height;

        links = new HashSet<>();
        squares = new HashMap<>();

    }

    public boolean addLink(int x1, int y1, int x2, int y2){
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

        return true;
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

    public HashSet<Link> getLinks() {
        return links;
    }


    public class Link {
        int x1, y1, x2, y2;

        Link(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
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
