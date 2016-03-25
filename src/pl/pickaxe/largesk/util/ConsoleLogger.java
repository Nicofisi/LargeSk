package pl.pickaxe.largesk.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsoleLogger extends JavaPlugin {
	public void onEnable() {
		
		reloadConfig();
		reloadFilter();
		
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			@Override
			public void run() {
				reloadConfig();
				reloadFilter();
				log("&3Filters now turned on.");
			}
		}, 100);
		
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			log("&4Failed to start Metrics: &e" + e.getMessage());
		}
		
		log(getName() + " v" + getDescription().getVersion() + " by BillyGalbreath enabled!");
	}
	
	public void log (Object obj) {
		if (getConfig().getBoolean("color-logs", true))
			getServer().getConsoleSender().sendMessage(colorize("&3[&d" +  getName() + "&3]&r " + obj));
		else
			Bukkit.getLogger().log(java.util.logging.Level.INFO, "[" + getName() + "] " + ((String) obj).replaceAll("(?)\u00a7([a-f0-9k-or])", ""));
	}
	
	public String colorize(String str) {
		return str.replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1");
	}
	
	public void reloadFilter() {
		org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
		Filter filter = new Filter() {
			@Override
			public Result filter(LogEvent event) {
				ConfigurationSection filters = getConfig().getConfigurationSection("filters");
				for (String level : filters.getKeys(false)) {
					if (!event.getLevel().equals(Level.valueOf(level)))
						continue;
					ConfigurationSection filterLevels = filters.getConfigurationSection(level);
					for (String filterType: filterLevels.getKeys(false)) {
						List<String> msgs = filterLevels.getStringList(filterType);
						for (String msg : msgs) {
							if (filterType.equalsIgnoreCase("equals"))
								if (event.getMessage().toString().equals(msg))
									return Result.DENY;
							if (filterType.equalsIgnoreCase("equalsignorecase"))
								if (event.getMessage().toString().equalsIgnoreCase(msg))
									return Result.DENY;
							if (filterType.equalsIgnoreCase("contains"))
								if (event.getMessage().toString().contains(msg))
									return Result.DENY;
							if (filterType.equalsIgnoreCase("endswith"))
								if (event.getMessage().toString().endsWith(msg))
									return Result.DENY;
							if (filterType.equalsIgnoreCase("startswith"))
								if (event.getMessage().toString().startsWith(msg))
									return Result.DENY;
							if (filterType.equalsIgnoreCase("matches"))
								if (event.getMessage().toString().matches(msg))
									return Result.DENY;
						}
					}
				}
				return null;
			}
			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object... arg4) {
				return null;
			}
			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2, Object arg3, Throwable arg4) {
				return null;
			}
			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2, Message arg3, Throwable arg4) {
				return null;
			}
			@Override
			public Result getOnMatch() {
				return null;
			}
			@Override
			public Result getOnMismatch() {
				return null;
			}
			@Override
			public State getState()
			{
				return null;
			}
			@Override
			public void initialize()
			{
				
			}
			@Override
			public boolean isStarted()
			{
				return false;
			}
			@Override
			public boolean isStopped()
			{
				return false;
			}
			@Override
			public void start()
			{
				
			}
			@Override
			public void stop()
			{
				
			}
		};
		boolean alreadyLoaded = false;
		Iterator<Filter> iter = coreLogger.getFilters();
		while (iter.hasNext()) {
			if (filter.equals(iter.next())) {
				alreadyLoaded = true;
				break;
			}
		}
		if (!alreadyLoaded)
			coreLogger.addFilter(filter);
	}
}