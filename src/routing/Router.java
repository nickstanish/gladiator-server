package routing;

import java.util.HashMap;
import java.util.Map;

import net.vizbits.gladiator.server.Constants;
import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.exceptions.ActionDoesNotExistException;
import net.vizbits.gladiator.server.request.BaseRequest;

public class Router {


  private static Map<String, RouteAction> map;

  public Router() {
    map = new HashMap<String, RouteAction>();
    map.put(Constants.GAME_REQUEST, (a, b) -> gameRequest(a, b));
  }

  public static void route(String action, GladiatorClient gladiatorClient, BaseRequest baseRequest)
      throws ActionDoesNotExistException {
    if (!map.containsKey(action))
      throw new ActionDoesNotExistException(action);
    else
      map.get(action).apply(gladiatorClient, baseRequest);
  }

  private static void gameRequest(GladiatorClient gladiatorClient, BaseRequest baseRequest) {
    return;
  }
}
