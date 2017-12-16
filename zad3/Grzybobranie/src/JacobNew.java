public class JacobNew extends Matrix {

    public JacobNew(int length) {
        super(length);
    }


    @Override
    public Matrix countMatrix(Matrix b) {
        double epsilon = 0.000000000000001;

        Matrix X1 = new Matrix(this.rows, 1); //X new
        Matrix X2 = new Matrix(this.rows, 1); //X new
        double norm2 = b.countNorm();
        double sum = 0.0;
        int iterations = 0;
        while (iterations!=750) {
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
            //double norm1 = substract(b,multiply(this, X1)).countNorm();
            //if (norm1 / norm2 < Main.epsylonNew) {
            //    break;
            //}
            for(int i=0;i<this.rows;i++){
                X2.matrix[i][0]=X1.matrix[i][0];
            }
            iterations++;
        }
        System.out.println("needed iterations: "+iterations);
        return X1;

    }
}
