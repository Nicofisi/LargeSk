package pl.pickaxe.largesk.register;

import ch.njol.skript.Skript;
import pl.pickaxe.largesk.aac.CondCheckEnabled;
import pl.pickaxe.largesk.aac.CondIsBypassed;
import pl.pickaxe.largesk.aac.CondOnGround;
import pl.pickaxe.largesk.conditions.CondEvaluateCondition;
import pl.pickaxe.largesk.skinsrestorer.CondPlayerHasSkin;

public class Conditions {
	public void registerGeneral()
	{
		CondEvaluateCondition.register();
	}
	public void registerAAC()
	{
		Skript.registerCondition(CondIsBypassed.class, "[aac] %player%('s| is) bypass(ed by|ing) aac");
		Skript.registerCondition(CondOnGround.class, "[aac] %player%('s| is) on ground");
		CondCheckEnabled.register();
	}
	public void registerSkinsRestorer()
	{
		CondPlayerHasSkin.register();
	}
}
