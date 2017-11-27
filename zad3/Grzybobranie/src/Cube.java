import java.util.List;
import java.util.Map;
import java.util.Random;

public class Cube {

    private List<Integer> values;
    private List<Integer> probability;
    private int sumOfProbability;
    private Random random;
    public Cube(List<Integer> values, List<Integer> probability) {
        this.probability = probability;
        random = new Random();
        this.values = values;
        countSum();
    }
    public void countSum(){
        for(Integer value : probability){
            sumOfProbability+=value;
        }
    }
    public int nextRandomMove(){
        int generatedValue = Math.abs(random.nextInt())%sumOfProbability;
        int lowerBound = 0;
        int higherBound = probability.get(0);
        for(int i = 0 ;i <probability.size();i++){
            if(generatedValue>=lowerBound && generatedValue<higherBound){
                return values.get(i);
            }
            lowerBound +=probability.get(i);
            higherBound+=probability.get(i+1);

        }
        return 0;
    }
}
