package net.vizbits.gladiator.server.exceptions;

public class AlreadyConnectedException extends BaseGladiatorException {
  /**
   * 
   */
  private static final long serialVersionUID = 8457890264447433012L;

  public AlreadyConnectedException(String username) {
    super(username);
  }
}
