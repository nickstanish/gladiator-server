package net.vizbits.gladiator.server.routing;

import net.vizbits.gladiator.server.ClientState;
import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.WaitingQueue;
import net.vizbits.gladiator.server.exceptions.GladiatorMissingInActionException;
import net.vizbits.gladiator.server.game.Arena;
import net.vizbits.gladiator.server.game.Gladiator;
import net.vizbits.gladiator.server.request.BaseRequest;
import net.vizbits.gladiator.server.response.ConnectingResponse;
import net.vizbits.gladiator.server.service.ClientService;
import net.vizbits.gladiator.server.utils.JsonUtils;
import net.vizbits.gladiator.server.utils.LogUtils;

public class GameRequestHandler {
  public static void gameRequest(GladiatorClient gladiatorClient, BaseRequest baseRequest,
      String json) {
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
          new ConnectingResponse(false, waitingQueue.positionInQueue(username),
              waitingQueue.size(), clientService.size());
      JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
      return;

    } else {
      GladiatorClient opponentClient;
      try {
        opponentClient = clientService.findGladiatorByName(opponent);
      } catch (GladiatorMissingInActionException e) {
        LogUtils.logError(e);
        // game cancelled, tell waiting person
        return;
      }
      Arena arena = new Arena(gladiatorClient.getGladiator(), opponentClient.getGladiator());
      gladiatorClient.setArena(arena);
      opponentClient.setArena(arena);
      gladiatorClient.setClientState(ClientState.Battle);
      opponentClient.setClientState(ClientState.Battle);
      ConnectingResponse response = new ConnectingResponse(true, null, null, clientService.size());
      JsonUtils.writeToSocket(gladiatorClient.getOut(), response);

    }
  }

  public static void waitRequest(GladiatorClient gladiatorClient, BaseRequest baseRequest,
      String json) {
    WaitingQueue waitingQueue = gladiatorClient.getWaitingQueue();
    ClientService clientService = gladiatorClient.getClientService();
    if (gladiatorClient.getClientState() == ClientState.Battle) {
      ConnectingResponse response = new ConnectingResponse(true, null, null, clientService.size());
      JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
      return;
    }
    String username = gladiatorClient.getUsername();
    ConnectingResponse response =
        new ConnectingResponse(false, waitingQueue.positionInQueue(username), waitingQueue.size(),
            clientService.size());
    JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
    return;
  }
}
