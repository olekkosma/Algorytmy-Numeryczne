import java.util.Arrays;

public class Main {
    public static double epsylon = 0.000000001;

    public static void main(String[] args) {
        int dataSize = 15;
        double[] values = new double[dataSize];
        values[0] = 0.00009;
        values[1] = 0.00028;
        values[2] = 0.00079;
        values[3] = 0.001419;
        values[4] = 0.00172;
        values[5] = 0.00395;
        values[6] = 0.00745;
        values[7] = 0.01105;
        values[8] = 0.0189;
        values[9] = 0.033;
        values[10] = 0.056333333;
        values[11] = 0.086333333;
        values[12] = 0.119;
        values[13] = 0.163333333;
        values[14] = 0.231;

        double[] keys = new double[dataSize];
        keys[0] = 40;
        keys[1] = 84;
        keys[2] = 144;
        keys[3] = 220;
        keys[4] = 312;
        keys[5] = 420;
        keys[6] = 544;
        keys[7] = 684;
        keys[8] = 840;
        keys[9] = 1012;
        keys[10] = 1200;
        keys[11] = 1404;
        keys[12] = 1624;
        keys[13] = 1860;
        keys[14] = 2112;


        int function = 2;
        int unknowns = function + 1;
        int sUnknowns = function * 2 + 1;
        int tUnknowns = function + 1;

        double[][] tableS = new double[dataSize][sUnknowns];
        double[] resultS = new double[sUnknowns];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < sUnknowns; j++) {
                tableS[i][j] = Math.pow(keys[i], j);
            }
        }
        double[][] tableT = new double[dataSize][tUnknowns];
        double[] resultT = new double[tUnknowns];

        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < tUnknowns; j++) {
                tableT[i][j] = values[i] * tableS[i][j];
            }
        }
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < sUnknowns; j++) {
                resultS[j] += tableS[i][j];
            }
        }
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < tUnknowns; j++) {
                resultT[j] += tableT[i][j];
            }
        }

        GaussSeidlNew matrix = new GaussSeidlNew(unknowns);
        int counter = 0;
        for(int i=0;i<unknowns;i++){
            for(int j=0;j<unknowns;j++){
                matrix.matrix[i][j]=resultS[j+counter];

            }
            counter++;
        }
        Matrix vector = new Matrix(unknowns,1);
        for(int i=0;i<unknowns;i++) {
            vector.matrix[i][0]=resultT[i];
        }
        Matrix result = matrix.countMatrix(vector);
        result.printMatrix();

        double[] results = new double[unknowns];
        for(int i=0;i<unknowns;i++){
            results[i] = result.matrix[i][0];
        }
        Function functionFinal = new Function(results);
        System.out.println(functionFinal.count(100000));

    }
}
