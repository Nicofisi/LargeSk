package pl.pickaxe.largesk.register;

import pl.pickaxe.largesk.aac.EffDisableCheck;
import pl.pickaxe.largesk.aac.EffEnableCheck;
import pl.pickaxe.largesk.aac.EffReloadAAC;
import pl.pickaxe.largesk.aac.EffReloadPermissionCache;
import pl.pickaxe.largesk.effects.EffDisableAllPlugins;
import pl.pickaxe.largesk.effects.EffThrowNPE;

public class Effects {
  public void registerGeneral() {
    EffDisableAllPlugins.register();
    EffThrowNPE.register();
  }

  public void registerAAC() {
    EffReloadAAC.register();
    EffReloadPermissionCache.register();
    EffEnableCheck.register();
    EffDisableCheck.register();
  }
}
