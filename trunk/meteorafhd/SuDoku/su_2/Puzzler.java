package su_2;
import static java.util.Arrays.*;
public class Puzzler{
    public static final int SIZE =9;
    private boolean[][] fixed  =new boolean[SIZE][SIZE];
    private int[][]     number =new int[SIZE][SIZE];
    public Puzzler(){
    }
    public Puzzler(int[][] p){
        setPuzzler(p);
    }
  
    public void setPuzzler(int[][] p){
        for(int i=0;i<SIZE;i++)
            for(int j=0;j<SIZE;j++){
                if(p[i][j] ==0){
                    fixed[i][j] =false;
                    number[i][j] =0;
                } else{
                    number[i][j] =p[i][j];
                    fixed[i][j] =true;
                }
            }
    }

    public void clear(){
        for(int n=0;n<SIZE;n++){
            fill(fixed[n],false);
            fill(number[n],0);
        }
        return;
    }

    public boolean isFixed(int i,int j){
        return fixed[i][j];
    }

    public int getNumber(int i,int j){
        return number[i][j];
    }

    public void setNumber(int i,int j,int num){
        if(num<0||num>9) throw new IllegalArgumentException("number is out of 0~9 :"+num);
        if(isFixed(i,j)) throw new IllegalStateException("puzzler("+i+","+j+") is fixed");
        number[i][j] =num;
    }
}
