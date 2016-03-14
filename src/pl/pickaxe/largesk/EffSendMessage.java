package pl.pickaxe.largesk;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSendMessage extends Effect {
	private Expression<Player> player;
	private Expression<String> message;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		message =  (Expression<String>) expr [0];
		player = (Expression<Player>) expr [1];
		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean ParamBoolean) {
		return "send great message to player";
	}

	@Override
	protected void execute(Event e) {
		player.getSingle(e).sendMessage(message.getSingle(e));
		
	}
	
}
