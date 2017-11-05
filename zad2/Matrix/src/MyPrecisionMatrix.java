import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyPrecisionMatrix {
    private MyOwnPrecision[][] matrix;
    private int rows;
    private int columns;

    public MyPrecisionMatrix(int length) {
        this.rows = length;
        this.columns = length;
        matrix = new MyOwnPrecision[rows][columns];
    }

    public MyPrecisionMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new MyOwnPrecision[rows][columns];
    }

    public void add(MyPrecisionMatrix secondMatrix) {

        if (!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)) {
            return;
        }

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < columns; j++) {

                this.matrix[i][j].add(secondMatrix.matrix[i][j]);
            }

        }
    }

    public MyPrecisionMatrix multiply(MyPrecisionMatrix secondMatrix) {
        if (!(this.columns == secondMatrix.rows)) {
            return null;
        }
        MyPrecisionMatrix tmp = new MyPrecisionMatrix(this.rows, secondMatrix.columns);
        tmp.fillWithZero();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                for (int k = 0; k < secondMatrix.columns; k++) {

                    MyOwnPrecision tmpMultiply = this.matrix[i][j].newInstance();
                    tmpMultiply.multiply(secondMatrix.matrix[j][k]);
                    tmp.matrix[i][k].add(tmpMultiply);

                }
            }
        }
        return tmp;
    }

    public void fillWithZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new MyOwnPrecision("0.0");
            }

        }
    }

    public MyPrecisionMatrix gaussBase(MyPrecisionMatrix vector) {

        MyPrecisionMatrix finalMatrix = new MyPrecisionMatrix(this.rows, this.columns + 1);
        MyPrecisionMatrix tmpMatrix = new MyPrecisionMatrix(this.rows, this.columns + 1);

        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);


        for (int i = 0; i < this.columns - 1; i++) {
            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {

                    MyOwnPrecision tmp = MyOwnPrecision.flip(finalMatrix.matrix[i][i]);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[j][i], tmp);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[i][k], tmp);
                    tmp = MyOwnPrecision.negate(tmp);
                    tmpMatrix.matrix[j][k] = MyOwnPrecision.add(finalMatrix.matrix[j][k], tmp);

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
    public MyPrecisionMatrix partialChoiseGauss(MyPrecisionMatrix vector) {

        MyPrecisionMatrix finalMatrix = new MyPrecisionMatrix(this.rows, this.columns + 1);
        MyPrecisionMatrix tmpMatrix = new MyPrecisionMatrix(this.rows, this.columns + 1);

        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);

        for (int i = 0; i < this.columns - 1; i++) {

            int index = absValueInColumn(finalMatrix, i);
            finalMatrix = flipRows(finalMatrix, i, index);

            for (int ii = 0; ii < this.rows; ii++) {
                for (int jj = 0; jj < this.columns + 1; jj++) {
                    finalMatrix.matrix[ii][jj] = finalMatrix.matrix[ii][jj];
                }
            }
            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {
                    MyOwnPrecision tmp = MyOwnPrecision.flip(finalMatrix.matrix[i][i]);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[j][i], tmp);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[i][k], tmp);
                    tmp = MyOwnPrecision.negate(tmp);
                    tmpMatrix.matrix[j][k] = MyOwnPrecision.add(finalMatrix.matrix[j][k], tmp);
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

    public MyPrecisionMatrix fullChoiseGauss(MyPrecisionMatrix vector,ArrayList<Integer> queue) {

        MyPrecisionMatrix finalMatrix = new MyPrecisionMatrix(this.rows, this.columns + 1);
        MyPrecisionMatrix tmpMatrix = new MyPrecisionMatrix(this.rows, this.columns + 1);
        for(int i = 0;i<finalMatrix.rows;i++){
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
                    MyOwnPrecision tmp = MyOwnPrecision.flip(finalMatrix.matrix[i][i]);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[j][i], tmp);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[i][k], tmp);
                    tmp = MyOwnPrecision.negate(tmp);
                    tmpMatrix.matrix[j][k] = MyOwnPrecision.add(finalMatrix.matrix[j][k], tmp);
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

    public MyPrecisionMatrix findAndSetBiggestValueInMatrix(MyPrecisionMatrix finalMatrix, int iteration, ArrayList<Integer> queue) {

        MyOwnPrecision maxValue = finalMatrix.matrix[iteration][iteration];
        int rowIndex = iteration;
        int columnIndex = iteration;
        for (int ii = iteration; ii < finalMatrix.rows; ii++) {
            for (int jj = iteration; jj < finalMatrix.columns - 1; jj++) {

                if (Math.abs(finalMatrix.matrix[ii][jj].returnDoubleFormat()) > maxValue.returnDoubleFormat()) {
                    MyOwnPrecision tmp = finalMatrix.matrix[ii][jj].newInstance();
                    tmp.absConvert();
                    maxValue = tmp;
                    rowIndex = ii;
                    columnIndex = jj;
                }
            }
        }
        finalMatrix = flipRows(finalMatrix, iteration, rowIndex);
        finalMatrix = flipColumns(finalMatrix, iteration, columnIndex, queue);

        return finalMatrix;
    }


    public MyPrecisionMatrix countResultsFromGauss(ArrayList<Integer> queue) {

        MyPrecisionMatrix vectorMatrix = new MyPrecisionMatrix(this.rows, 1);

        for (int i = 0; i < this.rows; i++) {
            vectorMatrix.matrix[i][0] = this.matrix[i][this.columns - 1];
        }

        MyOwnPrecision m, s;
        int n = this.rows;
        for (int i = n - 1; i >= 0; i--) {
            s = this.matrix[i][n];
            for (int j = n - 1; j >= i + 1; j--) {
                MyOwnPrecision tmp = MyOwnPrecision.multiply(this.matrix[i][j], vectorMatrix.matrix[j][0]);
                tmp = MyOwnPrecision.negate(tmp);
                s = MyOwnPrecision.add(s, tmp);
                MyOwnPrecision divider = MyOwnPrecision.flip(this.matrix[i][i]);
                vectorMatrix.matrix[i][0] = MyOwnPrecision.multiply(s, divider);
            }
        }
        //queue is used only in fullChoiseGauss so it is good to check there is any queue
        if (queue.size() > 0) vectorMatrix = sortResultsByQueue(vectorMatrix, queue);


        return vectorMatrix;

    }

    public MyPrecisionMatrix sortResultsByQueue(MyPrecisionMatrix vectorMatrix, ArrayList<Integer> queue){

        MyPrecisionMatrix tmp = new MyPrecisionMatrix(vectorMatrix.rows,1);

        for (int ii = 0; ii < vectorMatrix.rows; ii++) {
            for (int jj = 0; jj < vectorMatrix.columns ; jj++) {
                tmp.matrix[ii][jj] = vectorMatrix.matrix[ii][jj];
            }
        }

        for(int i =0 ; i<vectorMatrix.rows;i++){
            vectorMatrix.matrix[queue.get(i)][0] = tmp.matrix[i][0];
        }
        return vectorMatrix;
    }

    private void joinMatrixAndVector(MyPrecisionMatrix vector, MyPrecisionMatrix matrixToJoin) {
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

    public MyPrecisionMatrix flipRows(MyPrecisionMatrix finalMatrix, int row1, int row2) {
        if (row1 == row2) {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) {
            MyOwnPrecision tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }

    public MyPrecisionMatrix flipColumns(MyPrecisionMatrix finalMatrix, int column1, int column2, ArrayList<Integer> queue) {
        if (column1 == column2) {
            return finalMatrix;
        }
        int tmp = queue.get(column1);
        queue.set(column1,queue.get(column2));
        queue.set(column2,tmp);

        for (int i = 0; i < finalMatrix.rows; i++) {
            MyOwnPrecision tmp2 = finalMatrix.matrix[i][column1];
            finalMatrix.matrix[i][column1] = finalMatrix.matrix[i][column2];
            finalMatrix.matrix[i][column2] = tmp2;
        }
        return finalMatrix;
    }

    public int absValueInColumn(MyPrecisionMatrix finalMatrix, int column) {
        int index = column;
        MyOwnPrecision max = finalMatrix.matrix[column][column];
        for (int i = column + 1; i < finalMatrix.rows; i++) {

            if (Math.abs(finalMatrix.matrix[i][column].returnDoubleFormat()) > max.returnDoubleFormat()) {
                MyOwnPrecision tmp = finalMatrix.matrix[i][column].newInstance();
                tmp.absConvert();
                max = tmp;
                index = i;
            }
        }
        return index;
    }

    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        int lineNumber = 0;
        rows = Integer.parseInt(br.readLine());
        columns = Integer.parseInt(br.readLine());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                strLine = br.readLine();
                matrix[i][j] = new MyOwnPrecision(strLine);
            }

        }
        br.close();
        fstream.close();
    }

    public void printMatrix() {
        System.out.println("Matrix's values");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%2.16f ", matrix[i][j].returnDoubleFormat());
            }
            System.out.println("");
        }
        System.out.println("-----------\n");
    }
}
