package pl.pickaxe.largesk.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprPlayersFlying extends SimpleExpression<Player> {

  private Expression<Player> players;

  @Override
  public Class<? extends Player> getReturnType() {
    return Player.class;
  }

  @Override
  public boolean isSingle() {
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    players = (Expression<Player>) expr[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event e, boolean arg1) {
    return "players flying";
  }

  @Override
  @Nullable
  protected Player[] get(Event e) {
    final ArrayList<Player> r = new ArrayList<Player>();
    for (Player l : players.getArray(e)) {
      if (l.isFlying()) {
        r.add(l);
      }
    }
    return r.toArray(new Player[r.size()]);
  }

  public static void register() {
    Skript.registerExpression(ExprPlayersFlying.class, Player.class, ExpressionType.SIMPLE, "%players% flying",
        "flying %players%");
  }
}
