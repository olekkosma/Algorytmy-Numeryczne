//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//17.12.2017
//Algorytmy Numeryczne
//--------------------
public class GaussSeidlNew extends Matrix {

    public GaussSeidlNew(int length) {
        super(length);
    }

    @Override
    public Matrix countMatrix(Matrix b) {
        Matrix X = new Matrix(this.rows, 1);
        double sum = 0.0;
        int iterations = 0;
        double norm2 = b.countNorm();
        while (iterations!=Main.iterations) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < i; j++) {
                    sum -= this.matrix[i][j] * X.matrix[j][0];
                }
                for (int j = i + 1; j < this.rows; j++) {
                    sum -= this.matrix[i][j] * X.matrix[j][0];
                }
                if (this.matrix[i][i] == 0.0) {
                    int row = findBiggestRowInColumn(this, i);
                    swapRows(this, i, row);
                    swapRows(b, i, row);
                }
                X.matrix[i][0] = (b.matrix[i][0] + sum) / this.matrix[i][i];
                sum = 0.0;
            }

            double norm1 = substract(b, multiply(this, X)).countNorm();
            if ((norm1 / norm2) < Main.epsylonNew) {
                break;
            }
            iterations++;

        }
        System.out.println("needed iterations: "+iterations);
        return X;
    }
}
