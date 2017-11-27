import java.util.ArrayList;
import java.util.List;

public class Board {

    private int fields;
    private List<Integer> mushrooms;

    public Board(int fields,ArrayList<Integer> mushrooms){
        this.fields = fields;
        this.mushrooms = mushrooms;
    }

    public boolean removeMushroomIfExists(int field){
        if(mushrooms.contains(field)){
            mushrooms.remove((Integer) field);
            return true;
        }
        return false;
    }

    public int getFields() {
        return fields;
    }

    public List<Integer> getMushrooms() {
        return mushrooms;
    }

    @Override
    public String toString() {
        return "Plansza ma "+fields+" pól, jest na niej "+mushrooms.size()+" grzybów.";
    }
}
