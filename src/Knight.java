import java.util.ArrayList;
import java.util.Scanner;

public class Knight {
    //keeps track of the knight's current position
    int[] knightPos = new int[2];
    //2d array that acts as the chessBoard
    int[][] chessBoard;

    //knight constructor sets the size of the board
    public Knight(int x, int y){
        chessBoard = new int[y][x];
    }

    //returns whether a move is valid for the given position
    boolean validMove(int x, int y){
        boolean onBoard = true;
        boolean notVisited = true;
        boolean isValid = false;

        //checks if the given x and y is on the board
        if(x > chessBoard[0].length-1){
            onBoard = false;
        } else if(x < 0){
            onBoard = false;
        } else if(y > chessBoard.length-1){
            onBoard = false;
        } else if(y < 0){
            onBoard = false;
        }

        //makes sure the space is on the board to avoid indexOutOfBound error
        if(onBoard){
            //checks if the space has been visited
            if(chessBoard[y][x] != 0) {
                notVisited = false;
            }
        }

        //move is valid if it is on the board and has not been visited
        if(onBoard && notVisited){
            isValid = true;
        }

        //returns whether move is valid
        return isValid;
    }

    //returns the number of possibles moves at the given position
    int checkMoves(int[] move){
        int x = move[0];
        int y = move[1];
        int moveNum = 0;

        //checks all possible moves for if they are valid. If one is, add 1 to moveNum
        if(validMove(x+2, y+1)){
            moveNum++;
        }
        if(validMove(x+2, y-1)){
            moveNum++;
        }
        if(validMove(x-2, y+1)){
            moveNum++;
        }
        if(validMove(x-2, y-1)){
            moveNum++;
        }
        if(validMove(x+1, y+2)){
            moveNum++;
        }
        if(validMove(x-1, y+2)){
            moveNum++;
        }
        if(validMove(x+1, y-2)){
            moveNum++;
        }
        if(validMove(x-1, y-2)){
            moveNum++;
        }

        return moveNum;
    }

    //returns an array of the possible moves at the given position
    ArrayList<int[]> getMoves(int[] move){
        int x = move[0];
        int y = move[1];
        ArrayList<int[]> posMoves = new ArrayList<int[]>();

        //checks all possible moves for if they are valid. If one is, add to the posMove list
        if(validMove(x+2, y+1)){
            int[] newMove = {x+2, y+1};
            posMoves.add(newMove);
        }
        if(validMove(x+2, y-1)){
            int[] newMove = {x+2, y-1};
            posMoves.add(newMove);
        }
        if(validMove(x-2, y+1)){
            int[] newMove = {x-2, y+1};
            posMoves.add(newMove);
        }
        if(validMove(x-2, y-1)){
            int[] newMove = {x-2, y-1};
            posMoves.add(newMove);
        }
        if(validMove(x+1, y+2)){
            int[] newMove = {x+1, y+2};
            posMoves.add(newMove);
        }
        if(validMove(x-1, y+2)){
            int[] newMove = {x-1, y+2};
            posMoves.add(newMove);
        }
        if(validMove(x+1, y-2)){
            int[] newMove = {x+1, y-2};
            posMoves.add(newMove);
        }
        if(validMove(x-1, y-2)){
            int[] newMove = {x-1, y-2};
            posMoves.add(newMove);
        }

        return posMoves;
    }

    //returns the move with the least move options
    int[] pickMove(int[] pos){
        ArrayList<int[]> moves = getMoves(pos);
        int[] bestMove = {0,0};
        int bestOptNum = 100;

        //loops through all the valid possible moves
        for(int[] move : moves){
            //the move with the lower options becomes the best move
            if(checkMoves(move) < bestOptNum){
                bestMove = move;
                bestOptNum = checkMoves(bestMove);
            }
            //if the move and bestMove have the same options choose one at random
            else if(checkMoves(move) == bestOptNum){
                int rNum = (int) Math.round(Math.random());
                if(rNum == 0){
                    bestMove = move;
                    bestOptNum = checkMoves(bestMove);
                }
            }
        }

        return bestMove;
    }

    //finds the best possible move, moves the knight there, and changes the value of the space to the given number
    void moveKnight(int moveNum){
        int[] bestMove = pickMove(knightPos);
        knightPos = bestMove;
        chessBoard[bestMove[1]][bestMove[0]] = moveNum;
    }

    //prints the whole board
    void printBoard(){
        //loops through the chess board printing at certain spaces
        for(int j = 0; j < chessBoard.length; j++){
            //prints out a column with | between
            for(int k = 0; k < chessBoard[0].length; k++){
                //if the value is less than 10 it adds a space to make every line match each other
                if(chessBoard[j][k] < 10){
                    System.out.print(" | " + " " + chessBoard[j][k]);
                }else{
                    System.out.print(" | " + chessBoard[j][k]);
                }
            }
            //after a column is printed cap it with | and start a new line
            System.out.println(" |");
        }
    }

    //returns whether they're any empty spaces on the board
    boolean checkTour(){
        boolean failedTour = false;
        //loops through the entire chess board
        for(int j = 0; j < chessBoard.length; j++){
            for(int k = 0; k < chessBoard[0].length; k++){
                //if any space has 0 we know that the tour failed
                if(chessBoard[j][k] == 0){
                    failedTour = true;
                }
            }
        }
        return failedTour;
    }

    //run the full knight's tour
    void runTour(int x, int y){
        knightPos[0] = x;
        knightPos[1] = y;
        chessBoard[y][x] = 1;
        //if a tour fails we create another until we finish a tour
        while(checkTour()){
            //moves the knight to the best position however many times the board needs
            for(int i = 2; i <= ((chessBoard.length * chessBoard[0].length)); i++){
                moveKnight(i);
            }
        }
        printBoard();
    }

    //main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type knight's starting x position 1-8");
        int x = scanner.nextInt();

        while(x > 8 || x <= 0){
            System.out.println("Position not on board, please type a different x position 1-8");
            x = scanner.nextInt();
        }

        System.out.println("Type knight's starting y position 1-8");
        int y = scanner.nextInt();

        while(y > 8 || y <= 0){
            System.out.println("Position not on board, please type a different y position 1-8");
            y = scanner.nextInt();
        }

        Knight knight = new Knight(8,8);
        knight.runTour(x-1,y-1);
    }
}



