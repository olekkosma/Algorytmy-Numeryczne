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

        MyMatrix.calculateResult(Float.class,1,1);
        MyMatrix.calculateResult(Float.class,1,2);
        MyMatrix.calculateResult(Float.class,1,3);

        MyMatrix.calculateResult(Double.class,1,1);
        MyMatrix.calculateResult(Double.class,1,2);
        MyMatrix.calculateResult(Double.class,1,3);

        MyMatrix.calculateResult(MyOwnPrecision.class,1,1);
        MyMatrix.calculateResult(MyOwnPrecision.class,1,2);
        MyMatrix.calculateResult(MyOwnPrecision.class,1,3);
    }
}
