import java.util.List;

public class Main {

    public static void main(String[] args){

        myOwnPrecision test = new myOwnPrecision("10.44456544");
        test.printAsDecimal();

        //test.add(new myOwnPrecision("12.4562"));
        //test.printAsDecimal();
        test.multiply(new myOwnPrecision("12.455764565462"));
        test.printAsDecimal();
        test.multiply(new myOwnPrecision("5.2234534534211"));
        test.printAsDecimal();
        test.multiply(new myOwnPrecision("128.12324534535"));
        test.printAsDecimal();
        test.multiply(new myOwnPrecision("8474.12856456821374"));
        test.printAsDecimal();
        test.multiply(new myOwnPrecision("1.39383012384845445643424"));
        test.printAsDecimal();

    }
}
