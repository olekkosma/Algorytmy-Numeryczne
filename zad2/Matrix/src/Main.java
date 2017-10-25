import java.util.List;

public class Main {

    public static void main(String[] args){

        myOwnPrecision test = new myOwnPrecision("10.44456544");
        test.printAsDecimal();

       GenericValue liczba = new GenericValue(Double.class,"110.342354123123");
       liczba.printValue();
        GenericValue liczba2 = new GenericValue(Float.class,"110.34235421321321");
        liczba2.printValue();

    }
}
