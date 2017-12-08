import java.util.ArrayList;

public class EquationsGenerator {
    Cube cube;
    ArrayList<State> allStates;

    public EquationsGenerator(Cube cube, ArrayList<State> allStates) {
        this.cube = cube;
        this.allStates = allStates;
    }

    public void generate(State state) {

        if (state.positionOne == 0) {
            state.addToEquation(new State(0, 0, 0));
        } else if (state.positionTwo == 0) {
            state.addToEquation(new State(0, 0, 0));
            state.addToEquation(new State(0, 0, 0));
            state.addToEquation(new State(0, 0, 0));
        } else {
            if (!(state.positionOne == 0 || state.positionTwo == 0)) {
                ArrayList<State> tmpList = new ArrayList<>();
                for (int i = 0; i < cube.getValues().size(); i++) {
                    if (state.tour == 1) {
                        State tmp = new State(state.reverseTour(), state.getPositionOne() + cube.getValues().get(i), state.getPositionTwo());
                        isContains(state, tmp);

                    } else if (state.tour == 0) {
                        State tmp = new State(state.reverseTour(), state.getPositionOne(), state.getPositionTwo() + cube.getValues().get(i));
                        isContains(state, tmp);
                    }
                }
            }
        }
    }

    private void isContains(State state, State tmp) {
        if (!allStates.contains(tmp)) {
            tmp.setIndex(allStates.size());
            allStates.add(tmp);
            state.addToEquation(tmp);
            System.out.println(tmp);
            generate(tmp);
        } else {
            int founded = allStates.indexOf(tmp);
            state.addToEquation(allStates.get(founded));
        }
    }
}
