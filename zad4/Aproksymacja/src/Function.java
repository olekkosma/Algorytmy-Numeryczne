//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//07.01.2018
//Algorytmy Numeryczne
//--------------------

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
