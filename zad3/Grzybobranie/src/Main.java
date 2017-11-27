import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {



        int counter = 0;
        int counter2 = 0;
        for (int i = 0; i < 1; i++) {
            Cube cube = new Cube();
            Board board = new Board();
            Player player1 = new Player();
            Player player2 = new Player();
            Loader.readFile(board, cube, player1, player2, "input");

            int result = board.move();
            while (result == 0) {
                result = board.move();
            }
            if (result == 1) {
                counter++;
            }

        }
        System.out.printf("%2.2f ",(double) counter/1);

    }
}
