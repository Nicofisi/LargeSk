package pl.pickaxe.largesk.util;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import pl.pickaxe.largesk.LargeSk;

public class LargeConfig
{
	
	public static File configf;
    public static FileConfiguration config;
	public static int lastestConfigVersion = 5;
	Plugin lsk = LargeSk.getPlugin();
	public void load()
	{
		//Configs
		configf = new File(lsk.getDataFolder(), "config.yml");
		
		if ( ! configf.exists() || lastestConfigVersion != lsk.getConfig().getInt("configVersion"))
		{
			configf.getParentFile().mkdirs();
			if (configf.exists()) {
				Xlog.logWarning("Your config.yml file is outdated.");
				Xlog.logInfo("I'll copy the default one from the plugin's .jar file in a moment.");
				String path = lsk.getDataFolder() + "/config-old-ver" + lsk.getConfig().getInt("configVersion") + "-" + System.currentTimeMillis();
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
			cp.copy(lsk.getResource("config.yml"), configf);
			lsk.reloadConfig();
			Xlog.logInfo("Done.");
			Xlog.logInfo("You are now using DEFAULT configuration of the plugin.");
			if ( ! configf.exists() || lastestConfigVersion != lsk.getConfig().getInt("configVersion"))
			{
				Xlog.logError("Whooops! The default config file is broken. It's not compatybile with the current version of the plugin.");
				Xlog.logError("All you may do is to contact the developer or use older version of the plugin.");
			}
		}
	}
	
	public void checkObjects()
	{
		
	}
	
}
