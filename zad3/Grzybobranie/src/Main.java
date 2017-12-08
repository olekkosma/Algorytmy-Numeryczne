//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws IOException {

        Cube cube2 = null;
        Player player1 = null;
        Player player2 = null;
        Board board = null;
        int counter = 0;
        for (int i = 0; i < 10000; i++) {
            cube2 = new Cube();
            board = new Board();
            player1 = new Player();
            player2 = new Player();
            Loader.readFile(board, cube2, player1, player2, "input");
            int result3 = board.move();
            while (result3 == 0) {
                result3 = board.move();
            }
            if (result3 == 1) {
                counter++;
            }

        }
        cube2 = new Cube();
        board = new Board();
        player1 = new Player();
        player2 = new Player();
        Loader.readFile(board, cube2, player1, player2, "input");
        ArrayList<State> allStates = new ArrayList<>();

        int tour = 1;
        int positionOne = 7;
        int positionTwo = 8;

        State state1 = new State(tour, player1.getField(), player2.getField());
        state1.setIndex(0);
        allStates.add(state1);
        EquationsGenerator generator = new EquationsGenerator(cube2, allStates);
        System.out.println(state1);
        generator.generate(state1);

        for (int i = 0; i < allStates.size(); i++) {
            System.out.println(allStates.get(i).printEquation());
        }

        Gauss matrix1 = new Gauss(allStates.size());
        Jacob matrix2 = new Jacob(allStates.size());
        GaussSeidl matrix3 = new GaussSeidl(allStates.size());
        Matrix vector1 = new Matrix(allStates.size(),1);
        Matrix vector2 = new Matrix(allStates.size(),1);
        Matrix vector3 = new Matrix(allStates.size(),1);

        matrix1.fillWithZero();
        matrix2.fillWithZero();
        matrix3.fillWithZero();
        matrix1.fillDiagonalOne();
        matrix2.fillDiagonalOne();
        matrix3.fillDiagonalOne();
        for(State state : allStates){
            if(state.equation.size()==cube2.getValues().size()) {
                for (State stateTmp : state.getEquation()) {
                    matrix1.matrix[state.getIndex()][stateTmp.getIndex()] = 1 / (double) cube2.getSumOfProbability();
                    matrix2.matrix[state.getIndex()][stateTmp.getIndex()] = 1 / (double) cube2.getSumOfProbability();
                    matrix3.matrix[state.getIndex()][stateTmp.getIndex()] = 1 / (double) cube2.getSumOfProbability();
                }
            }
        }
        for(State state : allStates){
            if(state.getEquation().size()==1) {
                vector1.matrix[state.getIndex()][0] = 1.0;
                vector2.matrix[state.getIndex()][0] = 1.0;
                vector3.matrix[state.getIndex()][0] = 1.0;
            }else{
                vector1.matrix[state.getIndex()][0] = 0.0;
                vector2.matrix[state.getIndex()][0] = 0.0;
                vector3.matrix[state.getIndex()][0] = 0.0;

            }
        }
        matrix1.printMatrix();
        vector1.printMatrix();
        Generator.writeToFile(vector1,"vector");
        Generator.writeToFile(matrix1,"matrix");

        Matrix resultMatrix1 = matrix1.partialChoiseGauss(vector1);
        Matrix resultMatrix2 = matrix2.countJacob(vector2);
        Matrix resultMatrix3 = matrix3.countGaussSiedl(vector3);

        //resultMatrix.printMatrix();

        double chanceToWin1 = resultMatrix1.matrix[state1.getIndex()][0];
        double chanceToWin2 = resultMatrix2.matrix[state1.getIndex()][0];
        double chanceToWin3 = resultMatrix3.matrix[state1.getIndex()][0];

        chanceToWin1 = Math.abs(chanceToWin1);
        chanceToWin2 = Math.abs(chanceToWin2);
        chanceToWin3 = Math.abs(chanceToWin3);
        chanceToWin1= chanceToWin1*100;
        chanceToWin2= chanceToWin2*100;
        chanceToWin3= chanceToWin3*100;
        String win1 = String.valueOf(chanceToWin1);
        String win2 = String.valueOf(chanceToWin2);
        String win3 = String.valueOf(chanceToWin3);
        win1 = win1.substring(0,4);
        win2 = win2.substring(0,4);
        win3 = win3.substring(0,4);
        System.out.println("Partial: "+win1+"%");
        System.out.println("Jacobie: "+win2+"%");
        System.out.println("Gauss Siedl: "+win3+"%");




        System.out.printf("%2.2f ",(double) counter/10000);


    }
}
