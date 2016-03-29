package pl.pickaxe.largesk.util;

import java.io.IOException;

import ch.njol.skript.Skript;
import pl.pickaxe.largesk.LargeSk;

public class MetricsManager
{
	
	public void enableMetrics()
	{
		if (LargeSk.debug)
			Xlog.logInfo("Trying to enable Metrics..");
		try
		{
			Metrics metrics = new Metrics(LargeSk.getPluginInstance());
			metrics.start();
		}
		catch (IOException e)
		{
			Xlog.logWarning("Enabling Metrics failed ¯\\_(ツ)_/¯");
			e.printStackTrace();
		}
	}	

	public void disableMetrics()
	{
		if (LargeSk.debug)
			Xlog.logInfo("Disabling Metrics..");
		try
		{
			Metrics metrics = new Metrics(Skript.getInstance());
			metrics.disable();
		}
		catch (IOException e)
		{
			Xlog.logWarning("Disabling Metrics failed ¯\\_(ツ)_/¯");
			e.printStackTrace();
		}
	
	}
}
