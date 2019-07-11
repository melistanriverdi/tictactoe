import com.sun.org.apache.bcel.internal.classfile.Utility;

import java.util.*;

public class Main {

    public static int[][] initializeTheBoard(){
        int[][] board = new int[][]{
                { 0, 0, 0},
                { 0, 0, 0},
                { 0, 0, 0}};
        return board;

    }

    public static int winnerDecider(int[][] board){
        //returning 2 means 2 has won, returning 1 means 1 has won, returning 0 means draw, -1 means not complete

        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        map.put(8,false); //means 2=X has won
        map.put(4,false); //draw
        map.put(2,false); //draw
        map.put(1,false); //means 1=O has won
        map.put(0,false); //means there are still empty rows

        int mult1=1;
        int mult2=1;

        //adding multiplications by rows and columns
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                mult1*=board[i][j];
                mult2*=board[j][i];
            }

            map.put(mult1, true);
            map.put(mult2, true);
            mult1=1;
            mult2=1;

        }

        //adding the diagonals
        map.put(board[0][0]*board[1][1]*board[2][2], true);
        map.put(board[2][0]*board[1][1]*board[0][2], true);

        if(map.get(8)){return 2;}
        else if(map.get(1)){return 1;}
        else if(map.get(0)){return -1;}
        else{return 0;}
    }

