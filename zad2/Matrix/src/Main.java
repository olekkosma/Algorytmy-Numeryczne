import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


       // MyMatrix doubleMatrix = new MyMatrix(Float.class,4);
        //doubleMatrix.readFile("1");
       // doubleMatrix.printMatrix();

       // GenericValue<Float> dubel = new GenericValue<>(10.5333453453454535354554f);
        //dubel.printValue();


        MyOwnPrecision number = new MyOwnPrecision("-0.4");
        number.printAsFraction();



        FloatMatrix vector = new FloatMatrix(3,1);
        vector.loadValues("Vector");

        FloatMatrix matrix = new FloatMatrix(4);
        matrix.loadValues("1");
        matrix.printMatrix();
        FloatMatrix results = matrix.gaussBase(vector);
        results.printMatrix();

        vector = results.countResultsFromGauss();
        vector.printMatrix();


        MyPrecisionMatrix vector1 = new MyPrecisionMatrix(3,1);
        vector1.loadValues("Vector");
        vector1.printMatrix();

        MyPrecisionMatrix matrix2 = new MyPrecisionMatrix(4);
        matrix2.loadValues("1");
        matrix2.printMatrix();

        MyPrecisionMatrix results2 = matrix2.gaussBase(vector1);
        results2.printMatrix();

        vector1 = results2.countResultsFromGauss();
        vector1.printMatrix();

        //MyPrecisionMatrix matrix2 = new MyPrecisionMatrix(4);
        //matrix2.loadValues("2");
       // matrix2.printMatrix();

       // matrix2 = matrix2.multiply(matrix2);
       // matrix2.printMatrix();
    }
}
