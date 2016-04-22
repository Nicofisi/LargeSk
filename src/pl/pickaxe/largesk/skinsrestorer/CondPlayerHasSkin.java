package pl.pickaxe.largesk.skinsrestorer;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import skinsrestorer.shared.api.SkinsRestorerAPI;

public class CondPlayerHasSkin extends Condition {

  // %player% has skin

  private Expression<OfflinePlayer> p;

  @Override
  @SuppressWarnings("unchecked")
  public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
    p = (Expression<OfflinePlayer>) e[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event e, boolean arg1) {
    return p.getSingle(e).toString() + " has skin";
  }

  @Override
  public boolean check(Event e) {
    if (SkinsRestorerAPI.hasSkin(p.getSingle(e).getName()) == true) {
      return true;
    } else {
      return false;
    }
  }

  public static void register() {
    Skript.registerCondition(CondPlayerHasSkin.class, "%offlineplayer% (has|have) [a] skin");
  }
}
