package pl.pickaxe.largesk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.konsolas.aac.api.HackType;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import pl.pickaxe.largesk.util.Xlog;
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
import pl.pickaxe.largesk.events.EvtPlayerViolation;
import pl.pickaxe.largesk.events.PlayerViolationEvt;
import pl.pickaxe.largesk.expressions.ExprFullTime;
import pl.pickaxe.largesk.expressions.ExprLinkAsk;
import pl.pickaxe.largesk.expressions.ExprLinkBing;
import pl.pickaxe.largesk.expressions.ExprLinkDuckDuckGo;
import pl.pickaxe.largesk.expressions.ExprLinkGoogle;
import pl.pickaxe.largesk.expressions.ExprPastebin;
import pl.pickaxe.largesk.expressions.ExprUrlDecode;
import pl.pickaxe.largesk.expressions.ExprUrlEncode;
import pl.pickaxe.largesk.skinsrestorer.CondPlayerHasSkin;
import pl.pickaxe.largesk.skinsrestorer.ExprSkinOfPlayer;
import pl.pickaxe.largesk.util.EnumClassInfo;
import pl.pickaxe.largesk.util.Metrics;
import pl.pickaxe.largesk.util.SkAddons;

public class LargeSk extends JavaPlugin implements Listener {
	
	
	public static File configf;
    public static FileConfiguration config;
    
    public static LargeSk getPlugin() {
        return LargeSk.getPlugin(LargeSk.class);
    }
    
