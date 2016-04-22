package pl.pickaxe.largesk.aac;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class ExprAacTps extends SimpleExpression<Double> {
  // AAC TPS

  @Override
  public Class<? extends Double> getReturnType() {
    return Double.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "aac ping";
  }

  @Override
  @Nullable
  protected Double[] get(Event e) {
    if (AACAPIProvider.isAPILoaded()) {
      return new Double[] { AACAPIProvider.getAPI().getTPS() };
    } else {
      return new Double[] { 0.0 };
    }
  }

  public static void register() {
    Skript.registerExpression(ExprAacTps.class, Double.class, ExpressionType.SIMPLE, "[aac] tps", "tps (of|by) aac");
  }
}
