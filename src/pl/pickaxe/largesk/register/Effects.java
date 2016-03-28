package pl.pickaxe.largesk.register;

import ch.njol.skript.Skript;
import pl.pickaxe.largesk.aac.EffDisableCheck;
import pl.pickaxe.largesk.aac.EffEnableCheck;
import pl.pickaxe.largesk.aac.EffReloadAAC;
import pl.pickaxe.largesk.aac.EffReloadPermissionCache;
import pl.pickaxe.largesk.effects.EffDisableAllPlugins;
import pl.pickaxe.largesk.protocollib.EffMakePlayersSleep;

public class Effects {
	public void registerGeneral()
	{
		Skript.registerEffect(EffDisableAllPlugins.class, "disable all plugins","disable every plugin");
	}
	public void registerAAC()
	{
		Skript.registerEffect(EffReloadAAC.class, "aac reload [config[s]]","reload aac [config[s]]","reload config[s] of aac");
		Skript.registerEffect(EffReloadPermissionCache.class, "aac reload permission(s|[s] cache)","reload permission(s|[s] cache) of aac","reload aac['s] permission(s|[s] cache)");
		Skript.registerEffect(EffEnableCheck.class, "enable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
		Skript.registerEffect(EffDisableCheck.class, "disable ([hack[ ]]check %-hacktype%|%-hacktype% [hack[ ]]check)");
	}
	public void registerProtocolLib()
	{
		Skript.registerEffect(EffMakePlayersSleep.class, "(make|force) %player% sleep");
	}
}
