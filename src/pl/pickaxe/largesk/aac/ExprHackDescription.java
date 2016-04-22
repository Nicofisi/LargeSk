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
import pl.pickaxe.largesk.events.EvtPlayerViolation;

public class ExprHackDescription extends SimpleExpression<String> {
  String desc;

  @Override
  public Class<? extends String> getReturnType() {
    return String.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerViolation.class)) {
      Skript.error("The Hack Description expression can only be used inside Player Violation Event.");
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
  protected String[] get(Event e) {
    EvtPlayerViolation event = ((EvtPlayerViolation) e);
    desc = event.getMessage();
    return new String[] { desc };
  }

  public static void register() {
    Skript.registerExpression(ExprHackDescription.class, String.class, ExpressionType.SIMPLE, "hack desc[ription]",
        "cheat desc[ription]", "violation desc[ription]");
  }
}
