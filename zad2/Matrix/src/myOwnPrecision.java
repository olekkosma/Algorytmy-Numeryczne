import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class myOwnPrecision {
    BigInteger numerator;
    BigInteger denominator;
    String numberString;
    MathContext mc = new MathContext(512, RoundingMode.HALF_DOWN);

    public myOwnPrecision(String numberString){
        this.numberString=numberString;
        convertion();
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


    public void add(myOwnPrecision second){
        this.numerator = this.numerator.multiply(second.denominator).add(this.denominator.multiply(second.numerator));
        this.denominator = this.denominator.multiply(second.denominator);
    }

    public void multiply(myOwnPrecision second){
        this.numerator = this.numerator.multiply(second.numerator);
        this.denominator = this.denominator.multiply(second.denominator);
        fractureBigInteger();
    }

    public void printAsFraction(){
        System.out.println(this.numerator + " / " + this.denominator);
    }

    public void printAsDecimal(){
        BigDecimal result = new BigDecimal(this.numerator.toString());
        result = result.divide(new BigDecimal(String.valueOf(denominator)),mc);
        System.out.println(result);
    }

    public void fractureBigInteger(){
        BigInteger commonFactor = commonFactor(numerator,denominator);
        this.numerator = numerator.divide(commonFactor);
        this.denominator = denominator.divide(commonFactor);

    }
    public BigInteger  commonFactor(BigInteger numerator, BigInteger denominator){
        if(denominator.equals(BigInteger.valueOf(0))){
            return numerator;
        }
        return  commonFactor(denominator,numerator.mod(denominator));
    }
}
