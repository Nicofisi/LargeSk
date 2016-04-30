package pl.pickaxe.largesk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.konsolas.aac.api.HackType;

public class EvtPlayerViolation extends Event {

	Player player;
	HackType hack;
	String message;
	Integer violations;

	public EvtPlayerViolation(Player player, HackType hack, String message, Integer violations) {
		this.player = player;
		this.hack = hack;
		this.message = message;
		this.violations = violations;
	}

	public Player getPlayer() {
		return player;
	}

	public HackType getHack() {
		return hack;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Integer getViolations() {
		return violations;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}