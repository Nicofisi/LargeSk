package pl.pickaxe.largesk.bungee;

import an0nym8us.api.messaging.client.ServerListener;

public class Connector {

  private static ServerListener sl;

  public static ServerListener getServerListener() {
    return sl;
  }

  public static void connect() {

    try {
      sl = new ServerListener("name", "localhost", 1234);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}
