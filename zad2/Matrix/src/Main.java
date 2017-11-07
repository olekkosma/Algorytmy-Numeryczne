import java.io.IOException;
import java.util.ArrayList;

public class Main {

    //    1 -- gauss base
    //    2 -- gauss partial
    //    3 -- gauss full
    public static void main(String[] args) throws IOException {


        /*
        ArrayList<Integer> queue = new ArrayList<>();
        FloatMatrix myMatrix = new FloatMatrix(4);
        myMatrix.loadValues("1");
        FloatMatrix vector3 = new FloatMatrix(4, 1);
        vector3.loadValues("Vector");
        FloatMatrix extendedMatrix = myMatrix.gaussBase(myMatrix, vector3);
        FloatMatrix extendedMatrix2 = myMatrix.partialChoiseGauss(myMatrix, vector3);
        FloatMatrix extendedMatrix3 = myMatrix.fullChoiseGauss(myMatrix, vector3, queue);
        extendedMatrix.printMatrix();
        extendedMatrix2.printMatrix();
        extendedMatrix3.printMatrix();
*/


        ArrayList<Integer> queue = new ArrayList<>();
        MyMatrix<Float> myMatrixFloat = new  MyMatrix<Float>(Float.class,4);
        MyMatrix<Float> myMatrixFloat2 = new  MyMatrix<Float>(Float.class,4);
        myMatrixFloat.loadValues("1");
        myMatrixFloat2.loadValues("1");
        MyMatrix<Float> vectorFloat = new MyMatrix<Float>(Float.class,4, 1);
        MyMatrix<Float> vectorFloat2 = new MyMatrix<Float>(Float.class,4, 1);
        vectorFloat.loadValues("Vector");
        vectorFloat2.loadValues("Vector");
        MyMatrix<Float> extendedMatrix4 = myMatrixFloat.gaussBase(myMatrixFloat, vectorFloat);
        //MyMatrix<Float> extendedMatrix5 = myMatrixFloat.partialChoiseGauss(myMatrixFloat, vectorFloat);
        //MyMatrix<Float> extendedMatrix6 = myMatrixFloat.fullChoiseGauss(myMatrixFloat, vectorFloat, queue);
        extendedMatrix4.printMatrix();
       // extendedMatrix5.printMatrix();
        //extendedMatrix6.printMatrix();


        MyMatrix<MyOwnPrecision> myOwnMatrix = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4);
        MyMatrix<MyOwnPrecision> myOwnMatrix1 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4);
        MyMatrix<MyOwnPrecision> myOwnVector = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4,1);
        MyMatrix<MyOwnPrecision> myOwnVector1 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,4,1);
        myOwnMatrix.loadValues("1");
        myOwnMatrix1.loadValues("1");
        myOwnVector.loadValues("Vector");
        myOwnVector1.loadValues("Vector");
        MyMatrix<MyOwnPrecision> extendedMatrixMyOwn = myOwnMatrix.fullChoiseGauss(myOwnMatrix, myOwnVector,queue);
        extendedMatrixMyOwn.printMatrix();

        myOwnMatrix1.countAbsOfResult(myOwnVector1,extendedMatrixMyOwn);




    }
}
