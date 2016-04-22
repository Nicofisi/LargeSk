package pl.pickaxe.largesk.viaversion;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.myles.ViaVersion.api.ViaVersion;
import us.myles.ViaVersion.api.ViaVersionAPI;

public class ExprMinecraftClientVersion extends SimpleExpression<Integer> {

  Expression<Player> pl;

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
    pl = (Expression<Player>) expr[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event e, boolean arg1) {
    return "minecraft version of " + pl.getSingle(e).toString();
  }

  @Override
  @Nullable
  protected Integer[] get(Event e) {
    if (pl == null)
      return null;
    int version;
    ViaVersionAPI instance = ViaVersion.getInstance();
    version = instance.getPlayerVersion(pl.getSingle(e));
    return new Integer[] {version};
  }

  public static void register() {
    Skript.registerExpression(ExprMinecraftClientVersion.class, Integer.class,
        ExpressionType.SIMPLE, "(protocol|(mc|minecraft)) ver[sion] of %player%",
        "%player%'s (protocol|(mc|minecraft)) ver[sion]");
  }
}
