package net.vizbits.gladiator.server.exceptions;

public class GladiatorMissingInActionException extends BaseGladiatorException {
  /**
   * 
   */
  private static final long serialVersionUID = 8457890264447433012L;

  public GladiatorMissingInActionException(String username) {
    super(username);
  }
}
