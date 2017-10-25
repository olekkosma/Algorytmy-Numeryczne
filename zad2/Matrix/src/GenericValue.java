public class GenericValue<T extends Number> {
    T value;
    final Class<T> classType;

    public GenericValue(Class<T> classType,T value){
        this.classType = classType;
        this.value = value;
    }
    public void printValue(){
        System.out.println(this.value);
    }
}
