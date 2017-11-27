

public class Player {
    private int field;
    private int mushrooms;
    private String name;
    private Board board;

    public Player() {
    }

    public void move(int move){
        if(this.field+move>=board.getFields()){
            this.field-=board.getFields();
        }else{
            if(this.field+move<0){
                this.field+=board.getFields();
            }
        }
        this.field +=move;
        if(board.removeMushroomIfExists(this.field)){
            this.addMushroom();
        }
    }


    @Override
    public String toString() {
       return getName()+" jest na polu "+field+" i posiada "+mushrooms+" grzybów.";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setField(int field) {
        while(field<0){
            field+=board.getFields();
        }
        this.field = field;
    }

    public int getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(int mushrooms) {
        this.mushrooms = mushrooms;
    }

}
