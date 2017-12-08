//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//09.11.2017
//Algorytmy Numeryczne
//--------------------
import java.io.*;
import java.util.Random;

public class Generator {

  public static void writeToFile(Matrix matrix, String suffix) throws IOException {
    FileOutputStream fstream = new FileOutputStream("..\\..\\zad3\\Grzybobranie\\files\\" + suffix + ".txt");
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
}
