package pl.pickaxe.largesk.conditions;

import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondEvaluateCondition extends Condition 
{
	
	private Expression<String> condition;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		condition = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "evaluate condition " + condition.getSingle(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean check(Event e) {
		String cond = condition.getSingle(e);
		try 
		{
			ScriptLoader.setCurrentEvent("this", e.getClass());
			Condition c = Condition.parse(cond, null);
			ScriptLoader.deleteCurrentEvent();
			boolean answer = c.check(e);
			if (answer == true)
				return true;
		}
		catch (Exception ignored) {}
		return false;
	}
	
	public static void register()
	{
		Skript.registerCondition(CondEvaluateCondition.class,
				"[evaluate] cond[ition] %string%");
	}
}
