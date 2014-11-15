package net.vizbits.gladiator.server.utils;

public class StringUtils {

  /**
   * Combines a variable list of strings using a string builder for speed
   * 
   * @param strings
   * @return
   */
  public static String stringConcat(String... strings) {
    return stringConcatBuilder(strings).toString();
  }

  /**
   * Combines a variable list of strings using a string builder for speed but returns the string
   * builder so that you can continue to use it
   * 
   * @param strings
   * @return
   */
  public static StringBuilder stringConcatBuilder(String... strings) {
    StringBuilder builder = new StringBuilder();
    for (String string : strings) {
      builder.append(string);
    }
    return builder;
  }

}
