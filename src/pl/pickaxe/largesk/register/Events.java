package pl.pickaxe.largesk.register;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.LargeSk;
import pl.pickaxe.largesk.events.EvtPlayerChunkChange;
import pl.pickaxe.largesk.events.EvtPlayerViolation;
import pl.pickaxe.largesk.events.PlayerChunkChangeEvt;
import pl.pickaxe.largesk.events.PlayerViolationEvt;

public class Events {
	
	public void registerGeneral()
	{
		//Chunk Change Event
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerChunkChangeEvt(), LargeSk.getPlugin());
		
		Skript.registerEvent("chunk change", SimpleEvent.class,
		EvtPlayerChunkChange.class, new String[] { "chunk change" });
		
		//EvtPlayerViolation getPlayer()
		EventValues.registerEventValue(EvtPlayerChunkChange.class,
		Player.class, new Getter<Player, EvtPlayerChunkChange>() {
			@Override
			public Player get(
					EvtPlayerChunkChange event) {
				return event.getPlayer();
			}
		}, 0);
		
		//EvtPlayerViolation getFrom()
				EventValues.registerEventValue(EvtPlayerChunkChange.class,
				Chunk.class, new Getter<Chunk, EvtPlayerChunkChange>() {
					@Override
					public Chunk get(
							EvtPlayerChunkChange event) {
						return event.getFrom();
					}
				}, 0);
				
		//EvtPlayerViolation getTo()
		EventValues.registerEventValue(EvtPlayerChunkChange.class,
		Chunk.class, new Getter<Chunk, EvtPlayerChunkChange>() {
			@Override
			public Chunk get(
					EvtPlayerChunkChange event) {
				return event.getTo();
			}
		}, 0);
	}
	
	public void registerAAC()
	{
		//Register Events
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerViolationEvt(), LargeSk.getPlugin());
		
		//Register EvtPlayerViolation to Skript
		Skript.registerEvent("Player Violation", SimpleEvent.class,
		EvtPlayerViolation.class, new String[] { "violation","hack","cheat" });
		
		//EvtPlayerViolation getPlayer()
		EventValues.registerEventValue(EvtPlayerViolation.class,
		Player.class, new Getter<Player, EvtPlayerViolation>() {
			@Override
			public Player get(
					EvtPlayerViolation event) {
				return event.getPlayer();
			}
		}, 0);

		//EvtPlayerViolation getHack()
		EventValues.registerEventValue(EvtPlayerViolation.class,
		HackType.class, new Getter<HackType, EvtPlayerViolation>() {
			@Override
			public HackType get(
					EvtPlayerViolation event) {
				return event.getHack();
			}
		}, 0);

		//EvtPlayerViolation getMessage()
		EventValues.registerEventValue(EvtPlayerViolation.class,
		String.class, new Getter<String, EvtPlayerViolation>() {
			@Override
			public String get(
					EvtPlayerViolation event) {
				return event.getMessage();
			}
	
		//EvtPlayerViolation getViolations()
		}, 0);
		EventValues.registerEventValue(EvtPlayerViolation.class,
		Integer.class, new Getter<Integer, EvtPlayerViolation>() {
			@Override
			public Integer get(
					EvtPlayerViolation event) {
				return event.getViolations();
			}
		}, 0);	
	}
}
