//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//08.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

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

        //Wykres, przedstrawiający:
        //oś pozioma - rozmiar matrixów. spodziewam sie: tym wiekszy matrix tym wieksza bedzie roznica koncowa na tle eigen
        //poniewaz bedzie wiecej obliczen. Tylko MyMatrix pewnie bedzie nienaruszony
        // Float będzie robiony tak jak dobule, będzie tylko jego wartosc kastowana z pliku
        //dla pierwszych paru rozmiarów lepiej zrobic srednia z paru matrixow, potem juz jeden matrix wystarczy do obliczenia sredniej
        //os pionowa - koncowa srednia roznica na tle eigena. Srednia obliczona z sumowania wszystkich wartosci koncowego matrixa,
        //podzielona przez ilosc liczb w matrixie i odjeta od tego samego z eigena
        //--------------------------------------------------------------------------
        //drugi wykres:
        //os pozioma - tak samo rozmiar matrixów
        //os pionowa - czas wyliczania danej wartosci, tu chyba zostaniemy tylko przy double i MyPrecision
        //moze bedzie widoczna jaaks fajna funkcja widoczna, pewnie eigen bedzie najszybszy
        //------------------------------------------------------------------------------------
        //jak masz Tomek jakies pomysly pisz
        //---------------------------------------------------------
        //koncowe wyniki mozna wypluwac w formacie
        //-rozmiar tablicy
        //-czas obliczania
        //-koncowa roznica srednich juz po odliczenia od eigena
        //---------------------------------------------------------
        //kolejnosc obliczen
        //1.wygenerowanie liczb losowych
        //2.odpalenie eigena i wyplucie do pliku resultatow
        //3.wczytanie do programu eigena i obliczenie sredniej
        //4.odpalenie wlasnych obliczen
        //5.obliczenie roznicy
        //6. wyplucie ostatecznego pliku podusmowujacego wartosci dla danego rozmiaru
        //   moze jednak lepiej w jednym pliku oba czasy i roznice srednich
        //--------------------------------------------------------------------------

       // CountStatsForAddAndMultiply();
        //CountStatsForAddAndMultiplyMyOwn();

        countStatsForGauss();

        /*
        MyMatrix.calculateResult(Float.class,1,1);
        MyMatrix.calculateResult(Float.class,1,2);
        MyMatrix.calculateResult(Float.class,1,3);
*/
       // MyMatrix.calculateResult(Double.class,1,1);
        //MyMatrix.calculateResult(Double.class,1,2);
        //MyMatrix.calculateResult(Double.class,1,3);

        ///MyMatrix.calculateResult(MyOwnPrecision.class,1,1);
        //MyMatrix.calculateResult(MyOwnPrecision.class,1,2);
        //MyMatrix.calculateResult(MyOwnPrecision.class,1,3);

    }

    public static void countStatsForGauss() throws IOException {
        long start;
        long elapsedTimeMillis;
        int size = MyMatrix.loadSize("1");
        double sum = 0;
        double iterations = 1;
        double avgTime = 0;

        MyMatrix<Double> vectorPartial = new MyMatrix(Double.class, size, 1);
        MyMatrix<Double> vectorFull = new MyMatrix(Double.class, size, 1);

        Double timePartial = 0.0;
        Double timeFull = 0.0;

        timePartial = vectorPartial.loadValuesWithTime("partial", timePartial);
        timeFull = vectorFull.loadValuesWithTime("full", timeFull);

        MyMatrix<Double> matrixA = new MyMatrix<Double>(Double.class, size);
        MyMatrix<Double> vectorX = new MyMatrix<Double>(Double.class, size, 1);

        matrixA.loadValues("1");
        vectorX.loadValues("Vector");

        MyMatrix<Double> resultBase = matrixA.gaussBase(matrixA,vectorX);

       MyOwnPrecision averageDiff = matrixA.countAverageDiff(vectorX,resultBase);
        System.out.println("test");
        System.out.println(averageDiff.printAsDecimal());
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

       // MyOwnPrecision averageAX = matrixResultsAX.countAverageValue();
       // MyOwnPrecision averageABCX = matrixResultsABCX.countAverageValue();
        //MyOwnPrecision averageABC = matrixResultsABC.countAverageValue();

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
        //MyOwnPrecision averageAXMy = matrixAX.countAverageValue();
        writeToFile("AXResults", size, timeAX, avgTime, new MyOwnPrecision("0.0"), new MyOwnPrecision("0.0"));
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
       // MyOwnPrecision averageABCXMy = matrixABCX.countAverageValue();
        writeToFile("ABCXResults", size, timeABCX, avgTime, new MyOwnPrecision("0.0"), new MyOwnPrecision("0.0"));
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
        //MyOwnPrecision averageABCMy = matrixABC.countAverageValue();
        writeToFile("ABCResults", size, timeABC, avgTime, new MyOwnPrecision("0.0"), new MyOwnPrecision("0.0"));
        System.out.println("A*B*C counted...");
        System.out.println("the end");
        avgTime = 0;
        sum = 0;

    }

    public static void writeToFile(String suffix, int size, Double time, Double timeMy, MyOwnPrecision average, MyOwnPrecision averageMy) throws IOException {
        FileOutputStream fstream = new FileOutputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\Values\\AddMultiplyFiles\\values" + suffix + ".txt");
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
}
