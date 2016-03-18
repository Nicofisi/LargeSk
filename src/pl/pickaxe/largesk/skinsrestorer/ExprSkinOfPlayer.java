package pl.pickaxe.largesk.skinsrestorer;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import skinsrestorer.shared.api.SkinsRestorerAPI;
import skinsrestorer.shared.utils.SkinFetchUtils.SkinFetchFailedException;

public class ExprSkinOfPlayer extends SimpleExpression<String> {

	//<skin of %player%| %player%'s skin>
	
	private Expression<OfflinePlayer> p;
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		p = (Expression<OfflinePlayer>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "skin of player";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[] {SkinsRestorerAPI.getSkinName(p.getSingle(e).getName())};
	}
	
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			try { SkinsRestorerAPI.setSkin(p.getSingle(e).getName(), (String)delta[0]);
			} catch (SkinFetchFailedException ex) {}
		if (mode == ChangeMode.REMOVE)
			if (SkinsRestorerAPI.hasSkin(p.getSingle(e).getName()))
				try { SkinsRestorerAPI.setSkin(p.getSingle(e).getName(), p.getSingle(e).getName());
				} catch (SkinFetchFailedException ex) {}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(String.class);
		return null;
	}
}
