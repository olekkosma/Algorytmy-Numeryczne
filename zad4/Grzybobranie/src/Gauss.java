//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//07.01.2018
//Algorytmy Numeryczne
//--------------------

import java.util.ArrayList;

public class Gauss extends Matrix{


    public Gauss(int length) {
        super(length);
    }

    public Gauss(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public Matrix countMatrix( Matrix vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {

            int max = p;
            FlipBiggestRow(this, vector, n, p, max);
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(this, vector, n, p, i);
            }
        }

        Matrix resultVector = new Matrix(vector.rows, 1);
        CountBackwardResult(this, vector, n, resultVector);
        return resultVector;
    }

    private void FlipBiggestRow(Matrix matrix, Matrix vector, int n, int p, int max) {
        for (int i = p + 1; i < n; i++) {

            if (Math.abs(matrix.matrix[i][p]) > Math.abs(matrix.matrix[max][p])) {
                max = i;
            }
        }

        flipRows(matrix, p, max);
        flipRows(vector, p, max);
    }

    private void CleanMatrix(Matrix matrix, Matrix vector, int n, int p, int i) {

        double alpha = matrix.matrix[i][p] / matrix.matrix[p][p];
            vector.matrix[i][0] = vector.matrix[i][0] - alpha * vector.matrix[p][0];
            for (int j = p; j < n; j++) {
                matrix.matrix[i][j] = matrix.matrix[i][j] - alpha * matrix.matrix[p][j];

            }
    }

    private void CountBackwardResult(Matrix matrix, Matrix vector, int n, Matrix resultVector) {

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix.matrix[i][j] * resultVector.matrix[j][0];
            }
            resultVector.matrix[i][0] = (vector.matrix[i][0] - sum) / matrix.matrix[i][i];

        }
    }

    public Matrix flipRows(Matrix finalMatrix, int row1, int row2) {
        if (row1 == row2) {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) {
            Double tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }
}