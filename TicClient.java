/**
 * @name Tri Pham
 * @Filename TicClient.java
 * @Assigment Project 4 ACP
 * @ This class supposed to make the game runnable and have thread going so that the tic tac toe game is actually  
 * @ This is the clients, and they are sending out moves, and getting moves.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;
import javax.swing.JOptionPane;

public class TicClient {
	//all the variables used in client
	private int size = 3;
	private int x;
	private int y;
	
	
	//Create random moves for the player
	private Random rand = new Random();

	public TicClient(){
		x = 0;
		y = 0;
	}
	
	public void setX() {
		x = rand.nextInt(size);
	}
	
	public void setY() {
		y = rand.nextInt(size);
	}
	
	public int getX(){
		setX();
		return x;
	}
	
	public int getY(){
		setY();
		return y;
	}
	
	
	// will send out hello and move command to run the tictactoe game
	public static void main(String args[]) throws IOException{
		// all socket and program needed
		Game game = new Game();
		TicClient boi = new TicClient();
		final int SBAP_PORT = 8880;
		Socket s = new Socket("localhost", SBAP_PORT);
		InputStream instream = s.getInputStream();
		OutputStream outstream = s.getOutputStream();
		Scanner in = new Scanner(instream);
		PrintWriter out = new PrintWriter(outstream); 
		String sending;
		Random rand = new Random();
		String receiving;
		int SleepTime = 300;
		int SendingHello = 300;
		
		try {
			int threadSleep = rand.nextInt(SendingHello);
			System.out.println("%%%%%%%t: " + threadSleep); // sleep before sending hello
			Thread.sleep(threadSleep);
			sending = "hello";
			System.out.println("Sending: " + sending);
			out.print(sending + "\n");
			out.flush();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		receiving = in.nextLine();
		if(receiving.equals("new player command for player 1")){
			System.out.println("Receiving: " + receiving);
			game.setPlayer1(true);
			
			try {
				Thread.sleep(SleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		else{
			System.out.println("Receiving: " + receiving);
			game.setPlayer1(false);
			//try { WHEN YOU TRY BEST, BUT DON'T SUCCEED :(
			//	Thread.sleep(sleepTime);
			//} catch (InterruptedException e) {
			//		e.printStackTrace();
			//}
			
			//int i = in.nextInt();
			//int j = in.nextInt();
			//game.setOtherPlayer(i,j);
			
			
			try {
				Thread.sleep(SleepTime);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
			
		}
		
		while(true){ //infinite that should have alternate between moves and get the other player moves
			int i = boi.getX();
			int j = boi.getY();
			while(!game.ifLegal(i,j)){
				i = boi.getX();
				j = boi.getY();
			}
			if(game.getPlayer1()){
				System.out.println("I am player 1");
			}
			else{
				System.out.println("I am player 2");
			}
			game.setBoard(i,j);
			game.showBoard();
			out.print("move\n");
			out.flush();
			out.print(i + "\n");
			out.flush();
			out.print(j + "\n");
			out.flush();
			if(in.hasNextLine()){
				receiving = in.nextLine();
			}
			else{
				receiving = "";
			}
			if(receiving.equals("winner") || receiving.equals("draw")){
					break;
			}
			
			i = in.nextInt();
			j = in.nextInt();
			game.setOtherPlayer(i,j);
			if(in.hasNextLine()){
				receiving = in.nextLine();
			}
			else{
				receiving = "";
			}
			if(receiving.equals("winner") || receiving.equals("draw")){
				game.showBoard();
				if(game.getPlayer1() && receiving.equals("winner")){
					JOptionPane.showMessageDialog(null,"Player 1 win");
				}
				else if(!game.getPlayer1() && receiving.equals("winner")){
					JOptionPane.showMessageDialog(null,"Player 2 win");
				}
				if(receiving.equals("draw")){
					JOptionPane.showMessageDialog(null,"There a draw");
				}
				break;
			}
			else{
				try {
					Thread.sleep(SleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		s.close();
		
	}
		
		
}
