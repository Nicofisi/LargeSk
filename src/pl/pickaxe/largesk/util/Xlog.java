package pl.pickaxe.largesk.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import pl.pickaxe.largesk.LargeSk;
import pl.pickaxe.largesk.LargeSkBungee;

public class Xlog
{
	public static void logInfo(String msg)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + LargeSk.getPluginInstance().getDescription().getName() + " " + LargeSk.getPluginInstance().getDescription().getVersion() + ChatColor.BLUE + "] " + ChatColor.RESET + msg);
	}
	public static void logWarning(String msg)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + LargeSk.getPluginInstance().getDescription().getName() + " " + LargeSk.getPluginInstance().getDescription().getVersion() + ChatColor.LIGHT_PURPLE + " WARNING" + ChatColor.DARK_PURPLE + "] " + ChatColor.RESET + msg);
	}
	public static void logError(String msg)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + LargeSk.getPluginInstance().getDescription().getName() + " " + LargeSk.getPluginInstance().getDescription().getVersion() + ChatColor.RED + "  ERROR" + ChatColor.DARK_RED + " ] " + ChatColor.RESET + msg);
	}
	public static void logUpdater(String msg)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + LargeSk.getPluginInstance().getDescription().getName() + " " + LargeSk.getPluginInstance().getDescription().getVersion() + ChatColor.GREEN + " UPDATER" + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + msg);
	}
	public static void logRaw(String msg)
	{
		Bukkit.getConsoleSender().sendMessage(msg);
	}
	public static void logDefault(String msg)
	{
		Bukkit.getConsoleSender().sendMessage("[" + LargeSk.getPluginInstance().getName() + "] " + msg);
	}
	public static void logBungeeInfo(String msg)
	{
		TextComponent txp = new TextComponent(net.md_5.bungee.api.ChatColor.BLUE + "[" + net.md_5.bungee.api.ChatColor.AQUA + LargeSkBungee.getPlugin().getDescription().getName() + " " + LargeSkBungee.getPlugin().getDescription().getVersion() + net.md_5.bungee.api.ChatColor.BLUE + "] " + net.md_5.bungee.api.ChatColor.RESET + msg);
        BungeeCord.getInstance().getConsole().sendMessage(txp);
	}
}
