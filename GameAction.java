/**
 * @name Tri Pham
 * @Filename GameAction.java
 * @Assigment Project 4 ACP
 * @about This class create the server and display the moves that the client makes, 
 * @also give the client their player number, also lock the moves
 */
import java.util.Random;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;




public class GameAction implements Runnable{
	//private variables
	private Socket s;
	private Scanner in;
	private PrintWriter out;
	private Game game;
	private ReentrantLock moveLock = new ReentrantLock();
	private Condition moveMade = moveLock.newCondition();

	
	public GameAction(Socket aSocket, Game game)
   	{
      		s = aSocket;
      		this.game = game;
   	}

	//made for the server to read in and write out
   	public void run()
   	{
		try
		{
			try
			{
				in = new Scanner(s.getInputStream());
				out = new PrintWriter(s.getOutputStream());
				doService();            
			}
			finally
			{
				s.close();
			}
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
   	}

	//get command from client
   	public void doService() throws IOException
  	 {      
		while (true)
		{  
			if (!in.hasNext()) 
				return;
			String command = in.next();
			if (command.equals("QUIT"))
					return;
			else{
				executeCommand(command);
			}
			
		}
   	}
   
	

   	//command can be executed, if player say hello they are set as player 1 or player 2. 
	public void executeCommand(String command)
	{
		if(command.equals("hello") && game.getPlayer1()){
			System.out.println("**command is [" + command + "] by player 1");
			out.print("new player command for player 1\n");
			out.flush();
			game.setPlayer1(false);
		}
	
		else if(command.equals("hello") && !game.getPlayer1()){
			System.out.println("**command is [" + command + "] by player 2");
			out.print("new player command for player 2\n");
			out.flush();
			game.setPlayer1(true);
		}
		
		while(command.equals("move") && game.getPlayer1()){ // if player 1 say move this will happen
			System.out.println("**command is [" + command + "] by player 1");
			int x = in.nextInt();
			int y = in.nextInt();
			System.out.println("Move is player 1 x = " + x + " y = " + y); // move is announced
			System.out.println("player 1 move = " + x + " y = " + y);
			moveLock.lock();
			game.setBoard(x,y); // move is locks
			moveLock.unlock();
			System.out.println("the Board currently");
			game.showBoard();
			System.out.println("checking for win..."); // check for win then send back the moves
			if(!game.hasWinner() && game.hasWinner()){
				out.print(x + "\n");
				out.flush();
				out.print(y + "\n");
				out.flush();
				System.out.println("game continues");
			}
			else if(game.draw()){
				out.print("draw\n");
				out.flush();
			}
			else if(game.hasWinner()){
				out.print("winner\n");
				out.flush();
			}
			game.setPlayer1(false);	
		}
		
		while(command.equals("move") && !game.getPlayer1()){ // if player two say move this will happen
			System.out.println("**command is [" + command + "] by player 2");
			int x = in.nextInt();
			int y = in.nextInt();
			System.out.println("Move is player 2 x = " + x + " y = " + y); // move is announced
			System.out.println("player 2 move = " + x + " y = " + y);
			moveLock.lock(); // move is locks
			game.setBoard(x,y);
			moveLock.unlock();
			System.out.println("the Board currently");
			game.showBoard();
			System.out.println("checking for win..."); // check for win then send back the moves
			out.print(x + "\n");
			out.flush();
			out.print(y + "\n");
			out.flush();
			if(!game.hasWinner() && game.hasWinner()){
				System.out.println("game continues");
			}
			else if(game.draw()){
				out.print("draw\n");
				out.flush();
			}
			else if(game.hasWinner()){
				out.print("winner\n");
				out.flush();
			}
			game.setPlayer1(true);	
		}
		
   	}
   
	
	
	
}
