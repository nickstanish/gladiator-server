package net.vizbits.gladiator.server.exceptions;

public class ActionDoesNotExistException extends Exception {
  private String action;

  public ActionDoesNotExistException(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }
}
