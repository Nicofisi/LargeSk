package pl.pickaxe.largesk.aac;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;

public class CondCheckEnabled extends Condition {

	private Expression<HackType> hack;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		hack = (Expression<HackType>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "aac check enabled";
	}

	@Override
	public boolean check(Event e) {
		
		if (AACAPIProvider.isAPILoaded() && AACAPIProvider.getAPI().isEnabled(hack.getSingle(e)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
