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

  public boolean isStarted() {
    return started;
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

  public Gladiator getFoe(Gladiator me) {
    if (me.name.equals(username1)) {
      return team2;
    } else {
      return team1;
    }
  }

  public String getFoeName(Gladiator me) {
    if (me.name.equals(username1)) {
      return username2;
    } else {
      return username1;
    }
  }

  public void applyAttack(Gladiator me, Gladiator foe, double data) {
    if (data < 0) {
      me.current_hp -= data;
      if (me.current_hp > me.max_hp) {
        me.current_hp = me.max_hp;
      }
    } else {
      foe.current_hp -= data;
    }

  }

  public void swapTurns() {
    if (turn.equals(team1))
      turn = team2;
    else
      turn = team1;

  }
}
