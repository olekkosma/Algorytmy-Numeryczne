//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//13.12.2017
//Algorytmy Numeryczne
//--------------------

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static int counter = 1;
    public static int iterations = 750;
    public static double epsylon = 0.0000000000000001;
    public static double epsylonNew = 0.000000000000001;

    public static void printEquations(ArrayList<State> allStates) {

        for (int i = 0; i < allStates.size(); i++) {
            System.out.println(allStates.get(i).printEquation());
        }
    }
    public static int countStates(ArrayList<State> equations, State state, Data data) {
        int counter = 0;
        int i = 0;
        for (State stateTmp : equations) {
            if (stateTmp.equals(state)) {
                counter = counter + data.cubeProbability[i];
            }
            i++;
        }

        return counter;
    }

    public static Matrix countMatrix(Matrix matrix1, ArrayList<State> allStates, Data data) throws IOException {
        int size = allStates.size();
        Matrix vector1 = new Matrix(size, 1);
        matrix1.fillWithZero();

        for (State state : allStates) {
            if (state.equation.size() == data.cubeSize) {
                for (State stateTmp : state.getEquation()) {
                    matrix1.matrix[state.getIndex()][stateTmp.getIndex()] = countStates(state.getEquation(), stateTmp, data) / (double) data.sumOfProbabilty;

                }
            }
        }
        for (State state : allStates) {
            vector1.matrix[state.getIndex()][0] = (double) state.getStatus();
            if (state.getStatus() == 0) {
                matrix1.matrix[state.getIndex()][state.getIndex()] = -1.0;
            } else if (state.getStatus() == 1) {
                matrix1.matrix[state.getIndex()][state.getIndex()] = 1.0;

            }

        }
        if (counter == 1) {
            Writer.writeToFile(matrix1, "matrix");
            Writer.writeToFile(vector1, "vector");
        }
        counter = 2;
        return vector1;
    }

    public static void main(String[] args) throws IOException {
        long start;
        long elapsedTimeMillis;
        double timeTmp = 0;

        System.out.println("loading input data...");
        Data data = new Data("input");
        System.out.println("Done");

        //----------------------generate equations to arrayList-----------------------

        System.out.println("generate equations...");
        ArrayList<State> allStates = new ArrayList<>();
        EquationsGeneratorIterate eGenerator = new EquationsGeneratorIterate(allStates);
        eGenerator.generateMatrix(data);
        //printEquations(allStates);
        System.out.println("Done");

        //----------------------Counting player one percentage win--------------------
        ArrayList<Double> times = new ArrayList<>();
        ArrayList<Double> results = new ArrayList<>();
        int size = allStates.size();
        //------------------------------------------------------GAUSS
       /* System.out.println("Counting Gauss...");
        Gauss matrix = new Gauss(size);
        Matrix vector = countMatrix(matrix, allStates, data);
        start = System.currentTimeMillis();
        Matrix result = matrix.countMatrix(vector);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: "+timeTmp);
        results.add(result.matrix[0][0]);

        //------------------------------------------------------GAUSS SPARSE
        System.out.println("Counting GaussParse...");
        GaussParse matrix2 = new GaussParse(size);
        Matrix vector2 = countMatrix(matrix2, allStates, data);
        start = System.currentTimeMillis();
        Matrix result2 = matrix2.countMatrix(vector2);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: "+timeTmp);
        results.add(result2.matrix[0][0]);

        //------------------------------------------------------Jacobie
        System.out.println("Counting Jacob...");
        Jacob matrix3 = new Jacob(size);
        Matrix vector3 = countMatrix(matrix3, allStates, data);
        start = System.currentTimeMillis();
        Matrix result3 = matrix3.countMatrix(vector3);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: "+timeTmp);
        results.add(result3.matrix[0][0]);
*/
        //------------------------------------------------------Jacobie New
        System.out.println("Counting JacobNew...");
        JacobNew matrix4 = new JacobNew(size);
        Matrix vector4 = countMatrix(matrix4, allStates, data);
        start = System.currentTimeMillis();
        Matrix result4 = matrix4.countMatrix(vector4);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: "+timeTmp);
        System.out.println("Result:"+result4.matrix[0][0]);
        results.add(result4.matrix[0][0]);
/*
        //------------------------------------------------------Seidel
        System.out.println("Counting GaussSeidl...");
        GaussSeidl matrix5 = new GaussSeidl(size);
        Matrix vector5 = countMatrix(matrix5, allStates, data);
        start = System.currentTimeMillis();
        Matrix result5 = matrix5.countMatrix(vector5);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: "+timeTmp);
        results.add(result5.matrix[0][0]);
*/
        //------------------------------------------------------Seidel New
        System.out.println("Counting GaussSeidlNew...");
        GaussSeidlNew matrix6 = new GaussSeidlNew(size);
        Matrix vector6 = countMatrix(matrix6, allStates, data);
        start = System.currentTimeMillis();
        Matrix result6 = matrix6.countMatrix(vector6);
        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: "+timeTmp);
        System.out.println("Result:"+result6.matrix[0][0]);
        results.add(result6.matrix[0][0]);

        Writer.writeToFileResults(results,"Result");
        Writer.writeToFileResults(times,"Result");
    }
}
