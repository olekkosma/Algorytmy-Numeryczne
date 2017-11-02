import java.io.*;

public class FloatMatrix {
    private Float[][] matrix;
    private int rows;
    private int columns;

    public FloatMatrix(int length){
        this.rows = length;
        this.columns = length;
        matrix = new Float[rows][columns];
    }
    public FloatMatrix(int rows,int columns){
        this.rows = rows;
        this.columns = columns;
        matrix = new Float[rows][columns];
    }

    public void add(FloatMatrix secondMatrix){

        if(!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows && secondMatrix.columns == this.rows)){
            return ;
        }

        for(int i= 0 ; i <columns;i++){
            for(int j= 0 ; j <columns;j++){

                this.matrix[i][j] = this.matrix[i][j] + secondMatrix.matrix[i][j];
            }

        }
    }
    public FloatMatrix multiply(FloatMatrix secondMatrix){
        if(!(this.columns == secondMatrix.rows)){
            return null;
        }
        FloatMatrix tmp = new FloatMatrix(this.rows,secondMatrix.columns);
        tmp.fillWithZero();

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){

                for( int k= 0 ; k < secondMatrix.columns;k++){

                    tmp.matrix[i][k] = tmp.matrix[i][k] + this.matrix[i][j] * secondMatrix.matrix[j][k];

                }
            }
        }
        return tmp;
    }
    public void fillWithZero(){
        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
               matrix[i][j]=0f;
            }

        }
    }

    public FloatMatrix gaussBase(FloatMatrix vector){
        //TODO trzeba sprawdzic czy odpowiednie rozmiary są

        FloatMatrix finalMatrix = new FloatMatrix(this.rows,this.columns+1);
        FloatMatrix tmpMatrix = new FloatMatrix(this.rows,this.columns+1);

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
                    tmpMatrix.matrix[j][k]=finalMatrix.matrix[j][k]-
                            (finalMatrix.matrix[i][k]*(finalMatrix.matrix[j][i]/finalMatrix.matrix[i][i]));
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

    public FloatMatrix countResultsFromGauss(){

        FloatMatrix vectorMatrix = new FloatMatrix(this.rows,1);

        for(int i=0;i<this.rows;i++){
            vectorMatrix.matrix[i][0] = this.matrix[i][this.columns-1];
        }

        Float m, s;
        int n = this.rows;
        for(int i = n - 1 ;i>=0;i--){
            s = this.matrix[i][n];
            for(int j = n-1 ; j>= i + 1; j--){
                s = s - this.matrix[i][j] * vectorMatrix.matrix[j][0];
                vectorMatrix.matrix[i][0]= s/this.matrix[i][i];
            }
        }


        return vectorMatrix;

    }
    public void copyMatrix(FloatMatrix secondMatrix){

        for(int ii=0;ii<this.rows;ii++){
            for(int jj= 0 ; jj<this.columns+1;jj++){

                this.matrix[ii][jj] = secondMatrix.matrix[ii][jj];
            }
        }
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
                matrix[i][j] =  Float.parseFloat(strLine);
            }

        }
        br.close();
        fstream.close();
    }

    public void printMatrix(){
        System.out.println("Matrix's values");
        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                System.out.printf("%2.8f ",matrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("-----------\n");
    }
}
