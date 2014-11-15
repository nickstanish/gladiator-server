package net.vizbits.gladiator.server.utils;

import java.util.HashMap;
import java.util.Map;

public class RouteUtils {
  interface Operation {
    public int op(int a, int b);
  }

  private Map<String, Operation> map;

  RouteUtils(){
    map = new HashMap<String, RouteUtils.Operation>();
    map.put("FUCK", (a,b) -> stupid(a,b));
  }

  public int stupid(int a, int b) {
    return 0;
  }
}
