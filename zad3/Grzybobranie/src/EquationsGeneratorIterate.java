import java.util.ArrayList;

public class EquationsGeneratorIterate {
    Cube cube;
    ArrayList<State> allStates;

    public EquationsGeneratorIterate(Cube cube, ArrayList<State> allStates) {
        this.cube = cube;
        this.allStates = allStates;
    }

    public void generateMatrix(Player player1, Player player2, Board board) {

        int[] mushrooms = new int[board.getFields()];

        System.out.println(board.getMushrooms());
        for (int i = 0; i < mushrooms.length; i++) {
            if (board.getMushrooms().contains(i)) {
                mushrooms[i] = 1;
            } else {
                mushrooms[i] = 0;
            }
        }

            //State state1 = new State(1, player1.getField(), player2.getField(),0,0);
            State state1 = new State(1, player1.getField(), player2.getField(),0,0,mushrooms);
            state1.setIndex(0);
            allStates.add(state1);
            System.out.println(state1);
            generate();

        }

    public boolean isStillUncountedEquation() {
        for (State state : allStates) {
            if (state.getStatus() == 0) {
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
                State tmpMainState = allStates.get(i);




                if (tmpMainState.getMushOne() > (tmpMainState.getMushroomsCount()+tmpMainState.getMushTwo())) {
                    tmpMainState.addToEquation(new State(2, 0, 0, 0, 0, null));
                }else if(!(tmpMainState.getMushTwo() > (tmpMainState.getMushroomsCount()+tmpMainState.getMushOne()))){

                if (tmpMainState.positionOne == 0) {
                    if(tmpMainState.getMushOne()>=tmpMainState.getMushTwo()) {
                        tmpMainState.addToEquation(new State(2, 0, 0, 0, 0, null));
                    }
                } else if (!(tmpMainState.positionTwo == 0)) {

                    for (int j = 0; j < cube.getValues().size(); j++) {
                        if (tmpMainState.tour == 1) {
                            State tmp = new State(tmpMainState.reverseTour(), tmpMainState.getPositionOne() + cube.getValues().get(j), tmpMainState.getPositionTwo(), tmpMainState.getMushOne(), tmpMainState.getMushTwo(), tmpMainState.getMushrooms().clone());
                            isContains(tmpMainState, tmp);

                        } else if (tmpMainState.tour == 0) {
                            State tmp = new State(tmpMainState.reverseTour(), tmpMainState.getPositionOne(), tmpMainState.getPositionTwo() + cube.getValues().get(j), tmpMainState.getMushOne(), tmpMainState.getMushTwo(), tmpMainState.getMushrooms().clone());
                            isContains(tmpMainState, tmp);
                        }
                    }
                }else{
                    if(tmpMainState.getMushOne() > ((tmpMainState.getMushroomsCount()+tmpMainState.getMushTwo()))){
                        tmpMainState.addToEquation(new State(2, 0, 0, 0, 0, null));
                    }
                }
            }
                allStates.get(i).setStatus(1);
            }
            lowerBound = higherBound;
        }
    }

    private void isContains(State state, State tmp) {
        if (!allStates.contains(tmp)) {
            tmp.setIndex(allStates.size());
            allStates.add(tmp);
            state.addToEquation(tmp);
            System.out.println(tmp);
        } else {
            int founded = allStates.indexOf(tmp);
            state.addToEquation(allStates.get(founded));
        }
    }
}
