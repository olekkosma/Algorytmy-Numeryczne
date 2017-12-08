//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    public static void readFile(Board board, Cube cube, Player player1, Player player2, String fileName) throws IOException {
        //Scanner scanner = new Scanner(new File("../Files/" + fileName + ".txt"));
        File file = new File("..\\..\\zad3\\Grzybobranie\\files\\"+fileName+".txt");
        //File file = new File("..\\Files\\input.txt");
        Scanner scanner = new Scanner(file);

        int boardSize = scanner.nextInt();
        board.setFields((boardSize * 2) + 1);
        board.setPlayer1Turn(true);
        int mushroomsSize = scanner.nextInt();
        ArrayList<Integer> mushrooms = new ArrayList<>();
        for (int i = 0; i < mushroomsSize; i++) {
            int mush = scanner.nextInt();
            while (mush < 0) {
                mush += board.getFields();
            }
            mushrooms.add(mush);
        }
        board.setMushrooms(mushrooms);
        player1.setBoard(board);
        player2.setBoard(board);
        player1.setField(scanner.nextInt());
        player2.setField(scanner.nextInt());
        player1.setName("Olek");
        player2.setName("Tomek");
        player1.setMushrooms(0);
        player2.setMushrooms(0);
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
