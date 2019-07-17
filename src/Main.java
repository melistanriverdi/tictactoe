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

    public static int [][] updateBoardState(int [][]board, int row, int column, int playersNumberCorrespondance){
      if(row>3 || row<1 || column>3 || column<1){
          System.out.println("Invalid grid values i: "+row+ " j:"+column);
      }
      else {
          int row_index = row - 1;
          if(board[row_index][column-1]!=0){
              System.out.println("You can only enter values into squares are empy!");
          }
          else{
              if(board[row_index][column-1]!=0){
                  System.out.println("You cannot change the values of the grids, you can only play the empty fields, the given grid number is invalid!");
              }
              else{
                   board[row_index][column-1]=playersNumberCorrespondance;
                  }
          }
      }
        return board;

    }

    public static void randomMove(int [][] board){
        ArrayList<Integer>  emptyPlaces = new ArrayList<Integer>();
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board[i][j]==0){
                    int num=i*10+j;
                    emptyPlaces.add(num);
                }
            }
        }

        Random random = new Random();
        int indexes = random.nextInt(emptyPlaces.size());
        int num = emptyPlaces.get(indexes);
        int i=num/10 +1;
        int j=num%10 +1;
        updateBoardState(board, i,j, 2 );
    }

    public static int [][] winningStrategy(int [][] board, int iteration){
        //cases that computer begins first

        if(iteration>2){
            int [][] firstVersion = arrayCopy(board);
            board = blockOrWin(board,2);

            if(!isEqual(firstVersion,board)){
                //nextRound user plays.
                return board;
            }
            board = blockOrWin(board, 1);
            if(!isEqual(firstVersion,board)){
                return board;
            }
        }

        if (iteration==0){
            return updateBoardState(board, 1,1, 2);
        }
        if(iteration==2){
            if(board[1][1]==1){
                return updateBoardState(board, 3,3, 2);
            }
            else{
                if(board[1][0]==0 && board[2][0]==0){
                    return updateBoardState(board, 3,1, 2);
                }
                else if(board[0][2]==0 && board[1][1]==0){
                    return updateBoardState(board, 2,2, 2);
                }
            }
        }
        if(iteration==4){//trap turn
            if(board[1][1]==1){
                if(board[2][0]==0){return updateBoardState(board, 3,1, 2);}
                else if(board[0][2]==0){
                    return updateBoardState(board, 1,3, 2);
                }
            }
            else{
                if(board[2][0]==1 && board[0][2]==0){
                    return updateBoardState(board, 1,3, 2);
                }
                else if(board[2][0]==0 && board[0][2]==1){
                    return updateBoardState(board, 3,1, 2);
                }
                else{randomMove(board);}
            }
        }
        //cases that user begins first;
        if(iteration==1){
            if(board[0][0]==1 || board[2][2]==1 || board[2][0]==1 || board[0][2]==1){
                return updateBoardState(board, 2,2, 2);
            }
            else if(board[1][1]==1){
                return updateBoardState(board, 1,1, 2);
            }
        }
        if(iteration==3){
            if(board[1][1]==2){
                if((board[0][0]==1 && board[2][2]==1) || (board[0][2]==1 && board[2][0]==1)){
                    return updateBoardState(board, 1,2, 2);
                }
            }
            else if(board[0][0]==1 && board[2][2]==1){
                return updateBoardState(board, 3,1, 2);
            }
            else{randomMove(board);}
        }

        randomMove(board);
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

    public static void testWinnerDecider(){
        int[][] boardWonX = new int[][]{
                { 1, 0, 0},
                { 2, 2, 2},
                { 0, 1, 2}
        };

        int[][] boardWonO = new int[][]{
                { 1, 1, 1},
                { 2, 1, 2},
                { 0, 1, 2}
        };

        int[][] boardDraw = new int[][]{
                { 1, 2, 1, 1},
                { 2, 1, 2, 1},
                { 2, 1, 2, 2},
                { 1, 2, 1, 2}
        };

        int[][] boardIncomplete = new int[][]{
                { 1, 2, 1, 0},
                { 2, 1, 2, 1},
                { 0, 1, 2, 0},
                { 1, 0, 0, 0}
        };

        if(winnerDecider((boardWonX))=="X")
        {
            System.out.println("function can identify when X wins");
        }
        if(winnerDecider((boardWonO))=="O")
        {
            System.out.println("function can identify when O wins");
        }
        if(winnerDecider((boardDraw))=="draw")
        {
            System.out.println("function can identify when it is a draw");
        }
        if(winnerDecider((boardIncomplete))=="incomplete")
        {
            System.out.println("function can identify when the game is not complete yet");
        }

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
       System.out.println("BEFORE random move:");
       printGameState(board1);
        System.out.println("AFTER random move:");
       randomMove(board1);
        printGameState(board1);
        System.out.println("random move2:");
        randomMove(board1);
        printGameState(board1);


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
//        tests();
        System.out.println("Welcome to TicTacToe!");
        System.out.println("The first player is computer, you can play first in the next round.");
        System.out.println("To make your move, you can state the cell name you want to go by writing the coordination of the cell that you see in the board state. Please enter the coordinations with a dash.");
        System.out.println("For example: the top left corner has the coordinations of row 1 and column 1, therefore you should input 1-1 (for middle cell 2-2 and so on...)");
        System.out.println("You cannot change a cell that is already filled before, you can only make your moves to empty cells.");
        System.out.println("After each move of any of the players, you can see the updated state of the board");
        System.out.println("Now press 'e' if you want to play on an easier mode or 'h' to play on a harder mode!");


        int rounds=0;
        Scanner scanner = new Scanner(System.in);
        String play="e";
        play = scanner.nextLine();
        if(play.equalsIgnoreCase("e") || play.equalsIgnoreCase("h") ) {
            while (true) {
                System.out.println("A new round!");
                int iteration=0;
                int[][] board = initializeTheBoard();
                printGameState(board);
                int winner = -1;
                int i = rounds % 2;
                while (winner == -1) {
                    if (i % 2 == 0) {
                        if(play.equalsIgnoreCase("h")){
                            winningStrategy(board,iteration);
                        }
                        else{
                            int[][] beforeBoard=arrayCopy(board);
                            if(isEqual(blockOrWin(board,2),beforeBoard));{
                                if(isEqual(blockOrWin(board,1),beforeBoard)){
                                    randomMove(board);
                                }
                            }
                        }
                    }
                    else if (i % 2 == 1) {
                        int [][] boardBefore=arrayCopy(board);
                        while(isEqual(board,boardBefore)){
                            System.out.println("Now it's your turn to play:");
                            play = scanner.nextLine();
                            int player_i = Integer.parseInt(play.split("-")[0]);
                            int player_j = Integer.parseInt(play.split("-")[1]);
                            board = updateBoardState(board, player_i, player_j, 1);
                        }
//
                    }
                    printGameState(board);
                    i++;
                    iteration++;
                    winner = winnerDecider(board); }

                if (winner == 2) {
                    System.out.println("--------The X has WON the game!--------");
                }
                else if (winner == 1) {
                    System.out.println("--------The O has WON the game!--------");
                }
                else if (winner == 0) {
                    System.out.println("--------It's a DRAW!--------");
                }
                rounds++;

            }
        }


    }
}
