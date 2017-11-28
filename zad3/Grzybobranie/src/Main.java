//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        Jacob matrixA = new Jacob(2);
        Matrix vectorB = new Matrix(2, 1);
        matrixA.loadValues("1");
        vectorB.loadValues("2");
        Matrix result2 = matrixA.countJacob(vectorB);
        result2.printMatrix();

        GaussSeidl matrixAAA = new GaussSeidl(2);
        Matrix vectorBBB = new Matrix(2, 1);
        matrixAAA.loadValues("1");
        vectorBBB.loadValues("2");
        Matrix result3 = matrixAAA.countGaussSiedl(vectorBBB);
        result3.printMatrix();


        Gauss matrixAA = new Gauss(2);
        Matrix vectorBB = new Matrix(2, 1);
        matrixAA.loadValues("1");
        vectorBB.loadValues("2");
        Matrix result = matrixAA.partialChoiseGauss(vectorBB);
        result.printMatrix();


/*
        int counter = 0;
        int counter2 = 0;
        for (int i = 0; i < 1; i++) {
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
        System.out.printf("%2.2f ",(double) counter/1);
*/
    }
}
