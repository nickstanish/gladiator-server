package net.vizbits.gladiator.server.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.vizbits.gladiator.server.GladiatorServer;


public class LogUtils {
  private static Logger logger;
  public static boolean ENABLE_LOGGING = true;

  private LogUtils() {}

  public static Logger getLogger() {
    if (logger == null) {
      logger = Logger.getLogger(GladiatorServer.class.getName());
    }
    return logger;
  }

  public static void logInfo(Object... objects) {
    if (!ENABLE_LOGGING)
      return;
    for (Object object : objects) {
      getLogger().log(Level.INFO, object.toString());
    }
  }

  public static void logInfo(String title, Throwable throwable) {
    if (!ENABLE_LOGGING)
      return;
    getLogger().log(Level.INFO, title, throwable);
  }

  public static void logInfo(String string) {
    if (!ENABLE_LOGGING)
      return;
    getLogger().log(Level.INFO, string);
  }

  public static void logError(Object... objects) {
    if (!ENABLE_LOGGING)
      return;
    for (Object object : objects) {
      getLogger().log(Level.SEVERE, object.toString());
    }
  }

  public static void logError(String string) {
    if (!ENABLE_LOGGING)
      return;
    getLogger().log(Level.SEVERE, string);
  }

  public static void logError(String title, Throwable throwable) {
    if (!ENABLE_LOGGING)
      return;
    getLogger().log(Level.SEVERE, title, throwable);
  }
}
