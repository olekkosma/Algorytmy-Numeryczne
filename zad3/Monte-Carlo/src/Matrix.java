//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Matrix {
    Double[][] matrix;
    public int rows;
    public int columns;

    public Matrix(int length) {
        this.rows = length;
        this.columns = length;
        this.matrix = new Double[rows][columns];
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new Double[rows][columns];
    }

    public void printMatrix() {
        System.out.println("\n" + " matrix values:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%10.10s  ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("..\\..\\zad3\\Grzybobranie\\Files\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        loadData(br);
        br.close();
        fstream.close();
    }

    public void fillWithZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = 0.0;
            }

        }
    }

    public void fillDiagonalOne() {
        for (int i = 0; i < rows; i++) {
            matrix[i][i] = 1.0;
        }
    }
    public double findFirstNegative(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(matrix[i][j] <0.0){
                    return  matrix[i][j];
                }
            }

        }
        return 0.0;
    }

    public double countSum(){
        double sum = -2.0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                    sum+=  matrix[i][j];

            }

        }
        return sum;
    }

    public Matrix countMatrix(Matrix b) {return null;};
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
}
