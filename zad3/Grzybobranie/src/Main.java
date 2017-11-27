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
        list.add(0);
        list.add(2);
        list.add(4);
        list.add(6);
        Board board = new Board(7,list);
        Player gracz1 = new Player(3,board);
        System.out.println(gracz1);
        int kolejki = 0;
        while(!gracz1.Move(cube.nextRandomMove())){
            kolejki++;
            System.out.println(gracz1);
        }
        System.out.println(++kolejki);
    }
}
