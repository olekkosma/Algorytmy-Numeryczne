//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------


public class Jacob extends Matrix {
    //warunek by bylo zbierzne dla tego algorytmu : macierz przękotniowo dominująca
    Matrix M;
    Matrix N;
    Matrix x1;
    Matrix x2;
    public static int iter = 170;


    public Jacob(int length) {
        super(length);
        this.M = new Matrix(rows);
        this.N = new Matrix(rows);
        this.x1 = new Matrix(rows, 1);
        this.x2 = new Matrix(rows, 1);
    }

    public Jacob(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public Matrix countMatrix(Matrix b) {
        int num = rows;
        int i, j, k;

        for (i = 0; i < num; i++)
            N.matrix[i][0] = 1 / matrix[i][i];

        for (i = 0; i < num; i++)
            for (j = 0; j < num; j++)
                if (i == j)
                    M.matrix[i][j] = 0.0;
                else
                    M.matrix[i][j] = -(matrix[i][j] * N.matrix[i][0]);


        for (i = 0; i < num; i++)
            x1.matrix[i][0] = 0.0;


        for (k = 0; k < iter; k++) {
            for (i = 0; i < num; i++) {
                x2.matrix[i][0] = N.matrix[i][0] * b.matrix[i][0];
                for (j = 0; j < num; j++)
                    x2.matrix[i][0] += M.matrix[i][j] * x1.matrix[j][0];
            }
            for (i = 0; i < num; i++)
                x1.matrix[i][0] = x2.matrix[i][0];
        }

        return x1;
    }
}
