//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//13.12.2017
//Algorytmy Numeryczne
//--------------------

import java.util.ArrayList;
import java.util.Arrays;

public class State {
    public static int size;
    int index;
    int tour;
    int positionOne;
    int positionTwo;
    int mushOne;
    int mushTwo;
    int[] mushrooms;
    ArrayList<State> equation;
    int status = 0;
    int readStatus = 0;

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public State(int tour, int positionOne, int positionTwo, int mushOne, int mushTwo, int[] mushrooms) {
        this.tour = tour;
        this.positionOne = Math.floorMod(positionOne, size);
        this.positionTwo = Math.floorMod(positionTwo, size);
        this.mushOne = mushOne;
        this.mushTwo = mushTwo;
        if (this.tour == 0) {
            if (mushrooms[this.positionOne] == 1) {
                this.mushOne++;
                mushrooms[this.positionOne] = 0;
            }
        } else if (this.tour == 1) {
            if (mushrooms[this.positionTwo] == 1) {
                this.mushTwo++;
                mushrooms[this.positionTwo] = 0;
            }
        }
        this.mushrooms = mushrooms;
        equation = new ArrayList<>();
    }


    public int getMushOne() {
        return mushOne;
    }

    public int[] getMushrooms() {
        return mushrooms;
    }

    public int getMushroomsCount() {
        int counter = 0;
        for (int i = 0; i < mushrooms.length; i++) {
            counter += mushrooms[i];
        }
        return counter;
    }

    public int getMushTwo() {
        return mushTwo;
    }

    public int reverseTour() {
        if (tour == 1) {
            return 0;
        }
        return 1;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getTour() {
        return tour;
    }

    public int getPositionOne() {
        return positionOne;
    }

    public int getPositionTwo() {
        return positionTwo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void addToEquation(State newState) {
        equation.add(newState);
    }

    public String printEquation() {
        String line = "X" + index + " = ";
        if (equation.size() == 1) {
            return line + "1";
        } else {
            if (!equation.isEmpty()) {
                for (State state : equation) {
                    line += "X" + state.index + " ";
                }
            } else {
                line += "0";
            }
            return line;
        }
    }

    public ArrayList<State> getEquation() {
        return equation;
    }


    @Override
    public String toString() {

        return "State{" +
                "index=" + index +
                ", tour=" + tour +
                ", positionOne=" + positionOne +
                ", positionTwo=" + positionTwo +
                ", mushOne=" + mushOne +
                ", mushTwo=" + mushTwo +
                ", mushrooms=" + Arrays.toString(mushrooms) +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (tour != state.tour) return false;
        if (positionOne != state.positionOne) return false;
        if (positionTwo != state.positionTwo) return false;
        if (mushOne != state.mushOne) return false;
        if (mushTwo != state.mushTwo) return false;
        return Arrays.equals(mushrooms, state.mushrooms);
    }

    @Override
    public int hashCode() {
        int result = tour;
        result = 31 * result + positionOne;
        result = 31 * result + positionTwo;
        result = 31 * result + mushOne;
        result = 31 * result + mushTwo;
        result = 31 * result + Arrays.hashCode(mushrooms);
        return result;
    }
}
