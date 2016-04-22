package pl.pickaxe.largesk.util;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;

import pl.pickaxe.largesk.events.EvtConsoleLog;


public final class ConsoleFilter implements Filter {

  @Override
  public boolean isLoggable(LogRecord record) {
    EvtConsoleLog evt = new EvtConsoleLog(record.getMessage());
    Bukkit.getServer().getPluginManager().callEvent(evt);
    if (evt.isCancelled())
      return false;
    return true;
  }

}
