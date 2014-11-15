package net.vizbits.gladiator.server.game;

import net.vizbits.gladiator.server.utils.LogUtils;


public class Arena {
  private Gladiator team1;
  private Gladiator team2;
  private Boolean ready;
  private Gladiator turn;
  private Boolean started;
  private String username1;
  private String username2;

  public Arena(String username1, String username2) {
    this.username1 = username1;
    this.username2 = username2;
    ready = false;
    started = false;
  }

  public void startBattle() {
    turn = team1;
    started = true;
  }

  public boolean isMyTurn(Gladiator gladiator) {
    if (!started)
      return false;
    return gladiator.name.equals(turn.name);
  }

  public boolean isReady() {
    if (team1 != null && team2 != null) {
      ready = true;
    }
    return ready;
  }

  public synchronized void setGladiator(Gladiator gladiator) {
    LogUtils.logInfo("Assing gladiator '" + gladiator.name + "' to arena");
    if (gladiator.name.equals(username1)) {
      team1 = gladiator;
    } else {
      team2 = gladiator;
    }

  }
}
