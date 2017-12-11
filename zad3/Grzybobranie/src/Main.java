//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//27.11.2017
//Algorytmy Numeryczne
//--------------------

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static int counter = 1;
    public static int iterations = 50;

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

    public static double countMatrix(Matrix matrix1, ArrayList<State> allStates, Data data) throws IOException {
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
            }else if(state.getStatus()==1){
                matrix1.matrix[state.getIndex()][state.getIndex()] = 1.0;

            }

        }
        if (counter == 1) {
            Writer.writeToFile(matrix1, "matrix");
            Writer.writeToFile(vector1, "vector");
            //matrix1.printMatrix();
            //vector1.printMatrix();
        }
        Matrix resultMatrix1 = matrix1.countMatrix(vector1);
        if (counter == 1) {
            //resultMatrix1.printMatrix();
        }
        counter = 2;
        double chanceToWin1 = resultMatrix1.matrix[0][0];
        chanceToWin1 = Math.abs(chanceToWin1);
        return chanceToWin1;

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
        /*System.out.println("Counting Gauss...");
        start = System.currentTimeMillis();

        double resultGauss = countMatrix(new Gauss(size), allStates, data);

        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        results.add(resultGauss);
        System.out.println("Gauss:" + resultGauss);
*/
       /* System.out.println("Counting Parse Gauss...");
        start = System.currentTimeMillis();

        double resultGaussParse = countMatrix(new GaussParse(size), allStates, data);

        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        times.add(timeTmp);
        System.out.println("Gauss Parse:" + resultGaussParse);
*/
        System.out.println("Done\nCounting Gauss Siedl...");
        start = System.currentTimeMillis();

        double resultGaussSiedl = countMatrix(new GaussSeidl(size), allStates, data);

        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        results.add(resultGaussSiedl);
        times.add(timeTmp);

        System.out.println("Gauss Siedl:" + resultGaussSiedl);
        System.out.println("Done\nCounting Jacobie...");
        start = System.currentTimeMillis();

        double resultJacobie = countMatrix(new Jacob(size), allStates, data);

        elapsedTimeMillis = System.currentTimeMillis() - start;
        timeTmp = elapsedTimeMillis / 1000.0;
        results.add(resultJacobie);
        times.add(timeTmp);
        Writer.writeToFileResults(results,"Result");
        Writer.writeToFileResults(times,"Result");

        System.out.println("Jacobie:" + resultJacobie + "\nDone");




    }

}
