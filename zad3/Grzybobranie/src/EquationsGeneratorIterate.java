import java.util.ArrayList;

public class EquationsGeneratorIterate {

    ArrayList<State> allStates;
    int[] cubeValues;

    public EquationsGeneratorIterate(ArrayList<State> allStates) {
        this.allStates = allStates;
    }

    public void generateMatrix(Data data) {

        cubeValues = data.cubeValues.clone();
        State state1 = new State(1, data.playerOneField, data.playerTwoField, 0, 0, data.mushrooms.clone());
        state1.setIndex(0);
        allStates.add(state1);
        //System.out.println(state1);
        generate();

    }

    public boolean isStillUncountedEquation() {
        for (State state : allStates) {
            if (state.getReadStatus() == 0) {
                return true;
            }
        }
        return false;
    }

    public void generate() {

        int lowerBound = 0;
        int higherBound;
        while (isStillUncountedEquation()) {
            higherBound = allStates.size();

            for (int i = lowerBound; i < higherBound; i++) {
                State tmpState = allStates.get(i);

                if (tmpState.getMushOne() > (tmpState.getMushroomsCount()+tmpState.getMushTwo())) {
                    tmpState.setStatus(1);
                }else if(!(tmpState.getMushTwo() > (tmpState.getMushroomsCount()+tmpState.getMushOne()))){

                    if (tmpState.positionOne == 0) {
                        if(tmpState.getMushOne()>=tmpState.getMushTwo()) {
                            tmpState.setStatus(1);

                        }
                    } else if (!(tmpState.positionTwo == 0)) {

                        for (int j = 0; j < cubeValues.length; j++) {

                            int One = tmpState.getPositionOne();
                            int Two = tmpState.getPositionTwo();
                            if (tmpState.tour == 1) {
                                One += cubeValues[j];
                            } else if (tmpState.tour == 0) {
                                Two += cubeValues[j];
                            }
                            State tmp = new State(tmpState.reverseTour(),One, Two, tmpState.getMushOne(), tmpState.getMushTwo(), tmpState.getMushrooms().clone());
                            isContains(tmpState, tmp);
                        }
                    }else{
                        if(tmpState.getMushOne() > (tmpState.getMushTwo())){
                            tmpState.setStatus(1);
                        }
                    }
                }
                allStates.get(i).setReadStatus(1);
            }
            lowerBound = higherBound;
        }
    }

    private void isContains(State state, State tmp) {
        if (!allStates.contains(tmp)) {
            tmp.setIndex(allStates.size());
            allStates.add(tmp);
            state.addToEquation(tmp);
            //System.out.println(tmp);
        } else {
            int founded = allStates.indexOf(tmp);
            state.addToEquation(allStates.get(founded));
        }
    }
}
