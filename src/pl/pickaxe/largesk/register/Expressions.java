package pl.pickaxe.largesk.register;

import org.bukkit.configuration.file.FileConfiguration;

import pl.pickaxe.largesk.aac.ExprAacPing;
import pl.pickaxe.largesk.aac.ExprAacTps;
import pl.pickaxe.largesk.aac.ExprHack;
import pl.pickaxe.largesk.aac.ExprHackDescription;
import pl.pickaxe.largesk.aac.ExprHackLastMinute;
import pl.pickaxe.largesk.aac.ExprViolationLevel;
import pl.pickaxe.largesk.effects.EffLagServer;
import pl.pickaxe.largesk.expressions.ExprPlayersFlying;
import pl.pickaxe.largesk.expressions.ExprPlayersInWorld;
import pl.pickaxe.largesk.expressions.ExprPlayersInsideVehicle;
import pl.pickaxe.largesk.expressions.ExprPlayersSleeping;
import pl.pickaxe.largesk.expressions.ExprFullTime;
import pl.pickaxe.largesk.expressions.ExprLinkAsk;
import pl.pickaxe.largesk.expressions.ExprLinkBing;
import pl.pickaxe.largesk.expressions.ExprLinkDuckDuckGo;
import pl.pickaxe.largesk.expressions.ExprLinkGoogle;
import pl.pickaxe.largesk.expressions.ExprNewChunk;
import pl.pickaxe.largesk.expressions.ExprOldChunk;
import pl.pickaxe.largesk.expressions.ExprPastebin;
import pl.pickaxe.largesk.expressions.ExprPlayersWithPermission;
import pl.pickaxe.largesk.expressions.ExprPlayersSneaking;
import pl.pickaxe.largesk.expressions.ExprUrlDecodedText;
import pl.pickaxe.largesk.expressions.ExprUrlEncodedText;
import pl.pickaxe.largesk.networkcoins.ExprNetworkCoins;
import pl.pickaxe.largesk.skinsrestorer.ExprSkinOfOfflinePlayer;
import pl.pickaxe.largesk.util.LargeConfig;
import pl.pickaxe.largesk.viaversion.ExprMinecraftClientVersion;

public class Expressions {

  LargeConfig largeconfig = new LargeConfig();
  FileConfiguration config = largeconfig.getConfig();

  public void registerGeneral() {
    ExprFullTime.register();
    ExprPastebin.register();
    ExprLinkGoogle.register();
    ExprLinkBing.register();
    ExprLinkDuckDuckGo.register();
    ExprLinkAsk.register();
    ExprUrlEncodedText.register();
    ExprUrlDecodedText.register();
    ExprNewChunk.register();
    ExprOldChunk.register();
    ExprPlayersWithPermission.register();
    ExprPlayersFlying.register();
    ExprPlayersSneaking.register();
    ExprPlayersSleeping.register();
    ExprPlayersInsideVehicle.register();
    ExprPlayersInWorld.register();
    // Lag expression disable check
    if (config.getConfigurationSection("enable").getBoolean("lag")) { 
      EffLagServer.register();
    }

  }

  public void registerAAC() {
    ExprAacTps.register();
    ExprHack.register();
    ExprHackDescription.register();
    ExprHackLastMinute.register();
    ExprViolationLevel.register();
    ExprAacPing.register();
  }

  public void registerSkinsRestorer() {
    ExprSkinOfOfflinePlayer.register();
  }

  public void registerViaVersion() {
    ExprMinecraftClientVersion.register();
  }

  public void registerNetworkCoins() {
    ExprNetworkCoins.register();
  }
}
