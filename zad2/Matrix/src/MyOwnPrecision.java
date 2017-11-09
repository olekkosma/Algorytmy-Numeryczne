//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//08.11.2017
//Algorytmy Numeryczne
//--------------------

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyOwnPrecision extends Number {
    BigInteger numerator;
    BigInteger denominator;
    String numberString;
    MathContext mc = new MathContext(40, RoundingMode.HALF_DOWN);

    public MyOwnPrecision(String numberString){
        this.numberString=numberString;
        convertion();
    }
    public MyOwnPrecision(BigInteger numerator, BigInteger denominator, String numberString){
        this.numerator=numerator;
        this.denominator = denominator;
        this.numberString = numberString;
    }
    public static MyOwnPrecision flip(MyOwnPrecision first){
        MyOwnPrecision toReturn = first.newInstance();
        BigInteger tmp = toReturn.getNumerator();
        toReturn.setNumerator(toReturn.getDenominator());
        toReturn.setDenominator(tmp);
        return toReturn;
    }

    public static MyOwnPrecision negate(MyOwnPrecision first){
        MyOwnPrecision toReturn1 = new MyOwnPrecision(first.getNumerator(),first.getDenominator(),first.getNumberString());
        if(toReturn1.numerator.doubleValue() < 0 && toReturn1.denominator.doubleValue()<0
        ||toReturn1.numerator.doubleValue() > 0 && toReturn1.denominator.doubleValue()>0 ){
            toReturn1.setDenominator(toReturn1.getDenominator().negate());
        }else{
            if(toReturn1.numerator.doubleValue()<0){
                toReturn1.setNumerator(toReturn1.getNumerator().negate());
            }else{
                toReturn1.setDenominator(toReturn1.getDenominator().negate());
            }
        }
        return toReturn1;
    }

    public void convertion(){

        BigDecimal bigDecimalValue = new BigDecimal(numberString);
        int digits = numberString.length() - 1 - numberString.indexOf('.');
        int i=0;
        denominator = new BigInteger(String.valueOf(1));
        while(i<digits){
            bigDecimalValue= bigDecimalValue.multiply(BigDecimal.valueOf(10));
            denominator = denominator.multiply(BigInteger.valueOf(10));
            i++;
        }
        String trimDecimal = String.valueOf(bigDecimalValue);
        trimDecimal = trimDecimal.substring(0,trimDecimal.indexOf('.'));
        numerator = new BigInteger(trimDecimal);

        fractureBigInteger();
    }

    public void absConvert(){
        numerator = numerator.abs();
        denominator = denominator.abs();
    }

    public void add(MyOwnPrecision second){
        this.numerator = this.numerator.multiply(second.denominator).add(this.denominator.multiply(second.numerator));
        this.denominator = this.denominator.multiply(second.denominator);
    }
    public void substract(MyOwnPrecision second){
        second = MyOwnPrecision.negate(second);
        this.numerator = this.numerator.multiply(second.denominator).add(this.denominator.multiply(second.numerator));
        this.denominator = this.denominator.multiply(second.denominator);
    }

    public static MyOwnPrecision add(MyOwnPrecision first, MyOwnPrecision second){
        MyOwnPrecision toReturn1 = new MyOwnPrecision(first.getNumerator(),first.getDenominator(),first.getNumberString());
        MyOwnPrecision toReturn2 = new MyOwnPrecision(second.getNumerator(),second.getDenominator(),second.getNumberString());

        toReturn1.numerator = toReturn1.numerator.multiply(toReturn2.denominator).add(toReturn1.denominator.multiply(toReturn2.numerator));
        toReturn1.denominator = toReturn1.denominator.multiply(toReturn2.denominator);
        return toReturn1;
    }

    public void multiply(MyOwnPrecision second){
        this.numerator = this.numerator.multiply(second.numerator);
        this.denominator = this.denominator.multiply(second.denominator);
        fractureBigInteger();
    }
    public static MyOwnPrecision multiply(MyOwnPrecision first, MyOwnPrecision second){
        MyOwnPrecision toReturn1 = new MyOwnPrecision(first.getNumerator(),first.getDenominator(),first.getNumberString());
        MyOwnPrecision toReturn2 = new MyOwnPrecision(second.getNumerator(),second.getDenominator(),second.getNumberString());

        toReturn1.numerator = toReturn1.numerator.multiply(toReturn2.numerator);
        toReturn1.denominator = toReturn1.denominator.multiply(toReturn2.denominator);
        toReturn1.fractureBigInteger();
        return toReturn1;
    }

    public void printAsFraction(){
        System.out.println(this.numerator + " / " + this.denominator);
    }

    public String printAsDecimal(){
        BigDecimal result = new BigDecimal(this.numerator.toString());
        result = result.divide(new BigDecimal(String.valueOf(denominator)),mc);
        //System.out.print(result);
        return result.toString();
    }

    public MyOwnPrecision newInstance(){
        return new MyOwnPrecision(this.numerator,this.denominator,this.numberString);
    }

    public Double returnDoubleFormat(){
        BigDecimal result = new BigDecimal(this.numerator.toString());
        result = result.divide(new BigDecimal(String.valueOf(denominator)),mc);
        return result.doubleValue();
    }

    public void fractureBigInteger(){
        BigInteger commonFactor = commonFactor(numerator.abs(),denominator.abs());
        this.numerator = numerator.divide(commonFactor);
        this.denominator = denominator.divide(commonFactor);

    }

    public BigInteger  commonFactor(BigInteger numerator, BigInteger denominator){
        if(denominator.equals(BigInteger.valueOf(0))){
            return numerator;
        }
        return  commonFactor(denominator,numerator.mod(denominator));
    }

    @Override
    public String toString() {
        return  this.printAsDecimal();
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public String getNumberString() {
        return numberString;
    }

    public void setNumerator(BigInteger numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(BigInteger denominator) {
        this.denominator = denominator;
    }

    public void setNumberString(String numberString) {
        this.numberString = numberString;
    }

}
