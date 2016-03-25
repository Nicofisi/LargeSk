package pl.pickaxe.largesk;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import pl.pickaxe.largesk.util.Xlog;
import pl.pickaxe.largesk.util.LargeConfig;
import pl.pickaxe.largesk.util.Metrics;
import pl.pickaxe.largesk.util.SkAddons;
import pl.pickaxe.largesk.util.Updater;

public class LargeSk extends JavaPlugin implements Listener {
	
	public static boolean debug = false;
    
    public static LargeSk getPlugin() {
        return LargeSk.getPlugin(LargeSk.class);
    }
    
	@Override
	public void onEnable() {
		
		//Enabling timer
		long eTime = System.currentTimeMillis();
		Xlog.logInfo(ChatColor.YELLOW  + "=== ENABLE " + ChatColor.GREEN + "START" + ChatColor.YELLOW + " ===");
		
		//Config
		LargeConfig conf = new LargeConfig();
		conf.load();
		
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
		Xlog.logInfo("I will show you a list of your Skript addons as soon as everything loads up.");
		
		//Announcing how much time enabling took
		eTime = System.currentTimeMillis() - eTime;
		Xlog.logInfo(ChatColor.YELLOW  + "=== ENABLE " + ChatColor.GREEN + "COMPLETE" + ChatColor.YELLOW + " (Took " + ChatColor.LIGHT_PURPLE + eTime + "ms" + ChatColor.YELLOW + ") ===");
		
		//Update check schedule
		Updater updater = new Updater();
		updater.scheduleUpdates();
	}
	
	//On disable
	@Override
	public void onDisable() {
		if (debug) Xlog.logInfo("Cancelling tasks..");
		Bukkit.getScheduler().cancelTasks(this);
		
		if (getConfig().getBoolean("enableMetrics"))
		{
			if (debug) Xlog.logInfo("Disabling Metrics..");
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
		Xlog.logInfo("Bye, Senpai!");
	}
}
