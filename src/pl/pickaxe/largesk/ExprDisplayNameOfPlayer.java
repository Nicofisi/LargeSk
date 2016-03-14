package pl.pickaxe.largesk;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprDisplayNameOfPlayer extends SimpleExpression<String> {
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
	public boolean init(Expression<?>[] expr, int matcherPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "great display name of player";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{player.getSingle(e).getDisplayName()};
	}
	
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			player.getSingle(e).setDisplayName((String)delta[0]);
		if (mode == ChangeMode.ADD)
			player.getSingle(e).setDisplayName(player.getSingle(e).getDisplayName() + (String)delta[0]);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD)
			return CollectionUtils.array(String.class);
		return null;
	}
}	
