package pl.pickaxe.largesk.effects;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffThrowNPE extends Effect {

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	protected void execute(Event arg0) {
		throw new NullPointerException("Here you are");
	}
	
	public static void register()
	{
		Skript.registerEffect(EffThrowNPE.class, "throw [a] (npe|null[ ]pointer[ ]exception)");
	}
}
