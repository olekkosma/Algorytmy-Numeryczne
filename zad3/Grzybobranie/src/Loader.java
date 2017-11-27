import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    public static void readFile(Board board, Cube cube, Player player1, Player player2, String fileName) throws IOException {
        //Scanner scanner = new Scanner(new File("../Files/" + fileName + ".txt"));
        File file = new File("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad3\\Grzybobranie\\Files\\input.txt");
        //File file = new File("..\\Files\\input.txt");
        Scanner scanner = new Scanner(file);

        int boardSize = scanner.nextInt();
        board.setFields((boardSize * 2) + 1);
        int mushroomsSize = scanner.nextInt();
        ArrayList<Integer> mushrooms = new ArrayList<>();
        for (int i = 0; i < mushroomsSize; i++) {
            mushrooms.add(scanner.nextInt());
        }
        board.setMushrooms(mushrooms);
        player1.setField(scanner.nextInt());
        player2.setField(scanner.nextInt());
        player1.setBoard(board);
        player2.setBoard(board);
        player1.setName("Olek");
        player2.setName("Tomek");
        board.setPlayer1(player1);
        board.setPlayer2(player2);
        int valuesSize = scanner.nextInt();
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> probability = new ArrayList<>();
        for (int i = 0; i < valuesSize; i++) {
            values.add(scanner.nextInt());
        }
        for (int i = 0; i < valuesSize; i++) {
            probability.add(scanner.nextInt());
        }
        cube.setValues(values);
        cube.setProbability(probability);
        board.setCube(cube);
        scanner.close();
    }
}
