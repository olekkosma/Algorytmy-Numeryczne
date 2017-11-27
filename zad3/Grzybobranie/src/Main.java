import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> probability = new ArrayList<>();
        values.add(-2);
        values.add(-1);
        values.add(1);
        values.add(2);
        probability.add(3);
        probability.add(1);
        probability.add(2);
        probability.add(4);

        Cube cube = new Cube(values,probability);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(14);
        list.add(11);
        Board board = new Board(17,list);
        board.setCube(cube);
        Player gracz1 = new Player("Olek",3,board);
        Player gracz2 = new Player("Szymon",5,board);
        board.setPlayer1(gracz1);
        board.setPlayer2(gracz2);
        System.out.println(gracz1);
        System.out.println(gracz2);

        while(board.move()){

        }
    }
}
