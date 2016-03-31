package pl.pickaxe.largesk.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.LargeSk;
import pl.pickaxe.largesk.util.Xlog;

public class ExprPlayersSneaking extends SimpleExpression<Player> {

	private Expression<Player> players;
	
	static
	{
		if (LargeSk.debug)
			Xlog.logInfo("Test");
	}
	
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
		for (Player l : players.getArray(e))
		{
			if (l.isSneaking())
			{
				r.add(l);
			}
		}
		return r.toArray(new Player[r.size()]);
	}

}
