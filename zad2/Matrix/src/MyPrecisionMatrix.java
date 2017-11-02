import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyPrecisionMatrix {
    private MyOwnPrecision[][] matrix;
    private int rows;
    private int columns;

    public MyPrecisionMatrix(int length){
        this.rows = length;
        this.columns = length;
        matrix = new MyOwnPrecision[rows][columns];
    }
    public MyPrecisionMatrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        matrix = new MyOwnPrecision[rows][columns];
    }

    public void add(MyPrecisionMatrix secondMatrix){

        if(!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)){
            return ;
        }

        for(int i= 0 ; i <columns;i++){
            for(int j= 0 ; j <columns;j++){

                this.matrix[i][j].add(secondMatrix.matrix[i][j]);
            }

        }
    }

    public MyPrecisionMatrix multiply(MyPrecisionMatrix secondMatrix){
        if(!(this.columns == secondMatrix.rows)){
            return null;
        }
        MyPrecisionMatrix tmp = new MyPrecisionMatrix(this.rows,secondMatrix.columns);
        tmp.fillWithZero();

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){

                for( int k= 0 ; k < secondMatrix.columns;k++){

                    MyOwnPrecision tmpMultiply = this.matrix[i][j].newInstance();
                    tmpMultiply.multiply(secondMatrix.matrix[j][k]);
                    tmp.matrix[i][k].add(tmpMultiply);

                }
            }
        }
        return tmp;
    }

    public void fillWithZero(){
        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
               matrix[i][j]= new MyOwnPrecision("0.0");
            }

        }
    }

    public MyPrecisionMatrix gaussBase(MyPrecisionMatrix vector){
        //TODO trzeba sprawdzic czy odpowiednie rozmiary są

        MyPrecisionMatrix finalMatrix = new MyPrecisionMatrix(this.rows,this.columns+1);
        MyPrecisionMatrix tmpMatrix = new MyPrecisionMatrix(this.rows,this.columns+1);

        for(int i=0;i<this.rows;i++){
            for(int j= 0 ; j<this.columns+1;j++){

                if(j == this.columns){
                    finalMatrix.matrix[i][j] = vector.matrix[i][0];
                    tmpMatrix.matrix[i][j] = vector.matrix[i][0];
                }else {
                    finalMatrix.matrix[i][j] = this.matrix[i][j];
                    tmpMatrix.matrix[i][j] = this.matrix[i][j];
                }
            }
        }

        int n=this.columns+1;
        for (int i = 0; i<n-2; i++){
            for (int j = i+1; j<n-1; j++){
                for (int k = 0; k<n; k++){

                    MyOwnPrecision tmp = MyOwnPrecision.flip(finalMatrix.matrix[i][i]);
                    tmp =  MyOwnPrecision.multiply(finalMatrix.matrix[j][i],tmp);
                    tmp = MyOwnPrecision.multiply(finalMatrix.matrix[i][k],tmp);
                    tmp = MyOwnPrecision.negate(tmp);
                    tmpMatrix.matrix[j][k]= MyOwnPrecision.add(finalMatrix.matrix[j][k],tmp);

                }
            }

            for(int ii=0;ii<this.rows;ii++){
                for(int jj= 0 ; jj<this.columns+1;jj++){

                    finalMatrix.matrix[ii][jj] = tmpMatrix.matrix[ii][jj];
                }
            }

        }
        return tmpMatrix;
    }
    public MyPrecisionMatrix countResultsFromGauss(){

        MyPrecisionMatrix vectorMatrix = new MyPrecisionMatrix(this.rows,1);

        for(int i=0;i<this.rows;i++){
            vectorMatrix.matrix[i][0] = this.matrix[i][this.columns-1];
        }

        MyOwnPrecision m, s;
        int n = this.rows;
        for(int i = n - 1 ;i>=0;i--){
            s = this.matrix[i][n];
            for(int j = n-1 ; j>= i + 1; j--){
                MyOwnPrecision tmp = MyOwnPrecision.multiply(this.matrix[i][j], vectorMatrix.matrix[j][0]);
                tmp = MyOwnPrecision.negate(tmp);
                s = MyOwnPrecision.add(s,tmp);
                MyOwnPrecision divider = MyOwnPrecision.flip(this.matrix[i][i]);
                vectorMatrix.matrix[i][0]= MyOwnPrecision.multiply(s,divider);
            }
        }


        return vectorMatrix;

    }


    public void loadValues(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values"+suffix+".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        int lineNumber = 0;
        rows =  Integer.parseInt(br.readLine());
        columns =  Integer.parseInt(br.readLine());

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                strLine = br.readLine();
                matrix[i][j] =  new MyOwnPrecision(strLine);
            }

        }
        br.close();
        fstream.close();
    }

    public void printMatrix(){
        System.out.println("Matrix's values");
        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                System.out.printf("%2.16f ",matrix[i][j].returnDoubleFormat());
            }
            System.out.println("");
        }
        System.out.println("-----------\n");
    }
}
