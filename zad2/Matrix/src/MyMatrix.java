import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MyMatrix<T extends Number> {
    T[][] matrix;
    int length;
    final Class<T> classType;

    public MyMatrix(Class<T> classType, int length){
        this.length = length;
        this.classType = classType;
        @SuppressWarnings("unchecked")
        T[][] tmp = (T[][]) new Number[length][length];
        this.matrix = tmp;

    }

    public void printMatrix(){
        for(int i= 0 ; i <length;i++){
            for(int j= 0 ; j <length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }

    }

    public void add(T[][] secondMatrix){

        double[][] result = new double[length][length];

        for(int i= 0 ; i <length;i++){
            for(int j= 0 ; j <length;j++) {

                // 3 opcje dla 3 klass

                result[i][j] = this.matrix[i][j].doubleValue() + secondMatrix[i][j].doubleValue();
            }
        }
    }

    public void readFile(String suffix) throws IOException {

        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values"+suffix+".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        int lineNumber = 0;
        int i=0,j=0;
        length =  Integer.parseInt(br.readLine());
        while((strLine = br.readLine()) != null) {

                    matrix[i][j] = (T) (Number) Float.parseFloat(strLine);
            System.out.println(" "+i+" "+j);
           j++;

           if(j%length == 0){
               i++;
               j=j-length;
           }

        }

        br.close();
        fstream.close();
    }
}
