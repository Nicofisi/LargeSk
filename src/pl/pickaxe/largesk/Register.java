package pl.pickaxe.largesk;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.aac.CondCheckEnabled;
import pl.pickaxe.largesk.aac.CondIsBypassed;
import pl.pickaxe.largesk.aac.CondOnGround;
import pl.pickaxe.largesk.aac.EffDisableCheck;
import pl.pickaxe.largesk.aac.EffEnableCheck;
import pl.pickaxe.largesk.aac.EffReloadAAC;
import pl.pickaxe.largesk.aac.EffReloadPermissionCache;
import pl.pickaxe.largesk.aac.ExprAacPing;
import pl.pickaxe.largesk.aac.ExprAacTps;
import pl.pickaxe.largesk.aac.ExprViolationLevel;
import pl.pickaxe.largesk.bungee.EffSendPluginMessage;
import pl.pickaxe.largesk.bungee.EvtPluginMessageReceived;
import pl.pickaxe.largesk.bungee.LargeMessenger;
import pl.pickaxe.largesk.effects.EffDisableAllPlugins;
import pl.pickaxe.largesk.effects.EffLagServer;
import pl.pickaxe.largesk.events.EvtPlayerChunkChange;
import pl.pickaxe.largesk.events.EvtPlayerViolation;
import pl.pickaxe.largesk.events.PlayerChunkChangeEvt;
import pl.pickaxe.largesk.events.PlayerViolationEvt;
import pl.pickaxe.largesk.expressions.ExprFullTime;
import pl.pickaxe.largesk.expressions.ExprLinkAsk;
import pl.pickaxe.largesk.expressions.ExprLinkBing;
import pl.pickaxe.largesk.expressions.ExprLinkDuckDuckGo;
import pl.pickaxe.largesk.expressions.ExprLinkGoogle;
import pl.pickaxe.largesk.expressions.ExprNewChunk;
import pl.pickaxe.largesk.expressions.ExprOldChunk;
import pl.pickaxe.largesk.expressions.ExprPastebin;
import pl.pickaxe.largesk.expressions.ExprUrlDecode;
import pl.pickaxe.largesk.expressions.ExprUrlEncode;
import pl.pickaxe.largesk.protocollib.EffMakePlayersSleep;
import pl.pickaxe.largesk.skinsrestorer.CondPlayerHasSkin;
import pl.pickaxe.largesk.skinsrestorer.ExprSkinOfPlayer;
import pl.pickaxe.largesk.util.EnumClassInfo;
import pl.pickaxe.largesk.util.Metrics;
import pl.pickaxe.largesk.util.Xlog;

public class Register
{
	FileConfiguration config = LargeSk.getPlugin().getConfig();
	
