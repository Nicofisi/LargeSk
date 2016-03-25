package pl.pickaxe.largesk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import pl.pickaxe.largesk.LargeSk;

public class Updater
{
	public static String updatedVersion = null;
	
	public void runUpdate()
	{
		Bukkit.getScheduler().runTaskAsynchronously(LargeSk.getPlugin(), new Runnable()
		{
			@Override
			public void run()
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
				String currentVersion = LargeSk.getPlugin().getDescription().getVersion();
				if ( ! currentVersion.equals(newVersion) || LargeSk.debug == true)
				{
					Xlog.logUpdater("LargeSk " + newVersion + " was released! You are using " + currentVersion + ".");
					Xlog.logUpdater("Downloading.. I need more updatezzz");
				}
				else
				{
					Xlog.logUpdater("It seems like your using the latest version of the plugin, I can't update it");
					return;
				}
				File updated = new File(LargeSk.getPlugin().getDataFolder(), "LargeSk.jar");
				try
				{
					FileUtils.copyURLToFile(new URL("https://github.com/Nicofisi/LargeSk/releases/download/" + newVersion + "/LargeSk.jar"), updated);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				if (! updated.exists())
				{
					Xlog.logError("The downloading process failed!");
					return;
				}
				Xlog.logUpdater("Downloaded!");
				
				Updater updater = new Updater();
				updater.removeOld();
				
				File moved = new File(LargeSk.getPlugin().getDataFolder(), "../LargeSk.jar");
				Xlog.logUpdater("Moving newest LargeSk.jar to plugins folder");
				try
				{
					Files.move( updated.toPath(),
						moved.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				}
				catch (IOException e)
				{
					Xlog.logUpdater("An error has occured while attempting to move downloaded jar to the plugins folder.");
					e.printStackTrace();
				}
				if ( ! moved.exists())
				{
					Xlog.logUpdater("The file wasn't moved to the plugins folder for some reason. What.");
					return;
				}
				Xlog.logUpdater("The update was downloaded! Just restart your server :)");
				Updater.updatedVersion = newVersion;
			}
		});
		
		
	}
	public void removeOld()
	{
		File oldJar = new java.io.File(LargeSk.class.getProtectionDomain().getCodeSource().getLocation().getFile());
		oldJar.delete();
		Xlog.logUpdater("Removed old plugin version");
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
	    String currentVersion;
	   	currentVersion = LargeSk.getPlugin().getDescription().getVersion();
	    if ( ! Objects.equals(currentVersion, newVersion))
	    {
	    	if (updatedVersion == null)
	    	{
	    		Xlog.logUpdater("LargeSk " + newVersion + " was released! You are using " + currentVersion + ".");
	    		Xlog.logUpdater("To download the update, just use /lsk update");
	    	}
	    	else
	    	{
	    		Xlog.logUpdater("New version of LargeSk, " + newVersion + ", was found.");
	    		Xlog.logUpdater("I know you downloaded an update in this session already, but the developer published even cooler one");
	    		Xlog.logUpdater("Currently " + currentVersion + "is enabled, and " + updatedVersion + " is downloaded");
	    		Xlog.logUpdater("Use the command /lsk update again!");
	    	}
	    }
	    else
	    {
	    	if (updatedVersion == null)
	    	{
	    		Xlog.logUpdater("It seems like your using the latest version of the plugin.");
	    	}
	    	else
	    	{
	    		Xlog.logUpdater("The version of plugin you downloaded is the lastest one (but is still awaiting for a server restart)");
	    	}
	    }
	}
	public void scheduleUpdates()
	{
		if (LargeSk.getPlugin().getConfig().getConfigurationSection("updates").getBoolean("check"))
		{
			Bukkit.getScheduler().runTaskTimerAsynchronously(LargeSk.getPlugin(), this::checkUpdates, 1L, LargeSk.getPlugin().getConfig().getConfigurationSection("updates").getInt("frequency")*1200L);
		}
		else
		{
			Xlog.logInfo("Checking for updates is disabled in config, so I'll check it only once,");
			Xlog.logInfo("And I won't disturb you anymore, okay?");
			Bukkit.getScheduler().runTaskAsynchronously(LargeSk.getPlugin(), this::checkUpdates);
		}
	}
}
