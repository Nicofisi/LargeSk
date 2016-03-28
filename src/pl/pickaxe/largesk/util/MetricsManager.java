package pl.pickaxe.largesk.util;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import pl.pickaxe.largesk.LargeSk;

public class MetricsManager
{
	LargeConfig lc = new LargeConfig();
	FileConfiguration config = lc.getConfig();
	
	public void enableMetrics()
	{
		
		//Metrics
		if (LargeSk.getPlugin().getConfig().getBoolean("enableMetrics"))
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
	}
	public void disableMetrics()
	{
		if (config.getBoolean("enableMetrics"))
		{
			if (LargeSk.debug)
				Xlog.logInfo("Disabling Metrics..");
			try {
				Metrics metrics = new Metrics(LargeSk.getPlugin());
				metrics.disable();
			}
			catch (IOException e)
			{
				Xlog.logWarning("Disabling Metrics failed ¯\\_(ツ)_/¯");
				e.printStackTrace();
			}
		}
	}
}
