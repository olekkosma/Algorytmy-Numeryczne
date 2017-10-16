import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.lang.StrictMath;
import java.util.Collections;

public class Main {

    static int iterations = 300;

    public static ArrayList<BigDecimal> ValuesOfMcLourinList(String value) {

        MathContext mc = new MathContext(80, RoundingMode.HALF_DOWN);
        ArrayList<BigDecimal> bigDecimalList = new ArrayList<>();

        int n = 0;
        do {
            BigDecimal tmp = new BigDecimal(value);
            tmp = tmp.pow(2 * n + 1);
            tmp = tmp.divide(BigDecimal.valueOf((2 * n + 1)), mc);
            BigDecimal pattern = new BigDecimal(Math.pow((-1), n));
            pattern = pattern.multiply(tmp);
            bigDecimalList.add(pattern);
            n++;

        } while (n != iterations);

        return bigDecimalList;
    }
    public static BigDecimal sumForwardList(ArrayList<BigDecimal> bigDecimalList){
        BigDecimal sumForward = new BigDecimal(0);
        for(BigDecimal listValue : bigDecimalList){
            sumForward = sumForward.add(listValue);

        }
        return sumForward;
    }

    public static BigDecimal sumBackwardList(ArrayList<BigDecimal> bigDecimalList){

        ArrayList<BigDecimal> bigDecimalListInvert = invertList(bigDecimalList);
        BigDecimal sumBackward = new BigDecimal(0);

        for(BigDecimal listValue : bigDecimalListInvert){
            sumBackward = sumBackward.add(listValue);

        }
        return sumBackward;
    }



    public static ArrayList<Double> ValuesOfMcLourinListDouble(String value) {

        ArrayList<Double> doubleList = new ArrayList<>();
        double patternDouble;
        double valueDouble = Double.parseDouble(value);
        int n = 0;
        do {
            patternDouble = Math.pow((-1), n) * Math.pow(valueDouble, (2 * n + 1)) / (2 * n + 1);
            doubleList.add(patternDouble);
            n++;
        } while (n != iterations);

        return doubleList;
    }

    public static Double sumForwardListDouble(ArrayList<Double> doubleList){

        double sumForwardDouble = 0;
        for(Double doubleValue : doubleList){
            sumForwardDouble = sumForwardDouble+doubleValue;

        }
       return sumForwardDouble;


    }

    public static Double sumBackwardListDouble(ArrayList<Double> doubleList){

        double sumBackwardDouble = 0;
        ArrayList<Double> doubleListInvert = invertListDouble(doubleList);
        for(Double doubleValue : doubleListInvert){
            sumBackwardDouble = sumBackwardDouble+doubleValue;

        }
       return sumBackwardDouble;

    }

    public static ArrayList<BigDecimal> ValuesOfCountNextList(String value) {

        MathContext mc = new MathContext(70, RoundingMode.HALF_DOWN);
        ArrayList<BigDecimal> bigDecimalList = new ArrayList<>();
        BigDecimal  nextElement = new BigDecimal(value);
        bigDecimalList.add(new BigDecimal(value));
        int n=0;
        do{
            BigDecimal tmp =  new BigDecimal(value);
            tmp = tmp.multiply(tmp);
            tmp = tmp.negate();
            tmp = tmp.multiply(BigDecimal.valueOf(2*n + 1));
            tmp = tmp.divide(BigDecimal.valueOf((2*n + 3)),mc);
            nextElement = nextElement.multiply(tmp);
            bigDecimalList.add(nextElement);
            n++;
        }while(n != iterations);

       return bigDecimalList;
    }

