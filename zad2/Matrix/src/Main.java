//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//08.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    //    First field:
    //                -Class to count on
    //    Second field:
    //                 --suffix of fileToReadData
    //    Third field:
    //                1 -- gauss base
    //                2 -- gauss partial
    //                3 -- gauss full
    public static void main(String[] args) throws IOException {

        MyMatrix<Double> matrixTime = new MyMatrix(Double.class,1000,1);
        Double time = 0.0;
        time =  matrixTime.loadValuesWithTime("AX",time);
        System.out.println(time);

        MyOwnPrecision sum = matrixTime.sumAllValues();

        MyMatrix<MyOwnPrecision> matrixA = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,10);
        MyMatrix<MyOwnPrecision> vectorX = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class,10,1);

        matrixA.loadValues("1");
        vectorX.loadValues("Vector");
        vectorX = matrixA.multiply(vectorX);
        MyOwnPrecision sum2 = vectorX.sumAllValues();
        System.out.println(sum.printAsDecimal());
        System.out.println(sum2.printAsDecimal());
        sum.substract(sum2);

        System.out.println(sum.printAsDecimal());


        /*
        MyMatrix.calculateResult(Float.class,1,1);
        MyMatrix.calculateResult(Float.class,1,2);
        MyMatrix.calculateResult(Float.class,1,3);

        MyMatrix.calculateResult(Double.class,1,1);
        MyMatrix.calculateResult(Double.class,1,2);
        MyMatrix.calculateResult(Double.class,1,3);

        MyMatrix.calculateResult(MyOwnPrecision.class,1,1);
        MyMatrix.calculateResult(MyOwnPrecision.class,1,2);
        MyMatrix.calculateResult(MyOwnPrecision.class,1,3);
        */
    }
}
