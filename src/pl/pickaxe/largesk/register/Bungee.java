package pl.pickaxe.largesk.register;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import pl.pickaxe.largesk.bungee.EffSendPluginMessage;
import pl.pickaxe.largesk.bungee.EvtPluginMessageReceived;
import pl.pickaxe.largesk.bungee.LargeMessenger;
import pl.pickaxe.largesk.util.Xlog;

public class Bungee {
  public void registerAll() {
    // Message
    Xlog.logInfo("You use BungeeCord! I love it <3");

    // Register the event below
    LargeMessenger msg = new LargeMessenger();
    msg.getMessenger().registerMessenger();

    // Effect
    Skript.registerEffect(EffSendPluginMessage.class, "proxy send %string% [to %-string%]");

    // The PluginMessageReceived Event
    Skript.registerEvent("Message Receive", SimpleEvent.class, EvtPluginMessageReceived.class,
        new String[] { "message [(receiv(e|ing)|get[ting])]" });

    // EvtPluginMessageReceived getMessage()
    EventValues.registerEventValue(EvtPluginMessageReceived.class, String.class,
        new Getter<String, EvtPluginMessageReceived>() {
          @Override
          public String get(EvtPluginMessageReceived event) {
            return event.getMessage();
          }
        }, 0);
  }
}