    public static ArrayList<Double> ValuesOfCountNextListDouble(String value) {

        Double  nextElementDouble = Double.parseDouble(value);
        ArrayList<Double> doubleList = new ArrayList<>();
        double valueDouble = Double.parseDouble(value);
        doubleList.add(valueDouble);
        Double sumDouble = Double.parseDouble(value);

        int n=0;
        do{
            Double tmp;
            tmp = ((-(valueDouble*valueDouble))*(2*n+1))/(2*n+3);
            nextElementDouble = nextElementDouble * tmp;
            sumDouble = sumDouble +nextElementDouble;
            doubleList.add(nextElementDouble);
            n++;

        }while(n != iterations);
        return doubleList;
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

    public static void countOwnArctanValues(String value){

        System.out.print("Output of Math libary:              ");
        System.out.println(Math.atan(Double.parseDouble(value))+"");
        //System.out.print("Output of Wolfram:                  ");
        //System.out.println("0.2303949468807240960351954018340492626054069551810387153331776640\n");

        //Sumowanie ze szeregu MacLourina
        //BigDecimal
        //Od przodu
        //( (-1)^n * x^(2n +1) )/ (2n+1)

        System.out.print("Sum forward McLourin BigDecimal:    ");
        System.out.println(sumForwardList(ValuesOfMcLourinList(value))
                .setScale(64, RoundingMode.HALF_UP).toPlainString());

        //od tyłu

        System.out.print("Sum backward McLourin BigDecimal:   ");
        System.out.println(sumBackwardList(ValuesOfMcLourinList(value))
                .setScale(64, RoundingMode.HALF_UP).toPlainString());

        //double
        //od prozdu
        //( (-1)^n * x^(2n +1) )/ (2n+1)

        System.out.print("\nSum forward McLourin Double:        ");
        System.out.println(sumForwardListDouble(ValuesOfMcLourinListDouble(value)));

        //od tyłu

        System.out.print("Sum backward McLourin Double:       ");
        System.out.println(sumBackwardListDouble(ValuesOfMcLourinListDouble(value)));

        //sumowanie na podstawie poprzedniego
        //BigDecimal
        //Od przodu
        //((-(value*value))*(2*n+1))/(2*n+3);

        System.out.print("\nSum forward next BigDecimal:        ");
        System.out.println(sumForwardList(ValuesOfCountNextList(value))
                .setScale(64, RoundingMode.HALF_UP).toPlainString());

        //od tyłu

        System.out.print("Sum backward next BigDecimal:       ");
        System.out.println(sumBackwardList(ValuesOfCountNextList(value))
                .setScale(64, RoundingMode.HALF_UP).toPlainString());
        //double
        //od prozdu
        //((-(value*value))*(2*n+1))/(2*n+3);

        System.out.print("\nSum forward next Double:            ");
        System.out.println(sumForwardListDouble(ValuesOfCountNextListDouble(value)));

        //od tyłu

        System.out.print("Sum backward next Double:           ");
        System.out.println(sumBackwardListDouble(ValuesOfCountNextListDouble(value)));

    }

    public static void CountStatisticsOfArc(Double startValue,Double endValue,int samples){

        double libaryResult;
        double forwardClosed = 0;
        double backwardClosed = 0;
        double backwardClosedAverage = 0;
        double forwardClosedAverage = 0;
        Double step = (endValue-startValue)/samples;

        for(int i=0;i<samples;i++){

            startValue+=step;
            libaryResult  = Math.atan(startValue);
            ArrayList<Double> list = ValuesOfMcLourinListDouble(String.valueOf(startValue));
            double resultForward = sumForwardListDouble(list);
            double resultBackward = sumBackwardListDouble(list);
            double diffForward = Math.abs(libaryResult-resultForward);
            double diffBackward = Math.abs(libaryResult-resultBackward);
            backwardClosedAverage+=diffBackward;
            forwardClosedAverage+=diffForward;

            if(diffBackward<diffForward){
                backwardClosed++;
            }else{
                if(diffBackward>diffForward){
                    forwardClosed++;
                }
            }
            //System.out.println();
            //System.out.printf("%.17f\n",startValue);
            // System.out.println("libary result:   "+libaryResult);
            // System.out.println("forward result:  "+resultForward);
            //System.out.println("backward result: "+resultBackward);
            //System.out.printf("diff forward:    %.17f\n",diffForward);
            //System.out.printf("diff bacward:    %.17f\n",diffBackward);
        }
        System.out.println("backward is better in: "+backwardClosed/samples * 100+" %");
        System.out.printf("average diffrence: %.22f\n",backwardClosedAverage/samples);
        System.out.println("forward is better in: "+forwardClosed/samples* 100+" %");
        System.out.printf("average diffrence: %.22f\n",forwardClosedAverage/samples);

    }

    public static void main(String[] args){

        countOwnArctanValues("0.23456");

        //CountStatisticsOfArc(0.9,1.0,100000);

    }
}
