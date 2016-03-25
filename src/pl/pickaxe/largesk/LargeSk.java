package pl.pickaxe.largesk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import pl.pickaxe.largesk.util.Xlog;
import pl.pickaxe.largesk.util.Copier;
import pl.pickaxe.largesk.util.Metrics;
import pl.pickaxe.largesk.util.SkAddons;

public class LargeSk extends JavaPlugin implements Listener {
	
	public static boolean debug = false;
	public static int lastestConfigVersion = 5;
	public static boolean updateSession = false;
	
	public static File configf;
    public static FileConfiguration config;
    
    public static LargeSk getPlugin() {
        return LargeSk.getPlugin(LargeSk.class);
    }
    
	@Override
	public void onEnable() {
		
		//Enabling timer
		long eTime = System.currentTimeMillis();
		Xlog.logInfo(ChatColor.YELLOW  + "=== ENABLE " + ChatColor.GREEN + "START" + ChatColor.YELLOW + " ===");
		
		if (Bukkit.getPluginManager().isPluginEnabled("LargeSkUpdater"))
		{
			Xlog.logInfo("The LargeSkUpdater is enabled! So you were updating the plugin? Nice!");
			Plugin lsu = Bukkit.getPluginManager().getPlugin("LargeSkUpdater");
			Bukkit.getPluginManager().disablePlugin(lsu);
		}
		
		//Configs
		configf = new File(getDataFolder(), "config.yml");
		
		//Defines the lastest config version
		
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
			Copier cp = new Copier();
			cp.copy(getResource("config.yml"), configf);
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
		
		//Register Skript's stuff
		Register reg = new Register();
		reg.registerAll();

		//Register the command
		this.getCommand("largesk").setExecutor(new LargeSkCommand());
		
		//You see
		Xlog.logInfo("Share your problems and ideas on https://github.com/Nicofisi/LargeSk/issues");
		
		//Also obvious
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
		Bukkit.getScheduler().cancelTasks(this);
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
        
	//Checking updates, run on server startup and later by the Bukkit scheduler
	public void checkUpdates()
	{
		if (updateSession) return;
		
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
