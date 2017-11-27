import java.util.ArrayList;
import java.util.List;

public class Board {

    private int fields;
    private List<Integer> mushrooms;
    private Player player1;
    private Player player2;
    private boolean player1Turn = true;
    private Cube cube;

    public Board(int fields, ArrayList<Integer> mushrooms) {
        this.fields = fields;
        this.mushrooms = mushrooms;
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

    public boolean move() {
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
            } else {
                if (player2.getMushrooms() > player1.getMushrooms()) {
                    System.out.println(player2.getName() + " wygrał");
                } else {
                    System.out.println(currentPlayer.getName() + " wygrał");
                }
            }
            return false;
        }

        return true;

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

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    @Override
    public String toString() {
        return "Plansza ma " + fields + " pól, jest na niej " + mushrooms.size() + " grzybów.";
    }
}
