package pl.pickaxe.largesk.aac;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPI;

public class CondIsPlayerOnGround extends Condition {

	private Expression<Player> p;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		p = (Expression<Player>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "is player on ground";
	}

	@Override
	public boolean check(Event e) {
		if (AACAPI.isPlayerOnGround(p.getSingle(e)) == true) {
			return true;
		} else {
			return false;
		}
	}

}
