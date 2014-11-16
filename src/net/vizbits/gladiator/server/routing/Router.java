package net.vizbits.gladiator.server.routing;

import java.util.HashMap;
import java.util.Map;

import net.vizbits.gladiator.server.Constants;
import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.exceptions.ActionDoesNotExistException;
import net.vizbits.gladiator.server.request.BaseRequest;

public class Router {


  private static Map<String, RouteAction> map = null;

  private Router() {

  }

  private static void initSingleton() {
    if (map != null)
      return;
    map = new HashMap<String, RouteAction>();
    map.put(Constants.GAME_REQUEST,
        (client, base, json) -> GameRequestHandler.gameRequest(client, base, json));
    map.put(Constants.WAIT_REQUEST,
        (client, base, json) -> GameRequestHandler.waitRequest(client, base, json));
    map.put(Constants.CHARACTER_SELECTION,
        (client, base, json) -> BattleHandler.CharacterChoice(client, base, json));
    map.put(Constants.BATTLE_REQUEST,
        (client, base, json) -> BattleHandler.BattleSequence(client, base, json));
  }

  public static void route(String action, GladiatorClient gladiatorClient, BaseRequest baseRequest,
      String json_body) throws ActionDoesNotExistException {
    initSingleton();
    if (!map.containsKey(action))
      throw new ActionDoesNotExistException(action);
    else
      map.get(action).apply(gladiatorClient, baseRequest, json_body);
  }



}
