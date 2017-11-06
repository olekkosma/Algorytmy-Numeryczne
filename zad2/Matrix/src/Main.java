import java.io.IOException;
import java.util.ArrayList;

public class Main {

    //    1 -- gauss base
    //    2 -- gauss partial
    //    3 -- gauss full
    public static void main(String[] args) throws IOException {

/*
        MyMatrix<Float> baseMatrix = new MyMatrix<Float>(Float.class, 4);
        baseMatrix.loadValues("1");
        baseMatrix.printMatrix();
        MyMatrix<Float> vector = new MyMatrix<Float>(Float.class, 4, 1);
        vector.loadValues("Vector");
        vector.printMatrix();

        MyMatrix resultVectorBase = baseMatrix.calculateResult(vector, 1);
        MyMatrix cleanedExtendedMatrix2 = baseMatrix.calculateResult(vector, 2);

        resultVectorBase.printMatrix();
        cleanedExtendedMatrix2.printMatrix();

        Float resultBase = baseMatrix.countAbsOfResult(vector,resultVectorBase);
        Float resultPartial = baseMatrix.countAbsOfResult(vector,cleanedExtendedMatrix2);
        baseMatrix.printMatrix();
        vector.printMatrix();
        System.out.printf("%.8f\n",resultBase);
        System.out.printf("%.8f\n",resultPartial);
*/
        MyMatrix<Float> baseMatrix = new MyMatrix<Float>(Float.class, 4);
        baseMatrix.loadValues("1");
        baseMatrix.printMatrix();
        MyMatrix<Float> vector = new MyMatrix<Float>(Float.class, 4, 1);
        vector.loadValues("Vector");
        vector.printMatrix();
        ArrayList<Integer> queue = new ArrayList<>();
        MyMatrix cleanedExtendedMatrix1 = baseMatrix.fullChoiseGauss(vector, queue);
        cleanedExtendedMatrix1.printMatrix();
        MyMatrix resultVector1 = cleanedExtendedMatrix1.countResultsFromGauss(queue);
        System.out.println("wektor wynikowy");
        resultVector1.printMatrix();
        baseMatrix.countAbsOfResult(vector,resultVector1);


    }
}
