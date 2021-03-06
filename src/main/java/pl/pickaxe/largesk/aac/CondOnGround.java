package pl.pickaxe.largesk.aac;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class CondOnGround extends Condition {

  private Expression<Player> p;

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
    p = (Expression<Player>) e[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "aac player on ground";
  }

  @Override
  public boolean check(Event e) {
    if (AACAPIProvider.isAPILoaded() && AACAPIProvider.getAPI().isPlayerOnGround(p.getSingle(e))) {
      return true;
    } else {
      return false;
    }
  }

  public static void register() {
    Skript.registerCondition(CondOnGround.class, "[aac] %player%('s| is) on ground");
  }
}
