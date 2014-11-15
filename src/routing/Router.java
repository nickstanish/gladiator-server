package routing;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import net.vizbits.gladiator.server.ClientState;
import net.vizbits.gladiator.server.Constants;
import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.WaitingQueue;
import net.vizbits.gladiator.server.exceptions.ActionDoesNotExistException;
import net.vizbits.gladiator.server.exceptions.GladiatorMissingInActionException;
import net.vizbits.gladiator.server.game.Arena;
import net.vizbits.gladiator.server.game.Gladiator;
import net.vizbits.gladiator.server.request.BaseRequest;
import net.vizbits.gladiator.server.response.ConnectingResponse;
import net.vizbits.gladiator.server.service.ClientService;
import net.vizbits.gladiator.server.utils.JsonUtils;
import net.vizbits.gladiator.server.utils.LogUtils;

public class Router {


  private static Map<String, RouteAction> map;

  private Router() {

  }

  private static void initSingleton() {
    map = new HashMap<String, RouteAction>();
    map.put(Constants.GAME_REQUEST, (a, b) -> gameRequest(a, b));
    map.put(Constants.WAIT_REQUEST, (a, b) -> waitRequest(a, b));
  }

  public static void route(String action, GladiatorClient gladiatorClient, BaseRequest baseRequest)
      throws ActionDoesNotExistException {
    initSingleton();
    if (!map.containsKey(action))
      throw new ActionDoesNotExistException(action);
    else
      map.get(action).apply(gladiatorClient, baseRequest);
  }

  private static void gameRequest(GladiatorClient gladiatorClient, BaseRequest baseRequest) {
    WaitingQueue waitingQueue = gladiatorClient.getWaitingQueue();
    ClientService clientService = gladiatorClient.getClientService();
    if (gladiatorClient.getClientState() != ClientState.Ready) {
      LogUtils.logError("Client is requesting a game when not in ready state");
      return;
    }

    String username = gladiatorClient.getUsername();
    String opponent = waitingQueue.dequeue();
    if (opponent == null) {
      waitingQueue.enqueue(username);
      gladiatorClient.setClientState(ClientState.Waiting);
      ConnectingResponse response =
          new ConnectingResponse(false, waitingQueue.positionInQueue(username), waitingQueue.size());
      JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
      return;

    } else {
      Gladiator team1 = gladiatorClient.getGladiator();
      Gladiator team2;
      GladiatorClient opponentClient;
      try {
        opponentClient = clientService.findGladiatorByName(opponent);
        team2 = opponentClient.getGladiator();
      } catch (GladiatorMissingInActionException e) {
        LogUtils.logError(e);
        // game cancelled, tell waiting person
        return;
      }
      Arena arena = new Arena(team1, team2);
      gladiatorClient.setArena(arena);
      opponentClient.setArena(arena);
      gladiatorClient.setClientState(ClientState.Battle);
      opponentClient.setClientState(ClientState.Battle);
      ConnectingResponse response = new ConnectingResponse(true, null, null);
      JsonUtils.writeToSocket(gladiatorClient.getOut(), response);

    }
  }

  private static void waitRequest(GladiatorClient gladiatorClient, BaseRequest baseRequest) {
    WaitingQueue waitingQueue = gladiatorClient.getWaitingQueue();
    ClientService clientService = gladiatorClient.getClientService();
    if (gladiatorClient.getClientState() == ClientState.Battle) {
      ConnectingResponse response = new ConnectingResponse(true, null, null);
      JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
      return;
    }
    String username = gladiatorClient.getUsername();
    ConnectingResponse response =
        new ConnectingResponse(false, waitingQueue.positionInQueue(username), waitingQueue.size());
    JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
    return;
  }

}
