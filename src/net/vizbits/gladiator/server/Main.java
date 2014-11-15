package net.vizbits.gladiator.server;

import net.vizbits.gladiator.server.utils.LogUtils;

public class Main {
  public static void main(String[] args) {
    int portNumber = Constants.DEFAULT_PORT;
    if (args.length > 0) {
      try {
        portNumber = Integer.parseInt(args[0]);
      } catch (Exception e) {
        LogUtils.logError("Invalid port number.");
        LogUtils.logError("Usage is: > java Server [portNumber]");
        return;
      }
    }
    GladiatorServer server = new GladiatorServer(portNumber);
    server.start();
  }
}
