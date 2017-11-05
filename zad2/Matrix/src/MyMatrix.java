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

    public MyMatrix(Class<T> classType, int length) {
        this.rows = length;
        this.columns = length;
        this.matrix = (T[][]) new Number[rows][columns];
        this.classType = classType;
    }

    public MyMatrix(Class<T> classType,int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = (T[][]) new Number[rows][columns];
        this.classType = classType;
    }

    public void add(MyMatrix<T> secondMatrix) {

        if (!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)) {
            return;
        }

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < columns; j++) {

                if (classType.equals(Float.class)) {
                    Float sum = this.matrix[i][j].floatValue() + secondMatrix.matrix[i][j].floatValue();
                    this.matrix[i][j] = (T) sum;
                } else {
                    if(classType.equals(Double.class)){
                        Double sum = this.matrix[i][j].doubleValue() + secondMatrix.matrix[i][j].doubleValue();
                        this.matrix[i][j] = (T) sum;
                    }else {
                        if (classType.equals(MyOwnPrecision.class)) {
                            MyOwnPrecision first = (MyOwnPrecision) matrix[i][j];
                            first.add((MyOwnPrecision) secondMatrix.matrix[i][j]);
                            matrix[i][j] = (T) first;
                        }
                    }
                }
            }
        }
    }

    public MyMatrix<T> multiply(MyMatrix<T> secondMatrix) {
        if (!(this.columns == secondMatrix.rows)) {
            return null;
        }
        MyMatrix<T> sumMatrix = new MyMatrix<T>(classType,this.rows, secondMatrix.columns);
        sumMatrix.fillWithZero();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < secondMatrix.columns; k++) {

                    if (classType.equals(Float.class)) {
                        Float result = sumMatrix.matrix[i][k].floatValue() +
                                this.matrix[i][j].floatValue() * secondMatrix.matrix[j][k].floatValue();
                        sumMatrix.matrix[i][k] = (T) result;
                    } else {
                        if(classType.equals(Double.class)){
                            Double result = sumMatrix.matrix[i][k].doubleValue() +
                                    this.matrix[i][j].doubleValue() * secondMatrix.matrix[j][k].doubleValue();
                            sumMatrix.matrix[i][k] = (T) result;
                        }else {
                            if (classType.equals(MyOwnPrecision.class)) {
                                MyOwnPrecision tmp = (MyOwnPrecision) this.matrix[i][j];
                                MyOwnPrecision tmp2 = (MyOwnPrecision) secondMatrix.matrix[j][k];

                                MyOwnPrecision tmpMultiply = tmp.newInstance();
                                tmpMultiply.multiply(tmp2);
                                MyOwnPrecision tmp3 = (MyOwnPrecision) sumMatrix.matrix[i][k];
                                tmp3.add(tmpMultiply);
                                sumMatrix.matrix[i][k] = (T) tmp3;
                            }
                        }
                    }
                }
            }
        }
        return sumMatrix;
    }


    public void printMatrix() {
        System.out.println("\n"+classType.getName()+ " matrix values:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                    System.out.printf("%26.26s  ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public void printClass() {
        System.out.println(classType);
    }

    public void fillWithZero(){
        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                if (classType.equals(Float.class)) {
                    Float zero = 0f;
                    matrix[i][j] = (T) zero;
                } else {
                    if(classType.equals(Double.class)){
                        Double zero = new Double(0);
                        matrix[i][j] = (T) zero;
                    }else {
                        if (classType.equals(MyOwnPrecision.class)) {
                            matrix[i][j]= (T) new MyOwnPrecision("0.0");
                        }
                    }
                }
            }
        }
    }
    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        rows = Integer.valueOf(br.readLine());
        columns = Integer.parseInt(br.readLine());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                strLine = br.readLine();

                if (classType.equals(Float.class)) {
                    matrix[i][j] = (T) Float.valueOf(strLine);
                } else {
                    if(classType.equals(Double.class)){
                        matrix[i][j] = (T) Double.valueOf(strLine);
                    }else {
                        if (classType.equals(MyOwnPrecision.class)) {
                            matrix[i][j] = (T) new MyOwnPrecision(strLine);
                        }
                    }
                }
            }
        }
        br.close();
        fstream.close();
    }
}
