import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.Random;

public class MyMatrix<T extends Number> {
    T[][] matrix;
    int rows;
    int columns;
    private Class<T> classType;
    public MyMatrix(Class<T> classType, int length){
        this.rows = length;
        this.columns = length;
        this.matrix = (T[][]) new Number[rows][columns];
        this.classType = classType;
    }
    public MyMatrix(int rows,int columns){
        this.rows = rows;
        this.columns = columns;
        this.matrix = (T[][]) new Number[rows][columns];
    }

    public void printMatrix(){
        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public void printClass(){
        System.out.println(classType);
    }

    /*
    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values"+suffix+".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        int lineNumber = 0;
        rows =  Integer.parseInt(br.readLine());
        columns =  Integer.parseInt(br.readLine());

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                strLine = br.readLine();

                matrix[i][j] =  Float.parseFloat(strLine);
            }

        }
        br.close();
        fstream.close();
    }*/
}
