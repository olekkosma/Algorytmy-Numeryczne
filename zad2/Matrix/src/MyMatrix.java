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
            tmpMatrix.matrix[j][k] = (T) tmp3;

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

    public MyMatrix<T> gaussBase(MyMatrix<T> vector, ArrayList<Integer> queue) {

        MyMatrix<T> finalMatrix = new MyMatrix<T>(classType, this.rows, this.columns + 1);
        MyMatrix<T> tmpMatrix = new MyMatrix<T>(classType, this.rows, this.columns + 1);

        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);


        for (int i = 0; i < this.columns - 1; i++) {
            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {
                    CountValueForPlace(finalMatrix, tmpMatrix, i, j, k);
                }
            }
            for (int ii = 0; ii < this.rows; ii++) {
                for (int jj = 0; jj < this.columns + 1; jj++) {
                    finalMatrix.matrix[ii][jj] = tmpMatrix.matrix[ii][jj];
                }
            }
        }
        return tmpMatrix;
    }

    //search biggest value in column( search only in sub-matrix of course) column- can be also understood as iteration
    public MyMatrix<T> partialChoiseGauss(MyMatrix<T> vector, ArrayList<Integer> queue) {

        MyMatrix<T> finalMatrix = new MyMatrix<T>(classType, this.rows, this.columns + 1);
        MyMatrix<T> tmpMatrix = new MyMatrix<T>(classType, this.rows, this.columns + 1);

        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);

        for (int i = 0; i < this.columns - 1; i++) {

            int index = absValueInColumn(finalMatrix, i);
            finalMatrix = flipRows(finalMatrix, i, index);

            for (int ii = 0; ii < this.columns; ii++) {
                for (int jj = 0; jj < this.columns + 1; jj++) {
                    tmpMatrix.matrix[ii][jj] = finalMatrix.matrix[ii][jj];
                }
            }
            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {
                    CountValueForPlace(finalMatrix, tmpMatrix, i, j, k);
                }
            }
            for (int ii = 0; ii < this.columns; ii++) {
                for (int jj = 0; jj < this.columns + 1; jj++) {
                    finalMatrix.matrix[ii][jj] = tmpMatrix.matrix[ii][jj];
                }
            }
        }
        return tmpMatrix;
    }


    public MyMatrix<T> fullChoiseGauss(MyMatrix<T> vector, ArrayList<Integer> queue) {

        MyMatrix<T> finalMatrix = new MyMatrix(classType, this.rows, this.columns + 1);
        MyMatrix<T> tmpMatrix = new MyMatrix(classType, this.rows, this.columns + 1);
        for (int i = 0; i < finalMatrix.rows; i++) {
            queue.add(i);
        }

        //create extended matrix with one more column.
        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);

        for (int i = 0; i < this.columns - 1; i++) {
            //Everytime sub-matrix shrincks, final matrix is changed to find biggest value in it
            finalMatrix = findAndSetBiggestValueInMatrix(finalMatrix, i, queue);

            for (int ii = 0; ii < this.rows; ii++) {
                for (int jj = 0; jj < this.columns + 1; jj++) {
                    tmpMatrix.matrix[ii][jj] = finalMatrix.matrix[ii][jj];
                }
            }

            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {

                    CountValueForPlace(finalMatrix, tmpMatrix, i, j, k);
                }
            }

            for (int ii = 0; ii < this.rows; ii++) {
                for (int jj = 0; jj < this.columns + 1; jj++) {
                    finalMatrix.matrix[ii][jj] = tmpMatrix.matrix[ii][jj];
                }
            }
        }
        return tmpMatrix;
    }

    public MyMatrix<T> findAndSetBiggestValueInMatrix(MyMatrix<T> finalMatrix, int iteration, ArrayList<Integer> queue) {

        T maxValue = finalMatrix.matrix[iteration][iteration];
        int rowIndex = iteration;
        int columnIndex = iteration;
        for (int ii = iteration; ii < finalMatrix.rows; ii++) {
            for (int jj = iteration; jj < finalMatrix.columns - 1; jj++) {

                if (classType.equals(Float.class)) {
                    Float tmp1 = (Float) finalMatrix.matrix[ii][jj];
                    Float tmp2 = (Float) maxValue;
                    if (Math.abs(tmp1) > tmp2) {
                        tmp2 = Math.abs((Float) finalMatrix.matrix[ii][jj]);
                        rowIndex = ii;
                        columnIndex = jj;
                        maxValue = (T) tmp2;
                    }
                } else {
                    if (classType.equals(Double.class)) {
                        Double tmp1 = (Double) finalMatrix.matrix[ii][jj];
                        Double tmp2 = (Double) maxValue;
                        if (Math.abs(tmp1) > tmp2) {
                            tmp2 = Math.abs((Double) finalMatrix.matrix[ii][jj]);
                            rowIndex = ii;
                            columnIndex = jj;
                            maxValue = (T) tmp2;
                        }
                    } else {
                        if (classType.equals(MyOwnPrecision.class)) {
                            MyOwnPrecision tmp1 = (MyOwnPrecision) finalMatrix.matrix[ii][jj];
                            MyOwnPrecision tmp2 = (MyOwnPrecision) maxValue;
                            if (Math.abs(tmp1.returnDoubleFormat()) > tmp2.returnDoubleFormat()) {
                                MyOwnPrecision tmp3 = (MyOwnPrecision) finalMatrix.matrix[ii][jj];
                                MyOwnPrecision tmp = tmp3.newInstance();
                                tmp.absConvert();
                                maxValue = (T) tmp;
                                rowIndex = ii;
                                columnIndex = jj;
                            }
                        }
                    }
                }
            }
        }
        finalMatrix = flipRows(finalMatrix, iteration, rowIndex);
        finalMatrix = flipColumns(finalMatrix, iteration, columnIndex, queue);

        return finalMatrix;
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
        //queue is used only in fullChoiseGauss so it is good to check there is any queue
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

    private void joinMatrixAndVector(MyMatrix<T> vector, MyMatrix<T> matrixToJoin) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns + 1; j++) {

                if (j == this.columns) {
                    matrixToJoin.matrix[i][j] = vector.matrix[i][0];
                } else {
                    matrixToJoin.matrix[i][j] = this.matrix[i][j];
                }
            }
        }
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

    public void printClass() {
        System.out.println(classType);
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

    public MyMatrix<T> calculateResult(MyMatrix<T> vector, int choise) throws IOException {


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
    }

    public void countAbsOfResult(MyMatrix<T> vector, MyMatrix<T> result){
        Float resultFloat = 0.0f ;

        for(int j = 0 ; j<this.rows;j++) {
            resultFloat = 0.0f ;
            for (int i = 0; i < this.columns; i++) {
                resultFloat += this.matrix[j][i].floatValue() * result.matrix[i][0].floatValue();
            }
            //System.out.println(resultFloat + " - " + vector.matrix[j][0].floatValue());
            Float diff = Math.abs(resultFloat - vector.matrix[j][0].floatValue());
            System.out.printf("%.8f\n",diff);
        }
    }
}
