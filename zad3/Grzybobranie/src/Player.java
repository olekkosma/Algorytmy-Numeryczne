

public class Player {
    private int field;
    private int mushrooms;
    private Board board;


    public Player(int field,Board board) {
        this.field = field;
        this.board = board;
    }


    public boolean Move(int move){
        System.out.println("wylosowano: "+move);
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
        if(this.field==0){
            System.out.println("koniec");
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
       return "Gracz jest na polu "+field+" i posiada "+mushrooms+" grzybów.";
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
