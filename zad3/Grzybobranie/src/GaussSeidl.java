public class GaussSeidl extends Matrix {

    Matrix L;
    Matrix D;
    Matrix U;
    public static int iter = 15;

    Matrix x;

    public GaussSeidl(int length) {
        super(length);
        L = new Matrix(rows);
        D = new Matrix(rows);
        U = new Matrix(rows);
        x = new Matrix(rows, 1);
    }

    public GaussSeidl(int rows, int columns) {
        super(rows, columns);
    }

    public Matrix countGaussSiedl(Matrix b) {
        int n = rows;
        int i, j, k;


        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++) {
                if (i < j) {
                    U.matrix[i][j] = this.matrix[i][j];
                } else if (i > j) {
                    L.matrix[i][j] = this.matrix[i][j];
                } else {
                    D.matrix[i][j] = this.matrix[i][j];
                    System.out.println("test");
                }
            }

        for (i = 0; i < n; i++)
            D.matrix[i][i] = 1 / D.matrix[i][i];


        for (i = 0; i < n; i++)
            b.matrix[i][0] *= D.matrix[i][i];


        for (i = 0; i < n; i++)
            for (j = 0; j < i; j++)
                L.matrix[i][j] *= D.matrix[i][i];


        for (i = 0; i < n; i++)
            for (j = i + 1; j < n; j++)
                U.matrix[i][j] *= D.matrix[i][i];


        for (i = 0; i < n; i++)
            x.matrix[i][0] = 0.0;

        for (k = 0; k < iter; k++)
            for (i = 0; i < n; i++) {
                x.matrix[i][0] = b.matrix[i][0];
                for (j = 0; j < i; j++)
                    x.matrix[i][0] -= L.matrix[i][j] * x.matrix[j][0];
                for (j = i + 1; j < n; j++)
                    x.matrix[i][0] -= U.matrix[i][j] * x.matrix[j][0];
            }

        return x;
    }
}
