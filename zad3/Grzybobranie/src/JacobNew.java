//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//17.12.2017
//Algorytmy Numeryczne
//--------------------

public class JacobNew extends Matrix {

    public JacobNew(int length) {
        super(length);
    }


    @Override
    public Matrix countMatrix(Matrix b) {
        Matrix X1 = new Matrix(this.rows, 1);
        Matrix X2 = new Matrix(this.rows, 1);
        double norm2 = b.countNorm();
        double sum = 0.0;
        int iterations = 0;
        int z=0;
        boolean stillCount = true;
        double tmp = 0, tmp2 = 0;
        int counter = 0, iterator = 0;
        while (iterations!=Main.iterations) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.rows; j++) {
                    if (i != j) {
                        sum -= this.matrix[i][j] * X2.matrix[j][0];
                    }
                }
                if (this.matrix[i][i] == 0.0) {
                    int row = findBiggestRowInColumn(this, i);
                    swapRows(b, i, row);
                    swapRows(b, i, row);
                }

                X1.matrix[i][0] = (b.matrix[i][0] + sum) / this.matrix[i][i];
                sum = 0.0;

            }
            if (z != 0) {
                for (int g = 0; g < X1.matrix.length; g++) {
                    tmp2 += Math.abs(X1.matrix[g][0]);
                }
                tmp2 = tmp2 / X1.matrix.length;
                if (Math.abs(tmp - tmp2) > Main.epsylon) {
                    iterator = 0;
                } else {
                    if (iterator == 4) {
                        stillCount = false;
                    }
                    iterator++;
                }
                tmp = tmp2;
                counter++;
            }
            z = 1;
            iterations++;
        }
        System.out.println("needed iterations: "+iterations);
        return X1;

    }
}
