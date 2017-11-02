public class GenericValue<T extends Number> {
    T value;

    public GenericValue(T value){
        this.value = value;
    }

    public static Number convertion(Number value1){

        if(value1.getClass().equals(Double.class)){
            return  value1.doubleValue();
        }
        if(value1.getClass().equals(Float.class)){
            return  value1.floatValue();
        }
        return null;
    }



    public void printValue(){
        System.out.println(convertion(value));
    }
}
