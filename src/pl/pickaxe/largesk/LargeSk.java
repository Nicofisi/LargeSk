package pl.pickaxe.largesk;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import pl.pickaxe.largesk.GeneralEffects.EffDisableAllPlugins;
import pl.pickaxe.largesk.GeneralExpressions.ExprFullTime;
import pl.pickaxe.largesk.SkinsRestorer.ExprSkinOfPlayer;

public class LargeSk extends JavaPlugin {
	
	@Override
	
	public void onEnable() {
		Skript.registerAddon(this);
		
		//Learning
		Skript.registerExpression(ExprNameOfPlayer.class, String.class, ExpressionType.PROPERTY, "the name of the wonderful player %player%", "%player%'s wonderful name");
		Skript.registerExpression(ExprDisplayNameOfPlayer.class, String.class, ExpressionType.PROPERTY, "great display name of player %player%", "%player%'s amazing display name");
		Skript.registerEffect(EffSendMessage.class, "send great message %string% to %player%");
		Skript.registerEvent("Amazing Player Level Change", SimpleEvent.class, PlayerLevelChangeEvent.class, "amazing player level change");
		
		//Real
		Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "[full|total] time of %world%","%world%'s [full|total] time");
		//Skript.registerExpression(ExprCakeSlicesEaten.class, Integer.class, ExpressionType.PROPERTY, "[the] [(amount|number) of] (slices eaten|eaten slices) of %block%");
		Skript.registerEffect(EffDisableAllPlugins.class, "disable all plugins","disable every plugin");
		
		//AAC
		//Coming Soon
		
		//SkinsRestorer
		if (getServer().getPluginManager().isPluginEnabled("SkinsRestorer")) {
			getLogger().info("SkinsRestorer has been detected! Registring grammar..");
			Skript.registerExpression(ExprSkinOfPlayer.class, String.class, ExpressionType.PROPERTY, "skin of %player%","%player%'s skin");
		}
	}
	@Override
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Bye, Senpai!");
	}
	

}
