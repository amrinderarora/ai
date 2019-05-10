//Wang Yue
//2019.May.9
//CSCI 4511 final, MDP grid world

public class GridWorld{
  int n, m; //size
  char[][] gridWorld; //contains W,S
  int[][] gridWorldValue; //contains only numerical value
  double gamma = 0.9; //discount rate

  public GridWorld (int n, int m, char[][] gridWorld,int[][] gridWorldValue){
    this.n = n;
    this.m = m;
    this.gridWorld = gridWorld;
    this.gridWorldValue = gridWorldValue;
  }

  //print grindworld
  public void print(){
    System.out.println("n "+this.n);
    System.out.println("m " +this.m);
    for (int i = 0; i<this.n; i++){
      for (int j = 0; j<this.m; j++){
        System.out.print(gridWorld[i][j]+" ");
      }
      System.out.println(" ");
    }
    System.out.println(" ");
    for (int i = 0; i<this.n; i++){
      for (int j = 0; j<this.m; j++){
        System.out.print(gridWorldValue[i][j]+" ");
      }
      System.out.println(" ");
    }
  }
}
