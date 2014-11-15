package net.vizbits.gladiator.server.response;

import net.vizbits.gladiator.server.game.Arena;
import net.vizbits.gladiator.server.game.CharacterClass;
import net.vizbits.gladiator.server.game.Gladiator;

public class BattleStatusResponse {
  public Boolean game_ready;
  public Boolean your_turn;
  public Double me_health;
  public Double me_maxhealth;
  public Double foe_health;
  public Double foe_maxhealth;
  public String me;
  public String foe;
  public CharacterClass foe_class;

  public BattleStatusResponse() {

  }

  public BattleStatusResponse(boolean started, boolean my_turn, Arena arena, Gladiator me,
      Gladiator foe) {
    this.game_ready = started;
    this.your_turn = my_turn;
    this.me_health = me.current_hp;
    this.me_maxhealth = me.max_hp;
    this.me = me.name;
    if (started) {
      if (foe.name == null) {
        this.foe = arena.getFoeName(me);
      } else {
        this.foe = foe.name;
      }
      this.foe_health = foe.current_hp;
      this.foe_maxhealth = foe.max_hp;
      this.foe_class = foe.character_class;
    }

  }
}
