//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Random;

public class Gauss {
    Double[][] matrix;
    int rows;
    int columns;


    public Gauss(int length) {
        this.rows = length;
        this.columns = length;
        this.matrix = new Double[rows][columns];
    }

    public Gauss(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new Double[rows][columns];
    }

    public Gauss add(Gauss secondMatrix) {

        Gauss sumMatrix = new Gauss(this.rows, this.columns);


        if (!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)) {
            return null;
        }

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < columns; j++) {
                Double sum = this.matrix[i][j] + secondMatrix.matrix[i][j];
                sumMatrix.matrix[i][j] = sum;
            }
        }
        return sumMatrix;
    }


    public Gauss multiply(Gauss secondMatrix) {
        if (!(this.columns == secondMatrix.rows)) {
            return null;
        }
        Gauss sumMatrix = new Gauss(this.rows, secondMatrix.columns);
        sumMatrix.fillWithZero();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < secondMatrix.columns; k++) {

                    Double result = sumMatrix.matrix[i][k] + this.matrix[i][j] * secondMatrix.matrix[j][k];
                    sumMatrix.matrix[i][k] = result;
                }
            }
        }
        return sumMatrix;
    }

    public Gauss gaussBase(Gauss matrix, Gauss vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        Gauss resultVector = new Gauss(vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);
        return resultVector;
    }

    public Gauss partialChoiseGauss(Gauss matrix, Gauss vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {

            int max = p;
            FlipBiggestRow(matrix, vector, n, p, max);
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        Gauss resultVector = new Gauss(vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);
        return resultVector;
    }

    public Gauss fullChoiseGauss(Gauss matrix, Gauss vector, ArrayList<Integer> queue) {

        for (int i = 0; i < matrix.rows; i++) {
            queue.add(i);
        }
        int n = vector.rows;
        for (int p = 0; p < n; p++) {

            findAndSetBiggestValueInMatrix(matrix, vector, p, queue);
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        Gauss resultVector = new Gauss(vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);

        this.sortResultsByQueue(resultVector, queue);
        return resultVector;
    }

    private void FlipBiggestRow(Gauss matrix, Gauss vector, int n, int p, int max) {
        for (int i = p + 1; i < n; i++) {

            if (Math.abs(matrix.matrix[i][p]) > Math.abs(matrix.matrix[max][p])) {
                max = i;
            }
        }

        flipRows(matrix, p, max);
        flipRows(vector, p, max);
    }

    private void CleanMatrix(Gauss matrix, Gauss vector, int n, int p, int i) {

        double alpha = matrix.matrix[i][p] / matrix.matrix[p][p];
        vector.matrix[i][0] = vector.matrix[i][0] - alpha * vector.matrix[p][0];
        for (int j = p; j < n; j++) {
            matrix.matrix[i][j] = matrix.matrix[i][j] - alpha * matrix.matrix[p][j];
        }
    }

    private void CountBackwardResult(Gauss matrix, Gauss vector, int n, Gauss resultVector) {

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix.matrix[i][j] * resultVector.matrix[j][0];
            }
            resultVector.matrix[i][0] = (vector.matrix[i][0] - sum) / matrix.matrix[i][i];

        }
    }

    public void findAndSetBiggestValueInMatrix(Gauss matrix, Gauss vector, int p, ArrayList<Integer> queue) {

        Float maxValue = matrix.matrix[p][p].floatValue();
        int rowIndex = p;
        int columnIndex = p;
        for (int ii = p; ii < matrix.rows; ii++) {
            for (int jj = p; jj < matrix.columns; jj++) {

                if (Math.abs(matrix.matrix[ii][jj].floatValue()) > maxValue) {
                    maxValue = Math.abs(matrix.matrix[ii][jj].floatValue());
                    rowIndex = ii;
                    columnIndex = jj;
                }
            }
        }

        flipRows(matrix, p, rowIndex);
        flipRows(vector, p, rowIndex);
        flipColumns(matrix, p, columnIndex, queue);
    }

    public Gauss sortResultsByQueue(Gauss vectorMatrix, ArrayList<Integer> queue) {

        Gauss tmp = new Gauss(vectorMatrix.rows, 1);
        for (int ii = 0; ii < vectorMatrix.rows; ii++) {
            for (int jj = 0; jj < vectorMatrix.columns; jj++) {
                tmp.matrix[ii][jj] = vectorMatrix.matrix[ii][jj];
            }
        }
        for (int i = 0; i < vectorMatrix.rows; i++) {
            vectorMatrix.matrix[queue.get(i)][0] = tmp.matrix[i][0];
        }
        return vectorMatrix;
    }

    public Gauss flipRows(Gauss finalMatrix, int row1, int row2) {
        if (row1 == row2) {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) {
            Double tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }

    public Gauss flipColumns(Gauss finalMatrix, int column1, int column2, ArrayList<Integer> queue) {
        if (column1 == column2) {
            return finalMatrix;
        }
        int tmp = queue.get(column1);
        queue.set(column1, queue.get(column2));
        queue.set(column2, tmp);

        for (int i = 0; i < finalMatrix.rows; i++) {
            Double tmp2 = finalMatrix.matrix[i][column1];
            finalMatrix.matrix[i][column1] = finalMatrix.matrix[i][column2];
            finalMatrix.matrix[i][column2] = tmp2;
        }
        return finalMatrix;
    }

    public void printMatrix() {
        System.out.println("\n" + " matrix values:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%26.26s  ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public void fillWithZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Double zero = new Double(0);
                matrix[i][j] =  zero;
            }
        }
    }

    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("..\\..\\zad2\\Values\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        loadData(br);
        br.close();
        fstream.close();
    }

    public Double loadValuesWithTime(String suffix, Double time) throws IOException {
        FileInputStream fstream = new FileInputStream("..\\..\\zad2\\Values\\AddMultiplyFiles\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        time = Double.valueOf(br.readLine());
        loadData(br);
        br.close();
        fstream.close();
        return time;
    }

    private void loadData(BufferedReader br) throws IOException {
        String strLine;
        rows = Integer.valueOf(br.readLine());
        columns = Integer.parseInt(br.readLine());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                strLine = br.readLine();
                matrix[i][j] = Double.valueOf(strLine);

            }
        }
    }

    public static int loadSize(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("..\\..\\zad2\\Values\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        int size = Integer.valueOf(br.readLine());
        br.close();
        fstream.close();
        return size;
    }
}