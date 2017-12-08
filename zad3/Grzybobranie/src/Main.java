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


        Cube cube = new Cube();
        ArrayList<Integer> values = new ArrayList<>();
        values.add(-5);
        values.add(-4);
        values.add(-3);
        values.add(-2);
        values.add(-1);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);
        ArrayList<Integer> probability = new ArrayList<>();
        probability.add(1);
        probability.add(1);
        probability.add(1);
        probability.add(1);
        probability.add(1);
        cube.setValues(values);
        cube.setProbability(probability);


        ArrayList<State> allStates = new ArrayList<>();

        int tour = 1;
        int positionOne = 1;
        int positionTwo = 4;

        State state1 = new State(tour, positionOne, positionTwo);
        state1.setIndex(0);
        allStates.add(state1);
        EquationsGenerator generator = new EquationsGenerator(cube, allStates);
        System.out.println(state1);
        generator.generate(state1);

        for (int i = 0; i < allStates.size(); i++) {
            System.out.println(allStates.get(i).printEquation());
        }

        Gauss matrix = new Gauss(allStates.size());
        Matrix vector = new Matrix(allStates.size(),1);

        matrix.fillWithZero();
        matrix.fillDiagonalOne();
        for(State state : allStates){
            if(state.equation.size()==cube.getValues().size()) {
                for (State stateTmp : state.getEquation()) {
                    matrix.matrix[state.getIndex()][stateTmp.getIndex()] = 1 / (double) cube.getSumOfProbability();
                }
            }
        }
        for(State state : allStates){
            if(state.getEquation().size()==1) {
                vector.matrix[state.getIndex()][0] = 1.0;
            }else{
                vector.matrix[state.getIndex()][0] = 0.0;

            }
        }
        matrix.printMatrix();
        vector.printMatrix();

        Matrix resultMatrix = matrix.partialChoiseGauss(vector);
        resultMatrix.printMatrix();
        double chanceToWin = resultMatrix.matrix[state1.getIndex()][0];
        chanceToWin = Math.abs(chanceToWin);
        chanceToWin= chanceToWin*100;
        String win = String.valueOf(chanceToWin);
        win = win.substring(0,4);
        System.out.println("Szanse na wygraną pierwszego gracza to: "+win+"%");

        /*
        int size = 12;

        Jacob matrixA = new Jacob(size);
        Matrix vectorB = new Matrix(size, 1);
        matrixA.loadValues("1");
        vectorB.loadValues("2");
        Matrix result2 = matrixA.countJacob(vectorB);
        result2.printMatrix();

        GaussSeidl matrixAAA = new GaussSeidl(size);
        Matrix vectorBBB = new Matrix(size, 1);
        matrixAAA.loadValues("1");
        vectorBBB.loadValues("2");
        Matrix result3 = matrixAAA.countGaussSiedl(vectorBBB);
        result3.printMatrix();


        Gauss matrixAA = new Gauss(size);
        Matrix vectorBB = new Matrix(size, 1);
        matrixAA.loadValues("1");
        vectorBB.loadValues("2");
        Matrix result = matrixAA.partialChoiseGauss(vectorBB);
        result.printMatrix();
        */



        int counter = 0;
        int counter2 = 0;
        for (int i = 0; i < 10000; i++) {
            Cube cube2 = new Cube();
            Board board = new Board();
            Player player1 = new Player();
            Player player2 = new Player();
            Loader.readFile(board, cube2, player1, player2, "input");

            int result3 = board.move();
            while (result3 == 0) {
                result3 = board.move();
            }
            if (result3 == 1) {
                counter++;
            }

        }
        System.out.printf("%2.2f ",(double) counter/10000);


    }
}
