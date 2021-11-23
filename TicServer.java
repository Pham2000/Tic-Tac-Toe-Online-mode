/**
 * @name Tri Pham
 * @Filename TicServer.java
 * @Assigment Project 4 ACP
 * @ This class create the server, and show when the client is connected
 */
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.ServerSocket;

public class TicServer{
	public static void main(String[] args) throws IOException
	//sort and simple ways to connect the server and the 2 clients 
	{
		Game game = new Game();
		final int SBAP_PORT = 8880;
		ServerSocket server = new ServerSocket(SBAP_PORT);
		System.out.println("Tic Tac Toe Server Started.");
      
		while (true)
		{
			Socket s = server.accept();
			System.out.println("Client connected.");
			GameAction movement = new GameAction(s, game);
			Thread t = new Thread(movement);
			t.start();
		}
   }
}
