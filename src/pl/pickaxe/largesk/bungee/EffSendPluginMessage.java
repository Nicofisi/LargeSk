package pl.pickaxe.largesk.bungee;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.LargeSk;

public class EffSendPluginMessage extends Effect
{
	
	private Expression<String> msg;
	private Expression<String> srv;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3)
	{
		msg = (Expression<String>) expr[0];
		srv = (Expression<String>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1)
	{
		return null;
	}

	@Override
	protected void execute(Event e)
	{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		String rec = srv.getSingle(e);
		String msag = msg.getSingle(e);
		if (rec == null || rec.equalsIgnoreCase("all"))
		{
			rec = "ALL";
		}
		out.writeUTF(rec);
		out.writeUTF("LargeSkEff");
		out.writeUTF(msag);
		
		Player pl = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (pl == null)
		{
			//throw new SkriptAPIException("Unfortunately, you can't send nor receive proxy messages when there are no players online. Add a check for that to your script to remove the error.");
			return;
		}
		pl.sendPluginMessage(LargeSk.getPlugin(), "BungeeCord", out.toByteArray());
	}
}
