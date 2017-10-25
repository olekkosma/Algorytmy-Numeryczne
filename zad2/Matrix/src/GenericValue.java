public class GenericValue<T extends Number> {
    String value;
    final Class<T> classType;


    public GenericValue(Class<T> classType,String value){
        this.classType = classType;
        this.value = value;
    }

    public <T extends Number> T convertion(){

        if(classType.equals(Double.class)){
            return (T) Double.valueOf(value);
        }
        if(classType.equals(Float.class)){
            return (T) Float.valueOf(value);
        }
        return null;
    }


    public void printValue(){
        System.out.println(convertion());
    }
}
