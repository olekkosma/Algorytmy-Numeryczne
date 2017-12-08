import java.util.ArrayList;

public class EquationsGeneratorIterate {
    Cube cube;
    ArrayList<State> allStates;

    public EquationsGeneratorIterate(Cube cube, ArrayList<State> allStates) {
        this.cube = cube;
        this.allStates = allStates;
    }

    public void generateMatrix(Player player1,Player player2){

        State state1 = new State(1, player1.getField(), player2.getField());
        state1.setIndex(0);
        allStates.add(state1);
        System.out.println(state1);
        generate();

    }
    public boolean isStillUncountedEquation(){
        for(State state : allStates){
            if(state.getStatus()==0){
                return true;
            }
        }
        return false;
    }
    public void generate() {

        int lowerBound = 0;
        int higherBound;
        while(isStillUncountedEquation()) {

            higherBound = allStates.size();

            for (int i = lowerBound; i < higherBound; i++) {
                System.out.println("lower: "+lowerBound+" higher: "+higherBound);


                if (allStates.get(i).positionOne == 0) {
                    allStates.get(i).addToEquation(new State(0, 0, 0));
                }else if (!(allStates.get(i).positionOne == 0 || allStates.get(i).positionTwo == 0)) {

                    for (int j = 0; j < cube.getValues().size(); j++) {
                        if (allStates.get(i).tour == 1) {
                            State tmp = new State(allStates.get(i).reverseTour(), allStates.get(i).getPositionOne() + cube.getValues().get(j), allStates.get(i).getPositionTwo());
                            isContains(allStates.get(i), tmp);

                        } else if (allStates.get(i).tour == 0) {
                            State tmp = new State(allStates.get(i).reverseTour(), allStates.get(i).getPositionOne(), allStates.get(i).getPositionTwo() + cube.getValues().get(j));
                            isContains(allStates.get(i), tmp);
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
