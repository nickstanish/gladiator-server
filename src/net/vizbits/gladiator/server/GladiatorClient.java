package net.vizbits.gladiator.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    this.username = in.readLine();
    // if (!validateUser(username, out)) {
    // disconnect();
    // throw new Exception("Username taken");
    // }
    // this.start();
  }
}
