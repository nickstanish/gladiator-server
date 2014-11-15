package net.vizbits.gladiator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;

import net.vizbits.gladiator.server.utils.LogUtils;

public class GladiatorServer {
  private final int port;
  private boolean isRunning;

  public GladiatorServer(int port) {
    this.port = port;
    this.isRunning = false;
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
    // TODO Auto-generated method stub
    try {
      LogUtils.logError("Connection accepted from " + socket.getInetAddress());
      GladiatorClient client = new GladiatorClient(socket);
    } catch (Exception e) {
      LogUtils.logError(e);
    }


  }
}
