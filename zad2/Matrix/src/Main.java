import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Float matrix, partial");

        MyMatrix<Float> floatMatrix = new MyMatrix<Float>(Float.class,4);
        floatMatrix.loadValues("1");
        MyMatrix<Float> floatVector = new MyMatrix<Float>(Float.class,4,1);
        floatVector.loadValues("Vector");
        floatMatrix.printMatrix();
        floatVector.printMatrix();
        ArrayList<Integer> queue = new ArrayList<>();
        MyMatrix<Float> resultFloat = floatMatrix.partialChoiseGauss(floatVector);
        System.out.println("Result after gauss");
        resultFloat.printMatrix();
        MyMatrix<Float> resultVector = resultFloat.countResultsFromGauss(queue);
        resultVector.printMatrix();

        System.out.println("Float matrix, locale");
        FloatMatrix float2 = new FloatMatrix(4);
        float2.loadValues("1");
        FloatMatrix vector2 = new FloatMatrix(4);
        vector2.loadValues("Vector");
        ArrayList<Integer> queue3 = new ArrayList<>();
        FloatMatrix result3 = float2.partialChoiseGauss(vector2);
        result3.printMatrix();
        FloatMatrix resulVector3 = result3.countResultsFromGauss(queue);
        resulVector3.printMatrix();



        System.out.println("My own matrix, partial");


        MyMatrix<MyOwnPrecision> myOwnMatrix = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4);
        myOwnMatrix.loadValues("1");
        MyMatrix<MyOwnPrecision> myOwnVector = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4);
        myOwnVector.loadValues("Vector");
        myOwnMatrix.printMatrix();
        myOwnVector.printMatrix();
        ArrayList<Integer> queue2 = new ArrayList<>();
        MyMatrix<MyOwnPrecision> resultMyOwn = myOwnMatrix.partialChoiseGauss(myOwnVector);
        System.out.println("Result after gauss");

        resultMyOwn.printMatrix();
        MyMatrix<MyOwnPrecision> resultMyOwnVector = resultMyOwn.countResultsFromGauss(queue2);
        resultMyOwnVector.printMatrix();

        /////////

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

        /*
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


*/


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

/*
        MyPrecisionMatrix floatMatrix = new MyPrecisionMatrix(4);
        floatMatrix.loadValues("1");
        MyPrecisionMatrix vector = new MyPrecisionMatrix(4,1);
        vector.loadValues("Vector");
        MyPrecisionMatrix finalMatrix;
        ArrayList<Integer> queue = new ArrayList<>();

        finalMatrix = floatMatrix.fullChoiseGauss(vector,queue);
        vector = finalMatrix.countResultsFromGauss(queue);
        vector.printMatrix();

        MyPrecisionMatrix floatMatrix2 = new MyPrecisionMatrix(4);
        floatMatrix2.loadValues("1");
        floatMatrix2.printMatrix();
        MyPrecisionMatrix vector2 = new MyPrecisionMatrix(4,1);
        vector2.loadValues("Vector");
        vector2.printMatrix();
        MyPrecisionMatrix finalMatrix2;
        ArrayList<Integer> queue2 = new ArrayList<>();

        finalMatrix2 = floatMatrix2.partialChoiseGauss(vector2);
        vector2 = finalMatrix2.countResultsFromGauss(queue2);
        vector2.printMatrix();

        MyPrecisionMatrix floatMatrix3 = new MyPrecisionMatrix(4);
        floatMatrix3.loadValues("1");
        MyPrecisionMatrix vector3 = new MyPrecisionMatrix(4,1);
        vector3.loadValues("Vector");
        MyPrecisionMatrix finalMatrix3;
        ArrayList<Integer> queue3 = new ArrayList<>();

        finalMatrix3 = floatMatrix3.gaussBase(vector3);
        vector3 = finalMatrix3.countResultsFromGauss(queue3);
        vector3.printMatrix();
        */
    }
}
