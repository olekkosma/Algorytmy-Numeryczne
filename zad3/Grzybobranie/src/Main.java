//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static int counter = 1;

    public static int MonteCarloCalculation(int repetition) throws IOException {

        int counter = 0;
        for (int i = 0; i < repetition; i++) {
            Cube cube = new Cube();
            Board board = new Board();
            Player player1 = new Player();
            Player player2 = new Player();
            Loader.readFile(board, cube, player1, player2, "input");
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

    public static void printEquations(ArrayList<State> allStates) {

        for (int i = 0; i < allStates.size(); i++) {
            System.out.println(allStates.get(i).printEquation());
        }
    }

    public static int countStates(ArrayList<State> equations, State state){
        int counter = 0;
        for(State stateTmp : equations){
            if(stateTmp.equals(state)){
                counter++;
            }
        }
        return counter;
    }

    public static double countMatrix(Matrix matrix1, ArrayList<State> allStates, Cube cube) throws IOException {
        int size = allStates.size();
        Matrix vector1 = new Matrix(size, 1);
        matrix1.fillWithZero();
        matrix1.fillDiagonalOne();

        for (State state : allStates) {
            if (state.equation.size() == cube.getValues().size()) {
                for (State stateTmp : state.getEquation()) {
                    matrix1.matrix[state.getIndex()][stateTmp.getIndex()] = countStates(state.getEquation(),stateTmp) / (double) cube.getSumOfProbability();

                }
            }
        }
        for (State state : allStates) {
            if (state.getEquation().size() == 1) {
                vector1.matrix[state.getIndex()][0] = 1.0;
            } else {
                vector1.matrix[state.getIndex()][0] = 0.0;

            }
        }
        if (counter == 1) {
            Generator.writeToFile(vector1, "vector");
            Generator.writeToFile(matrix1, "matrix");

            matrix1.printMatrix();
        }
        counter=2;
        Matrix resultMatrix1 = matrix1.countMatrix(vector1);
        double chanceToWin1 = resultMatrix1.matrix[0][0];
        chanceToWin1 = Math.abs(chanceToWin1);
        return chanceToWin1;

        //chanceToWin1 = chanceToWin1 * 100;
        //String win1 = String.valueOf(chanceToWin1);
        //win1 = win1.substring(0, 4);
    }

    public static void main(String[] args) throws IOException {
        int repetition = 100000;
        Cube cube = new Cube();
        Board board = new Board();
        Player player1 = new Player();
        Player player2 = new Player();
        Loader.readFile(board, cube, player1, player2, "input");
        ArrayList<State> allStates = new ArrayList<>();
        State.size = board.getFields();

        //----------------------generate equations to arrayList-----------------------
        System.out.println("generate equations...");
        EquationsGeneratorIterate eGenerator = new EquationsGeneratorIterate(cube, allStates);
        eGenerator.generateMatrix(player1, player2);
        printEquations(allStates);
        System.out.println("Done");
        //----------------------Counting player one percentage win--------------------

        int size = allStates.size();
        System.out.println("Cointing gauss...");
        double resultGauss = countMatrix(new Gauss(size), allStates, cube);
        System.out.println("Gauss:" + resultGauss);
        System.out.println("Cointing gauss siedl...");

        double resultGaussSiedl = countMatrix(new GaussSeidl(size), allStates, cube);
        System.out.println("Gauss Siedl:" + resultGaussSiedl);
        System.out.println("Cointing jacobie...");

        double resultJacobie = countMatrix(new Jacob(size), allStates, cube);
        System.out.println("Jacobie:" + resultJacobie);

        //---------------------Calculating wins by Monce Carlo method-----------------

        int counter = MonteCarloCalculation(repetition);
        System.out.printf("%2.2f ", (double) counter / repetition);

    }
}
