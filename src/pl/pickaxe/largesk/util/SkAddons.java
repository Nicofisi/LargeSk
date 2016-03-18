package pl.pickaxe.largesk.util;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SkAddons
{
	public static void logAddons()
	{
		String list = "";
		Integer num = 0;
		for (SkriptAddon addon : Skript.getAddons())
		{
			num++;
			list = list + addon.getName() + ", ";
			list = list.substring(0, list.length() - 2);
		}
		if (num > 1)
		{
			Xlog.logInfo("You are using " + num + " addons for Skript. A list of them: " + list);
		}
		else
		{
			Xlog.logInfo("You are using only " + num + " addon for Skript. And the name of it is " + list);
		}
	}
}