package pl.pickaxe.largesk.GeneralEffects;

import org.bukkit.Server;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffDisableAllPlugins extends Effect {
	
	private Server sr;
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "disable all plugins";
	}

	@Override
	protected void execute(Event arg0) {
		sr.getPluginManager().disablePlugins();
	}

}
