import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Cube cube= new Cube();
        Board board= new Board();
        Player player1= new Player();
        Player player2= new Player();

        Loader.readFile(board,cube,player1,player2,"input");

        while(board.move()){

        }
    }
}
