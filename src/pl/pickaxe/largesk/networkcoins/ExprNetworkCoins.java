package pl.pickaxe.largesk.networkcoins;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.josh.networkcoins.API;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import com.google.common.primitives.Ints;

import javax.annotation.Nullable;

public class ExprNetworkCoins extends SimpleExpression<Integer> {

	private Expression<OfflinePlayer> pl;
	
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
		pl = (Expression<OfflinePlayer>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "network coins of " + pl.toString();
	}

	@Override
	protected Integer[] get(Event e) {
		return new Integer[] { API.getCoinsOffline((OfflinePlayer) pl.getSingle(e)) };
	}
	public void change(Event e, Object[] delta, Changer.ChangeMode mode)
	{
		
		OfflinePlayer o = (OfflinePlayer) pl.getSingle(e);

		if (o == null)
			return;
		if ( ! API.isInDb(o))
			return;
		if (mode == ChangeMode.SET)
		{
			Integer wanted = Ints.checkedCast((Long) delta[0]);
			Integer before = API.getCoinsOffline(o);
			if (wanted == null || before == null || before == wanted)
				return;
			else if (before < wanted)
			{
				API.addCoinsOffline(o, (int) wanted - before);
			}
			else if (before > wanted)
			{
				API.takeCoinsOffline(o, (int) (before - wanted));
			}
		}
		if (mode == ChangeMode.ADD)
		{
			Integer wanted = Ints.checkedCast((Long) delta[0]);
			API.addCoinsOffline(o, wanted);
		}
		if (mode == ChangeMode.REMOVE)
		{
			Integer wanted = Ints.checkedCast((Long) delta[0]);
			API.takeCoinsOffline(o, wanted);
		}
		if (mode == ChangeMode.RESET)
		{
			API.resetCoinsOffline(pl.getSingle(e));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}	
}
