package pl.pickaxe.largesk.aac;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class ExprAacPing extends SimpleExpression<Integer>
{
	// aac ping
	
	private Expression<Player> player;
	
	@Override
	public Class<? extends Integer> getReturnType()
	{
		return Integer.class;
	}

	@Override
	public boolean isSingle()
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3)
	{
		player = (Expression<Player>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1)
	{
		return "aac ping";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e)
	{
		if (AACAPIProvider.isAPILoaded())
		{
			return new Integer[]{AACAPIProvider.getAPI().getPing(player.getSingle(e))};
		}
		else
		{
			return new Integer[]{0};
		}
	}
	
}
