package pl.pickaxe.largesk.effects;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.util.Xlog;

public class EffLagServer extends Effect {
  private Expression<Timespan> time;

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    time = (Expression<Timespan>) expr[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return null;
  }

  @SuppressWarnings("unused")
  @Override
  protected void execute(Event e) {
    int something = 2;
    try {
      Thread.sleep(time.getSingle(e).getMilliSeconds());
    } catch (InterruptedException e1) {
      Xlog.logWarning("The " + this.toString() + " expression was interrupted: " + e1.getMessage());
    }
  }

  public static void register() {
    Skript.registerEffect(EffLagServer.class, "lag [the] server for %timespan%",
        "(make|create) a %timespan% lag[[ ]spike]");
  }
}
