package pl.pickaxe.largesk.aac;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.events.EvtPlayerViolation;

public class ExprHack extends SimpleExpression<HackType> {
  HackType hack;

  @Override
  public Class<? extends HackType> getReturnType() {
    return HackType.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerViolation.class)) {
      Skript.error(
          "The hack expression can only be used inside Player Violation Event. Else you may want to use %hacktype%");
      return false;
    }
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "Hack Expression";
  }

  @Override
  @Nullable
  protected HackType[] get(Event e) {
    EvtPlayerViolation event = ((EvtPlayerViolation) e);
    hack = event.getHack();
    return new HackType[] { hack };
  }

  public static void register() {
    Skript.registerExpression(ExprHack.class, HackType.class, ExpressionType.SIMPLE, "hack", "cheat", "violation");
  }
}
