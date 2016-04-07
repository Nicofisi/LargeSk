package pl.pickaxe.largesk.effects;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptConfig;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.classes.Changer.ChangerUtils;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.StringUtils;

public class EffReplaceFirst extends Effect {

	private Expression<String> haystack, needles, replacement;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		haystack = (Expression<String>) exprs[1 + matchedPattern];
		if (!ChangerUtils.acceptsChange(haystack, ChangeMode.SET, String.class)) {
			Skript.error(haystack + " cannot be changed and can thus not have parts replaced.");
			return false;
		}
		needles = (Expression<String>) exprs[0];
		replacement = (Expression<String>) exprs[2 - matchedPattern];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	protected void execute(Event e) {
		String h = haystack.getSingle(e);
		final String[] ns = needles.getAll(e);
		final String r = replacement.getSingle(e);
		if (h == null || ns.length == 0 || r == null)
			return;
		for (final String n : ns) {
			assert n != null;
			h = StringUtils.replace(h, n, r, SkriptConfig.caseSensitive.value());
		}
		haystack.change(e, new String[] {h}, ChangeMode.SET);
	}

}
