package net.vizbits.gladiator.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;

import net.vizbits.gladiator.server.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by nick on 9/6/14.
 */
public class JsonUtils {

  private static Gson gson;

  public static Gson getGson() {
    if (gson == null) {
      gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmmmmm").create();
    }
    return gson;
  }

  public static <T> T fromHttpEntity(String entity, Class<T> type) {
    T object;
    object = getGson().fromJson(entity, type);
    return object;
  }

  public static String toJsonString(Object object) {
    return getGson().toJson(object);
  }

  public static void writeToSocket(PrintWriter out, Object object) {
    out.print(toJsonString(object));
    out.print(Constants.HTTP_SEPARATOR);
    out.flush();
  }

  public static <T> T readFromSocket(BufferedReader in, Class<T> type) {
    T object;
    StringBuilder builder = new StringBuilder();
    int curr;
    int[] search = {'\0', '\0', '\0', '\0'};

    try {
      while (!matches(search, Constants.HTTP_SEPARATOR)) {
        curr = in.read();
        shift(search, curr);
        System.out.println((char) curr);
        if (curr == -1)
          return null;
        builder.append((char) curr);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(builder.toString());

    object = getGson().fromJson(builder.toString().replaceFirst("\r\n\r\n$", ""), type);
    return object;
  }

  public static boolean matches(int[] from, int[] to) {
    boolean match = true;
    if (from.length != to.length)
      return false;
    for (int i = 0; i < from.length && match; i++) {
      match = match && from[i] == to[i];
    }
    return match;
  }

  public static void shift(int[] shift, int next) {
    for (int i = shift.length - 1; i >= 0; i--) {
      int swap = shift[i];
      shift[i] = next;
      next = swap;
    }

  }

  public static void main(String[] args) {
    int[] s1 = {'\0', '\0', '\0', '\0'};
    int[] s2 = {'\r', '\n', '\r', '\0'};
    System.out.println(JsonUtils.matches(s1, s2));
    System.out.println(JsonUtils.matches(s2, s1));
    System.out.println(JsonUtils.matches(s1, s1));
    System.out.println(JsonUtils.matches(s1, Constants.HTTP_SEPARATOR));
    System.out.println(JsonUtils.matches(Constants.HTTP_SEPARATOR, Constants.HTTP_SEPARATOR));
    System.out.println("asfkjlsadfkjgjkshdaf\r\n\r\n\r\n".replaceFirst("\r\n\r\n$", ""));

  }

}
