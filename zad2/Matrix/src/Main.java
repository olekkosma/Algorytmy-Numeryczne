//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//08.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {

        //CountStatsForAddAndMultiply();
        //CountStatsForAddAndMultiplyMyOwn();

        countStatsForGauss();

    }

    public static void countStatsForGauss() throws IOException {
        ArrayList<String> data = new ArrayList<>();
        long start;
        long elapsedTimeMillis;
        int size = MyMatrix.loadSize("1");
        double sum = 0;
        double iterations = 1;
        double timeTmp = 0;

        MyMatrix<Double> vectorPartial = new MyMatrix(Double.class, size, 1);
        MyMatrix<Double> vectorFull = new MyMatrix(Double.class, size, 1);

        Double timePartial = 0.0;
        Double timeFull = 0.0;

        timePartial = vectorPartial.loadValuesWithTime("partial", timePartial);
        timeFull = vectorFull.loadValuesWithTime("full", timeFull);
        MyMatrix<Double> matrixADoubleTmp = new MyMatrix<>(Double.class, size);
        MyMatrix<Double> vectorXDoubleTmp = new MyMatrix<>(Double.class, size, 1);
        matrixADoubleTmp.loadValues("1");
        vectorXDoubleTmp.loadValues("Vector");

        MyOwnPrecision averageDiffDoubleEigenpartial = matrixADoubleTmp.countAverageDiff(vectorXDoubleTmp, vectorPartial);
        MyOwnPrecision averageDiffDoubleEigenFull = matrixADoubleTmp.countAverageDiff(vectorXDoubleTmp, vectorFull);
        data.add("eigen partial: \n" + timePartial.toString());
        data.add(averageDiffDoubleEigenpartial.printAsDecimal());
        data.add("eigen full: \n" + timeFull.toString());
        data.add(averageDiffDoubleEigenFull.printAsDecimal());


        MyMatrix<Float> matrixAFloat = new MyMatrix<>(Float.class, size);
        MyMatrix<Double> matrixADouble = new MyMatrix<>(Double.class, size);
        MyMatrix<MyOwnPrecision> matrixAMyOwnPrecision = new MyMatrix<>(MyOwnPrecision.class, size);
        MyMatrix<Float> vectorXFloat = new MyMatrix<>(Float.class, size, 1);
        MyMatrix<Double> vectorXDouble = new MyMatrix<>(Double.class, size, 1);
        MyMatrix<MyOwnPrecision> vectorXMyOwnPrecision = new MyMatrix<>(MyOwnPrecision.class, size, 1);
        ArrayList<Integer> queue = new ArrayList<>();
        ArrayList<Integer> queue2 = new ArrayList<>();
        ArrayList<Integer> queue3 = new ArrayList<>();

        loadFloatValues(matrixAFloat, vectorXFloat);
        loadDoubleValues(matrixADouble, vectorXDouble);
        loadMyOwnValues(matrixAMyOwnPrecision, vectorXMyOwnPrecision);

        System.out.println("eigen counted...");
        //---------------------------------Float--------------------------------------

        start = System.currentTimeMillis();
        MyMatrix<Float> resultBaseFloat = matrixAFloat.gaussBase(matrixAFloat, vectorXFloat);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadFloatValues(matrixAFloat, vectorXFloat);
        MyOwnPrecision averageDiffBaseFloat = matrixAFloat.countAverageDiff(vectorXFloat, resultBaseFloat);

        data.add("java base float: \n" + String.valueOf(timeTmp));
        data.add(averageDiffBaseFloat.printAsDecimal());

        start = System.currentTimeMillis();
        MyMatrix<Float> resultPartialFloat = matrixAFloat.partialChoiseGauss(matrixAFloat, vectorXFloat);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadFloatValues(matrixAFloat, vectorXFloat);
        MyOwnPrecision averageDiffPartialFloat = matrixAFloat.countAverageDiff(vectorXFloat, resultPartialFloat);
        data.add("java partial float: \n" + String.valueOf(timeTmp));
        data.add(averageDiffPartialFloat.printAsDecimal());


        start = System.currentTimeMillis();
        MyMatrix<Float> resultFullFloat = matrixAFloat.fullChoiseGauss(matrixAFloat, vectorXFloat, queue);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadFloatValues(matrixAFloat, vectorXFloat);
        MyOwnPrecision averageDiffFullFloat = matrixAFloat.countAverageDiff(vectorXFloat, resultFullFloat);
        data.add("java full float: \n" + String.valueOf(timeTmp));
        data.add(averageDiffFullFloat.printAsDecimal());
        System.out.println("float counted...");


        //---------------------------------Double--------------------------------------

        start = System.currentTimeMillis();
        MyMatrix<Double> resultBaseDouble = matrixADouble.gaussBase(matrixADouble, vectorXDouble);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadDoubleValues(matrixADouble, vectorXDouble);
        MyOwnPrecision averageDiffBaseDouble = matrixADouble.countAverageDiff(vectorXDouble, resultBaseDouble);
        data.add("java base double: \n" + String.valueOf(timeTmp));
        data.add(averageDiffBaseDouble.printAsDecimal());

        start = System.currentTimeMillis();
        MyMatrix<Double> resultPartialDouble = matrixADouble.partialChoiseGauss(matrixADouble, vectorXDouble);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadDoubleValues(matrixADouble, vectorXDouble);
        MyOwnPrecision averageDiffPartialDouble = matrixADouble.countAverageDiff(vectorXDouble, resultPartialDouble);
        data.add("java partial double: \n" + String.valueOf(timeTmp));
        data.add(averageDiffPartialDouble.printAsDecimal());


        queue = new ArrayList<>();
        start = System.currentTimeMillis();
        MyMatrix<Double> resultFullDouble = matrixADouble.fullChoiseGauss(matrixADouble, vectorXDouble, queue);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadDoubleValues(matrixADouble, vectorXDouble);
        MyOwnPrecision averageDiffFullDouble = matrixADouble.countAverageDiff(vectorXDouble, resultFullDouble);
        data.add("java full double: \n" + String.valueOf(timeTmp));
        data.add(averageDiffFullDouble.printAsDecimal());

        System.out.println("double counted...");

        //---------------------------------My Own--------------------------------------
/*
        start = System.currentTimeMillis();
        MyMatrix<MyOwnPrecision> resultBaseMyOwnPrecision = matrixAMyOwnPrecision.gaussBase(matrixAMyOwnPrecision, vectorXMyOwnPrecision);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadMyOwnValues(matrixAMyOwnPrecision, vectorXMyOwnPrecision);
        MyOwnPrecision averageDiffBaseMyOwnPrecision = matrixAMyOwnPrecision.countAverageDiff(vectorXMyOwnPrecision, resultBaseMyOwnPrecision);
        data.add("java base my own: \n" + String.valueOf(timeTmp));
        data.add(averageDiffBaseMyOwnPrecision.printAsDecimal());

        System.out.println("my own base counted...");

        start = System.currentTimeMillis();
        MyMatrix<MyOwnPrecision> resultPartialMyOwnPrecision = matrixAMyOwnPrecision.partialChoiseGauss(matrixAMyOwnPrecision, vectorXMyOwnPrecision);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadMyOwnValues(matrixAMyOwnPrecision, vectorXMyOwnPrecision);
        MyOwnPrecision averageDiffPartialMyOwnPrecision = matrixAMyOwnPrecision.countAverageDiff(vectorXMyOwnPrecision, resultPartialMyOwnPrecision);
        data.add("java partial my own: \n" + String.valueOf(timeTmp));
        data.add(averageDiffPartialMyOwnPrecision.printAsDecimal());

        System.out.println("my own partial counted...");

        queue = new ArrayList<>();
        start = System.currentTimeMillis();
        MyMatrix<MyOwnPrecision> resultFullMyOwnPrecision = matrixAMyOwnPrecision.fullChoiseGauss(matrixAMyOwnPrecision, vectorXMyOwnPrecision, queue3);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;

        loadMyOwnValues(matrixAMyOwnPrecision, vectorXMyOwnPrecision);
        MyOwnPrecision averageDiffFullMyOwnPrecision = matrixAMyOwnPrecision.countAverageDiff(vectorXMyOwnPrecision, resultFullMyOwnPrecision);
        data.add("java full my own: \n" + String.valueOf(timeTmp));
        data.add(averageDiffFullMyOwnPrecision.printAsDecimal());
        System.out.println("my own counted...");
*/
        writeToFileGauss("GaussStats", size, data);

    }

    private static void CountStatsForAddAndMultiply() throws IOException {
        long start;
        long elapsedTimeMillis;
        int size = MyMatrix.loadSize("1");
        double sum = 0;
        double iterations = 1;
        double avgTime = 0;

        MyMatrix<Double> matrixResultsAX = new MyMatrix(Double.class, size, 1);
        MyMatrix<Double> matrixResultsABCX = new MyMatrix(Double.class, size, 1);
        MyMatrix<Double> matrixResultsABC = new MyMatrix(Double.class, size);

        Double timeAX = 0.0;
        Double timeABCX = 0.0;
        Double timeABC = 0.0;

        timeAX = matrixResultsAX.loadValuesWithTime("AX", timeAX);
        timeABCX = matrixResultsABCX.loadValuesWithTime("ABCX", timeABCX);
        //timeABC = matrixResultsABC.loadValuesWithTime("ABC", timeABC);
        System.out.println("Eigen results loaded...");

        //MyOwnPrecision averageAX = matrixResultsAX.countAverageValue();
        //MyOwnPrecision averageABCX = matrixResultsABCX.countAverageValue();
        // MyOwnPrecision averageABC = matrixResultsABC.countAverageValue();

        System.out.println("Counted average for eigen...");
        MyMatrix<Double> matrixA = new MyMatrix<Double>(Double.class, size);
        MyMatrix<Double> matrixA2 = new MyMatrix<Double>(Double.class, size);
        MyMatrix<Double> matrixB = new MyMatrix<Double>(Double.class, size);
        MyMatrix<Double> matrixC = new MyMatrix<Double>(Double.class, size);
        MyMatrix<Double> vectorX = new MyMatrix<Double>(Double.class, size, 1);

        matrixA.loadValues("1");
        //matrixA2.loadValues("1");
        matrixB.loadValues("2");
        matrixC.loadValues("3");
        vectorX.loadValues("Vector");
        System.out.println("Values loaded to matrixs...");

        //----------------------------------------------------------------------------
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            MyMatrix<Double> matrixAX = matrixA.multiply(vectorX);                 //A * X
            elapsedTimeMillis = System.currentTimeMillis() - start;
            Double timeTMp = elapsedTimeMillis / 1000.0;
            System.out.println(timeTMp);
            sum = sum + timeTMp;
        }
        avgTime = sum / iterations;
        MyMatrix<Double> matrixAX = matrixA.multiply(vectorX);
        //MyOwnPrecision averageAXMy = matrixAX.countAverageValue();
        writeToFile("AXResults", size, timeAX, avgTime, new MyOwnPrecision("0.0"), new MyOwnPrecision("0.0"));
        System.out.println("A * X counted...");
        avgTime = 0;
        sum = 0;

        //----------------------------------------------------------------------------
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            MyMatrix<Double> tmp = matrixA.add(matrixB);
            tmp = tmp.add(matrixC);
            MyMatrix<Double> matrixABCX = tmp.multiply(vectorX);           //A+B+C * X
            elapsedTimeMillis = System.currentTimeMillis() - start;
            Double timeTMp = elapsedTimeMillis / 1000.0;
            System.out.println(timeTMp);
            sum = sum + timeTMp;
        }
        avgTime = sum / iterations;
        MyMatrix<Double> tmp = matrixA.add(matrixB);
        tmp = tmp.add(matrixC);
        MyMatrix<Double> matrixABCX = tmp.multiply(vectorX);
        matrixABCX.printMatrix();
        MyOwnPrecision averageABCXMy = matrixABCX.countAverageValue();
        writeToFile("ABCXResults", size, timeABCX, avgTime, new MyOwnPrecision("0.0"), new MyOwnPrecision("0.0"));
        System.out.println("A+B+C * X counted...");
        avgTime = 0;
        sum = 0;
        /*
        //----------------------------------------------------------------------------
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            MyMatrix<Double> matrixAB = matrixA.multiply(matrixB);
            MyMatrix<Double> matrixABC = matrixAB.multiply(matrixC);           //A*B*C
            elapsedTimeMillis = System.currentTimeMillis() - start;
            Double timeTMp = elapsedTimeMillis / 1000.0;
            System.out.println(timeTMp);
            sum = sum + timeTMp;
        }
        avgTime = sum / iterations;
        MyMatrix<Double> matrixAB = matrixA.multiply(matrixB);
        MyMatrix<Double> matrixABC = matrixAB.multiply(matrixC);
        MyOwnPrecision averageABCMy = matrixABC.countAverageValue();
        writeToFile("ABCResults", size, timeABC, avgTime, averageABC, averageABCMy);
        System.out.println("A*B*C counted...");
        */
        System.out.println("the end");
        avgTime = 0;
        sum = 0;


    }

    private static void CountStatsForAddAndMultiplyMyOwn() throws IOException {
        long start;
        long elapsedTimeMillis;
        int size = MyMatrix.loadSize("1");
        double sum = 0;
        double iterations = 1;
        double avgTime = 0;

        MyMatrix<MyOwnPrecision> matrixResultsAX = new MyMatrix(Double.class, size, 1);
        MyMatrix<MyOwnPrecision> matrixResultsABCX = new MyMatrix(Double.class, size, 1);
        MyMatrix<MyOwnPrecision> matrixResultsABC = new MyMatrix(Double.class, size);

        Double timeAX = 0.0;
        Double timeABCX = 0.0;
        Double timeABC = 0.0;

        timeAX = matrixResultsAX.loadValuesWithTime("AX", timeAX);
        timeABCX = matrixResultsABCX.loadValuesWithTime("ABCX", timeABCX);
        timeABC = matrixResultsABC.loadValuesWithTime("ABC", timeABC);
        System.out.println("Eigen results loaded...");

         MyOwnPrecision averageAX = matrixResultsAX.countAverageValue();
         MyOwnPrecision averageABCX = matrixResultsABCX.countAverageValue();
        MyOwnPrecision averageABC = matrixResultsABC.countAverageValue();

        System.out.println("Counted average for eigen...");
        MyMatrix<MyOwnPrecision> matrixA = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixA2 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixA3 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixB = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixB2 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixB3 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixC = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixC2 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> matrixC3 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size);
        MyMatrix<MyOwnPrecision> vectorX = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size, 1);
        MyMatrix<MyOwnPrecision> vectorX2 = new MyMatrix<MyOwnPrecision>(MyOwnPrecision.class, size, 1);

        matrixA.loadValues("1");
        matrixA2.loadValues("1");
        matrixA3.loadValues("1");
        matrixB.loadValues("2");
        matrixB2.loadValues("2");
        matrixB3.loadValues("2");
        matrixC.loadValues("3");
        matrixC2.loadValues("3");
        matrixC3.loadValues("3");
        vectorX.loadValues("Vector");
        vectorX2.loadValues("Vector");
        System.out.println("Values loaded to matrixs...");
        //----------------------------------------------------------------------------
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            MyMatrix<MyOwnPrecision> matrixAX = matrixA.multiply(vectorX);                 //A * X
            elapsedTimeMillis = System.currentTimeMillis() - start;
            Double timeTMp = elapsedTimeMillis / 1000.0;
            System.out.println(timeTMp);
            sum = sum + timeTMp;
        }
        avgTime = sum / iterations;
        MyMatrix<MyOwnPrecision> matrixAX = matrixA.multiply(vectorX);
        MyOwnPrecision averageAXMy = matrixAX.countAverageValue();
        writeToFile("AXResults", size, timeAX, avgTime, averageAX, averageAXMy);
        System.out.println("A * X counted...");
        avgTime = 0;
        sum = 0;
        //----------------------------------------------------------------------------
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            MyMatrix<MyOwnPrecision> tmp = matrixA.add(matrixB);
            tmp = tmp.add(matrixC);
            MyMatrix<MyOwnPrecision> matrixABCX = tmp.multiply(vectorX);           //A+B+C * X
            elapsedTimeMillis = System.currentTimeMillis() - start;
            Double timeTMp = elapsedTimeMillis / 1000.0;
            System.out.println(timeTMp);
            sum = sum + timeTMp;
        }
        avgTime = sum / iterations;
        MyMatrix<MyOwnPrecision> tmp = matrixA3.add(matrixB3);
        tmp = tmp.add(matrixC3);
        MyMatrix<MyOwnPrecision> matrixABCX = tmp.multiply(vectorX2);
         MyOwnPrecision averageABCXMy = matrixABCX.countAverageValue();
        writeToFile("ABCXResults", size, timeABCX, avgTime, averageABCX, averageABCXMy);
        System.out.println("A+B+C * X counted...");
        avgTime = 0;
        sum = 0;
        //----------------------------------------------------------------------------
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            MyMatrix<MyOwnPrecision> matrixAB = matrixA.multiply(matrixB);
            MyMatrix<MyOwnPrecision> matrixABC = matrixAB.multiply(matrixC);           //A*B*C
            elapsedTimeMillis = System.currentTimeMillis() - start;
            Double timeTMp = elapsedTimeMillis / 1000.0;
            System.out.println(timeTMp);
            sum = sum + timeTMp;
        }
        avgTime = sum / iterations;
        MyMatrix<MyOwnPrecision> matrixAB = matrixA2.multiply(matrixB2);
        MyMatrix<MyOwnPrecision> matrixABC = matrixAB.multiply(matrixC2);
        MyOwnPrecision averageABCMy = matrixABC.countAverageValue();
        writeToFile("ABCResults", size, timeABC, avgTime, averageABC, averageABCMy);
        System.out.println("A*B*C counted...");
        System.out.println("the end");
        avgTime = 0;
        sum = 0;

    }

    public static void writeToFile(String suffix, int size, Double time, Double timeMy, MyOwnPrecision average, MyOwnPrecision averageMy) throws IOException {
        FileOutputStream fstream = new FileOutputStream("..\\..\\zad2\\Values\\AddMultiplyFiles\\values" + suffix + ".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        averageMy = MyOwnPrecision.negate(averageMy);
        average.add(averageMy);
        average.absConvert();
        String averageString = average.toString();
        br.write("size, timeEigen, timeMy, average difference");
        br.newLine();
        br.write(String.valueOf(size));
        br.newLine();
        br.write(time.toString());
        br.newLine();
        br.write(timeMy.toString());
        br.newLine();
        br.write(averageString);
        br.newLine();
        br.close();
        fstream.close();
    }

    public static void writeToFileGauss(String suffix, int size, ArrayList<String> data) throws IOException {
        FileOutputStream fstream = new FileOutputStream("..\\..\\zad2\\Values\\AddMultiplyFiles\\values" + suffix + ".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));

        br.write(String.valueOf(size));
        br.newLine();
        for (String line : data) {
            br.write(line);
            br.newLine();
        }
        br.close();
        fstream.close();
    }

    private static void loadMyOwnValues(MyMatrix<MyOwnPrecision> matrixAMyOwnPrecision, MyMatrix<MyOwnPrecision> vectorXMyOwnPrecision) throws IOException {
        matrixAMyOwnPrecision.loadValues("1");
        vectorXMyOwnPrecision.loadValues("Vector");
    }

    private static void loadDoubleValues(MyMatrix<Double> matrixADouble, MyMatrix<Double> vectorXDouble) throws IOException {
        matrixADouble.loadValues("1");
        vectorXDouble.loadValues("Vector");
    }

    private static void loadFloatValues(MyMatrix<Float> matrixAFloat, MyMatrix<Float> vectorXFloat) throws IOException {
        matrixAFloat.loadValues("1");
        vectorXFloat.loadValues("Vector");
    }
}
