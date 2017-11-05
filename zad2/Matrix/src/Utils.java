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

    public static FloatMatrix copyMatrix(FloatMatrix firstMatrix, FloatMatrix secondMatrix) {

        for (int ii = 0; ii < firstMatrix.getRows(); ii++) {
            for (int jj = 0; jj < firstMatrix.getColumns(); jj++) {

                firstMatrix.getMatrix()[ii][jj] = secondMatrix.getMatrix()[ii][jj];
            }
        }
        return firstMatrix;
    }
}
