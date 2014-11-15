package net.vizbits.gladiator.server.game;


public class Arena {
  private Gladiator team1;
  private Gladiator team2;
  private Boolean ready;
  private Gladiator turn;
  private Boolean started;

  public Arena() {
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
    if (team1 == null) {
      team1 = gladiator;
    } else {
      team2 = gladiator;
    }

  }
}
