import java.util.ArrayList;
import java.util.List;

public class Board {

    private int fields;
    private List<Integer> mushrooms;
    private Player player1;
    private Player player2;
    private boolean player1Turn = true;
    private Cube cube;

    public Board() {
    }

    public boolean removeMushroomIfExists(int field) {
        if (mushrooms.contains(field)) {
            mushrooms.remove((Integer) field);
            return true;
        }
        return false;
    }

    public int getFields() {
        return fields;
    }

    public int move() {
        int number = cube.nextRandomMove();
        Player currentPlayer;

        if (player1Turn) {
            currentPlayer = player1;
            player1Turn=false;
        } else {
            currentPlayer = player2;
            player1Turn=true;
        }
        System.out.println("wylosowano: "+number);
        currentPlayer.move(number);
        System.out.println(currentPlayer);
        if (currentPlayer.getField() == 0) {
            if (player1.getMushrooms() > player2.getMushrooms()) {
                System.out.println(player1.getName() + " wygrał");
                return 1;
            } else {
                if (player2.getMushrooms() > player1.getMushrooms()) {
                    System.out.println(player2.getName() + " wygrał");
                    return 2;
                } else {
                    System.out.println(currentPlayer.getName() + " wygrał");
                    if(currentPlayer.equals(player1)){
                        return 1;
                    }else{
                        return 2;
                    }
                }
            }
        }

        return 0;

    }

    public void setFields(int fields) {
        this.fields = fields;
    }

    public List<Integer> getMushrooms() {
        return mushrooms;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setMushrooms(List<Integer> mushrooms) {
        this.mushrooms = mushrooms;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    @Override
    public String toString() {
        return "Plansza ma " + fields + " pól, jest na niej " + mushrooms.size() + " grzybów.";
    }
}
