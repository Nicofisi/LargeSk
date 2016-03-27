package pl.pickaxe.largesk.forbungee;

//All the stuff here does nothing at the moment

import java.net.*;
import java.io.*;

import pl.pickaxe.largesk.util.Xlog;

public class Core
{
	public static void main(int listenPort) throws IOException {
		try (
			ServerSocket serverSocket = new ServerSocket(listenPort);
		Socket clientSocket = serverSocket.accept();	 
			PrintWriter out =
				new PrintWriter(clientSocket.getOutputStream(), true);				   
			BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		)
		{
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				out.println(inputLine);
			}
		} catch (IOException e)
		{
			Xlog.logBungeeInfo("Exception caught when trying to listen on port "
				+ listenPort + " or listening for a connection");
			Xlog.logBungeeInfo(e.getMessage());
		}
	}
}
