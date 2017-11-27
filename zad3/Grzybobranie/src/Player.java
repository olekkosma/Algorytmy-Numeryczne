

public class Player {
    private int field;
    private int mushrooms;

    public Player(int field) {
        this.field = field;
    }


    public void Move(int move){
        this.field +=move;
    }


    public void addMushroom(){
        this.mushrooms++;
    }
    public boolean isFinish() {
        if (field == 0) return true;
        return false;
    }

    public int getField() {

        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public int getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(int mushrooms) {
        this.mushrooms = mushrooms;
    }

}
