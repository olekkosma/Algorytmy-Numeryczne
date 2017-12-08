import java.util.ArrayList;

public class State {
    public static int size = 11;
    int index;
    int tour;
    int positionOne;
    int positionTwo;
    ArrayList<State> equation;
    int status = -1;

    public State(int tour, int positionOne, int positionTwo) {
        this.tour = tour;
        this.positionOne = positionOne % size;
        this.positionTwo = positionTwo % size;
        equation = new ArrayList<>();
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

    public void checkStatus() {
        if (positionOne == 0) {
            status = 1;
        }
        if (positionTwo == 0) {
            status = 0;
        }
    }

    public void addToEquation(State newState) {
        equation.add(newState);
    }

    public String printEquation() {
        String line = "X" + index + " = ";
        if (equation.size() == 1) {
            return line +"1";
        } else if (equation.size() == 3) {
            return line + "0";
        } else {
            for (State state : equation) {
                line += "X" + state.index + " ";
            }
            return line;
        }
    }

    public ArrayList<State> getEquation() {
        return equation;
    }

    @Override
    public String toString() {
        if (equation.size() == 1) {
            return "1";
        } else if (equation.size() == 3) {
            return "0";
        } else {
            return "State{" +
                    "index=" + index +
                    ", tour=" + tour +
                    ", positionOne=" + positionOne +
                    ", positionTwo=" + positionTwo +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (tour != state.tour) return false;
        if (positionOne != state.positionOne) return false;
        return positionTwo == state.positionTwo;
    }

    @Override
    public int hashCode() {
        int result = tour;
        result = 31 * result + positionOne;
        result = 31 * result + positionTwo;
        return result;
    }
}
