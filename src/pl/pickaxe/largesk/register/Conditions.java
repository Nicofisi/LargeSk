package pl.pickaxe.largesk.register;

import pl.pickaxe.largesk.aac.CondCheckEnabled;
import pl.pickaxe.largesk.aac.CondIsBypassed;
import pl.pickaxe.largesk.aac.CondOnGround;
import pl.pickaxe.largesk.conditions.CondEvaluateCondition;
import pl.pickaxe.largesk.skinsrestorer.CondPlayerHasSkin;

public class Conditions {
  public void registerGeneral() {
    CondEvaluateCondition.register();
  }

  public void registerAAC() {
    CondIsBypassed.register();
    CondOnGround.register();
    CondCheckEnabled.register();
  }

  public void registerSkinsRestorer() {
    CondPlayerHasSkin.register();
  }
}
