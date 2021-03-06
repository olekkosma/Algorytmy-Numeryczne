//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//13.12.2017
//Algorytmy Numeryczne
//--------------------

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LoaderWriter {

    public static void readFile(Board board, Cube cube, Player player1, Player player2, String fileName) throws IOException {
        File file = new File("..\\" + fileName + ".txt");
        //File file = new File("..\\..\\"+fileName + ".txt");
        Scanner scanner = new Scanner(file);

        int boardSize = scanner.nextInt();
        board.setFields((boardSize * 2) + 1);
        board.setPlayer1Turn(true);
        int mushroomsSize = scanner.nextInt();
        ArrayList<Integer> mushrooms = new ArrayList<>();
        for (int i = 0; i < mushroomsSize; i++) {
            int mush = scanner.nextInt();
            mush = Math.floorMod(mush, board.getFields());
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
//rozmiar;ilosc grzybow;grzyby;pole gracz 1; pole gracz 2; kostka; wartosci kostki; prawdop. kostki;MonteCarlo;Gauss;Gauss Siedl; Jacobie;
// Gauss Time;Gauss Parse Time;Gauss Siedl Time;Jacobie Time;Eigen;Eigen Time;Eigen Parse Time;
    public static void writeToFile(String value, String suffix) throws IOException {
        FileOutputStream fstream = new FileOutputStream("..\\output\\" + suffix + ".txt",true);
        //FileOutputStream fstream = new FileOutputStream("..\\..\\output\\" + suffix + ".txt",true);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        Data data = new Data("..\\input");
        br.newLine();
        br.append(String.valueOf(data.size));
        br.append(String.valueOf(";"+data.mushroomsSize));
        br.append(String.valueOf(";"+ data.mushroomsList));
        br.append(String.valueOf(";"+data.playerOneField));
        br.append(String.valueOf(";"+data.playerTwoField));
        br.append(String.valueOf(";"+data.cubeSize));
        br.append(String.valueOf(";"+Arrays.toString(data.cubeValues)));
        br.append(String.valueOf(";"+Arrays.toString(data.cubeProbability)));
        br.write(String.valueOf(";"+value));

        br.close();
        fstream.close();
    }
}
