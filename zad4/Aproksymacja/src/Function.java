public class Function {
    double[] factor;

    public Function(double[] factor) {
        this.factor = factor;
    }

    public double count(double value) {
        double result = 0.0;

        for (int i = 0; i < factor.length; i++) {
            result += factor[i] * Math.pow(value, i);
        }

        return result;
    }
}
