

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
                    if (classType.equals(Double.class)) {
                        Double sum = this.matrix[i][j].doubleValue() + secondMatrix.matrix[i][j].doubleValue();
                        this.matrix[i][j] = (T) sum;
                    } else {
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

    private void CountValueForPlace(MyMatrix<T> finalMatrix, MyMatrix<T> tmpMatrix, int i, int j, int k) {
        if (classType.equals(Float.class)) {
            Float tmp1 = finalMatrix.matrix[j][i].floatValue() / finalMatrix.matrix[i][i].floatValue();
            Float tmp2 = finalMatrix.matrix[i][k].floatValue() * tmp1;
            Float tmp3 = finalMatrix.matrix[j][k].floatValue() - tmp2;
            tmpMatrix.matrix[j][k] = (T) (Float) (finalMatrix.matrix[j][k].floatValue() - tmp2);

        } else {
            if (classType.equals(Double.class)) {
                Double tmp1 = finalMatrix.matrix[j][i].doubleValue() / finalMatrix.matrix[i][i].doubleValue();
                Double tmp2 = finalMatrix.matrix[i][k].doubleValue() * tmp1;
                Double tmp3 = finalMatrix.matrix[j][k].doubleValue() - tmp2;
                tmpMatrix.matrix[j][k] = (T) tmp3;
            } else {

                if (classType.equals(MyOwnPrecision.class)) {
                    MyOwnPrecision tmp1 = (MyOwnPrecision) finalMatrix.matrix[i][i];
                    MyOwnPrecision tmp2 = (MyOwnPrecision) finalMatrix.matrix[j][i];
                    MyOwnPrecision tmp3 = (MyOwnPrecision) finalMatrix.matrix[i][k];
                    MyOwnPrecision tmp4 = (MyOwnPrecision) finalMatrix.matrix[j][k];
                    MyOwnPrecision tmp = MyOwnPrecision.flip(tmp1);
                    tmp = MyOwnPrecision.multiply(tmp2, tmp);
                    tmp = MyOwnPrecision.multiply(tmp3, tmp);
                    tmp = MyOwnPrecision.negate(tmp);
                    tmpMatrix.matrix[j][k] = (T) MyOwnPrecision.add(tmp4, tmp);
                }
            }
        }
    }

    public MyMatrix<T> gaussBase(MyMatrix<T> matrix, MyMatrix<T> vector) {

        int n = vector.rows;

        for (int p = 0; p < n; p++) {

            for (int i = p + 1; i < n; i++) {
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
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);

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
        return resultVector;
    }

    //search biggest value in column( search only in sub-matrix of course) column- can be also understood as iteration
    public MyMatrix<T> partialChoiseGauss(MyMatrix<T> matrix, MyMatrix<T> vector) {


        int n = vector.rows;

        for (int p = 0; p < n; p++) {

            int max = p;

            for (int i = p + 1; i < n; i++) {
                if (classType.equals(Float.class)) {
                    if (Math.abs(matrix.matrix[i][p].floatValue()) > Math.abs(matrix.matrix[max][p].floatValue())) {
                        max = i;
                    }
                }else{
                    if(classType.equals(Double.class)){
                        if (Math.abs(matrix.matrix[i][p].doubleValue()) > Math.abs(matrix.matrix[max][p].doubleValue())) {
                            max = i;
                        }

                    }else {
                        if (classType.equals(MyOwnPrecision.class)) {
                            if (Math.abs(((MyOwnPrecision) matrix.matrix[i][p]).returnDoubleFormat()) > Math.abs(((MyOwnPrecision) matrix.matrix[max][p]).returnDoubleFormat())) {
                                max = i;
                            }

                        }
                    }
                }
            }
            matrix.printMatrix();
            flipRows(matrix, p, max);
            flipRows(vector, p, max);
            matrix.printMatrix();


            for (int i = p + 1; i < n; i++) {
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
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);

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
        return resultVector;
    }


    public MyMatrix<T> fullChoiseGauss(MyMatrix<T> matrix, MyMatrix<T> vector, ArrayList<Integer> queue) {


        int n = vector.rows;

        for (int p = 0; p < n; p++) {

            findAndSetBiggestValueInMatrix(matrix, vector, p, queue);

            for (int i = p + 1; i < n; i++) {
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
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);

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
        return resultVector;
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

    public int absValueInColumn(MyMatrix<T> finalMatrix, int column) {
        int index = column;
        T max = finalMatrix.matrix[column][column];
        for (int i = column + 1; i < finalMatrix.rows; i++) {
            if (classType.equals(Float.class)) {
                Float tmp1 = (Float) finalMatrix.matrix[i][column];
                Float tmp2 = (Float) max;
                if (Math.abs(tmp1) > tmp2) {
                    tmp1 = Math.abs((Float) finalMatrix.matrix[i][column]);
                    index = i;
                    max = (T) tmp1;
                }
            } else {
                if (classType.equals(Double.class)) {
                    Double tmp1 = (Double) finalMatrix.matrix[i][column];
                    Double tmp2 = (Double) max;
                    if (Math.abs(tmp1) > tmp2) {
                        tmp1 = Math.abs((Double) finalMatrix.matrix[i][column]);
                        index = i;
                        max = (T) tmp1;
                    }
                } else {
                    if (classType.equals(MyOwnPrecision.class)) {
                        MyOwnPrecision tmp1 = (MyOwnPrecision) finalMatrix.matrix[i][column];
                        MyOwnPrecision tmp2 = (MyOwnPrecision) max;
                        if (Math.abs(tmp1.returnDoubleFormat()) > tmp2.returnDoubleFormat()) {
                            MyOwnPrecision tmp3 = (MyOwnPrecision) finalMatrix.matrix[i][column];
                            MyOwnPrecision tmp = tmp3.newInstance();
                            tmp.absConvert();
                            max = (T) tmp;
                            index = i;
                        }
                    }
                }
            }

        }
        return index;
    }

    public MyMatrix<T> countResultsFromGauss(ArrayList<Integer> queue) {

        MyMatrix<T> vectorMatrix = new MyMatrix(classType, this.rows, 1);

        for (int i = 0; i < this.rows; i++) {
            vectorMatrix.matrix[i][0] = this.matrix[i][this.columns - 1];
        }
        T s;
        int n = this.rows;
        for (int i = n - 1; i >= 0; i--) {
            s = this.matrix[i][n];

            for (int j = n - 1; j >= i + 1; j--) {
                if (classType.equals(Float.class)) {
                    Float tmp1 = this.matrix[i][j].floatValue() * vectorMatrix.matrix[j][0].floatValue();
                    s = (T) ((Float) (s.floatValue() - tmp1));
                    Float end = s.floatValue() / this.matrix[i][i].floatValue();
                    vectorMatrix.matrix[i][0] = (T) end;
                } else {
                    if (classType.equals(Double.class)) {
                        Double tmp1 = this.matrix[i][j].doubleValue() * vectorMatrix.matrix[j][0].doubleValue();
                        s = (T) ((Double) (s.doubleValue() - tmp1));
                        Double end = s.doubleValue() / this.matrix[i][i].doubleValue();
                        vectorMatrix.matrix[i][0] = (T) end;
                    } else {
                        if (classType.equals(MyOwnPrecision.class)) {
                            MyOwnPrecision tmp = MyOwnPrecision.multiply((MyOwnPrecision) this.matrix[i][j], (MyOwnPrecision) vectorMatrix.matrix[j][0]);
                            tmp = MyOwnPrecision.negate(tmp);
                            s = (T) MyOwnPrecision.add((MyOwnPrecision) s, tmp);
                            MyOwnPrecision divider = MyOwnPrecision.flip((MyOwnPrecision) this.matrix[i][i]);
                            vectorMatrix.matrix[i][0] = (T) MyOwnPrecision.multiply((MyOwnPrecision) s, divider);
                        }
                    }
                }
            }
        }
        if (queue.size() > 0) vectorMatrix = sortResultsByQueue(vectorMatrix, queue);
        return vectorMatrix;
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
                        if (classType.equals(MyOwnPrecision.class)) {
                            matrix[i][j] = (T) new MyOwnPrecision("0.0");
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
                    if (classType.equals(Double.class)) {
                        matrix[i][j] = (T) Double.valueOf(strLine);
                    } else {
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

    public void calculateResult(MyMatrix<T> vector, int choise) throws IOException {

/*
        switch (choise) {
            case 1:       //base gauss
                ArrayList<Integer> queue = new ArrayList<>();
                MyMatrix<T> cleanedExtendedMatrix1 = this.gaussBase(vector, queue);
                System.out.println("Result after gauss");
                cleanedExtendedMatrix1.printMatrix();
                MyMatrix<T> resultVector1 = cleanedExtendedMatrix1.countResultsFromGauss(queue);
                return resultVector1;
            case 2:
                ArrayList<Integer> queue2 = new ArrayList<>();
                MyMatrix<T> cleanedExtendedMatrix2 = this.partialChoiseGauss(vector, queue2);
                cleanedExtendedMatrix2.printMatrix();
                MyMatrix<T> resultVector2 = cleanedExtendedMatrix2.countResultsFromGauss(queue2);
                return resultVector2;
            case 3:
                ArrayList<Integer> queue3 = new ArrayList<>();
                MyMatrix<T> cleanedExtendedMatrix3 = this.fullChoiseGauss(vector, queue3);
                cleanedExtendedMatrix3.printMatrix();

                MyMatrix<T> resultVector3 = cleanedExtendedMatrix3.countResultsFromGauss(queue3);
                return resultVector3;
        }
        return null;
        */
    }

    public void countAbsOfResult(MyMatrix<T> vector, MyMatrix<T> result) {
        if (classType.equals(Float.class)) {
            Float resultFloat = 0.0f;

            for (int j = 0; j < this.rows; j++) {
                resultFloat = 0.0f;
                for (int i = 0; i < this.columns; i++) {
                    resultFloat += this.matrix[j][i].floatValue() * result.matrix[i][0].floatValue();
                }
                Float diff = Math.abs(resultFloat - vector.matrix[j][0].floatValue());
                System.out.printf("%.8f\n", diff);
            }
        } else {
            Double resultFloat = 0.0;
            for (int j = 0; j < this.rows; j++) {
                resultFloat = 0.0;
                for (int i = 0; i < this.columns; i++) {
                    resultFloat += this.matrix[j][i].doubleValue() * result.matrix[i][0].doubleValue();
                }
                Double diff = Math.abs(resultFloat - vector.matrix[j][0].doubleValue());
                System.out.printf("%.8f\n", diff);
            }
        }
    }
}
