package pl.pickaxe.largesk.protocollib;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.util.Xlog;

public class EffMakePlayersSleep extends Effect {
  private Expression<Player> pl;

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    pl = (Expression<Player>) expr[0];
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "make players sleep";
  }

  @Override
  protected void execute(Event e) {
    PacketContainer packet = new PacketContainer(PacketType.Play.Server.BED);
    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(pl.getSingle(e), packet);
    } catch (InvocationTargetException e1) {
      Xlog.logWarning("An error has occured while trying to send bed (sleep) packet");
      e1.printStackTrace();
    }
  }

  public static void register() {
    Skript.registerEffect(EffMakePlayersSleep.class, "(make|force) %player% sleep");
  }
}
