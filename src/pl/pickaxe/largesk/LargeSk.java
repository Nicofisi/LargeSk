package pl.pickaxe.largesk;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.GeneralEffects.EffDisableAllPlugins;
import pl.pickaxe.largesk.GeneralExpressions.ExprFullTime;
import pl.pickaxe.largesk.SkinsRestorer.ExprSkinOfPlayer;
import pl.pickaxe.largesk.aac.CondCheckEnabled;
import pl.pickaxe.largesk.aac.CondIsBypassed;
import pl.pickaxe.largesk.aac.CondOnGround;
import pl.pickaxe.largesk.aac.EffReloadAAC;
import pl.pickaxe.largesk.aac.EffReloadPermissionCache;
import pl.pickaxe.largesk.aac.ExprAacPing;
import pl.pickaxe.largesk.util.EnumClassInfo;

public class LargeSk extends JavaPlugin {
	
	@Override
	
	public void onEnable() {
		Skript.registerAddon(this);
		
		//Different Stuff
		EnumClassInfo.create(HackType.class, "hacktype").register();
		
		//Learning
		Skript.registerExpression(ExprNameOfPlayer.class, String.class, ExpressionType.PROPERTY, "the name of the wonderful player %player%", "%player%'s wonderful name");
		Skript.registerExpression(ExprDisplayNameOfPlayer.class, String.class, ExpressionType.PROPERTY, "great display name of player %player%", "%player%'s amazing display name");
		Skript.registerEffect(EffSendMessage.class, "send great message %string% to %player%");
		Skript.registerEvent("Amazing Player Level Change", SimpleEvent.class, PlayerLevelChangeEvent.class, "amazing player level change");
		
		//General Expressions
		Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "(full|total)[ ]time of %world%","%world%'s (full|total)[ ]time");
		//Skript.registerExpression(ExprCakeSlicesEaten.class, Integer.class, ExpressionType.PROPERTY, "[the] [(amount|number) of] (slices eaten|eaten slices) of %block%");
		
		//General Effects
		Skript.registerEffect(EffDisableAllPlugins.class, "disable all plugins","disable every plugin");
		
		//AAC
		if (getServer().getPluginManager().isPluginEnabled("AAC")) {
			getLogger().info("You've got AAC, wow. You are soooo rich! I'll collaborate!");
			Skript.registerCondition(CondIsBypassed.class, "[aac] %player%('s| is) bypass(ed|ing) aac");
			Skript.registerCondition(CondOnGround.class, "[aac] %player%('s| is) (on ground|not in air)");
			Skript.registerExpression(ExprAacPing.class, Integer.class, ExpressionType.PROPERTY, "aac (ping of %player%|%player%'s ping)", "[aac] (ping of %player%|%player%'s ping) by aac");
			Skript.registerEffect(EffReloadAAC.class, "aac reload [config[s]]","reload aac [config[s]]","reload config[s] of aac");
			Skript.registerEffect(EffReloadPermissionCache.class, "aac reload permission(s|[s] cache)","reload permission(s|[s] cache) of aac","reload aac's permission(s|[s] cache)");
			Skript.registerCondition(CondCheckEnabled.class, "[aac ](check %-hacktype%|%-hacktype% check) is (enabled|on|running)");
		}
		
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
