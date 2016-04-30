package pl.pickaxe.largesk.aac;

import org.bukkit.entity.Player;
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
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;

public class ExprViolationLevel extends SimpleExpression<Integer> {

  private Expression<Player> player;
  private Expression<HackType> hack;

  @Override
  public Class<? extends Integer> getReturnType() {
    return Integer.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    player = (Expression<Player>) expr[0];
    hack = (Expression<HackType>) expr[1];
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "aac violation level";
  }

  @Override
  @Nullable
  protected Integer[] get(Event e) {
    if (AACAPIProvider.isAPILoaded()) {
      return new Integer[] { AACAPIProvider.getAPI().getViolationLevel(player.getSingle(e), hack.getSingle(e)) };
    } else {
      return new Integer[] { 0 };
    }
  }

  public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
    Player p = player.getSingle(e);
    HackType h = hack.getSingle(e);
    Integer i = (Integer) delta[0];

    if (mode == ChangeMode.SET) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, i);
    } else if (mode == ChangeMode.ADD) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, AACAPIProvider.getAPI().getViolationLevel(p, h) - i);
    } else if (mode == ChangeMode.REMOVE) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, AACAPIProvider.getAPI().getViolationLevel(p, h) - i);
    } else if (mode == ChangeMode.RESET) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, 0);
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
    Skript.registerExpression(ExprViolationLevel.class, Integer.class, ExpressionType.PROPERTY,
        "%player%['s][ aac] [hack[s]|violation[s]|cheat[s]] level of %hacktype%",
        "[aac ] %hacktype% [hack[s]|violation[s]|cheat[s]] level of %player%", "%player%'s vl of %hacktype%",
        "%hacktype% vl of %player%");
  }

}
