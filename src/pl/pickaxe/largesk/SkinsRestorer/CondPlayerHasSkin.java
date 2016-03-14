package pl.pickaxe.largesk.SkinsRestorer;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import skinsrestorer.shared.api.SkinsRestorerAPI;

public class CondPlayerHasSkin extends Condition {
	
	//%player% has skin

	private Expression<Player> p;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		p = (Expression<Player>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "player has skin";
	}

	@Override
	public boolean check(Event e) {
		if (SkinsRestorerAPI.hasSkin(p.getSingle(e).getName()) == true) {
			return true;
		} else {
			return false;
		}
	}
	
}
