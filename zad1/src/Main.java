import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Main {


    public static void main(String[] args){

        //TODO zrobienie wyników dla paru tysiecy wejsciowych
        //TODO usrednienie i wykazanie poprzez wartosc bezwzgledna ze od tylu jest efektywniej : )
        //TODO wyjasnienie czemu to wynika, oczywiscie z przesuwania floating point
        //TODO sprawdzenie czy nasz algorytm oblicza precyzyjniej  lepiej niz biblioteka
        //TODO poprzez sprawdzeeni 16 miejsca po przecinku czy sa takie same zawsze w zaokragleniu...ehh
        //TODO odpowiednie wykresy tzn... np zbieganie danej funkcji od tylu i przodu do naszego wyniku wzdluz iteracji sumy..
        //TODO po chuj nie wiem takie cos xD
        //TODO czemu dla normalnego wzoru wynik potem jest zły
        //TODO chyba dlatego ze kurwa licze na doublach wynik raczej nie xD ja jebie, konwersja przy inicializacji
        //TODO z gownianego powodu do 1 tylko mozna sumowac potem sie pierdoli chuj wie czemu




        //TODO wnioski:
        //TODO sumowanie ze wzoru nie działa potem bo licze na ddoublach w momencie liczenia ułamka-pattern
        //TODO moj big decimal liczy najprecyzejniej : D
        ArrayList<BigDecimal> bigDecimalList = new ArrayList<>();
        MathContext mc = new MathContext(32, RoundingMode.HALF_UP);
        double valueDouble = 0.6;
        System.out.print("Output of Math libary:            ");
        System.out.println(Math.atan(valueDouble));
        System.out.print("Output of wolfram:                0.54041950027058415544357836460859\n");

                    //Sumowanie ze szeregu MacLourina
            //BigDecimal
        //Od przodu
        //( (-1)^n * x^(2n +1) )/ (2n+1)

        BigDecimal value = BigDecimal.valueOf(valueDouble);
        BigDecimal sum = new BigDecimal(0);
        BigDecimal pattern;
        int n=0;
        do{
            BigDecimal tmp = new BigDecimal(valueDouble);
            tmp = tmp.pow(2*n+1);
            tmp = tmp.divide(BigDecimal.valueOf((2*n+1)),mc);
            pattern = new BigDecimal(Math.pow((-1),n));
            pattern = pattern.multiply(tmp);
            //pattern = new BigDecimal(Math.pow((-1),n)*Math.pow((valueDouble),(2*n +1))/(2*n +1));

            bigDecimalList.add(pattern);
            n++;
        }while(n!=50);

        BigDecimal sumForward = new BigDecimal(0);
        for(BigDecimal listValue : bigDecimalList){
            sumForward = sumForward.add(listValue);

        }
        System.out.print("Output of suming values forward:  ");
        System.out.println(sumForward.setScale(32, RoundingMode.HALF_EVEN).toPlainString());


        //od tyłu

        ArrayList<BigDecimal> bigDecimalListInvert = invertList(bigDecimalList);
        BigDecimal sumBackward = new BigDecimal(0);

        for(BigDecimal listValue : bigDecimalListInvert){
            sumBackward = sumBackward.add(listValue);

        }
        System.out.print("Output of suming values backward: ");
        System.out.println(sumBackward.setScale(32, RoundingMode.HALF_EVEN).toPlainString());

            //double
        //od prozdu
        //( (-1)^n * x^(2n +1) )/ (2n+1)
        ArrayList<Double> doubleList = new ArrayList<>();
        double patternDouble;
        n=0;
        do{
            patternDouble = Math.pow((-1),n)*Math.pow(valueDouble,(2*n +1))/(2*n +1);
            doubleList.add(patternDouble);
            n++;
        }while(n!=27);

        double sumForwardDouble = 0;
        for(Double doubleValue : doubleList){
            sumForwardDouble = sumForwardDouble+doubleValue;

        }
        System.out.print("Output of suming double forward:  ");
        System.out.println(sumForwardDouble);

        //od tyłu


        double sumBackwardDouble = 0;
        ArrayList<Double> doubleListInvert = invertListDouble(doubleList);
        for(Double doubleValue : doubleListInvert){
            sumBackwardDouble = sumBackwardDouble+doubleValue;

        }
        System.out.print("Output of suming double backward: ");
        System.out.println(sumBackwardDouble);


                //sumowanie na podstawie poprzedniego
            //BigDecimal
        //Od przodu
        //((-(value*value))*(2*n+1))/(2*n+3);


        value = new BigDecimal(valueDouble);
        sum = new BigDecimal(valueDouble);
        n=0;
        BigDecimal  nextElement = value;

        do{
            BigDecimal tmp = value;
            tmp = tmp.multiply(tmp);
            tmp = tmp.negate();
            tmp = tmp.multiply(BigDecimal.valueOf(2*n + 1));

            tmp = tmp.divide(BigDecimal.valueOf((2*n + 3)),mc);

            nextElement = nextElement.multiply(tmp);
            sum = sum.add(nextElement);

            n++;
        }while(n != 500);

        System.out.print("Output of suming next forward:    ");
        System.out.println(sum.setScale(32, RoundingMode.HALF_DOWN).toPlainString());

        //od tyłu

        bigDecimalList.clear();
        bigDecimalListInvert.clear();
        nextElement = value;
        value = new BigDecimal(valueDouble);
        sum = new BigDecimal(valueDouble);
        n=0;

        do{
            BigDecimal tmp = value;
            tmp = tmp.multiply(tmp);
            tmp = tmp.negate();
            tmp = tmp.multiply(BigDecimal.valueOf(2*n + 1));

            tmp = tmp.divide(BigDecimal.valueOf((2*n + 3)),mc);

            nextElement = nextElement.multiply(tmp);
            bigDecimalList.add(nextElement);
            //sum = sum.add(nextElement);

            n++;
        }while(n != 500);

        bigDecimalListInvert = invertList(bigDecimalList);

        for(BigDecimal listValue : bigDecimalListInvert){
            sum = sum.add(listValue);

        }

        System.out.print("Output of suming next backward:   ");
        System.out.println(sum.setScale(32, RoundingMode.HALF_DOWN).toPlainString());

            //double
        //od prozdu

        //((-(value*value))*(2*n+1))/(2*n+3);

        Double sumDouble = valueDouble;
        n=0;
        Double  nextElementDouble = valueDouble;

        doubleList.clear();

        do{
            Double tmp = valueDouble;
           tmp = ((-(valueDouble*valueDouble))*(2*n+1))/(2*n+3);

            nextElementDouble = nextElementDouble * tmp;
            sumDouble = sumDouble +nextElementDouble;
            doubleList.add(nextElementDouble);
            n++;

        }while(n != 500);

        System.out.print("Output of double next forward:    ");
        System.out.println(sumDouble);

        //od tyłu

        doubleListInvert = invertListDouble(doubleList);
        sumBackwardDouble = 0;

        for(Double doubleValue : doubleListInvert){
            sumBackwardDouble = sumBackwardDouble+doubleValue;

        }
        sumBackwardDouble+=valueDouble;
        System.out.print("Output of double next backward:   ");
        System.out.println(sumBackwardDouble);

/*
//                              LICZENIE KTÓRE SUMOWANIE JEST PRECYZYJNIEJSZE

        int repetition = 1000;
        double valueChanged= 0.9;
        double libaryResult;
        double forwardClosed = 0;
        double backwardClosed = 0;
        double backwardClosedAverage = 0;
        double forwardClosedAverage = 0;
        for(int i=0;i<repetition;i++){
            valueChanged+=0.0001;
            libaryResult  = Math.atan(valueChanged);
            ArrayList<Double> list = atanArrayDouble(valueChanged);
            double resultForward = atanDoubleForward(list);
            double resultBackward = atanDoubleBackward(list);
            double diffForward = Math.abs(libaryResult-resultForward);
            double diffBackward = Math.abs(libaryResult-resultBackward);
            System.out.println();
            System.out.printf("%.17f\n",valueChanged);
            System.out.println("libary result:   "+libaryResult);
            System.out.println("forward result:  "+resultForward);
            System.out.println("backward result: "+resultBackward);
            System.out.printf("diff forward:    %.17f\n",diffForward);
            System.out.printf("diff bacward:    %.17f\n",diffBackward);
            backwardClosedAverage+=diffBackward;
            forwardClosedAverage+=diffForward;

            if(diffBackward<diffForward){
                backwardClosed++;
            }else{
                if(diffBackward>diffForward){
                    forwardClosed++;
                }
            }

        }

        System.out.println("backward is better in: "+backwardClosed/repetition * 100+" %");
        System.out.printf("average diffrence: %.22f\n",backwardClosedAverage/repetition);
        System.out.println("forward is better in: "+forwardClosed/repetition* 100+" %");
        System.out.printf("average diffrence: %.22f\n",forwardClosedAverage/repetition);
*/

    }

    public static  ArrayList<Double> atanArrayDouble(double value){
        ArrayList<Double> doubleList = new ArrayList<>();
        double patternDouble;
        int n=0;
        do{
            patternDouble = Math.pow((-1),n)*Math.pow(value,(2*n +1))/(2*n +1);
            doubleList.add(patternDouble);
            n++;
        }while(n!=25);

        return doubleList;


    }
    public static  double atanDoubleForward(ArrayList<Double> doubleList){

        double sumForwardDouble = 0;
        for(Double doubleValue : doubleList){
            sumForwardDouble = sumForwardDouble+doubleValue;

        }

        return sumForwardDouble;
    }
    public static double atanDoubleBackward(ArrayList<Double> doubleList){

        ArrayList<Double> doubleListInvert = invertListDouble(doubleList);


        return atanDoubleForward(doubleListInvert);
    }

    public static ArrayList<BigDecimal> invertList(ArrayList<BigDecimal> list){
        ArrayList<BigDecimal> toReturn = new ArrayList<>();

        for(int i = list.size()-1 ; i>=0 ; i--){
            toReturn.add(list.get(i));
        }


        return toReturn;
    }

    public static ArrayList<Double> invertListDouble(ArrayList<Double> list){
        ArrayList<Double> toReturn = new ArrayList<>();

        for(int i = list.size()-1 ; i>=0 ; i--){
            toReturn.add(list.get(i));
        }


        return toReturn;
    }

}
