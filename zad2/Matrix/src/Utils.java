public class Utils {

    public static Number addNumbers(Number one, Number two){
        if( one instanceof Double && two instanceof  Double){
            return new Float(one.doubleValue() + two.doubleValue());
        }
        if( one instanceof Float && two instanceof  Float){
            return new Float(one.floatValue() + two.floatValue());
        }
        return null;
    }
}
