package pl.pickaxe.largesk.register;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.LargeSk;
import pl.pickaxe.largesk.util.EnumClassInfo;
import pl.pickaxe.largesk.util.LargeConfig;
import pl.pickaxe.largesk.util.Xlog;

public class Register
{
	LargeConfig largeconfig = new LargeConfig();
	FileConfiguration config = largeconfig.getConfig();
	
	public void registerAll()
	{
		//Construct
		Expressions expressions = new Expressions();
		Conditions conditions = new Conditions();
		Effects effects = new Effects();
		Events events = new Events();
		Bungee bungee = new Bungee();
		
		//General
		expressions.registerGeneral();
		conditions.registerGeneral();
		effects.registerGeneral();
		events.registerGeneral();
		
		//AAC
		if (isPluginOnServer("AAC") && config.getConfigurationSection("enable").getBoolean("AAC"))
		{
			Xlog.logInfo("I have found AAC on your server. I'm pleased to announce we will work together from now.");
			EnumClassInfo.create(HackType.class, "hacktype").register();
			expressions.registerAAC();
			conditions.registerAAC();
			effects.registerAAC();
			events.registerAAC();
		}
		//SkinsRestorer
		if (isPluginOnServer("SkinsRestorer") && config.getConfigurationSection("enable").getBoolean("SkinsRestorer"))
		{
			Xlog.logInfo("SkinsRestorer has been detected! Registring conditions, expressions and other boring stuff..");
			expressions.registerSkinsRestorer();
			conditions.registerSkinsRestorer();
		}

		//ProtocolLib
		if (isPluginOnServer("ProtocolLib") && LargeSk.debug)
		{
			Xlog.logInfo("Yeah, ProtocolLib! Let's go crazy..");
			effects.registerProtocolLib();
		}
		
		//BungeeCord
		if (config.getConfigurationSection("bungee").getBoolean("use"))
		{
			bungee.registerAll();
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