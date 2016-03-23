package pl.pickaxe.largesk.bungee;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtPluginMessageReceived extends Event {

	String message;

	public EvtPluginMessageReceived(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}