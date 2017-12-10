//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//09.11.2017
//Algorytmy Numeryczne
//--------------------
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Writer {

  public static void writeToFile(Matrix matrix, String suffix) throws IOException {
    FileOutputStream fstream = new FileOutputStream("..\\output\\" + suffix + ".txt");
    BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
    br.write(String.valueOf(matrix.rows));
    br.newLine();
    br.write(String.valueOf(matrix.columns));
    br.newLine();
    for (int i = 0; i < matrix.rows; i++) {
      for (int j = 0; j < matrix.columns; j++) {
        br.write(String.valueOf(matrix.matrix[i][j]));
        br.newLine();
      }
    }
    br.close();
    fstream.close();
  }

  public static void writeToFileResults(ArrayList<Double> results, String suffix) throws IOException {
    FileOutputStream fstream = new FileOutputStream("..\\output\\" + suffix + ".txt",true);
    BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
    for(Double value : results){
        br.append(";"+String.valueOf(value).replace(".",","));
    }
    br.close();
    fstream.close();
  }

}