	public void registerAll()
	{
		
		//General Expressions
		Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "(full|total)[ ]time of %world%","%world%'s (full|total)[ ]time");
		Skript.registerExpression(ExprPastebin.class, String.class, ExpressionType.SIMPLE, "pastebin upload %string% [(named|[with] name) %-string%] [[with ]expire date %-string%] [[with ]paste (format|language) %-string%]");
		Skript.registerExpression(ExprLinkGoogle.class, String.class, ExpressionType.SIMPLE, "google link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkBing.class, String.class, ExpressionType.SIMPLE, "bing link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkDuckDuckGo.class, String.class, ExpressionType.SIMPLE, "duckduckgo link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkAsk.class, String.class, ExpressionType.SIMPLE, "ask link (of|to) [search] %string%");
		Skript.registerExpression(ExprUrlEncode.class, String.class, ExpressionType.SIMPLE, "url encoded %string%");
		Skript.registerExpression(ExprUrlDecode.class, String.class, ExpressionType.SIMPLE, "url decoded %string%");
		Skript.registerExpression(ExprNewChunk.class, Chunk.class, ExpressionType.SIMPLE, "new chunk");
		Skript.registerExpression(ExprOldChunk.class, Chunk.class, ExpressionType.SIMPLE, "old chunk");
		
		//Lag expression disable check
		if (config.getConfigurationSection("enable").getBoolean("lag"))
		{
			Skript.registerEffect(EffLagServer.class, "lag [the] server for %timespan%","(make|create) a %timespan% lag[[ ]spike]");
		}
		
		//General Events
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
		
		//General Effects
		Skript.registerEffect(EffDisableAllPlugins.class, "disable all plugins","disable every plugin");
		
		//AAC
		if (isPluginOnServer("AAC") && config.getConfigurationSection("enable").getBoolean("AAC"))
		{
			Xlog.logInfo("I have found AAC on your server. I'm pleased to announce we will work together from now.");
			EnumClassInfo.create(HackType.class, "hacktype").register();
			Skript.registerCondition(CondIsBypassed.class, "[aac] %player%('s| is) bypass(ed by|ing) aac");
			Skript.registerCondition(CondOnGround.class, "[aac] %player%('s| is) on ground");
			Skript.registerCondition(CondCheckEnabled.class, "[aac] (check %-hacktype%|%-hacktype% check) is (enabled|on|running)");
			Skript.registerExpression(ExprAacPing.class, Integer.class, ExpressionType.PROPERTY, "aac (ping of %player%|%player%'s ping)", "[aac] (ping of %player%|%player%'s ping) by aac");
			Skript.registerExpression(ExprAacTps.class, Double.class, ExpressionType.SIMPLE, "[aac] tps","tps (of|by) aac");
			Skript.registerExpression(ExprViolationLevel.class, Integer.class, ExpressionType.PROPERTY, "%player%['s][ aac] [hack[s]|violation[s]|cheat[s]] level of %hacktype%","[aac ] %hacktype% [hack[s]|violation[s]|cheat[s]] level of %player%","%player%'s vl of %hacktype%", "%hacktype% vl of %player%");
			Skript.registerEffect(EffReloadAAC.class, "aac reload [config[s]]","reload aac [config[s]]","reload config[s] of aac");
			Skript.registerEffect(EffReloadPermissionCache.class, "aac reload permission(s|[s] cache)","reload permission(s|[s] cache) of aac","reload aac['s] permission(s|[s] cache)");
			Skript.registerEffect(EffEnableCheck.class, "enable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
			Skript.registerEffect(EffDisableCheck.class, "disable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
	
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
		//SkinsRestorer
		if (isPluginOnServer("SkinsRestorer") && config.getConfigurationSection("enable").getBoolean("SkinsRestorer"))
		{
			Xlog.logInfo("SkinsRestorer has been detected! Registring conditions, expressions and other boring stuff..");
			Skript.registerExpression(ExprSkinOfPlayer.class, String.class, ExpressionType.PROPERTY, "skin of %offlineplayer%","%offlineplayer%'s skin");
			Skript.registerCondition(CondPlayerHasSkin.class, "%offlineplayer% (has|have) [a] skin");
		}

		//ProtocolLib
		if (isPluginOnServer("ProtocolLib"))
		{
			Xlog.logInfo("Yeah, ProtocolLib! Let's go crazy..");
			Skript.registerEffect(EffMakePlayersSleep.class, "(make|force) %player% sleep");
		}
		
		//Metrics
		if (config.getBoolean("enableMetrics"))
		{
			if (LargeSk.debug)
				Xlog.logInfo("Trying to enable Metrics..");
			try
			{
				Metrics metrics = new Metrics(LargeSk.getPlugin());
				metrics.start();
			}
			catch (IOException e)
			{
				Xlog.logWarning("Enabling Metrics failed ¯\\_(ツ)_/¯");
				e.printStackTrace();
			}
		}
		else
		{
			Xlog.logInfo("You have disabled Metrics, sorry to hear that but it's not my problem ¯\\_(ツ)_/¯");
		}
		
		//BungeeCord
		if (config.getConfigurationSection("bungee").getBoolean("use"))
		{
			//Message
			Xlog.logInfo("You use BungeeCord! I love it <3");
			
			//Register the event below
			LargeMessenger msg = new LargeMessenger();
			msg.getMessenger().registerMessenger();
			
			//Effect
			Skript.registerEffect(EffSendPluginMessage.class, "proxy send %string% [to %-string%]");
			
			//The PluginMessageReceived Event
			Skript.registerEvent("Message Receive", SimpleEvent.class,
			EvtPluginMessageReceived.class, new String[] { "message [(receiv(e|ing)|get[ting])]" });
			
			//EvtPluginMessageReceived getMessage()
			EventValues.registerEventValue(EvtPluginMessageReceived.class,
			String.class, new Getter<String, EvtPluginMessageReceived>() {
				@Override
				public String get(
						EvtPluginMessageReceived event){
					return event.getMessage();
				}
			}, 0);	
		}
	}
	public boolean isPluginOnServer(String name)
	{
		Plugin pl = Bukkit.getServer().getPluginManager().getPlugin(name);
		if (pl == null)
			return false;
		return true;
	}
}
