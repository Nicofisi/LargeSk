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

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import pl.pickaxe.largesk.util.Updater;
import pl.pickaxe.largesk.util.Xlog;

public class LargeSkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0)
		{
			if (sender instanceof Player && ! sender.hasPermission("largesk.command.help"))
			{
				sender.sendMessage(ChatColor.GRAY + "You are lacking the permission " + ChatColor.YELLOW + ChatColor.ITALIC + "largesk.command.help " + ChatColor.GRAY + ".");
			}
			else
			{
				sender.sendMessage(ChatColor.GRAY + "[LargeSk]> " + ChatColor.YELLOW + "An Addon for Skript");
				sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "/lsk info" + ChatColor.GRAY + " - version, author etc.");
				sender.sendMessage(ChatColor.YELLOW + "/lsk check" + ChatColor.GRAY + " - check for updates");
				sender.sendMessage(ChatColor.YELLOW + "/lsk update" + ChatColor.GRAY + " - not implemented yet");
				sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "/lsk reload" + ChatColor.GRAY + " - reloads config");
				sender.sendMessage(ChatColor.YELLOW + "/lsk debug" + ChatColor.GRAY + " - debug info for the developer");
			}
			return true;
		}
		if ( sender instanceof Player && ! sender.hasPermission("largesk.command." + args[0]))
		{
			sender.sendMessage(ChatColor.GRAY + "You are lacking the permission " + ChatColor.YELLOW + ChatColor.ITALIC + "largesk.command." + args[0] + ChatColor.GRAY + " .");
		}
		if (args[0].equalsIgnoreCase("info"))
		{
			sender.sendMessage(ChatColor.GRAY + "This is not implemented yet.");
		}
		else if (args[0].equalsIgnoreCase("reload"))
		{
			sender.sendMessage(ChatColor.GRAY + "This is not implemented yet. (Will be soon)");
		}
		else if (args[0].equalsIgnoreCase("check"))
		{
			sender.sendMessage(ChatColor.GRAY + "Checking..");
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
		        sender.sendMessage(ChatColor.GRAY + "Could not check for updates (info above)");
		    }
		    String currentVersion = Bukkit.getPluginManager().getPlugin("LargeSk").getDescription().getVersion();
		    if ( ! Objects.equals(currentVersion, newVersion))
		    {
		    	sender.sendMessage(ChatColor.GRAY + "LargeSk " + ChatColor.YELLOW + newVersion + ChatColor.GRAY + " was released! You are using" + ChatColor.YELLOW + currentVersion + ChatColor.GRAY + ".");
		    	sender.sendMessage(ChatColor.GRAY + "Download it from https://github.com/Nicofisi/LargeSk/releases");
		    }
		    else
		    {
		        sender.sendMessage("It seems like your using the latest version of the plugin.");
		    }
		}
		else if (args[0].equalsIgnoreCase("update"))
		{
			sender.sendMessage(ChatColor.GRAY + "[LargeSk]> " + ChatColor.YELLOW + "The update progress will be shown in console");
			Updater u = new Updater();
			u.runUpdate();
		}
		else if (args[0].equalsIgnoreCase("xlogtest"))
		{
			Xlog.logInfo("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			Xlog.logWarning("Ut porttitor feugiat mi a vehicula. Curabitur sed urna quam.");
			Xlog.logError("Morbi et rhoncus dolor, vitae commodo enim.");
			Xlog.logUpdater("Mauris ac odio mattis, fringilla nisi molestie, blandit dolor.");
			Xlog.logDefault("Phasellus non sem eget dolor ultrices commodo et dignissim dolor.");
			Xlog.logRaw("Nunc cursus ex vitae diam mollis, ut faucibus neque fringilla.");
		}
		else if (args[0].equalsIgnoreCase("debug"))
		{
			if (sender instanceof Player)
			{
				sender.sendMessage(ChatColor.GRAY + "The message has been sent to the console.");
			}
			Xlog.logInfo(ChatColor.YELLOW  + "=== DEBUG " + ChatColor.GREEN + "START" + ChatColor.YELLOW + " ===");
			Xlog.logInfo(ChatColor.YELLOW  + "=== PLUGINS " + ChatColor.YELLOW + " ===");
			for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
			{
				Xlog.logInfo(plugin.getName() + " " + plugin.getDescription().getVersion() + " >> " + plugin.getDescription().getAuthors());;
			}
			Xlog.logInfo(ChatColor.YELLOW  + "=== SERVER INFO " + ChatColor.YELLOW + " ===");
			Xlog.logInfo("Server version: " + Bukkit.getVersion());
			Xlog.logInfo("Bukkit version: " + Bukkit.getBukkitVersion());
			Xlog.logInfo(ChatColor.YELLOW  + "=== SKRIPT INFO " + ChatColor.YELLOW + " ===");
			Xlog.logInfo("Skript version: " + Bukkit.getPluginManager().getPlugin("Skript").getDescription().getVersion());
			Xlog.logInfo("Skript Addons: " + Skript.getAddons());
			String addlist = "";
			for (SkriptAddon addon : Skript.getAddons())
			{
				addlist = addlist + addon.getName() + " " + Bukkit.getPluginManager().getPlugin(addon.toString()).getDescription().getVersion() + ", ";
			}
			addlist = addlist.substring(0, addlist.length() - 2);
			Xlog.logInfo("With versions: " + addlist);
			Xlog.logInfo(ChatColor.YELLOW  + "=== DEBUG " + ChatColor.GREEN + "END" + ChatColor.YELLOW + " ===");
		}
		else
		{
			sender.sendMessage(ChatColor.GRAY + "Use " + ChatColor.YELLOW + "/lsk " + ChatColor.GRAY + "to get a list of arguments.");
		}
	return true;
	}
}
