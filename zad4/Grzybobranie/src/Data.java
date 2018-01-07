//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//07.01.2018
//Algorytmy Numeryczne
//--------------------


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Data {
    public int size;
    public int mushroomsSize;
    public int[] mushrooms;
    public int playerOneField;
    public int playerTwoField;
    public int cubeSize;
    public int[] cubeValues;
    public int[] cubeProbability;
    public int sumOfProbabilty;

    public Data(String fileName) throws FileNotFoundException {

        File file = new File("..\\" + fileName + ".txt");
        //File file = new File("..\\..\\"+fileName + ".txt");
        Scanner scanner = new Scanner(file);

        this.size = ((scanner.nextInt() * 2) + 1);
        State.size = this.size;
        this.mushroomsSize = scanner.nextInt();
        mushrooms = new int[size];

        for(int i=0;i<this.size;i++){
            mushrooms[i] = 0;
        }
        for (int i = 0; i < this.mushroomsSize; i++) {
            int mush = scanner.nextInt();
            mush = Math.floorMod(mush, this.size);
            mushrooms[mush] = 1;
        }

        this.playerOneField = scanner.nextInt();
        this.playerTwoField = scanner.nextInt();
        this.cubeSize = scanner.nextInt();
        cubeValues = new int[cubeSize];
        cubeProbability = new int[cubeSize];

        for (int i = 0; i < cubeSize; i++) {
            cubeValues[i] = scanner.nextInt();
        }

        sumOfProbabilty = 0;
        for (int i = 0; i < cubeSize; i++) {
            int tmp = scanner.nextInt();
            cubeProbability[i] = tmp;
            sumOfProbabilty+=tmp;
        }
        scanner.close();
    }
}
