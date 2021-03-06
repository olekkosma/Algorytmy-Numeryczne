//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//09.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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

    public MyMatrix(Class<T> classType, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = (T[][]) new Number[rows][columns];
        this.classType = classType;
    }

    //Matrixs have to be the same, return frst matrix
    public MyMatrix<T> add(MyMatrix<T> secondMatrix) {

        MyMatrix<T> sumMatrix = new MyMatrix<T>(classType, this.rows, this.columns);


        if (!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)) {
            return null;
        }

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < columns; j++) {
                if (classType.equals(Float.class)) {

                    Float sum = this.matrix[i][j].floatValue() + secondMatrix.matrix[i][j].floatValue();
                    sumMatrix.matrix[i][j] = (T) sum;

                } else {
                    if (classType.equals(Double.class)) {

                        Double sum = this.matrix[i][j].doubleValue() + secondMatrix.matrix[i][j].doubleValue();
                        sumMatrix.matrix[i][j] = (T) sum;

                    } else {

                        MyOwnPrecision sum = (MyOwnPrecision) matrix[i][j];
                        MyOwnPrecision tmp = MyOwnPrecision.add(sum,(MyOwnPrecision) secondMatrix.matrix[i][j]);
                        sumMatrix.matrix[i][j] = (T) tmp;

                    }
                }
            }
        }
        return sumMatrix;
    }

    //Return new Matrix, rows from first, columns from second matrix
    public MyMatrix<T> multiply(MyMatrix<T> secondMatrix) {
        if (!(this.columns == secondMatrix.rows)) {
            return null;
        }
        MyMatrix<T> sumMatrix = new MyMatrix<T>(classType, this.rows, secondMatrix.columns);
        sumMatrix.fillWithZero();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < secondMatrix.columns; k++) {

                    if (classType.equals(Float.class)) {
                        Float result = sumMatrix.matrix[i][k].floatValue() +
                                this.matrix[i][j].floatValue() * secondMatrix.matrix[j][k].floatValue();
                        sumMatrix.matrix[i][k] = (T) result;
                    } else {
                        if (classType.equals(Double.class)) {
                            Double result = sumMatrix.matrix[i][k].doubleValue() +
                                    this.matrix[i][j].doubleValue() * secondMatrix.matrix[j][k].doubleValue();
                            sumMatrix.matrix[i][k] = (T) result;
                        } else {
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

    public MyMatrix<T> gaussBase(MyMatrix<T> matrix, MyMatrix<T> vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);
        return resultVector;
    }

    //search biggest value in column( search only in sub-matrix of course) column- can be also understood as iteration
    public MyMatrix<T> partialChoiseGauss(MyMatrix<T> matrix, MyMatrix<T> vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {

            int max = p;
            FlipBiggestRow(matrix, vector, n, p, max);
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);
        return resultVector;
    }

    public MyMatrix<T> fullChoiseGauss(MyMatrix<T> matrix, MyMatrix<T> vector, ArrayList<Integer> queue) {

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

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);

        this.sortResultsByQueue(resultVector,queue);
        return resultVector;
    }

    private void FlipBiggestRow(MyMatrix<T> matrix, MyMatrix<T> vector, int n, int p, int max) {
        for (int i = p + 1; i < n; i++) {
            if (classType.equals(Float.class)) {
                if (Math.abs(matrix.matrix[i][p].floatValue()) > Math.abs(matrix.matrix[max][p].floatValue())) {
                    max = i;
                }
            } else {
                if (classType.equals(Double.class)) {
                    if (Math.abs(matrix.matrix[i][p].doubleValue()) > Math.abs(matrix.matrix[max][p].doubleValue())) {
                        max = i;
                    }

                } else {
                    if (classType.equals(MyOwnPrecision.class)) {
                        if (Math.abs(((MyOwnPrecision) matrix.matrix[i][p]).returnDoubleFormat()) > Math.abs(((MyOwnPrecision) matrix.matrix[max][p]).returnDoubleFormat())) {
                            max = i;
                        }

                    }
                }
            }
        }

        flipRows(matrix, p, max);
        flipRows(vector, p, max);
    }

    private void CleanMatrix(MyMatrix<T> matrix, MyMatrix<T> vector, int n, int p, int i) {
        if (classType.equals(Float.class)) {
            float alpha = matrix.matrix[i][p].floatValue() / matrix.matrix[p][p].floatValue();
            vector.matrix[i][0] = (T) (Float) (vector.matrix[i][0].floatValue() - alpha * vector.matrix[p][0].floatValue());
            for (int j = p; j < n; j++) {
                matrix.matrix[i][j] = (T) (Float) (matrix.matrix[i][j].floatValue() - alpha * matrix.matrix[p][j].floatValue());
            }
        } else {
            if (classType.equals(Double.class)) {
                double alpha = matrix.matrix[i][p].doubleValue() / matrix.matrix[p][p].doubleValue();
                vector.matrix[i][0] = (T) (Double) (vector.matrix[i][0].doubleValue() - alpha * vector.matrix[p][0].doubleValue());
                for (int j = p; j < n; j++) {
                    matrix.matrix[i][j] = (T) (Double) (matrix.matrix[i][j].doubleValue() - alpha * matrix.matrix[p][j].doubleValue());
                }
            } else {
                if (classType.equals(MyOwnPrecision.class)) {
                    MyOwnPrecision alpha = MyOwnPrecision.multiply((MyOwnPrecision) matrix.matrix[i][p], MyOwnPrecision.flip((MyOwnPrecision) matrix.matrix[p][p]));
                    MyOwnPrecision tmp1 = MyOwnPrecision.multiply(alpha, (MyOwnPrecision) vector.matrix[p][0]);
                    tmp1 = MyOwnPrecision.negate(tmp1);
                    vector.matrix[i][0] = (T) MyOwnPrecision.add((MyOwnPrecision) vector.matrix[i][0], tmp1);
                    for (int j = p; j < n; j++) {
                        MyOwnPrecision tmp2 = MyOwnPrecision.multiply(alpha, (MyOwnPrecision) matrix.matrix[p][j]);
                        tmp2 = MyOwnPrecision.negate(tmp2);
                        matrix.matrix[i][j] = (T) MyOwnPrecision.add((MyOwnPrecision) matrix.matrix[i][j], tmp2);
                    }
                }
            }
        }
    }

    private void CountBackwardResult(MyMatrix<T> matrix, MyMatrix<T> vector, int n, MyMatrix<T> resultVector) {
        if (classType.equals(Float.class)) {
            for (int i = n - 1; i >= 0; i--) {
                float sum = 0f;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());

            }
        } else {
            if (classType.equals(Double.class)) {
                for (int i = n - 1; i >= 0; i--) {
                    double sum = 0.0;
                    for (int j = i + 1; j < n; j++) {
                        sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                    }
                    resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());

                }
            } else {
                if (classType.equals(MyOwnPrecision.class)) {
                    for (int i = n - 1; i >= 0; i--) {
                        MyOwnPrecision sum = new MyOwnPrecision("0.0");
                        for (int j = i + 1; j < n; j++) {
                            MyOwnPrecision tmp1 = (MyOwnPrecision) matrix.matrix[i][j];
                            MyOwnPrecision tmp2 = (MyOwnPrecision) resultVector.matrix[j][0];
                            tmp1.multiply(tmp2);
                            sum.add(tmp1);
                        }
                        MyOwnPrecision tmp3 = (MyOwnPrecision) vector.matrix[i][0];
                        sum = MyOwnPrecision.negate(sum);
                        tmp3.add(sum);
                        MyOwnPrecision tmp4 = (MyOwnPrecision) matrix.matrix[i][i];
                        tmp4 = MyOwnPrecision.flip(tmp4);
                        tmp3.multiply(tmp4);
                        resultVector.matrix[i][0] = (T) tmp3;
                    }
                }
            }
        }
    }

    public void findAndSetBiggestValueInMatrix(MyMatrix<T> matrix, MyMatrix<T> vector, int p, ArrayList<Integer> queue) {

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

    public MyMatrix<T> sortResultsByQueue(MyMatrix<T> vectorMatrix, ArrayList<Integer> queue) {

        MyMatrix<T> tmp = new MyMatrix(classType, vectorMatrix.rows, 1);
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

    public MyMatrix<T> flipRows(MyMatrix<T> finalMatrix, int row1, int row2) {
        if (row1 == row2) {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) {
            T tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }

    //When columns are flipped, results order does to. Need to save it in queue arrayList
    public MyMatrix<T> flipColumns(MyMatrix<T> finalMatrix, int column1, int column2, ArrayList<Integer> queue) {
        if (column1 == column2) {
            return finalMatrix;
        }
        int tmp = queue.get(column1);
        queue.set(column1, queue.get(column2));
        queue.set(column2, tmp);

        for (int i = 0; i < finalMatrix.rows; i++) {
            T tmp2 = finalMatrix.matrix[i][column1];
            finalMatrix.matrix[i][column1] = finalMatrix.matrix[i][column2];
            finalMatrix.matrix[i][column2] = tmp2;
        }
        return finalMatrix;
    }

    public void printMatrix() {
        System.out.println("\n" + classType.getName() + " matrix values:");
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
                if (classType.equals(Float.class)) {
                    Float zero = 0f;
                    matrix[i][j] = (T) zero;
                } else {
                    if (classType.equals(Double.class)) {
                        Double zero = new Double(0);
                        matrix[i][j] = (T) zero;
                    } else {
                        matrix[i][j] = (T) new MyOwnPrecision("0.0");
                    }
                }
            }
        }
    }

    public MyOwnPrecision countAverageValue() {
        MyOwnPrecision sum = new MyOwnPrecision("0.0");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if(classType.equals(Float.class)){
                    MyOwnPrecision value = new MyOwnPrecision(String.valueOf(this.matrix[i][j].floatValue()));
                    sum.add(value);
                }else{
                    if(classType.equals(Double.class)){
                        MyOwnPrecision value = new MyOwnPrecision(String.valueOf(this.matrix[i][j].doubleValue()));
                        sum.add(value);
                    }else{
                        MyOwnPrecision value = (MyOwnPrecision) this.matrix[i][j];
                        sum.add(value);
                    }
                }


            }
        }
        int divider = rows*columns;
        MyOwnPrecision divide = new MyOwnPrecision(String.valueOf(divider)+".0");
        divide = MyOwnPrecision.flip(divide);
         sum.multiply(divide);
        return sum;
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
                if (classType.equals(Float.class)) {
                    matrix[i][j] = (T) Float.valueOf(strLine);
                } else {
                    if (classType.equals(Double.class)) {
                        matrix[i][j] = (T) Double.valueOf(strLine);
                    } else {
                        matrix[i][j] = (T) new MyOwnPrecision(strLine);
                    }
                }
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

    public MyOwnPrecision countAverageDiff(MyMatrix<T> vector, MyMatrix<T> result){
        MyOwnPrecision sum = new MyOwnPrecision("0.0");
        if (classType.equals(Float.class)) {
            Float resultFloat = 0.0f;

            for (int j = 0; j < this.rows; j++) {
                resultFloat = 0.0f;
                for (int i = 0; i < this.columns; i++) {
                    resultFloat += this.matrix[j][i].floatValue() * result.matrix[i][0].floatValue();
                }
                sum.add( new MyOwnPrecision(String.valueOf(Math.abs(resultFloat - vector.matrix[j][0].floatValue()))));
            }
        } else {
            if (classType.equals(Double.class)) {
                Double resultFloat = 0.0;
                for (int j = 0; j < this.rows; j++) {
                    resultFloat = 0.0;
                    for (int i = 0; i < this.columns; i++) {
                        resultFloat += this.matrix[j][i].doubleValue() * result.matrix[i][0].doubleValue();
                    }
                    sum.add( new MyOwnPrecision(String.valueOf(Math.abs(resultFloat - vector.matrix[j][0].doubleValue()))));

                }
            } else {

                MyOwnPrecision resultMyOwn = new MyOwnPrecision("0.0");
                for (int j = 0; j < this.rows; j++) {
                    resultMyOwn = new MyOwnPrecision("0.0");
                    for (int i = 0; i < this.columns; i++) {
                        MyOwnPrecision tmp1 = ((MyOwnPrecision) this.matrix[j][i]).newInstance();
                        tmp1 = MyOwnPrecision.multiply(tmp1,(MyOwnPrecision) result.matrix[i][0]);
                        resultMyOwn.add(tmp1);

                    }
                    MyOwnPrecision tmp2 = resultMyOwn.newInstance();
                    MyOwnPrecision negateValue = ((MyOwnPrecision) vector.matrix[j][0]).newInstance();
                    negateValue = MyOwnPrecision.negate(negateValue);
                    tmp2.add(negateValue);
                    MyOwnPrecision diff = tmp2;
                    diff.absConvert();
                    sum.add(diff);
                }
            }
        }
        int divider = rows*vector.columns;
        MyOwnPrecision divide = new MyOwnPrecision(String.valueOf(divider)+".0");
        divide = MyOwnPrecision.flip(divide);
        sum.multiply(divide);
        return sum;


    }
}