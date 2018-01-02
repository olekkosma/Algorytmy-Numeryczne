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
        int z=0;
        boolean stillCount = true;
        double tmp = 0, tmp2 = 0;
        int counter = 0, iterator = 0;
boolean oko=true;
        while (stillCount) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < i; j++) {
                    sum -= this.matrix[i][j] * X.matrix[j][0];
                    counter++;
                }
                for (int j = i + 1; j < this.rows; j++) {
                    sum -= this.matrix[i][j] * X.matrix[j][0];
                    counter++;

                }
                if (this.matrix[i][i] == 0.0) {
                    int row = findBiggestRowInColumn(this, i);
                    swapRows(this, i, row);
                    swapRows(b, i, row);
                }
                X.matrix[i][0] = (b.matrix[i][0] + sum) / this.matrix[i][i];
                sum = 0.0;
            }
            if (z != 0) {
                for (int g = 0; g < X.matrix.length; g++) {
                    tmp2 += Math.abs(X.matrix[g][0]);
                }
                tmp2 = tmp2 / X.matrix.length;
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
        return X;
    }
}
