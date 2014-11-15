package net.vizbits.gladiator.server;

public class Constants {
  public static final String VERSION = "0.0.20";
  public static final String STARTUP_TEXT = "\n\tGladiatorServer:\n\n";
  public static final int DEFAULT_PORT = 8080;
  public static final int[] HTTP_SEPARATOR = {'\r', '\n', '\r', '\n'};
  public static final String HTTP_SEPARATOR_STRING = "\r\n\r\n";
  /*
   * ACTIONS
   */
  public static final String GAME_REQUEST = "GameRequest";
  public static final String WAIT_REQUEST = "WaitRequest";
  public static final String CHARACTER_SELECTION = "NewCharacter";
}
