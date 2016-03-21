package pl.pickaxe.largesk.util;

//import java.io.File;

import pl.pickaxe.largesk.LargeSk;

public class LargeConfig extends LargeSk
{
//	public void check(boolean bool)
//	{
//		//Configs
//		configf = new File(getDataFolder(), "config.yml");
//		
//		//Defines the lastest config version
//		int lastestConfigVersion = 4;
//		
//		if ( ! configf.exists() || lastestConfigVersion != getConfig().getInt("configVersion"))
//		{
//			configf.getParentFile().mkdirs();
//			if (configf.exists()) {
//				Xlog.logWarning("Your config.yml file is outdated.");
//				Xlog.logInfo("I'll copy the default one from the plugin's .jar file in a moment.");
//				String path = this.getDataFolder() + "/config-old-ver" + getConfig().getInt("configVersion") + "-" + System.currentTimeMillis();
//				File oldConfig = new File(path);
//				configf.renameTo(oldConfig);
//				Xlog.logInfo("Your old configuration was moved to " + path);
//			}
//			else
//			{
//				Xlog.logWarning("The config.yml file does not exist.");
//				Xlog.logInfo("I'll copy the default config from the plugin's .jar file now.");
//			}
//			copy(getResource("config.yml"), configf);
//			reloadConfig();
//			Xlog.logInfo("Done.");
//			Xlog.logInfo("You are now using DEFAULT configuration of the plugin.");
//			if ( ! configf.exists() || lastestConfigVersion != getConfig().getInt("configVersion"))
//			{
//				getLogger().info(getConfig().getInt("configVersion") + lastestConfigVersion + "");
//				Xlog.logError("Whooops! The default config file is broken. It's not compatybile with the current version of the plugin.");
//				Xlog.logError("All you may do is to contact the developer or use older version of the plugin.");
//			}
//		}
//	}
}
