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

    public FloatMatrix multiply(FloatMatrix secondMatrix) {
        if (!(this.columns == secondMatrix.rows)) {
            return null;
        }
        FloatMatrix tmp = new FloatMatrix(this.rows, secondMatrix.columns);
        tmp.fillWithZero();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                for (int k = 0; k < secondMatrix.columns; k++) {

                    tmp.matrix[i][k] = tmp.matrix[i][k] + this.matrix[i][j] * secondMatrix.matrix[j][k];

                }
            }
        }
        return tmp;
    }

    public void fillWithZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = 0f;
            }

        }
    }

    public FloatMatrix gaussBase(FloatMatrix vector) {
        //TODO trzeba sprawdzic czy odpowiednie rozmiary są

        FloatMatrix finalMatrix = new FloatMatrix(this.rows, this.columns + 1);
        FloatMatrix tmpMatrix = new FloatMatrix(this.rows, this.columns + 1);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns + 1; j++) {

                if (j == this.columns) {
                    finalMatrix.matrix[i][j] = vector.matrix[i][0];
                    tmpMatrix.matrix[i][j] = vector.matrix[i][0];
                } else {
                    finalMatrix.matrix[i][j] = this.matrix[i][j];
                    tmpMatrix.matrix[i][j] = this.matrix[i][j];
                }
            }
        }

        int n = this.columns + 1;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = 0; k < n; k++) {
                    tmpMatrix.matrix[j][k] = finalMatrix.matrix[j][k] -
                            (finalMatrix.matrix[i][k] * (finalMatrix.matrix[j][i] / finalMatrix.matrix[i][i]));
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

    public int absValueInColumn(FloatMatrix finalMatrix, int column) {
        int index = column;
        Float max = finalMatrix.matrix[column][column];
        for (int i = column + 1; i < finalMatrix.rows; i++) {
            //System.out.println("\n"+matrixAbs.matrix[i][column]+ "comapre to " + max+ "\n");
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

    public FloatMatrix partialChoiseGauss(FloatMatrix vector) {
        //TODO trzeba sprawdzic czy odpowiednie rozmiary są

        FloatMatrix finalMatrix = new FloatMatrix(this.rows, this.columns + 1);
        FloatMatrix tmpMatrix = new FloatMatrix(this.rows, this.columns + 1);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns + 1; j++) {

                if (j == this.columns) {
                    finalMatrix.matrix[i][j] = vector.matrix[i][0];
                    tmpMatrix.matrix[i][j] = vector.matrix[i][0];
                } else {
                    finalMatrix.matrix[i][j] = this.matrix[i][j];
                    tmpMatrix.matrix[i][j] = this.matrix[i][j];
                }
            }
        }

        int n = this.columns + 1;
        for (int i = 0; i < n - 2; i++) {
            //TODO Wyznaczaniek najwiekszego spolczynnika w kolumnie
            finalMatrix.printMatrix();
            int index = absValueInColumn(finalMatrix, i);
            finalMatrix = flipRows(finalMatrix, i, index);
            tmpMatrix = Utils.copyMatrix(tmpMatrix, finalMatrix);

            for (int j = i + 1; j < n - 1; j++) {
                for (int k = 0; k < n; k++) {
                    tmpMatrix.matrix[j][k] = finalMatrix.matrix[j][k] -
                            (finalMatrix.matrix[i][k] * (finalMatrix.matrix[j][i] / finalMatrix.matrix[i][i]));
                }
            }

            finalMatrix = Utils.copyMatrix(finalMatrix, tmpMatrix);
        }
        return tmpMatrix;
    }

    public FloatMatrix findAndSetBiggestValueInMatrix(FloatMatrix finalMatrix, int iteration, ArrayList<Integer> queue) {

        Float maxValue = finalMatrix.matrix[iteration][iteration];
        int rowIndex = 0;
        int columnIndex = 0;
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

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns + 1; j++) {

                if (j == this.columns) {
                    finalMatrix.matrix[i][j] = vector.matrix[i][0];
                    tmpMatrix.matrix[i][j] = vector.matrix[i][0];
                } else {
                    finalMatrix.matrix[i][j] = this.matrix[i][j];
                    tmpMatrix.matrix[i][j] = this.matrix[i][j];
                }
            }
        }

        int n = this.columns + 1;
        for (int i = 0; i < n - 2; i++) {
            //TODO Wyznaczaniek najwiekszego spolczynnika w kolumnie
            finalMatrix = findAndSetBiggestValueInMatrix(finalMatrix, i, queue);
            tmpMatrix = Utils.copyMatrix(tmpMatrix, finalMatrix);

            for (int j = i + 1; j < n - 1; j++) {
                for (int k = 0; k < n; k++) {
                    tmpMatrix.matrix[j][k] = finalMatrix.matrix[j][k] -
                            (finalMatrix.matrix[i][k] * (finalMatrix.matrix[j][i] / finalMatrix.matrix[i][i]));
                }
            }

            finalMatrix = Utils.copyMatrix(finalMatrix, tmpMatrix);
        }
        return tmpMatrix;
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
                s = s - this.matrix[i][j] * vectorMatrix.matrix[j][0];
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
        tmp = Utils.copyMatrix(tmp,vectorMatrix);

        for(int i =0 ; i<vectorMatrix.rows;i++){
                vectorMatrix.matrix[queue.get(i)][0] = tmp.matrix[i][0];
        }
        return vectorMatrix;
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
