public class GaussSeidl extends Matrix {

    Matrix L;
    //Matrix D;
    Matrix U;


    Matrix x;

    public GaussSeidl(int length) {
        super(length);
        L = new Matrix(rows);
        //D = new Matrix(rows);
        U = new Matrix(rows);
        x = new Matrix(rows, 1);
    }

    public GaussSeidl(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public Matrix countMatrix(Matrix b) {
        int n = rows;
        int i, j, k;


        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++) {
                if (i < j) {
                    U.matrix[i][j] = this.matrix[i][j];
                } else if (i > j) {
                    L.matrix[i][j] = this.matrix[i][j];
                }
            }

        for (i = 0; i < n; i++)
            b.matrix[i][0] *= this.matrix[i][i];


        for (i = 0; i < n; i++)
            for (j = 0; j < i; j++)
                L.matrix[i][j] *= this.matrix[i][i];


        for (i = 0; i < n; i++)
            for (j = i + 1; j < n; j++)
                U.matrix[i][j] *= this.matrix[i][i];


        for (i = 0; i < n; i++)
            x.matrix[i][0] = 0.0;


        Matrix tmpMatrix = new Matrix(rows, 1);
        for (int w = 0; w < rows; w++) {
            tmpMatrix.matrix[w][0] = 0.0;
        }
        boolean stillCount = true;
        double tmp = x.matrix[0][0],tmp2 = x.matrix[0][0];
        int counter = 0,z = 0,iterator =0;
        //for(k=0;k<Main.iterations;k++){
        while (stillCount) {
            for (i = 0; i < n; i++) {
                x.matrix[i][0] = b.matrix[i][0];
                for (j = 0; j < i; j++)
                    x.matrix[i][0] -= L.matrix[i][j] * x.matrix[j][0];
                for (j = i + 1; j < n; j++)
                    x.matrix[i][0] -= U.matrix[i][j] * x.matrix[j][0];
            }
            if (z != 0) {
                for (int g = 0; g < x.matrix.length; g++) {
                    tmp2 += tmpMatrix.matrix[g][0] - x.matrix[g][0];
                }
                tmp2 = tmp2 / x.matrix.length;
                //System.out.println(tmp + " - " + tmp2 + " = " + Math.abs(tmp - x1.matrix[0][0]));
                if (Math.abs(tmp - tmp2) > 0.0000000000000001) {

                    tmp = tmp2;
                    counter++;
                    iterator =0;
                } else {
                    if(iterator==1) {
                        stillCount = false;
                    }
                    iterator++;
                    tmp = tmp2;
                    counter++;
                }
            }
            z = 1;
        }
        System.out.println("needed iterations: " + counter);
        return x;
    }
}
