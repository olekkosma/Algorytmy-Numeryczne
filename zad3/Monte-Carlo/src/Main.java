//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//13.12.2017
//Algorytmy Numeryczne
//--------------------

import java.io.IOException;

public class Main {

    public static int MonteCarloCalculation(int repetition) throws IOException {

        int counter = 0;
        for (int i = 0; i < repetition; i++) {
            Cube cube = new Cube();
            Board board = new Board();
            Player player1 = new Player();
            Player player2 = new Player();
            LoaderWriter.readFile(board, cube, player1, player2, "input");
            int result3 = board.move();
            while (result3 == 0) {
                result3 = board.move();
            }
            if (result3 == 1) {
                counter++;
            }

        }
        return counter;
    }

    public static String changePointToComma(double value){
        String line = String.valueOf(value);
       line =  line.replace(".",",");
        return line;
    }

    public static void main(String[] args) throws IOException {

        int repetition = 10000000;
        int counter = MonteCarloCalculation(repetition);
        double value = ((double) counter) / ((double) repetition);
        String result = changePointToComma(value);
        System.out.println(result);
        LoaderWriter.writeToFile(changePointToComma(value),"Result");

    }

}
