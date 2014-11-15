package net.vizbits.gladiator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import net.vizbits.gladiator.server.service.ClientService;
import net.vizbits.gladiator.server.utils.LogUtils;

public class GladiatorServer {
  private final int port;
  private boolean isRunning;
  private ClientService clientService;


  public GladiatorServer(int port) {
    this.port = port;
    isRunning = false;
    clientService = new ClientService();

    LogUtils.logInfo(Constants.STARTUP_TEXT);

  }

  public void start() {
    LogUtils.logInfo("Starting on port " + port);
    isRunning = true;
    ServerSocket server = null;
    try {
      server = new ServerSocket(port);
      server.setSoTimeout(10000);
      while (isRunning) {
        Socket socket = null;
        try {
          socket = server.accept(); // block and wait to accept a connection
        } catch (SocketTimeoutException ste) {
        }
        if (socket != null) {
          connectClient(socket);
        }

      }
    } catch (IOException e) {
      LogUtils.logError(e);
    } finally {
      try {
        server.close();
      } catch (IOException e) {

      }

    }



  }

  private void connectClient(Socket socket) {
    try {
      LogUtils.logInfo("Connection accepted from " + socket.getInetAddress());
      GladiatorClient client = new GladiatorClient(socket, clientService);

    } catch (Exception e) {
      LogUtils.logError(e);
    }


  }
}
