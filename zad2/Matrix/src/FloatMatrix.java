import java.io.*;
import java.util.ArrayList;

public class FloatMatrix {
    private Float[][] matrix;
    private int rows;
    private int columns;

    public FloatMatrix(int length) {
        this.rows = length;
        this.columns = length;
        matrix = new Float[rows][columns];
    }

    public FloatMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new Float[rows][columns];
    }

    public void add(FloatMatrix secondMatrix) {

        if (!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)) {
            return;
        }

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < columns; j++) {

                this.matrix[i][j] = this.matrix[i][j] + secondMatrix.matrix[i][j];
            }
        }
    }

    //returned new matrix,thats how multiply works
    public FloatMatrix multiply(FloatMatrix secondMatrix) {
        if (!(this.columns == secondMatrix.rows)) {
            return null;
        }
        FloatMatrix sumMatrix = new FloatMatrix(this.rows, secondMatrix.columns);
        sumMatrix.fillWithZero();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < secondMatrix.columns; k++) {

                    sumMatrix.matrix[i][k] = sumMatrix.matrix[i][k] + this.matrix[i][j] * secondMatrix.matrix[j][k];
                }
            }
        }
        return sumMatrix;
    }

    public FloatMatrix gaussBase(FloatMatrix vector) {

        FloatMatrix finalMatrix = new FloatMatrix(this.rows, this.columns + 1);
        FloatMatrix tmpMatrix = new FloatMatrix(this.rows, this.columns + 1);

        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);

        for (int i = 0; i < this.columns - 1; i++) {
            for (int j = i + 1; j < this.columns ; j++) {
                for (int k = 0; k < this.columns + 1; k++) {
                    tmpMatrix.matrix[j][k] = finalMatrix.matrix[j][k] -
                            (finalMatrix.matrix[i][k] * (finalMatrix.matrix[j][i] / finalMatrix.matrix[i][i]));
                }
            }
            Utils.copyMatrix(finalMatrix, tmpMatrix);
        }
        return tmpMatrix;
    }

    //search biggest value in column( search only in sub-matrix of course) column- can be also understood as iteration
    public FloatMatrix partialChoiseGauss(FloatMatrix vector) {

        FloatMatrix finalMatrix = new FloatMatrix(this.rows, this.columns + 1);
        FloatMatrix tmpMatrix = new FloatMatrix(this.rows, this.columns + 1);

        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);


        for (int i = 0; i < this.columns - 1; i++) {

            int index = absValueInColumn(finalMatrix, i);
            finalMatrix = flipRows(finalMatrix, i, index);
            Utils.copyMatrix(tmpMatrix, finalMatrix);

            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {
                    tmpMatrix.matrix[j][k] = finalMatrix.matrix[j][k] -
                            (finalMatrix.matrix[i][k] * (finalMatrix.matrix[j][i] / finalMatrix.matrix[i][i]));
                }
            }
           Utils.copyMatrix(finalMatrix, tmpMatrix);
        }
        return tmpMatrix;
    }

    //This case find biggest value in sub-matrix
    //It saves proper final order of results in arrayList queue
    //Returns matrix ready to count results
    public FloatMatrix fullChoiseGauss(FloatMatrix vector,ArrayList<Integer> queue) {

        FloatMatrix finalMatrix = new FloatMatrix(this.rows, this.columns + 1);
        FloatMatrix tmpMatrix = new FloatMatrix(this.rows, this.columns + 1);
        for(int i = 0;i<finalMatrix.rows;i++){
            queue.add(i);
        }

        //create extended matrix with one more column.
        joinMatrixAndVector(vector, finalMatrix);
        joinMatrixAndVector(vector, tmpMatrix);

        for (int i = 0; i < this.columns - 1; i++) {

            //Everytime sub-matrix shrincks, final matrix is changed to find biggest value in it
            finalMatrix = findAndSetBiggestValueInMatrix(finalMatrix, i, queue);
            Utils.copyMatrix(tmpMatrix, finalMatrix);

            for (int j = i + 1; j < this.columns; j++) {
                for (int k = 0; k < this.columns + 1; k++) {
                    tmpMatrix.matrix[j][k] = finalMatrix.matrix[j][k] -
                            (finalMatrix.matrix[i][k] * (finalMatrix.matrix[j][i] / finalMatrix.matrix[i][i]));
                }
            }
            Utils.copyMatrix(finalMatrix, tmpMatrix);
        }
        return tmpMatrix;
    }

    private void joinMatrixAndVector(FloatMatrix vector, FloatMatrix matrixToJoin) {
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

    public FloatMatrix findAndSetBiggestValueInMatrix(FloatMatrix finalMatrix, int iteration, ArrayList<Integer> queue) {

        Float maxValue = finalMatrix.matrix[iteration][iteration];
        int rowIndex = iteration;
        int columnIndex = iteration;
        for (int ii = iteration; ii < finalMatrix.rows; ii++) {
            for (int jj = iteration; jj < finalMatrix.columns - 1; jj++) {

                if (Math.abs(finalMatrix.matrix[ii][jj]) > maxValue) {
                    maxValue = Math.abs(finalMatrix.matrix[ii][jj]);
                    rowIndex = ii;
                    columnIndex = jj;
                }
            }
        }

        finalMatrix = flipRows(finalMatrix, iteration, rowIndex);
        finalMatrix = flipColumns(finalMatrix, iteration, columnIndex, queue);

        return finalMatrix;
    }

    //receive cleaned Matrix. Ready to count searched values
    //Returns vector with result in proper order
    public FloatMatrix countResultsFromGauss(ArrayList<Integer> queue) {

        FloatMatrix vectorMatrix = new FloatMatrix(this.rows, 1);

        //Copy last column(where are unkown values jet)
        for (int i = 0; i < this.rows; i++) {
            vectorMatrix.matrix[i][0] = this.matrix[i][this.columns - 1];
        }


        Float s;
        int n = this.rows;
        for (int i = n - 1; i >= 0; i--) {
            s = this.matrix[i][n];
            for (int j = n - 1; j >= i + 1; j--) {

                Float tmp1 = this.matrix[i][j] * vectorMatrix.matrix[j][0];


                s = s - tmp1;
                System.out.println("s = "+s + " - " + tmp1);

                vectorMatrix.matrix[i][0] = s / this.matrix[i][i];

            }
        }


        //queue is used only in fullChoiseGauss so it is good to check there is any queue
        if(queue.size()>0) vectorMatrix = sortResultsByQueue(vectorMatrix, queue);

        return vectorMatrix;
    }

    //Queue has proper order for our results. So now is only need to fix the order in final vector
    public FloatMatrix sortResultsByQueue(FloatMatrix vectorMatrix, ArrayList<Integer> queue){

        FloatMatrix tmp = new FloatMatrix(vectorMatrix.rows,1);
        Utils.copyMatrix(tmp,vectorMatrix);

        for(int i =0 ; i<vectorMatrix.rows;i++){
                vectorMatrix.matrix[queue.get(i)][0] = tmp.matrix[i][0];
        }
        return vectorMatrix;
    }

    public int absValueInColumn(FloatMatrix finalMatrix, int column) {
        int index = column;
        Float max = finalMatrix.matrix[column][column];
        for (int i = column + 1; i < finalMatrix.rows; i++) {

            if (Math.abs(finalMatrix.matrix[i][column]) > max) {
                max = Math.abs(finalMatrix.matrix[i][column]);
                index = i;
            }
        }
        return index;
    }

    public FloatMatrix flipRows(FloatMatrix finalMatrix, int row1, int row2) {
        if (row1 == row2) {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) {
            Float tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }

    //When columns are flipped, results order does to. Need to save it in queue arrayList
    public FloatMatrix flipColumns(FloatMatrix finalMatrix, int column1, int column2, ArrayList<Integer> queue) {
        if (column1 == column2) {
            return finalMatrix;
        }
        int tmp = queue.get(column1);
        queue.set(column1,queue.get(column2));
        queue.set(column2,tmp);

        for (int i = 0; i < finalMatrix.rows; i++) {
            Float tmp2 = finalMatrix.matrix[i][column1];
            finalMatrix.matrix[i][column1] = finalMatrix.matrix[i][column2];
            finalMatrix.matrix[i][column2] = tmp2;
        }
        return finalMatrix;
    }

    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        rows = Integer.parseInt(br.readLine());
        columns = Integer.parseInt(br.readLine());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                strLine = br.readLine();
                matrix[i][j] = Float.parseFloat(strLine);
            }
        }
        br.close();
        fstream.close();
    }

    public void printMatrix() {
        System.out.println("Matrix's values");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%2.8f ", matrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("-----------\n");
    }

    public void fillWithZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = 0f;
            }

        }
    }

    public Float[][] getMatrix() {
        return matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
