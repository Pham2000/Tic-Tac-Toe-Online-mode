/**
 * @name Tri Pham
 * @Filename Game.java
 * @Assigment Project 4 ACP
 * @about This class represent the board tic tac toe, this class make move and find legal moves
 */

public class Game {
	//private variables
	private int size = 3;
	private int[][] board = new int[size][size];
	private boolean player1;
	
	
	//Game will start with empty board
	public Game() {
		emptyBoard();
		setPlayer1(true);
	}
	
	//can set the player
	public void setPlayer1(boolean player){
		player1 = player;
	}
	
	//can get the player
	public boolean getPlayer1(){
		return player1;
	}
	
	public void emptyBoard() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	//set the board
	public void setBoard(int i, int j){
		if(player1){
			board[i][j] = 1;
		}
		else{
			board[i][j] = 2;
		}
		
	}
	
	//set other player board
	public void setOtherPlayer(int i, int j){
		if(player1){
			board[i][j] = 2;
		}
		else{
			board[i][j] = 1;
		}
	}
	
	//if legal
	public boolean ifLegal(int i, int j){
		if(board[i][j] > 0){
			return false;
		}
		return true;
 
	}

	//can show the board
	public void showBoard() {
		for(int i = 0; i < size; i++) {
			System.out.println(board[i][0] + "" + board[i][1] + "" + board[i][2]);
		}
	}
	
	//Check for winner or draw down here
	public boolean hasWinner() {
		//check side
		for(int i = 0; i < 0; i++){
			if(board[i][0] == 1 &&  board[i][1] == 1 && board[i][2] == 1) {
				return true;
			}
			if(board[i][0] == 2 &&  board[i][1] == 2 && board[i][2] == 2) {
				return true;
			}
		}
		
		//check down
		for(int i = 0; i < 0; i++){
			if(board[0][i] == 1 &&  board[1][i] == 1 && board[2][i] == 1) {
				return true;
			}
			if(board[0][i] == 2 &&  board[1][i] == 2 && board[2][i] == 2) {
				return true;
			}
		}
		
		//diagonal check
		if(board[0][2] == 1 &&  board[1][1] == 1 && board[2][0] == 1) {
			return true;
		}
		if(board[0][2] == 2 &&  board[1][1] == 2 && board[2][0] == 2) {
			return true;
		}
		
		if(board[0][0] == 1 &&  board[1][1] == 1 && board[2][2] == 1) {
			return true;
		}
		if(board[0][0] == 2 &&  board[1][1] == 2 && board[2][2] == 2) {
			return true;
		}
		
		return false;
	}
	
	public boolean draw() {
		if(!hasWinner()) {
			if(board[0][0] > 0 &&  board[0][1] > 0 && board[0][2] > 0 &&
			   board[1][0] > 0 &&  board[1][1] > 0 && board[1][2] > 0 &&
			   board[2][0] > 0 &&  board[2][1] > 0 && board[2][2] > 0 ) {
			   return true;
			}
		}
		return false;
	}
	
	
	
}
