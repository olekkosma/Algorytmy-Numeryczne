//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//13.12.2017
//Algorytmy Numeryczne
//--------------------

public class Jacob extends Matrix {
    //warunek by bylo zbiezne dla tego algorytmu : macierz przękotniowo dominująca
    Matrix M;
    Matrix N;
    Matrix x1;
    Matrix x2;

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

        for (i = 0; i < num; i++) {
            N.matrix[i][0] = 1 / matrix[i][i];
        }

        for (i = 0; i < num; i++) {
            for (j = 0; j < num; j++) {
                if (i == j) {
                    M.matrix[i][j] = 0.0;
                } else {
                    M.matrix[i][j] = -(matrix[i][j] * N.matrix[i][0]);
                }
            }
        }

        for (i = 0; i < num; i++)
            x1.matrix[i][0] = 0.0;

        boolean stillCount = true;
        double tmp = x1.matrix[0][0], tmp2 = x1.matrix[0][0];
        int counter = 0, z = 0, iterator = 0;
        //for(k=0;k<Main.iterations;k++){
        while (stillCount) {
            for (i = 0; i < num; i++) {
                x2.matrix[i][0] = N.matrix[i][0] * b.matrix[i][0];
                for (j = 0; j < num; j++) {
                    x2.matrix[i][0] += M.matrix[i][j] * x1.matrix[j][0];
                }
            }
            for (i = 0; i < num; i++) {
                x1.matrix[i][0] = x2.matrix[i][0];
            }
            if (z != 0) {
                for (int g = 0; g < x1.matrix.length; g++) {
                    tmp2 += Math.abs(x1.matrix[g][0]);
                }
                tmp2 = tmp2 / x1.matrix.length;
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
        }
        System.out.println("needed iterations: " + counter);
        return x1;
    }
}
