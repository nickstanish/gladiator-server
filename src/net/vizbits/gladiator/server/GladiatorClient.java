package net.vizbits.gladiator.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.vizbits.gladiator.server.request.LoginRequest;
import net.vizbits.gladiator.server.response.LoginResponse;
import net.vizbits.gladiator.server.service.ClientService;
import net.vizbits.gladiator.server.utils.JsonUtils;
import net.vizbits.gladiator.server.utils.LogUtils;

public class GladiatorClient {
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private String username;
  private ClientService clientService;
  private WaitingQueue waitingQueue;

  public GladiatorClient(Socket socket, ClientService clientService, WaitingQueue waitingQueue)
      throws Exception {
    this.clientService = clientService;
    this.waitingQueue = waitingQueue;
    this.socket = socket;

    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    LoginRequest loginRequest = JsonUtils.readFromSocket(in, LoginRequest.class);
    if (loginRequest == null || loginRequest.getUsername() == null) {
      close();
      return;
    }
    String validationMessage;
    if ((validationMessage = validateUser(loginRequest)) != null) {
      JsonUtils.writeToSocket(out, new LoginResponse(false, validationMessage));
      close();
      return;
    }
    LogUtils.logInfo(loginRequest.getUsername());
    JsonUtils.writeToSocket(out, new LoginResponse(true, null));
    this.username = loginRequest.getUsername();
    clientService.addClient(this);

    // this.start();
  }

  private String validateUser(LoginRequest loginRequest) {
    if (!loginRequest.getUsername().matches("^[\\w\\d]+$"))
      return "Usernames must be alphanumeric";
    if (!clientService.usernameAvailable(loginRequest.getUsername()))
      return "Username is not available";
    return null;
  }

  public void disconnect() {
    clientService.removeClient(username);
    close();

  }

  private void close() {
    try {
      if (socket != null)
        socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getUsername() {
    return username;
  }
}
