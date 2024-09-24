import java.util.ArrayList;
import java.util.Scanner;

public class Knight {
    int[][] board = new int[8][8];
    int currRow;
    int currCol;

    //knight constructor sets the starting row and column as well set the value on the board to 1
    public Knight(int sRow, int sCol){
        board[sRow][sCol] = 1;
        currRow = sRow;
        currCol = sCol;
    }
    //checks of the given row and column is on the board and been visited before
    boolean checkMove(int row, int col){
        boolean onBoard = true;
        boolean visited = false;

        //checks if row is too big to be on the board
        if(row > 7){
            onBoard = false;
        }
        //checks if row is too small to be on the board
        else if(row < 0){
            onBoard = false;
        }
        //checks if col is too big to be on the board
        else if(col > 7){
            onBoard = false;
        }
        //checks if col is too small to be on the board
        else if(col < 0){
            onBoard =false;
        }

        //checks if the given space is on the board, so it can be called without an index out of bound error
        if(onBoard){
            //checks if the space is not 0 and therefore has been visited
            if(board[row][col] != 0){
                visited = true;
            }
        }

        boolean goodMove = false;
        //checks if the space is on the board and has not been visited
        if(onBoard && !visited){
            goodMove = true;
        }
        return goodMove;
    }
    //gets an array list of all the possible moves from the given row and column
    ArrayList<int[]> getMoves(int row, int col){
        //array of all the ways the knight can move
        int[] moves = {2,2,-2,-2,1,-1,1,-1};
        ArrayList<int[]> posMoves = new ArrayList<int[]>();
        //check the 8 possible moves
        for (int i = 0; i < 8; i++) {
            //add the move to the list if it is possible from the given row and column
            if(checkMove(row+moves[i],col+moves[7-i])){
                int[] move = {row+moves[i],col+moves[7-i]};
                posMoves.add(move);
            }
        }
        return posMoves;
    }
    //picks the move with the lowest move options from the possible moves
    int[] bestMove(int row, int col){
        ArrayList<int[]> moves = getMoves(row,col);
        int[] bestMove = {0,0};
        int bMNum = 8;

        //checks every possible move from the given position
        for (int[] move : moves) {
            //check if the move has fewer option than the current best and set it to bestMove if it is
            if(getMoves(move[0],move[1]).size() < bMNum){
                bestMove = move;
                bMNum = getMoves(move[0],move[1]).size();
            }
            //if the move and the best current move have the same amount of option pick one at random
            else if (getMoves(move[0],move[1]).size() == bMNum) {
                //create a random between 0 and 1 and see if it 1. flipping a coin to see what move is picked
                if((int) (Math.random()*2) == 1){
                    bestMove = move;
                    bMNum = getMoves(move[0],move[1]).size();
                }
            }
        }

        return bestMove;
    }
    //changes the values in board array to the path found with bestMove method and prints the final board
    void startTour(){
        int[] bMove;
        //checks if a finished board has any empty spaces and got stuck and if so makes a new board
        while (checkForZero()){
            //moves the knight to the best move 64 times and changes the value of the 2d array according to the path
            for (int i = 2; i < 65; i++) {
                bMove = bestMove(currRow, currCol);
                board[bMove[0]][bMove[1]] = i;
                currRow = bMove[0];
                currCol = bMove[1];
            }
        }

        //loops through every row of the board
        for (int i = 0; i < board.length; i++) {
            //loops through every column of the board
            for (int j = 0; j < board[0].length; j++) {
                //prints out the value of each column on one line
                // if it is less than 0 add a space to create a clean square
                if(board[i][j] < 10)
                    System.out.print(" ");
                System.out.print(" " + board[i][j]);
            }
            //starts a new line at the end of the column
            System.out.println();
        }
    }
    //checks entire board for any 0 and therefore an empty space
    boolean checkForZero(){
        boolean zeroFound = false;
        //loop through every index of the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //checks if the value of is 0
                if(board[i][j] == 0){
                    zeroFound = true;
                }
            }
        }
        return zeroFound;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter starting row and column (0-7) putting a space between\nExample: 3 8");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        while (row > 7 || row < 0 || col > 7 || col < 0){
            System.out.println("Outside of board, enter a different starting row and column (0-7)\nExample: 2 3");
            row = scanner.nextInt();
            col = scanner.nextInt();
        }
        Knight k = new Knight(row,col);
        k.startTour();
    }
}
