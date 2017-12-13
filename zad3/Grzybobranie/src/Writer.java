//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//13.12.2017
//Algorytmy Numeryczne
//--------------------

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Writer {
    public static int value = 1;

    public static void writeToFile(Matrix matrix, String suffix) throws IOException {
        FileOutputStream fstream = new FileOutputStream("..\\output\\" + suffix + ".txt");
        //FileOutputStream fstream = new FileOutputStream("..\\..\\output\\" + suffix + ".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        br.write(String.valueOf(matrix.rows));
        br.newLine();
        br.write(String.valueOf(matrix.columns));
        br.newLine();
        int[] colsDens = new int[matrix.columns];
        for (int i = 0; i < matrix.columns; i++) {
            colsDens[i] = 0;
        }
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.columns; j++) {
                if (matrix.matrix[i][j] != 0.0) {
                    colsDens[j]++;
                }
                br.write(String.valueOf(matrix.matrix[i][j]));
                br.newLine();
            }
        }
        br.close();
        fstream.close();
        if (value == 1) {
            FileOutputStream fstream2 = new FileOutputStream("..\\output\\Dense" + suffix + ".txt");
            //FileOutputStream fstream2 = new FileOutputStream("..\\..\\output\\Dense" + suffix + ".txt");
            BufferedWriter br2 = new BufferedWriter(new OutputStreamWriter(fstream2, "utf-8"));
            br2.write(String.valueOf(colsDens.length));
            br2.newLine();
            for (int i = 0; i < matrix.columns; i++) {
                br2.write(String.valueOf(colsDens[i]));
                br2.newLine();

            }
            br2.close();
            fstream.close();
        }
        value = 2;
    }

    public static void writeToFileResults(ArrayList<Double> results, String suffix) throws IOException {
        FileOutputStream fstream = new FileOutputStream("..\\output\\" + suffix + ".txt", true);
        //FileOutputStream fstream = new FileOutputStream("..\\..\\output\\" + suffix + ".txt", true);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        for (Double value : results) {
            br.append(";" + String.valueOf(value).replace(".", ","));
        }
        br.close();
        fstream.close();
    }

}
