package pl.pickaxe.largesk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LargeSkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ( sender instanceof Player && sender.hasPermission("largesk.command.help"))
		{
			if ( ! (args.length == 0)) {
				if ( sender instanceof Player && ! sender.hasPermission("largesk.command." + args[0]))
				{
					sender.sendMessage(ChatColor.GRAY + "You are missing the permission " + ChatColor.YELLOW + ChatColor.ITALIC + "largesk.command." + args[0] + ChatColor.GRAY + " .");
				}
				if (args[0].equalsIgnoreCase("info"))
				{
					
				}
				else if (args[0].equalsIgnoreCase("check"))
				{
				    String newVersion = "";
				    try
				    {
				        BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Nicofisi/LargeSk/master/lastest.version").openStream()));
				        newVersion = in.readLine();
				        in.close();
				    }
				    catch (Exception e)
				    {
				        sender.sendMessage(e.getMessage());
				        sender.sendMessage("Could not check for updates (info above)");
				    }
				    String currentVersion = Bukkit.getPluginManager().getPlugin("LargeSk").getDescription().getVersion();
				    if ( ! Objects.equals(currentVersion, newVersion))
				    {
				    	sender.sendMessage("LargeSk" + newVersion + "was released! You are using" + currentVersion + ".");
				    	sender.sendMessage("Download it from https://github.com/Nicofisi/LargeSk/releases");
				    }
				    else
				    {
				        sender.sendMessage("It seems like your using the latest version of the plugin.");
				    }
				}
				else if (args[0].equalsIgnoreCase("update"))
				{
					sender.sendMessage("This is not implemented yet.");
				}
				else if (args[0].equalsIgnoreCase("debug"))
				{
					sender.sendMessage("========== START OF DEBUG MESSAGE ==========");
					sender.sendMessage("========== PLUGINS ==========");
					for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
					{
						sender.sendMessage("==========");
						sender.sendMessage("Plugin: " + plugin.getName() + " | Version: " + plugin.getDescription().getVersion());
						sender.sendMessage("Description: " + plugin.getDescription().getDescription());
						sender.sendMessage("Website: " + plugin.getDescription().getWebsite());
						sender.sendMessage("Data Folder: " + plugin.getDataFolder());
					}
					sender.sendMessage("========== SHORT PLUGINS ==========");
					String pls = "";
					for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
					{
						pls = pls + plugin.getName() + " " + plugin.getDescription().getVersion() + ", ";
					}
					sender.sendMessage(pls);
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
		}
		else
		{
			sender.sendMessage(ChatColor.GRAY + "You are missing the permission " + ChatColor.YELLOW + ChatColor.ITALIC + "largesk.command.help " + ChatColor.GRAY + ".");
		}
		return true;
	}
}
