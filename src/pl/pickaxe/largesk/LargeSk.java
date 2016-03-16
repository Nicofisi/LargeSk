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
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.GeneralEffects.EffDisableAllPlugins;
import pl.pickaxe.largesk.GeneralExpressions.ExprFullTime;
import pl.pickaxe.largesk.SkinsRestorer.ExprSkinOfPlayer;
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
import pl.pickaxe.largesk.util.EnumClassInfo;
import pl.pickaxe.largesk.util.Metrics;

public class LargeSk extends JavaPlugin {
	
	public static File configf;
    public static FileConfiguration config;
	
	@Override
	
	public void onEnable() {
		
		//Enabling timer
		long eTime = System.currentTimeMillis();
		Server s = getServer();
	
		//Fancy message
		s.getPluginManager().getPlugin("Skript").getLogger().info("LargeSk, welcome to the server!");
		
		//Configs
		configf = new File(getDataFolder(), "config.yml");
		if ( ! configf.exists())
		{
			getLogger().info("The config.yml file does not exist or is outdated.");
			getLogger().info("I'll copy it from the plugin's jar file now.");
			configf.getParentFile().mkdirs();
			copy(getResource("config.yml"), configf);
		}
		
		
		//Registring Skript addon
		Skript.registerAddon(this);
		
		//Registring hacktype enum from AAC
		EnumClassInfo.create(HackType.class, "hacktype").register();
		
		//Learning
		Skript.registerExpression(ExprNameOfPlayer.class, String.class, ExpressionType.PROPERTY, "the name of the wonderful player %player%", "%player%'s wonderful name");
		Skript.registerExpression(ExprDisplayNameOfPlayer.class, String.class, ExpressionType.PROPERTY, "great display name of player %player%", "%player%'s amazing display name");
		Skript.registerEffect(EffSendMessage.class, "send great message %string% to %player%");
		Skript.registerEvent("Amazing Player Level Change", SimpleEvent.class, PlayerLevelChangeEvent.class, "amazing player level change");
		
		//General Expressions
		Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "(full|total)[ ]time of %world%","%world%'s (full|total)[ ]time");
		
		//General Effects
		Skript.registerEffect(EffDisableAllPlugins.class, "disable all plugins","disable every plugin");
		
		//AAC
		if (getServer().getPluginManager().isPluginEnabled("AAC")) {
			getLogger().info("You've got AAC, wow. You are soooo rich! I'll collaborate!");
			Skript.registerCondition(CondIsBypassed.class, "[aac] %player%('s| is) bypass(ed|ing) aac");
			Skript.registerCondition(CondOnGround.class, "[aac] %player%('s| is) (on ground|not in air)");
			Skript.registerCondition(CondCheckEnabled.class, "[aac ](check %-hacktype%|%-hacktype% check) is (enabled|on|running)");
			Skript.registerExpression(ExprAacPing.class, Integer.class, ExpressionType.PROPERTY, "aac (ping of %player%|%player%'s ping)", "[aac] (ping of %player%|%player%'s ping) by aac");
			Skript.registerExpression(ExprAacTps.class, Double.class, ExpressionType.SIMPLE, "[aac] tps","tps (of|by) aac");
			Skript.registerExpression(ExprViolationLevel.class, Integer.class, ExpressionType.PROPERTY, "%player%['s][ aac] [hack[s]|violation[s]|cheat[s]] level of %hacktype%","[aac ] %hacktype% [hack[s]|violation[s]|cheat[s]] level of %player%");
			Skript.registerEffect(EffReloadAAC.class, "aac reload [config[s]]","reload aac [config[s]]","reload config[s] of aac");
			Skript.registerEffect(EffReloadPermissionCache.class, "aac reload permission(s|[s] cache)","reload permission(s|[s] cache) of aac","reload aac's permission(s|[s] cache)");
			Skript.registerEffect(EffEnableCheck.class, "enable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
			Skript.registerEffect(EffDisableCheck.class, "disable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
		}
		
		//SkinsRestorer
		if (s.getPluginManager().isPluginEnabled("SkinsRestorer")) {
			getLogger().info("SkinsRestorer has been detected! Registring grammar..");
			Skript.registerExpression(ExprSkinOfPlayer.class, String.class, ExpressionType.PROPERTY, "skin of %player%","%player%'s skin");
		}
		
		//Metrics
		if (getConfig().getBoolean("enableMetrics"))
		{
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			}
			catch (IOException e)
			{
				getLogger().warning("Enabling Metrics failed ¯\\_(ツ)_/¯");
				e.printStackTrace();
			}
		}
		else
		{
			getLogger().info("Metrics are disabled, sorry to hear that but it's not my problem ¯\\_(ツ)_/¯");
		}
		
		//Register the command
		this.getCommand("largesk").setExecutor(new LargeSkCommand());
		
		//Announcing how much time enabling took
		eTime = System.currentTimeMillis() - eTime;
		getLogger().info("Enabling LargeSk " + this.getDescription().getVersion() + " by Nicofisi completed, took " + eTime + "ms.");
		getLogger().info("Share your problems and ideas on https://github.com/Nicofisi/LargeSk/issues");
		
		//Update check schedule
		if (getConfig().getConfigurationSection("updates").getBoolean("check"))
		{
			getLogger().info("Checking for updates..");
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::checkUpdates, 1L, getConfig().getConfigurationSection("updates").getInt("frequency")*1200L);
		}
		else
		{
			getLogger().info("Checking for updates is disabled in config.");
		}
				
	}
	
	//On disable
	@Override
	public void onDisable() {
		getLogger().info("Bye, Senpai!");
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
			getLogger().severe("Mate, I really tried to copy the default config, but I could not.");
			getLogger().severe("Report it on https://github.com/Nicofisi/LargeSk/issues please.");
			e.printStackTrace();
		}
	}
	
	//Checking updates, run on server startup and later by Bukkit scheduler
	public void checkUpdates()
	{
	        String v = "";
	        try
	        {
	            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Nicofisi/LargeSk/master/lastest.version").openStream()));
	            v = in.readLine();
	            in.close();
	        }
	        catch (Exception e)
	        {
	            getLogger().severe(e.getCause().getMessage());
	        }
	        String version = this.getDescription().getVersion();
	        if ( ! Objects.equals(version, v))
	        {
	        	getLogger().info("LargeSk " + v + " was released! You are using " + version + ".");
	        	getLogger().info("Download update from https://github.com/Nicofisi/LargeSk/releases");
	        	
	        }
	        else
	        {
	            getLogger().info("It seems like your using the latest version of the plugin.");
	        }
	}
}
