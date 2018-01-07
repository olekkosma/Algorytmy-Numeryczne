//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//07.01.2018
//Algorytmy Numeryczne
//--------------------

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Writer {
    public static void writeToFileResults(ArrayList<Double> results, String suffix) throws IOException {
        FileOutputStream fstream = new FileOutputStream("..\\output\\" + suffix + ".txt", true);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        br.newLine();
        for (Double value : results) {
            br.append(";   " + String.valueOf(value).replace(".", ","));
        }
        br.close();
        fstream.close();
    }
}
