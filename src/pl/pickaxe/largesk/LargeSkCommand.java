package pl.pickaxe.largesk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class LargeSkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if ( ! (args.length == 0)) { 
			if (args[0].equalsIgnoreCase("info"))
			{
				sender.sendMessage("This is not implemented yet.");
			}
			else if (args[0].equalsIgnoreCase("check"))
			{
				sender.sendMessage("This is not implemented yet.");
			}
			else if (args[0].equalsIgnoreCase("update"))
			{
				sender.sendMessage("This is not implemented yet.");
			}
			else if (args[0].equalsIgnoreCase("debug"))
			{
				sender.sendMessage("========== START OF DEBUG MESSAGE ==========");
				for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
				{
					sender.sendMessage("======");
					sender.sendMessage("Plugin: " + plugin.getName() + " | Version: " + plugin.getDescription().getVersion());
					sender.sendMessage("Description: " + plugin.getDescription().getDescription());
					sender.sendMessage("Website: " + plugin.getDescription().getWebsite());
					sender.sendMessage("Data Folder: " + plugin.getDataFolder());
				}
				sender.sendMessage("========== SERVER ==========");
				sender.sendMessage("Server version: " + Bukkit.getVersion());
				sender.sendMessage("Bukkit version: " + Bukkit.getBukkitVersion());
				sender.sendMessage("========== END OF DEBUG MESSAGE ==========");
			}
			else
			{
				sender.sendMessage(ChatColor.GRAY + "Use " + ChatColor.YELLOW + "/lsk " + ChatColor.GRAY + "to get a list of arguments.");
			}
		}
		else
			{
			sender.sendMessage(ChatColor.GRAY + "[LargeSk]> " + ChatColor.YELLOW + "An Addon for Skript");
			sender.sendMessage(ChatColor.YELLOW + "/lsk info" + ChatColor.GRAY + " - version, author etc.");
			sender.sendMessage(ChatColor.YELLOW + "/lsk check" + ChatColor.GRAY + " - check for updates");
			sender.sendMessage(ChatColor.YELLOW + "/lsk update" + ChatColor.GRAY + " - not implemented yet");
			sender.sendMessage(ChatColor.YELLOW + "/lsk debug" + ChatColor.GRAY + " - debug info for the developer");
		}
		return true;
		
	}
}
