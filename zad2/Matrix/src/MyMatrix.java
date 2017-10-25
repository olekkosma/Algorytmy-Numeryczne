import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MyMatrix<T extends Number> {
    T[][] matrix;
    int length;

    public MyMatrix(T[][] matrix){
        this.matrix = matrix;
        System.out.println(this.matrix.getClass());
    }
    public MyMatrix(int length){
        this.length = length;
        @SuppressWarnings("unchecked")
        T[][] tmp = (T[][]) new Number[length][length];
        this.matrix = tmp;
    }


    public void fillMatrix(){

    Random random = new Random();

            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {

               //     if(this instanceof  Double ) {
              //          matrix[i][j] = (T) (Number) (random.nextDouble() * 2);
              //      }
              //      if(this.matrix.getClass().isInstance(Float.class)) {
               //         matrix[i][j] = (T) (Number) (random.nextFloat() * 2);
               //     }
                }
            }
    }

    public void printMatrix(){
        for(int i= 0 ; i <length;i++){
            for(int j= 0 ; j <length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }

    }

    public void add(T[][] secondMatrix){

        double[][] result = new double[length][length];

        for(int i= 0 ; i <length;i++){
            for(int j= 0 ; j <length;j++) {

                // 3 opcje dla 3 klass

                result[i][j] = this.matrix[i][j].doubleValue() + secondMatrix[i][j].doubleValue();
            }
        }
    }
}
