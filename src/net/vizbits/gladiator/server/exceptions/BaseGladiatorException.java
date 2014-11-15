package net.vizbits.gladiator.server.exceptions;

public class BaseGladiatorException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = -8440939578520449895L;
  private String username;

  public BaseGladiatorException(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

}
