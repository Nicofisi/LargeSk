package pl.pickaxe.largesk.expressions;

import org.bukkit.World;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprFullTime extends SimpleExpression<Long> {
  // full time of %world%

  private Expression<World> world;

  @Override
  public Class<? extends Long> getReturnType() {
    return Long.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    world = (Expression<World>) expr[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "full time of world";
  }

  @Override
  @Nullable
  protected Long[] get(Event e) {
    return new Long[] { world.getSingle(e).getFullTime() };
  }

  public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
    if (mode == ChangeMode.SET) {
      world.getSingle(e).setFullTime((Long) delta[0]);
    } else if (mode == ChangeMode.ADD) {
      world.getSingle(e).setFullTime(world.getSingle(e).getFullTime() + (Long) delta[0]);
    } else if (mode == ChangeMode.RESET) {
      world.getSingle(e).setFullTime(0);
    } else if (mode == ChangeMode.REMOVE) {
      if (world.getSingle(e).getFullTime() > (Long) delta[0]) {
        world.getSingle(e).setFullTime(world.getSingle(e).getFullTime() - (Long) delta[0]);
      } else {
        world.getSingle(e).setFullTime(0);
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
    if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE)
      return CollectionUtils.array(Long.class);
    return null;
  }
  public static void register() {
    Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "(full|total)[ ]time of %world%",
        "%world%'s (full|total)[ ]time");
  }
}
