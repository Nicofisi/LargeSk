package pl.pickaxe.largesk.aac;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;

public class EffDisableCheck extends Effect {
  private Expression<HackType> check;

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    check = (Expression<HackType>) expr[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "disable aac check";
  }

  @Override
  protected void execute(Event e) {
    if (AACAPIProvider.isAPILoaded())
      AACAPIProvider.getAPI().disableCheck(check.getSingle(e));
  }

  public static void register() {
    Skript.registerEffect(EffDisableCheck.class, "disable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
  }
}
