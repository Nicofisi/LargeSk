package pl.pickaxe.largesk.register;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import me.konsolas.aac.api.HackType;
import pl.pickaxe.largesk.aac.ExprAacPing;
import pl.pickaxe.largesk.aac.ExprAacTps;
import pl.pickaxe.largesk.aac.ExprHack;
import pl.pickaxe.largesk.aac.ExprHackDescription;
import pl.pickaxe.largesk.aac.ExprViolationLevel;
import pl.pickaxe.largesk.effects.EffLagServer;
import pl.pickaxe.largesk.expressions.ExprFullTime;
import pl.pickaxe.largesk.expressions.ExprLinkAsk;
import pl.pickaxe.largesk.expressions.ExprLinkBing;
import pl.pickaxe.largesk.expressions.ExprLinkDuckDuckGo;
import pl.pickaxe.largesk.expressions.ExprLinkGoogle;
import pl.pickaxe.largesk.expressions.ExprNewChunk;
import pl.pickaxe.largesk.expressions.ExprOldChunk;
import pl.pickaxe.largesk.expressions.ExprPastebin;
import pl.pickaxe.largesk.expressions.ExprUrlDecode;
import pl.pickaxe.largesk.expressions.ExprUrlEncode;
import pl.pickaxe.largesk.skinsrestorer.ExprSkinOfPlayer;
import pl.pickaxe.largesk.util.LargeConfig;

public class Expressions {
	
	LargeConfig largeconfig = new LargeConfig();
	FileConfiguration config = largeconfig.getConfig();
	
	public void registerGeneral()
	{
		Skript.registerExpression(ExprFullTime.class, Long.class, ExpressionType.PROPERTY, "(full|total)[ ]time of %world%","%world%'s (full|total)[ ]time");
		Skript.registerExpression(ExprPastebin.class, String.class, ExpressionType.SIMPLE, "pastebin upload %string% [(named|[with] name) %-string%] [[with ]expire date %-string%] [[with ]paste (format|language) %-string%]");
		Skript.registerExpression(ExprLinkGoogle.class, String.class, ExpressionType.SIMPLE, "google link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkBing.class, String.class, ExpressionType.SIMPLE, "bing link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkDuckDuckGo.class, String.class, ExpressionType.SIMPLE, "duckduckgo link (of|to) [search] %string%");
		Skript.registerExpression(ExprLinkAsk.class, String.class, ExpressionType.SIMPLE, "ask link (of|to) [search] %string%");
		Skript.registerExpression(ExprUrlEncode.class, String.class, ExpressionType.SIMPLE, "url encoded %string%");
		Skript.registerExpression(ExprUrlDecode.class, String.class, ExpressionType.SIMPLE, "url decoded %string%");
		Skript.registerExpression(ExprNewChunk.class, Chunk.class, ExpressionType.SIMPLE, "new chunk");
		Skript.registerExpression(ExprOldChunk.class, Chunk.class, ExpressionType.SIMPLE, "old chunk");
		
		//Lag expression disable check
		if (config.getConfigurationSection("enable").getBoolean("lag"))
		{
			Skript.registerEffect(EffLagServer.class, "lag [the] server for %timespan%","(make|create) a %timespan% lag[[ ]spike]");
		}
		
	}
	public void registerAAC()
	{
		Skript.registerExpression(ExprAacTps.class, Double.class, ExpressionType.SIMPLE, "[aac] tps","tps (of|by) aac");
		Skript.registerExpression(ExprHack.class, HackType.class, ExpressionType.SIMPLE, "hack","cheat","violation");
		Skript.registerExpression(ExprHackDescription.class, String.class, ExpressionType.SIMPLE, "hack desc[ription]","cheat desc[ription]","violation desc[ription]");
		Skript.registerExpression(ExprHackDescription.class, String.class, ExpressionType.SIMPLE, "(hacks|cheats|violations) within last minute");
		Skript.registerExpression(ExprViolationLevel.class, Integer.class, ExpressionType.PROPERTY, "%player%['s][ aac] [hack[s]|violation[s]|cheat[s]] level of %hacktype%","[aac ] %hacktype% [hack[s]|violation[s]|cheat[s]] level of %player%","%player%'s vl of %hacktype%", "%hacktype% vl of %player%");
		Skript.registerExpression(ExprAacPing.class, Integer.class, ExpressionType.PROPERTY, "aac (ping of %player%|%player%'s ping)", "[aac] (ping of %player%|%player%'s ping) by aac");
	}
	public void registerSkinsRestorer()
	{
		Skript.registerExpression(ExprSkinOfPlayer.class, String.class, ExpressionType.PROPERTY, "skin of %offlineplayer%","%offlineplayer%'s skin");
	}
}
