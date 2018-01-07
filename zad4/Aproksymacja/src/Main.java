//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//07.01.2018
//Algorytmy Numeryczne
//--------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;



public class Main {
    public static double epsylon = 0.000000001;

    public static double[] loadData(String fileName,int size) throws FileNotFoundException {
        File file = new File("..\\" + fileName);
        Scanner scanner = new Scanner(file);
        double[] table = new double[size];
        for(int i=0;i<size;i++){
            table[i] =Double.valueOf(scanner.nextLine());
        }
        scanner.close();
        return table;

    }

    public static void main(String[] args) throws FileNotFoundException {

        int dataSize = 24;
        double[] values = loadData("values.txt",dataSize);
        double[] keys = loadData("keys.txt",dataSize);

        int function = 3;
        int unknowns = function + 1;
        int sUnknowns = function * 2 + 1;
        int tUnknowns = function + 1;

        double[][] tableS = new double[dataSize][sUnknowns];
        double[] resultS = new double[sUnknowns];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < sUnknowns; j++) {
                tableS[i][j] = Math.pow(keys[i], j);
            }
        }
        double[][] tableT = new double[dataSize][tUnknowns];
        double[] resultT = new double[tUnknowns];

        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < tUnknowns; j++) {
                tableT[i][j] = values[i] * tableS[i][j];
            }
        }
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < sUnknowns; j++) {
                resultS[j] += tableS[i][j];
            }
        }
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < tUnknowns; j++) {
                resultT[j] += tableT[i][j];
            }
        }

        GaussSeidlNew matrix = new GaussSeidlNew(unknowns);
        int counter = 0;
        for(int i=0;i<unknowns;i++){
            for(int j=0;j<unknowns;j++){
                matrix.matrix[i][j]=resultS[j+counter];

            }
            counter++;
        }
        Matrix vector = new Matrix(unknowns,1);
        for(int i=0;i<unknowns;i++) {
            vector.matrix[i][0]=resultT[i];
        }
        Matrix result = matrix.countMatrix(vector);
        result.printMatrix();

        double[] results = new double[unknowns];
        for(int i=0;i<unknowns;i++){
            results[i] = result.matrix[i][0];
        }
        Function functionFinal = new Function(results);
        System.out.println(functionFinal.count(84));

    }
}
