package pl.pickaxe.largesk.aac;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class EffReloadAAC extends Effect {

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "reload AAC";
	}

	@Override
	protected void execute(Event arg0) {
		if (AACAPIProvider.isAPILoaded() == true) {
			AACAPIProvider.getAPI().reloadAAC();
		}
	}

	public static void register() {
	  Skript.registerEffect(EffReloadAAC.class, "aac reload [config[s]]", "reload aac [config[s]]",
	        "reload config[s] of aac");
	}
}
