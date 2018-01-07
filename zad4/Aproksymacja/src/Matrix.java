//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//07.01.2018
//Algorytmy Numeryczne
//--------------------

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Matrix {
    double[][] matrix;
    public int rows;
    public int columns;

    public Matrix(int length) {
        this.rows = length;
        this.columns = length;
        this.matrix = new double[rows][columns];
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

    public void printMatrix() {
        System.out.println("\n" + " matrix values:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%10.16f  ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public double countNorm() {
        double normValue = 0.0;
        for (int i = 0; i < this.rows; i++) {
    normValue += this.matrix[i][0]* this.matrix[i][0];
        }
        normValue = Math.sqrt(normValue);
        return  normValue;
    }
    public static Matrix substract(Matrix firstMatrix, Matrix secondMatrix) {

        Matrix resultMatrix = new Matrix(firstMatrix.rows, secondMatrix.columns);


        for (int i = 0; i < firstMatrix.rows; i++) {
            for (int j = 0; j < firstMatrix.columns; j++) {

                resultMatrix.matrix[i][j] = firstMatrix.matrix[i][j] - secondMatrix.matrix[i][j];

            }
        }
        return resultMatrix;
    }

    public static Matrix multiply(Matrix firstMatrix, Matrix secondMatrix) {

        Matrix resultMatrix = new Matrix(firstMatrix.rows, firstMatrix.columns);

        for (int i = 0; i < firstMatrix.rows; i++) {
            for (int j = 0; j < secondMatrix.columns; j++) {
                resultMatrix.matrix[i][j]=0.0;
                for (int k = 0; k < secondMatrix.rows; k++) {

                    resultMatrix.matrix[i][j]+=firstMatrix.matrix[i][k] * secondMatrix.matrix[k][j];
                }
            }
        }
        return resultMatrix;
    }

    public static void swapRows(Matrix matrix, int row1, int row2) {
        for (int i = 0; i < matrix.columns; i++) {
            double tmp = matrix.matrix[row1][i];
            matrix.matrix[row1][i] = matrix.matrix[row2][i];
            matrix.matrix[row2][i] = tmp;
        }
    }

    public static int findBiggestRowInColumn(Matrix A, int column) {
        double max = 0.0;
        int row = 0;

        for (int i = 0; i < A.rows; i++) {
            if (max < A.matrix[i][column]) {
                max = A.matrix[i][column];
                row = i;
            }

        }
        return row;
    }

    public Matrix countMatrix(Matrix b) {
        return null;
    }
}
