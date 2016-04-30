package pl.pickaxe.largesk.expressions;

import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.util.Pastebin;

public class ExprPastebin extends SimpleExpression<String> {
  private Expression<String> textToPaste;
  private Expression<String> nameOfPaste;
  private Expression<String> expireDate;
  private Expression<String> pasteFormat;

  @Override
  public Class<? extends String> getReturnType() {
    return String.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
    textToPaste = (Expression<String>) expr[0];
    if (!((Expression<String>) expr[1] == null)) {
      nameOfPaste = (Expression<String>) expr[1];
    }
    if (!((Expression<String>) expr[2] == null)) {
      expireDate = (Expression<String>) expr[2];
    }
    if (!((Expression<String>) expr[3] == null)) {
      pasteFormat = (Expression<String>) expr[3];
    }
    return true;
  }

  @Override
  public String toString(@Nullable Event arg0, boolean arg1) {
    return null;
  }

  @Override
  @Nullable
  protected String[] get(Event e) {
    String ttp;
    String nop;
    String ed;
    String pf;

    ttp = textToPaste.getSingle(e);
    if (nameOfPaste == null) {
      nop = null;
    } else {
      nop = nameOfPaste.getSingle(e);
    }
    if (expireDate == null) {
      ed = null;
    } else {
      ed = expireDate.getSingle(e);
    }
    if (pasteFormat == null) {
      pf = null;
    } else {
      pf = pasteFormat.getSingle(e);
    }
    String test = "connection error";
    try {
      test = Pastebin.sendPost(ttp, nop, ed, pf);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return new String[] { test };
  }
  public static void register() {
    Skript.registerExpression(ExprPastebin.class, String.class, ExpressionType.SIMPLE,
        "pastebin upload %string% [(named|[with] name) %-string%] [[with ]expire date %-string%] [[with ]paste (format|language) %-string%]");
  }
}
