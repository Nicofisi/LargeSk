package pl.pickaxe.largesk;

import org.eclipse.jdt.annotation.Nullable;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.entity.Player;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNameOfPlayer extends SimpleExpression<String> {

	private Expression<Player> player;
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) expr[0];
		return false;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "name of player";
		return null;
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