//this func should be called after the opponent plays at least twice.
    public static int[][] blockOrWin(int[][] board, int winOrBlock){
        //if winOrBlock is 2, then its a win search and I am searching for 2's which are my numbers, otherwise searching for 1's to block.
        //search for columns
        for(int i=0; i<3; i++){
            if(board[i][0]==winOrBlock || board[i][0]==0){
                if(board[i][0]==winOrBlock && board[i][1]==winOrBlock && board[i][2]==0){
                    board[i][2]=2;
                    return board;
                    }
                else if(board[i][0]==winOrBlock && board[i][1]==0 && board[i][2]==winOrBlock){
                    board[i][1]=2;
                    return board;
                }
                else if(board[i][0]==0 && board[i][1]==winOrBlock && board[i][2]==winOrBlock){
                    board[i][0]=2;
                    return board;
                }
            }
        }
        //search for rows
        for(int i=0; i<3; i++){
            if(board[0][i]==winOrBlock || board[0][i]==0){
                if(board[0][i]==winOrBlock && board[1][i]==winOrBlock && board[2][i]==0){
                    board[2][i]=2;
                    return board;
                }
                else if(board[0][i]==winOrBlock && board[1][i]==0 && board[2][i]==winOrBlock){
                    board[1][i]=2;
                    return board;
                }
                else if(board[0][i]==0 && board[1][i]==winOrBlock && board[2][i]==winOrBlock){
                    board[0][i]=2;
                    return board;
                }
            }
        }

        //search for diagonals
        if(!(board[1][1]==winOrBlock || board[1][1]==0)){
            return board;
        }
        else {
            if(board[0][0]==winOrBlock && board[1][1]==winOrBlock && board[2][2]==0){
                board[2][2]=2;
                return board;
            }
            else if(board[0][0]==0 && board[1][1]==winOrBlock && board[2][2]==winOrBlock){
                board[0][0]=2;
                return board;
            }
            else if(board[0][0]==winOrBlock && board[1][1]==0 && board[2][2]==winOrBlock){
                board[1][1]=2;
                return board;
            }

            else if(board[2][0]==winOrBlock && board[1][1]==0 && board[0][2]==winOrBlock){
                board[1][1]=2;
                return board;
            }
            else if(board[2][0]==winOrBlock && board[1][1]==winOrBlock && board[0][2]==0){
                board[0][2]=2;
                return board;
            }
            else if(board[2][0]==0 && board[1][1]==winOrBlock && board[0][2]==winOrBlock){
                board[2][0]=2;
                return board;
            }
        }
        return board;
    }

    public static int [][] updateBoardState(int [][]board, int i, int j, int playersNumberCorrespondance){
      if(i>3 || i<1 || j>3 || j<1){
          System.out.println("Invalid grid values i: "+i+ " j:"+j);
      }
      else if(board[i-1][j-1]!=0){
          System.out.println("You can only enter values into squares are empy!");
      }
      else{
          if(board[i-1][j-1]!=0){
              System.out.println("You cannot change the values of the grids, you can only play the empty fields, the given grid number is invalid!");
          }
          else{
               board[i-1][j-1]=playersNumberCorrespondance;
              }
      }
        return board;

    }

    public static boolean isEqual(int [][]board1, int [][]board2){

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board1[i][j]!=board2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] arrayCopy(int[][]board){
        int [][]copy = new int[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                copy[i][j]=board[i][j];
            }
        }
        return copy;
    }



    public static void tests(){
//        adding boards for testing winnerDecider function:
        int[][] board1 = new int[][]{
                { 0, 1, 2},
                { 2, 0, 1},
                { 0, 1, 2}
        };
        int[][] board2 = new int[][]{
                { 1, 2, 1},
                { 2, 1, 1},
                { 2, 1, 2}
        };
        int[][] board3 = new int[][]{
                { 2, 2, 1},
                { 2, 1, 1},
                { 2, 1, 2}
        };
        int[][] board4 = new int[][]{
                { 1, 1, 1},
                { 2, 0, 1},
                { 0, 1, 2}
        };
        int[][] boardBlock = new int[][]{
                { 1, 1, 0},
                { 2, 2, 1},
                { 0, 1, 2}
        };
        int[][] boardBlocked = new int[][]{
                { 1, 1, 2},
                { 2, 2, 1},
                { 0, 1, 2}
        };
        int[][] boardBlock2 = new int[][]{
                { 1, 0, 2},
                { 2, 0, 1},
                { 1, 1, 0}
        };
        int[][] boardBlocked2 = new int[][]{
                { 1, 0, 2},
                { 2, 0, 1},
                { 1, 1, 2}
        };
        int[][] boardWin = new int[][]{
                { 1, 0, 0},
                { 2, 0, 2},
                { 0, 1, 2}
        };
        int[][] boardWon = new int[][]{
                { 1, 0, 0},
                { 2, 2, 2},
                { 0, 1, 2}
        };


        if(winnerDecider(board1)==-1){System.out.println("uncompleted case tested");}
        if(winnerDecider(board2)==0){System.out.println("draw tested");}
        if(winnerDecider(board3)==2){System.out.println("winner 2 tested");}
        if(winnerDecider(board4)==1){System.out.println("winner 1 tested");}

        printGameState(boardWon);
        printGameState(blockOrWin(boardWin, 2));
        if(isEqual(blockOrWin(boardWin, 2) , boardWon))
        {
            System.out.println("Successful!");
        }
        else
        {
            System.out.println("NOT Successful!");
        }

       if(boardWon == blockOrWin(boardWin, 2)){

           System.out.println("WINS successfully! BEFORE:");
           printGameState(boardBlock);
           System.out.println("AFTER:");
           printGameState(blockOrWin(boardBlock, 1));
       }
       else{
           printGameState(blockOrWin(boardBlock, 1));
           System.out.println("cannot WIN successfully!");
       }

    }

    public static void printGameState(int [][] board){
        System.out.println();
        System.out.println("*** THE CURRENT BOARD STATE ***");
        System.out.println();
        System.out.println("   1    2    3 ");
        System.out.println("    _ _ _ _ _ _");
        for(int i=0; i<3; i++){
            System.out.print(i+1+"  |");
            for(int j=0; j<3; j++){
                if(board[i][j]==2){
                    System.out.print(" X |");
                }
                else if(board[i][j]==1){
                    System.out.print(" O |");
                }
                else{System.out.print("   |");}
            }
            System.out.println();
            System.out.print("    _ _ _ _ _ _");
            System.out.println();
        }

        System.out.println();
    }





    public static void main(String[] args) {
//
//        System.out.println("Welcome to TicTacToe!");
//        System.out.println("The first player is computer, you can play first in the next round.");
//        System.out.println("To make your move, you can state the cell name you want to go by writing the coordination of the cell that you see in the board state. Please enter the coordinations with a dash.");
//        System.out.println("For example: the top left corner has the coordinations of row 1 and column 1, therefore you should input 1-1 (for middle cell 2-2 and so on...)");
//        System.out.println("You cannot change a cell that is already filled before, you can only make your moves to empty cells.");
//        System.out.println("After each move of any of the players, you can see the updated state of the board");
//        System.out.println("Now press p if you want to start the game");

        tests();

    }
}
