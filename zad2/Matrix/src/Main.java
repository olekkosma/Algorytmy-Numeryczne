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

        int size = MyMatrix.loadSize("1");

        MyMatrix<Double> matrixTime = new MyMatrix(Double.class, size, 1);
        Double time = 0.0;
        time = matrixTime.loadValuesWithTime("AX", time);
        System.out.println(time);
        MyOwnPrecision sum = matrixTime.sumAllValues();

        MyMatrix<Double> matrixA = new MyMatrix<Double>(Double.class, size);
        MyMatrix<Double> vectorX = new MyMatrix<Double>(Double.class, size, 1);

        matrixA.loadValues("1");
        vectorX.loadValues("Vector");
        vectorX = matrixA.multiply(vectorX);
        MyOwnPrecision sum2 = vectorX.sumAllValues();
        System.out.println(sum.printAsDecimal());
        System.out.println(sum2.printAsDecimal());
        sum.substract(sum2);
        sum.absConvert();

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
