package pl.pickaxe.largesk.effects;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

public class EffLagServer extends Effect
{
	private Expression<Timespan> time;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3)
	{
		time = (Expression<Timespan>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1)
	{
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	protected void execute(Event e)
	{
		int something = 2;
		Long start = System.currentTimeMillis();
		@SuppressWarnings("deprecation")
		Long stop = System.currentTimeMillis() + (time.getSingle(e).getTicks() * 50);
		do
		{
			something++;
			something--;
		}
		while (stop > System.currentTimeMillis());
	}

}
