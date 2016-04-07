package pl.pickaxe.largesk.effects;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.register.Register;
import pl.pickaxe.largesk.util.Xlog;
import uk.co.umbaska.UpdateChecker;

public class EffDisableUmbaskaUpdater extends Effect {

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if ( ! Register.isPluginOnServer("Umbaska"))
		{
			Skript.error("Umbaska is not enabled!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "disable umbaska update checker";
	}

	@Override
	protected void execute(Event arg0) {
		for (BukkitTask task : Bukkit.getScheduler().getPendingTasks())
		{
			Xlog.logInfo(((Integer) task.getTaskId()).toString() + task.getOwner().toString() + task.isSync());
			if (task.getClass() == UpdateChecker.class)
			{
				Xlog.logInfo("Hurra!");
			}
		}
	}
	public static void register()
	{
		Skript.registerEffect(EffDisableUmbaskaUpdater.class, "disable umbaska['s] update(r| checker)");
	}
}
