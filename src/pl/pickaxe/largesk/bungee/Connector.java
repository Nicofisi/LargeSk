package pl.pickaxe.largesk.bungee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pl.pickaxe.largesk.util.Xlog;

public class Connector {
  public static void connectTo(String bungeeHost, int bungeePort) throws IOException {
    try (Socket echoSocket = new Socket(bungeeHost, bungeePort);

        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
      String userInput;
      while ((userInput = stdIn.readLine()) != null) {
        out.println(userInput);
        Xlog.logInfo("echo: " + in.readLine());
      }
    } catch (UnknownHostException e) {
      Xlog.logInfo("Don't know about host " + bungeeHost);
      return;
    } catch (IOException e) {
      Xlog.logInfo("Couldn't get I/O for the connection to " + bungeeHost);
      return;
    }
  }
}
