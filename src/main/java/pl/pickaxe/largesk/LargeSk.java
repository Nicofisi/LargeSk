package pl.pickaxe.largesk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.SyntaxElementInfo;

import pl.pickaxe.largesk.util.Xlog;
import pl.pickaxe.largesk.register.Register;
import pl.pickaxe.largesk.util.LargeConfig;
import pl.pickaxe.largesk.util.MetricsManager;
import pl.pickaxe.largesk.util.SkAddons;
import pl.pickaxe.largesk.util.Updater;

public class LargeSk extends JavaPlugin {

  public static boolean debug = false;

  public static LargeSk getPluginInstance() {
    return LargeSk.getPlugin(LargeSk.class);
  }

  @SuppressWarnings("unused")
  @Override
  public void onEnable() {

    // Construct
    LargeConfig largeconfig = new LargeConfig();
    Register register = new Register();
    Updater updater = new Updater();

    // Enabling timer
    long eTime = System.currentTimeMillis();
    Xlog.logInfo(ChatColor.YELLOW + "=== ENABLE " + ChatColor.GREEN + "START" + ChatColor.YELLOW + " ===");

    // Config
    largeconfig.load();
    largeconfig.loadBeta();

    // Registring Skript addon
    Skript.registerAddon(this);

    // Register Skript's stuff
    register.registerAll();

    // Register the command
    this.getCommand("largesk").setExecutor(new LargeSkCommand());

    // Enable Metrics with 5 seconds delay
    Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {

      @Override
      public void run() {
        MetricsManager metricsmanager = new MetricsManager();
        metricsmanager.enableMetrics();
      }
    }, 100);

    // You see
    Xlog.logInfo("Share your problems and ideas on https://github.com/Nicofisi/LargeSk/issues");

    // Also obvious
    Bukkit.getScheduler().runTaskAsynchronously(this, SkAddons.logAddons());
    Xlog.logInfo("I will show you a list of your Skript addons as soon as everything loads up.");

    // Debug
    if (debug && false) {
      for (SyntaxElementInfo<? extends Condition> cond : Skript.getConditions()) {
        Xlog.logInfo("");
        for (String pat : cond.patterns) {
          Xlog.logInfo(pat);
        }
      }
    }

    // Announcing how much time enabling took
    eTime = System.currentTimeMillis() - eTime;
    Xlog.logInfo(ChatColor.YELLOW + "=== ENABLE " + ChatColor.GREEN + "COMPLETE" + ChatColor.YELLOW
        + " (Took " + ChatColor.LIGHT_PURPLE + eTime + "ms" + ChatColor.YELLOW + ") ===");

    // Update check schedule
    updater.scheduleUpdates();
  }

  // On disable
  @Override
  public void onDisable() {
    if (debug)
      Xlog.logInfo("Cancelling tasks..");
    Bukkit.getScheduler().cancelTasks(this);
    MetricsManager metricsmanager = new MetricsManager();
    metricsmanager.disableMetrics();
    Xlog.logInfo("Bye, Senpai!");
  }
}
