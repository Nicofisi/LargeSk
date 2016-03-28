package pl.pickaxe.largesk.register;

import ch.njol.skript.Skript;
import pl.pickaxe.largesk.aac.CondCheckEnabled;
import pl.pickaxe.largesk.aac.CondIsBypassed;
import pl.pickaxe.largesk.aac.CondOnGround;
import pl.pickaxe.largesk.skinsrestorer.CondPlayerHasSkin;

public class Conditions {
	public void registerGeneral()
	{
		
	}
	public void registerAAC()
	{
		Skript.registerCondition(CondIsBypassed.class, "[aac] %player%('s| is) bypass(ed by|ing) aac");
		Skript.registerCondition(CondOnGround.class, "[aac] %player%('s| is) on ground");
		Skript.registerCondition(CondCheckEnabled.class, "[aac] (check %-hacktype%|%-hacktype% check) is (enabled|on|running)");
	}
	public void registerSkinsRestorer()
	{
		Skript.registerCondition(CondPlayerHasSkin.class, "%offlineplayer% (has|have) [a] skin");
	}
}
