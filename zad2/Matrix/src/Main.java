import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        MyMatrix<Float> floatMatrix = new MyMatrix<Float>(Float.class,4);
        floatMatrix.loadValues("1");

        MyMatrix<Float> floatMatrix2 = new MyMatrix<Float>(Float.class,4);
        floatMatrix2.loadValues("2");

        MyMatrix<Double> doubleMatrix = new MyMatrix<Double>(Double.class,4);
        doubleMatrix.loadValues("1");

        MyMatrix<MyOwnPrecision> mPrecMatrix = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4);
        mPrecMatrix.loadValues("1");

        MyMatrix<MyOwnPrecision> mPrecMatrix2 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4);
        mPrecMatrix2.loadValues("2");

        //Suming Matrix
        /*
        floatMatrix.printMatrix();
        floatMatrix2.printMatrix();
        floatMatrix.add(floatMatrix2);
        floatMatrix.printMatrix();

        mPrecMatrix.printMatrix();
        mPrecMatrix2.printMatrix();
        mPrecMatrix.add(mPrecMatrix2);
        mPrecMatrix.printMatrix();
*/
        System.out.println("----------------\n\n");
        //Multiplying matrix
        floatMatrix.printMatrix();
        floatMatrix2.printMatrix();
        MyMatrix<Float> multipliedFloat;
        multipliedFloat = floatMatrix.multiply(floatMatrix2);
        multipliedFloat.printMatrix();

        mPrecMatrix.printMatrix();
        mPrecMatrix2.printMatrix();
        MyMatrix<MyOwnPrecision> multipliedMyOwn;
        multipliedMyOwn = mPrecMatrix.multiply(mPrecMatrix2);
        multipliedMyOwn.printMatrix();





        //Testowanie precyzji dla roznych Gaussow 3 wersje
/*
        FloatMatrix floatMatrix = new FloatMatrix(4);
        floatMatrix.loadValues("1");
        FloatMatrix vector = new FloatMatrix(4,1);
        vector.loadValues("Vector");
        FloatMatrix finalMatrix;
        ArrayList<Integer> queue = new ArrayList<>();

        finalMatrix = floatMatrix.fullChoiseGauss(vector,queue);
        vector = finalMatrix.countResultsFromGauss(queue);
        vector.printMatrix();

        FloatMatrix floatMatrix2 = new FloatMatrix(4);
        floatMatrix2.loadValues("1");
        FloatMatrix vector2 = new FloatMatrix(4,1);
        vector2.loadValues("Vector");
        FloatMatrix finalMatrix2;
        ArrayList<Integer> queue2 = new ArrayList<>();

        finalMatrix2 = floatMatrix2.partialChoiseGauss(vector2);
        vector2 = finalMatrix2.countResultsFromGauss(queue2);
        vector2.printMatrix();

        FloatMatrix floatMatrix3 = new FloatMatrix(4);
        floatMatrix3.loadValues("1");
        FloatMatrix vector3 = new FloatMatrix(4,1);
        vector3.loadValues("Vector");
        FloatMatrix finalMatrix3;
        ArrayList<Integer> queue3 = new ArrayList<>();

        finalMatrix3 = floatMatrix3.gaussBase(vector3);
        vector3 = finalMatrix3.countResultsFromGauss(queue3);
        vector3.printMatrix();
*/
    }
}
