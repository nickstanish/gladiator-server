package net.vizbits.gladiator.server.game;

import net.vizbits.gladiator.server.utils.LogUtils;


public class Arena {
  private Gladiator team1;
  private Gladiator team2;
  private Gladiator turn;
  private Boolean started;


  public Arena(Gladiator team1, Gladiator team2) {
    this.team1 = team1;
    this.team2 = team2;
    started = false;
  }

  public void startBattle() {
    turn = team1;
    started = true;
  }

  public boolean isMyTurn(Gladiator me) {
    if (!started)
      return false;
    return turn.equals(me);
  }

  public boolean isReady() {
    return started;
  }

  public Gladiator getFoe(Gladiator me) {
    if (me.equals(team1)) {
      return team2;
    } else {
      return team1;
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
    if (turn.equals(team1)) {
      turn = team2;
    } else {
      turn = team1;
    }

  }
}
