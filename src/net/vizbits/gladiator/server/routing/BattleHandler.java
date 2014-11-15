package net.vizbits.gladiator.server.routing;

import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.game.Arena;
import net.vizbits.gladiator.server.game.Gladiator;
import net.vizbits.gladiator.server.request.BaseRequest;
import net.vizbits.gladiator.server.request.CharacterRequest;
import net.vizbits.gladiator.server.response.BattleStatusResponse;
import net.vizbits.gladiator.server.utils.JsonUtils;

public class BattleHandler {
  public static void CharacterChoice(GladiatorClient gladiatorClient, BaseRequest baseRequest,
      String json) {
    Gladiator gladiator = JsonUtils.fromString(json, CharacterRequest.class).data;
    gladiatorClient.setGladiator(gladiator);
    Arena arena = gladiatorClient.getArena();
    arena.setGladiator(gladiator);
    if (arena.isReady()) {
      arena.startBattle();
    }
    BattleStatusResponse response = new BattleStatusResponse();
    response.game_ready = arena.isReady();
    response.your_turn = arena.isMyTurn(gladiator);
    JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
  }

  public static void BattleSequence(GladiatorClient gladiatorClient, BaseRequest baseRequest,
      String json) {
    Gladiator me = gladiatorClient.getGladiator();
    Arena arena = gladiatorClient.getArena();
    Gladiator foe = arena.getFoe(me);
    if (arena.isMyTurn(me) && baseRequest.getData() != null) {
      arena.applyAttack(me, foe, (double) baseRequest.getData());
      arena.swapTurns();
    }

    BattleStatusResponse response =
        new BattleStatusResponse(arena.isReady(), arena.isMyTurn(me), me, foe);
    JsonUtils.writeToSocket(gladiatorClient.getOut(), response);
  }
}
