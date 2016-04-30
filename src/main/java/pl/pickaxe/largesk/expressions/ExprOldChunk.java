package pl.pickaxe.largesk.expressions;

import org.bukkit.Chunk;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.events.EvtPlayerChunkChange;

public class ExprOldChunk extends SimpleExpression<Chunk> {
  Chunk chunk;

  @Override
  public Class<? extends Chunk> getReturnType() {
    return Chunk.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @Override
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerChunkChange.class)) {
      Skript.error("You can only use the Old Chunk expression inside Chunk Change event");
      return false;
    }
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "old chunk";
  }

  @Override
  @Nullable
  protected Chunk[] get(Event e) {
    EvtPlayerChunkChange event = ((EvtPlayerChunkChange) e);
    return new Chunk[] {event.getFrom()};
  }

  public static void register() {
    Skript.registerExpression(ExprOldChunk.class, Chunk.class, ExpressionType.SIMPLE, "old chunk");
  }
}
