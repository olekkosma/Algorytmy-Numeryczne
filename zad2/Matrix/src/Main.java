import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        //MyMatrix<Double> doubleMatrix = new MyMatrix<Double>(Double.class,4);
        //doubleMatrix.printClass();


        FloatMatrix floatMatrix = new FloatMatrix(4);
        floatMatrix.loadValues("1");
        FloatMatrix vector = new FloatMatrix(4,1);
        vector.loadValues("Vector");
        FloatMatrix finalMatrix;
        ArrayList<Integer> queue = new ArrayList<>();
        finalMatrix = floatMatrix.fullChoiseGauss(vector,queue);
        vector = finalMatrix.countResultsFromGauss(queue);
        vector.printMatrix();

    }
}