	@Override
	public void onEnable() {
		
		//Enabling timer
		long eTime = System.currentTimeMillis();
		Server s = getServer();
		Xlog.logInfo(ChatColor.YELLOW  + "=== ENABLE " + ChatColor.GREEN + "START" + ChatColor.YELLOW + " ===");
		
		//Configs
		configf = new File(getDataFolder(), "config.yml");
		
		//Defines the lastest config version
		int lastestConfigVersion = 5;
		
		if ( ! configf.exists() || lastestConfigVersion != getConfig().getInt("configVersion"))
		{
			configf.getParentFile().mkdirs();
			if (configf.exists()) {
				Xlog.logWarning("Your config.yml file is outdated.");
				Xlog.logInfo("I'll copy the default one from the plugin's .jar file in a moment.");
				String path = this.getDataFolder() + "/config-old-ver" + getConfig().getInt("configVersion") + "-" + System.currentTimeMillis();
				File oldConfig = new File(path);
				configf.renameTo(oldConfig);
				Xlog.logInfo("Your old configuration was moved to " + path);
			}
			else
			{
				Xlog.logWarning("The config.yml file does not exist.");
				Xlog.logInfo("I'll copy the default config from the plugin's .jar file now.");
			}
			copy(getResource("config.yml"), configf);
			reloadConfig();
			Xlog.logInfo("Done.");
			Xlog.logInfo("You are now using DEFAULT configuration of the plugin.");
			if ( ! configf.exists() || lastestConfigVersion != getConfig().getInt("configVersion"))
			{
				getLogger().info(getConfig().getInt("configVersion") + lastestConfigVersion + "");
				Xlog.logError("Whooops! The default config file is broken. It's not compatybile with the current version of the plugin.");
				Xlog.logError("All you may do is to contact the developer or use older version of the plugin.");
			}
		}
		
		//Registring Skript addon
		Skript.registerAddon(this);
				
		//General Expressions
		Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "(full|total)[ ]time of %world%","%world%'s (full|total)[ ]time");
		Skript.registerExpression(ExprPastebin.class, String.class, ExpressionType.SIMPLE, "pastebin upload %string% [(named|[with] name) %-string%] [[with ]expire date %-string%] [[with ]paste (format|language) %-string%]");
		Skript.registerExpression(ExprLinkGoogle.class, String.class, ExpressionType.SIMPLE, "google link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkBing.class, String.class, ExpressionType.SIMPLE, "bing link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkDuckDuckGo.class, String.class, ExpressionType.SIMPLE, "duckduckgo link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkAsk.class, String.class, ExpressionType.SIMPLE, "ask link (of|to) [search] %string%");
		Skript.registerExpression(ExprUrlEncode.class, String.class, ExpressionType.SIMPLE, "url encoded %string%");
		Skript.registerExpression(ExprUrlDecode.class, String.class, ExpressionType.SIMPLE, "url decoded %string%");
		
		//Lag expression disable check
		if (getConfig().getConfigurationSection("enable").getBoolean("lag"))
		{
			Skript.registerEffect(EffLagServer.class, "lag [the] server for %timespan%","(make|create) a %timespan% lag[[ ]spike]");
		}
		
		//General Effects
		Skript.registerEffect(EffDisableAllPlugins.class, "disable all plugins","disable every plugin");
		
		//AAC
		if (getServer().getPluginManager().isPluginEnabled("AAC") && getConfig().getConfigurationSection("enable").getBoolean("AAC"))
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
			s.getPluginManager().registerEvents(new PlayerViolationEvt(), this);
			
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
		if (s.getPluginManager().isPluginEnabled("SkinsRestorer") && getConfig().getConfigurationSection("enable").getBoolean("SkinsRestorer"))
		{
			Xlog.logInfo("SkinsRestorer has been detected! Registring conditions, expressions and other boring stuff..");
			Skript.registerExpression(ExprSkinOfPlayer.class, String.class, ExpressionType.PROPERTY, "skin of %offlineplayer%","%offlineplayer%'s skin");
			Skript.registerCondition(CondPlayerHasSkin.class, "%offlineplayer% (has|have) [a] skin");
		}
		
		//Metrics
		if (getConfig().getBoolean("enableMetrics"))
		{
			try
			{
				Metrics metrics = new Metrics(this);
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
		if (getConfig().getConfigurationSection("bungee").getBoolean("use"))
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
		//Register the command
		this.getCommand("largesk").setExecutor(new LargeSkCommand());
		
		//You see
		Xlog.logInfo("Share your problems and ideas on https://github.com/Nicofisi/LargeSk/issues");
		
		Bukkit.getScheduler().runTaskAsynchronously(this, SkAddons::logAddons);
		Xlog.logInfo("I will show you a list of your Skript as soon as everything loads up.");
		
		//Announcing how much time enabling took
		eTime = System.currentTimeMillis() - eTime;
		
		Xlog.logInfo(ChatColor.YELLOW  + "=== ENABLE " + ChatColor.GREEN + "COMPLETE" + ChatColor.YELLOW + " (Took " + ChatColor.LIGHT_PURPLE + eTime + "ms" + ChatColor.YELLOW + ") ===");
		
		//Update check schedule
		if (getConfig().getConfigurationSection("updates").getBoolean("check"))
		{
			Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::checkUpdates, 1L, getConfig().getConfigurationSection("updates").getInt("frequency")*1200L);
		}
		else
		{
			Xlog.logInfo("Checking for updates is disabled in config, so I'll check it only once,");
			Xlog.logInfo("And I won't disturb you anymore, okay?");
			Bukkit.getScheduler().runTaskAsynchronously(this, this::checkUpdates);
		}
	}
	
	//On disable
	@Override
	public void onDisable() {
		Xlog.logInfo("Bye, Senpai!");
		if (getConfig().getBoolean("enableMetrics"))
		{
			try {
				Metrics metrics = new Metrics(this);
				metrics.disable();
			}
			catch (IOException e)
			{
				Xlog.logWarning("Disabling Metrics failed ¯\\_(ツ)_/¯");
				e.printStackTrace();
			}
		}
	}
	
	//Some stuff to copy the default config file from the plugin jar file.
	public void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		}
		catch (Exception e)
		{
			//It shouldn't happen
			Xlog.logError("Mate, I really tried to copy the default config, but I could not.");
			Xlog.logError("Report it on https://github.com/Nicofisi/LargeSk/issues please.");
			e.printStackTrace();
		}
	}
        
	//Checking updates, run on server startup and later by the Bukkit scheduler
	public void checkUpdates()
	{
		Xlog.logUpdater("Checking for updates..");
	    String newVersion = "";
	    try
	    {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Nicofisi/LargeSk/master/lastest.version").openStream()));
	        newVersion = in.readLine();
	        in.close();
	    }
	    catch (Exception e)
	    {
	        Xlog.logError(e.getCause().getMessage());
	    }
	    String currentVersion = this.getDescription().getVersion();
	    if ( ! Objects.equals(currentVersion, newVersion))
	    {
	    	Xlog.logUpdater("LargeSk " + newVersion + " was released! You are using " + currentVersion + ".");
	    	Xlog.logUpdater("Download the update from https://github.com/Nicofisi/LargeSk/releases");
	       	
	    }
	    else
	    {
	    	Xlog.logUpdater("It seems like your using the latest version of the plugin.");
	    }
	}
}
