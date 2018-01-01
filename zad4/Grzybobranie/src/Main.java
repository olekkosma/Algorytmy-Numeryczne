//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//17.12.2017
//Algorytmy Numeryczne
//--------------------

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static int counter = 1;
    public static int iterations = 750;
    public static double epsylon = 0.0000000001;

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
        double start;
        double elapsedTimeMillis;
        double timeTmp = 0;
        int rep = 1;

        System.out.println("loading input data...");
        Data data = new Data("input");
        System.out.println("Done");

        //----------------------generate equations to arrayList-----------------------

        System.out.println("generate equations...");
        ArrayList<Double> times = new ArrayList<>();
        ArrayList<State> allStates = new ArrayList<>();
        start = System.currentTimeMillis();
            EquationsGeneratorIterate eGenerator = new EquationsGeneratorIterate(allStates);
            eGenerator.generateMatrix(data);
        elapsedTimeMillis = System.currentTimeMillis() - start;

        timeTmp = elapsedTimeMillis / 1000.0;
        int size = allStates.size();
        times.add((double) size);
        times.add(timeTmp);
        System.out.println("size:" + size);
        System.out.println("Time: " + timeTmp);
        System.out.println("Done");

        //----------------------Counting player one percentage win--------------------
        //------------------------------------------------------GAUSS
        System.out.println("Counting Gauss...");
        start = System.currentTimeMillis();
        for (int i = 0; i < rep; i++) {
            Gauss matrix = new Gauss(size);
            Matrix vector = countMatrix(matrix, allStates, data);
            Matrix result = matrix.countMatrix(vector);
        }

        elapsedTimeMillis = System.currentTimeMillis() - start;
        elapsedTimeMillis = elapsedTimeMillis / rep;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: " + timeTmp);

        //------------------------------------------------------GAUSS SPARSE
        System.out.println("Counting GaussParse...");
        start = System.currentTimeMillis();
        for (int i = 0; i < rep; i++) {
            GaussParse matrix2 = new GaussParse(size);
            Matrix vector2 = countMatrix(matrix2, allStates, data);
            Matrix result2 = matrix2.countMatrix(vector2);
        }
        elapsedTimeMillis = System.currentTimeMillis() - start;
        elapsedTimeMillis = elapsedTimeMillis / rep;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: " + timeTmp);

        //------------------------------------------------------Seidel New
        System.out.println("Counting GaussSeidlNew...");

        start = System.currentTimeMillis();

        for (int i = 0; i < rep; i++) {
            GaussSeidlNew matrix6 = new GaussSeidlNew(size);
            Matrix vector6 = countMatrix(matrix6, allStates, data);
            Matrix result6 = matrix6.countMatrix(vector6);
            System.out.println(result6.matrix[0][0]);
        }
        elapsedTimeMillis = System.currentTimeMillis() - start;
        elapsedTimeMillis = elapsedTimeMillis / rep;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Time: " + timeTmp);

        Writer.writeToFileResults(times, "Result");
    }
}
