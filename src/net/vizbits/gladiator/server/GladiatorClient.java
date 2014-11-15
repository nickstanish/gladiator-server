package net.vizbits.gladiator.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.vizbits.gladiator.server.exceptions.ActionDoesNotExistException;
import net.vizbits.gladiator.server.exceptions.AlreadyConnectedException;
import net.vizbits.gladiator.server.game.Arena;
import net.vizbits.gladiator.server.game.Gladiator;
import net.vizbits.gladiator.server.request.BaseRequest;
import net.vizbits.gladiator.server.request.LoginRequest;
import net.vizbits.gladiator.server.response.LoginResponse;
import net.vizbits.gladiator.server.service.ClientService;
import net.vizbits.gladiator.server.utils.JsonUtils;
import net.vizbits.gladiator.server.utils.LogUtils;
import routing.Router;

public class GladiatorClient extends Thread {
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private String username;
  private ClientService clientService;
  private WaitingQueue waitingQueue;
  private ClientState clientState;
  private boolean isAlive;
  private Arena arena;
  private Gladiator gladiator;

  public GladiatorClient(Socket socket, ClientService clientService, WaitingQueue waitingQueue)
      throws Exception {
    this.clientService = clientService;
    this.waitingQueue = waitingQueue;
    this.socket = socket;

    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    this.start();
  }

  private boolean init() {
    String json = JsonUtils.readFromSocket(in);

    LoginRequest loginRequest = JsonUtils.fromString(json, LoginRequest.class);
    if (loginRequest == null || loginRequest.getUsername() == null) {
      close();
      return false;
    }
    String validationMessage;
    if ((validationMessage = validateUser(loginRequest)) != null) {
      JsonUtils.writeToSocket(out, new LoginResponse(false, validationMessage));
      close();
      return false;
    }
    LogUtils.logInfo(loginRequest.getUsername());
    this.username = loginRequest.getUsername();


    try {
      clientService.addClient(this);
    } catch (AlreadyConnectedException e) {
      LogUtils.logError(e);
      JsonUtils.writeToSocket(out, new LoginResponse(false, "Error connecting."));
      return false;
    }
    JsonUtils.writeToSocket(out, new LoginResponse(true, null));
    this.clientState = ClientState.Ready;
    return true;
  }


  @Override
  public void run() {
    if (!init()) {
      LogUtils.logInfo("Disconnecting");
      return;
    }
    isAlive = true;
    while (isAlive) {
      try {
        String json = JsonUtils.readFromSocket(in);
        BaseRequest baseRequest = JsonUtils.fromString(json, BaseRequest.class);
        if (baseRequest == null)
          break;
        if (baseRequest.getAction() == null)
          continue;
        // do stuff
        Router.route(baseRequest.getAction(), this, baseRequest, json);

      } catch (ActionDoesNotExistException e) {
        LogUtils.logError(e);
        LogUtils.logError("Action " + e.getAction() + " does not exist");
        continue;
      }
    }
    disconnect();
  }

  private String validateUser(LoginRequest loginRequest) {
    if (loginRequest.getUsername().trim().length() == 0)
      return "Username is required";
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

  public WaitingQueue getWaitingQueue() {
    return waitingQueue;
  }

  public ClientService getClientService() {
    return clientService;
  }

  public Gladiator getGladiator() {
    return gladiator;
  }

  public void setGladiator(Gladiator gladiator) {
    this.gladiator = gladiator;
    gladiator.name = username;
  }

  public Arena getArena() {
    return arena;
  }

  public void setArena(Arena arena) {
    this.arena = arena;
  }

  public PrintWriter getOut() {
    return out;
  }

  public void setClientState(ClientState state) {
    this.clientState = state;
  }

  public ClientState getClientState() {
    return clientState;
  }
}
