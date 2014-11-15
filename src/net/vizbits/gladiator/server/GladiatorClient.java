package net.vizbits.gladiator.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.vizbits.gladiator.server.request.LoginRequest;
import net.vizbits.gladiator.server.response.LoginResponse;
import net.vizbits.gladiator.server.utils.JsonUtils;
import net.vizbits.gladiator.server.utils.LogUtils;

public class GladiatorClient {
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private String username;

  public GladiatorClient(Socket socket) throws Exception {
    // give each a unique id
    this.socket = socket;
    // create output first
    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    LoginRequest loginRequest = JsonUtils.readFromSocket(in, LoginRequest.class);
    if (loginRequest != null && loginRequest.getUsername() != null) {
      LogUtils.logInfo(loginRequest.getUsername());
      JsonUtils.writeToSocket(out, new LoginResponse(true, null));
    }
    socket.close();
    // if (!validateUser(username, out)) {
    // disconnect();
    // throw new Exception("Username taken");
    // }
    // this.start();
  }
}
